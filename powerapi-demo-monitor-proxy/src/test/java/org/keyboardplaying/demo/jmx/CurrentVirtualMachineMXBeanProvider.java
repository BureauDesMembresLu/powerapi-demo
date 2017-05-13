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

import javax.management.MalformedObjectNameException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * A {@link MXBeanProvider} that uses the current virtual machine instead of remote MXBeans.
 *
 * @author Cyrille Chopelet
 */
public class CurrentVirtualMachineMXBeanProvider implements MXBeanProvider {

    @Override
    public <T> T getMXBeanProxy(String name, Class<T> klass) throws MalformedObjectNameException {
        if (RuntimeMXBean.class.equals(klass)) {
            return (T) ManagementFactory.getRuntimeMXBean();
        }
        throw new MalformedObjectNameException("MXBean <" + klass.getName() + "> does not exist.");
    }
}