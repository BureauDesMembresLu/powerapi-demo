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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Test configuration for {@link MonitorFactoryBean}.
 *
 * @author Cyrille Chopelet
 */
@Configuration
public class MonitorFactoryBeanTestConfiguration {

    @Bean
    public OsInformation os() {
        return new OsInformation();
    }

    @Bean(destroyMethod = "tearDown")
    public MonitorFactoryBean monitor(OsInformation os, @Value("${monitoring.interval.length}") int intervalLength, @Value("${monitoring.interval.unit}") String intervalUnit) {
        return new MonitorFactoryBean(os, intervalLength, intervalUnit);
    }
}
