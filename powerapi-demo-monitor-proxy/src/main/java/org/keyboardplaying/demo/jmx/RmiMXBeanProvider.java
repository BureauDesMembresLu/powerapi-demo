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
package org.keyboardplaying.demo.jmx;

import org.keyboardplaying.demo.utils.JMXUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

/**
 * This class provides utilities to get MXBeans over RMI.
 *
 * @author Cyrille Chopelet
 */
@Component
public class RmiMXBeanProvider implements MXBeanProvider {

    @Value("${monitored.jmx.rmi.host}")
    private String host;

    @Value("${monitored.jmx.rmi.port}")
    private int port;

    @Value("${monitored.jmx.rmi.endpoint}")
    private String endpoint;

    private JMXConnector connector;
    private MBeanServerConnection connection;

    @PostConstruct
    public void connect() throws IOException {
        JMXServiceURL url = new JMXServiceURL(JMXUtils.createJMXUrl(host, port, endpoint));
        connector = JMXConnectorFactory.connect(url);
        connection = connector.getMBeanServerConnection();
    }

    @Override
    public <T> T getMXBeanProxy(String name, Class<T> klass) throws MalformedObjectNameException {
        return JMX.newMXBeanProxy(connection, new ObjectName(name), klass);
    }

    @PreDestroy
    public void disconnect() throws IOException {
        connector.close();
    }
}
