/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keyboardplaying.demo.controller;

import org.keyboardplaying.demo.OsInformation;
import org.keyboardplaying.demo.jmx.MXBeanProvider;
import org.keyboardplaying.demo.monitor.ListStoringDisplay;
import org.keyboardplaying.demo.monitor.Monitor;
import org.keyboardplaying.demo.utils.JMXUtils;
import org.keyboardplaying.demo.utils.RuntimeInformationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import scala.concurrent.duration.FiniteDuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.MalformedObjectNameException;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.RuntimeMXBean;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ProxyController {

    private static final String UNIT_WATTS = "W";

    private final MXBeanProvider jmxProvider;
    private final OsInformation os;
    private final RestTemplate restTemplate;

    @Value("${monitored.url.base}")
    private String redirectBase;

    private FiniteDuration monitoringInterval;

    private Monitor monitor;

    @Autowired
    public ProxyController(MXBeanProvider jmxProvider, OsInformation os, RestTemplate restTemplate) {
        this.jmxProvider = jmxProvider;
        this.os = os;
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setMonitoringInterval(@Value("${monitoring.interval.length}") int length, @Value("${monitoring.interval.unit}") String unit) {
        this.monitoringInterval = FiniteDuration.apply(length, unit);
    }

    @PostConstruct
    protected void setMonitorUp() throws MalformedObjectNameException {
        RuntimeMXBean runtimeMXBean = jmxProvider.getMXBeanProxy(JMXUtils.JMX_NAME_RUNTIME, RuntimeMXBean.class);
        monitor = new Monitor(RuntimeInformationUtils.getPidFromJVMName(runtimeMXBean.getName()), os, monitoringInterval);
    }

    @PreDestroy
    protected void destroyMonitor() {
        monitor.shutdown();
    }

    @RequestMapping(value = "**", method = {RequestMethod.POST, RequestMethod.PUT})
    public ProxiedResponse proxifyRequestsWithBody(HttpServletRequest request, @RequestHeader HttpHeaders headers, @RequestBody Object body) throws URISyntaxException {
        return proxifyRequest(request, headers, body);
    }

    @RequestMapping(value = "**")
    public ProxiedResponse proxifyRequestsWithoutBody(HttpServletRequest request, @RequestHeader HttpHeaders headers) throws URISyntaxException {
        return proxifyRequest(request, headers, null);
    }

    private ProxiedResponse proxifyRequest(HttpServletRequest request, @RequestHeader HttpHeaders headers, @RequestBody Object body) throws URISyntaxException {
        final RequestEntity<Object> requestEntity = convertToRequestEntity(request, headers, body);

        // Start monitoring
        long startTime = System.currentTimeMillis();
        ListStoringDisplay powerDisplay = new ListStoringDisplay();
        monitor.startMonitoring(powerDisplay);

        // call remote service
        final ResponseEntity<Object> proxied = restTemplate.exchange(requestEntity, Object.class);

        // Stop monitoring
        long stopTime = System.currentTimeMillis();
        monitor.stopMonitoring();

        // Return service result + monitoring information
        final ProxiedResponse response = new ProxiedResponse();
        response.setProxied(proxied.getBody());
        response.setPower(new MonitoredValue(powerDisplay.getPowers(), UNIT_WATTS));
        response.setProcessingTime(stopTime - startTime);

        return response;
    }

    private <T> RequestEntity<T> convertToRequestEntity(HttpServletRequest request, HttpHeaders headers, T body) throws URISyntaxException {
        // Build proxied URL
        final StringBuilder redirectUrl = new StringBuilder(redirectBase)
                .append(request.getRequestURI());
        final String queryString = request.getQueryString();
        if (queryString != null) {
            redirectUrl.append("?").append(queryString);
        }

        // TODO enhancement: transmit headers and request body to make this a real proxy
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        return new RequestEntity<>(body, headers, httpMethod, new URI(redirectUrl.toString()));
    }
}
