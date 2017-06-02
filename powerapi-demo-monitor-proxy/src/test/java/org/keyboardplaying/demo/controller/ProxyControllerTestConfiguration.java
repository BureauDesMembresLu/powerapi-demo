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
import org.keyboardplaying.demo.jmx.CurrentVirtualMachineMXBeanProvider;
import org.keyboardplaying.demo.jmx.MXBeanProvider;
import org.keyboardplaying.demo.monitor.Monitor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.management.MalformedObjectNameException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * A test configuration to use local MXBeans instead of RMI connection.
 *
 * @author Cyrille Chopelet
 */
@Configuration
@EnableAutoConfiguration
public class ProxyControllerTestConfiguration {

    @MockBean
    public Monitor monitor;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ProxiedController proxied() {
        return new ProxiedController();
    }

    @Bean
    public MXBeanProvider runtimeMXBean() {
        return new CurrentVirtualMachineMXBeanProvider();
    };

    @Bean
    public ProxyController proxy(RestTemplate restTemplate, MXBeanProvider mxBeanProvider) throws MalformedObjectNameException {
        return new ProxyController(restTemplate, monitor, mxBeanProvider);
    }
}
