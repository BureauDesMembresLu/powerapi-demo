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

/**
 * An interface for beans providing the MXBean instances required for monitoring.
 *
 * @author Cyrille Chopelet
 */
public interface MXBeanProvider {
    /**
     * Returns the specified MXBean.
     *
     * @param name  the name of the wanted MXBean
     * @param klass the class of the desired MXBean
     * @param <T>   the type of the desired MXBean
     * @return the desired MXBean
     * @throws MalformedObjectNameException if the supplied name is malformed
     */
    <T> T getMXBeanProxy(String name, Class<T> klass) throws MalformedObjectNameException;
}
