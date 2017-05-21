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
package org.keyboardplaying.demo.demo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keyboardplaying.demo.people.PeopleRepository;
import org.keyboardplaying.demo.people.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link DemoController}.
 *
 * @author Cyrille Chopelet
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoControllerTest {

    @Autowired
    private PeopleRepository repository;

    @Autowired
    private DemoController controller;

    @Test
    public void testFetchCyrilsWithOptimizedCall() {
        List<String> result = controller.fetchCyrils();
        assertEquals(1, result.size());
        assertEquals("Cyril Chopelet", result.get(0));
    }

    @Test
    public void testFetchCyrilsWithoutParameters() {
        String result = controller.fetchCyrils(null, null);
        assertEquals("[\"Cyril Chopelet\"]", result);
    }
    @Test
    public void testFetchCyrilsWithParameters() {
        String result = controller.fetchCyrils(AppendingSolution.STRING_BUILDER, IteratingSolution.FOREACH);
        assertEquals("[\"Cyril Chopelet\"]", result);
    }
}
