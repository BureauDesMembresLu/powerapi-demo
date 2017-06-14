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
package org.keyboardplaying.demo.monitor;

import org.keyboardplaying.demo.OsInformation;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import scala.concurrent.duration.FiniteDuration;

/**
 * A factory for building the {@link Monitor}.
 *
 * @author Cyrille Chopelet
 */
public class MonitorFactoryBean extends AbstractFactoryBean<Monitor> {

    private final OsInformation os;
    private final FiniteDuration monitoringInterval;

    private Monitor instance;

    public MonitorFactoryBean(OsInformation os, int intervalLength, String intervalUnit) {
        super();
        this.setSingleton(true);

        this.os = os;
        this.monitoringInterval = FiniteDuration.apply(intervalLength, intervalUnit);
    }

    @Override
    public Class<?> getObjectType() {
        return Monitor.class;
    }

    @Override
    protected Monitor createInstance() {
        this.instance = new Monitor(os, monitoringInterval);
        return this.instance;
    }

    public void tearDown() {
        if (this.instance != null) {
            this.instance.shutdown();
        }
    }
}
