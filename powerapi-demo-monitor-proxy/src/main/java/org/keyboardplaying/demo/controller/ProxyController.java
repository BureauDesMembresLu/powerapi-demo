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

import javax.management.MalformedObjectNameException;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.RuntimeMXBean;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This proxy transmits the request to the monitored server and executes it with the possibility of repeating the call
 * in order to have a better view of how much power this particular call consumes.
 * <p/>
 * This is a demonstration proxy, not production ready.
 *
 * @author Cyrille Chopelet
 */
@RestController
public class ProxyController {

    private final RestTemplate restTemplate;
    private final Monitor monitor;
    private final RuntimeMXBean runtimeMXBean;

    @Value("${monitored.url.base}")
    private String redirectBase;

    @Autowired
    public ProxyController(RestTemplate restTemplate, Monitor monitor, MXBeanProvider jmxProvider) throws MalformedObjectNameException {
        this.restTemplate = restTemplate;
        this.monitor = monitor;
        this.runtimeMXBean = jmxProvider.getMXBeanProxy(JMXUtils.JMX_NAME_RUNTIME, RuntimeMXBean.class);
    }

    @RequestMapping(value = "proxy/{nbIterations}/**", method = {RequestMethod.POST, RequestMethod.PUT})
    public ProxiedResponse proxifyRequestsWithBody(HttpServletRequest request, @PathVariable("nbIterations") int nbIterations, @RequestHeader HttpHeaders headers, @RequestBody Object body) throws URISyntaxException {
        return proxifyRequest(request, nbIterations, headers, body);
    }

    @RequestMapping(value = "proxy/{nbIterations}/**")
    public ProxiedResponse proxifyRequestsWithoutBody(HttpServletRequest request, @PathVariable("nbIterations") int nbIterations, @RequestHeader HttpHeaders headers) throws URISyntaxException {
        return proxifyRequest(request, nbIterations, headers, null);
    }

    private ProxiedResponse proxifyRequest(HttpServletRequest request, int nbIterations, @RequestHeader HttpHeaders headers, @RequestBody Object body) throws URISyntaxException {
        final RequestEntity<Object> requestEntity = convertToRequestEntity(request, headers, body);

        // Start monitoring
        long startTime = System.currentTimeMillis();
        ListStoringDisplay powerDisplay = new ListStoringDisplay();
        monitor.startMonitoring(RuntimeInformationUtils.getPidFromJVMName(runtimeMXBean.getName()), powerDisplay);

        // call remote service
        final int loopIterations = nbIterations - 1;
        for (int i = 0; i < loopIterations; ++i) {
            restTemplate.exchange(requestEntity, Object.class);
        }
        ResponseEntity<Object> proxied = restTemplate.exchange(requestEntity, Object.class);

        // Stop monitoring
        long stopTime = System.currentTimeMillis();
        monitor.stopMonitoring();

        // Return service result + monitoring information
        final ProxiedResponse response = new ProxiedResponse();
        response.setProxied(proxied.getBody());
        response.setPower(powerDisplay.getPowers());
        response.setProcessingTime(stopTime - startTime);

        return response;
    }

    private <T> RequestEntity<T> convertToRequestEntity(HttpServletRequest request, HttpHeaders headers, T body) throws URISyntaxException {
        // Build proxied URL
        final StringBuilder redirectUrl = new StringBuilder(redirectBase)
                .append(request.getRequestURI().replaceFirst("^/proxy/\\d+/", "/"));
        final String queryString = request.getQueryString();
        if (queryString != null) {
            redirectUrl.append("?").append(queryString);
        }

        // TODO enhancement: transmit headers and request body to make this a real proxy
        final HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        return new RequestEntity<>(body, headers, httpMethod, new URI(redirectUrl.toString()));
    }
}
