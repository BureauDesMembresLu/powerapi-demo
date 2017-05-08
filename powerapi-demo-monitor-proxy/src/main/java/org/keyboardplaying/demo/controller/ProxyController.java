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

import org.keyboardplaying.demo.JMXClient;
import org.keyboardplaying.demo.OsInformation;
import org.keyboardplaying.demo.monitor.Monitor;
import org.keyboardplaying.utils.JMXUtils;
import org.keyboardplaying.utils.RuntimeInformationUtils;
import org.powerapi.reporter.ConsoleDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.management.MalformedObjectNameException;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.RuntimeMXBean;

@RestController
public class ProxyController {

    @Value("${monitored.url.base}")
    private String redirectBase;

    @Autowired
    private JMXClient jmxClient;

    @Autowired
    private OsInformation os;

    private Monitor monitor;

    @PostConstruct
    public void initMonitor() throws MalformedObjectNameException {
        RuntimeMXBean runtimeMXBean = jmxClient.getMXBeanProxy(JMXUtils.JMX_NAME_RUNTIME, RuntimeMXBean.class);
        monitor = new Monitor(RuntimeInformationUtils.getPidFromJVMName(runtimeMXBean.getName()), os, new ConsoleDisplay());
    }

    @RequestMapping("**")
    public String proxyGetRequest(HttpServletRequest request) {
        final StringBuilder redirectUrl = new StringBuilder(redirectBase)
                .append(request.getRequestURI());
        final String queryString = request.getQueryString();
        if (queryString != null)
            redirectUrl.append("?").append(queryString);

        // FIXME start monitoring
        // FIXME call remote service
        // FIXME stop monitoring
        // FIXME return service result + monitoring information
        return redirectUrl.toString();
    }
}