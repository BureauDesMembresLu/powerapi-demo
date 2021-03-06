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
package org.keyboardplaying.demo;

import org.keyboardplaying.demo.utils.JMXUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

import javax.management.MalformedObjectNameException;

/**
 * A configuration to make JMX remotely available.
 *
 * @author Cyrille Chopelet
 */
@Configuration
public class RmiConfiguration {

    @Value("${jmx.rmi.host}")
    private String host;

    @Value("${jmx.rmi.port}")
    private int port;

    @Value("${jmx.rmi.endpoint}")
    private String endpoint;

    @Bean
    public RmiRegistryFactoryBean rmiRegistry() {
        final RmiRegistryFactoryBean rmiRegistry = new RmiRegistryFactoryBean();
        rmiRegistry.setPort(port);
        rmiRegistry.setAlwaysCreate(true);
        return rmiRegistry;
    }

    @Bean
    @DependsOn("rmiRegistry")
    public ConnectorServerFactoryBean connectorServerFactoryBean() throws MalformedObjectNameException {
        final ConnectorServerFactoryBean factory = new ConnectorServerFactoryBean();
        factory.setObjectName("connector:name=rmi");
        factory.setServiceUrl(JMXUtils.createJMXUrl(host, port, endpoint));
        return factory;
    }
}
