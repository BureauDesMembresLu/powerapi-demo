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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keyboardplaying.demo.OsInformation;
import org.keyboardplaying.demo.controller.ProxyControllerTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link MonitorFactoryBean}.
 *
 * @author Cyrille Chopelet
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonitorFactoryBeanTestConfiguration.class)
public class MonitorFactoryBeanTest {

    @Autowired
    private MonitorFactoryBean factory;

//    @Before
//    public void setUp() {
//        final OsInformation os = new OsInformation();
//        os.init();
//        this.factory = new MonitorFactoryBean(os, 500, "ms");
//    }
//
//    @After public void tearDown() {
//        this.factory.tearDown();
//    }

    @Test
    public void testInstanceIsSingleton() throws Exception {
        Monitor instance1 = this.factory.getObject();
        Monitor instance2 = this.factory.getObject();
        assertTrue("Factory is not creating a singleton", instance1 == instance2);
    }
}
