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
package org.keyboardplaying.demo.people;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.keyboardplaying.demo.utils.CommandTerminal;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link PersonGenerator}.
 *
 * @author Cyrille Chopelet
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PersonInjector.class, PersonGenerator.class})
public class PersonInjectorTest {

    @Autowired
    private PersonInjector injector;

    @MockBean
    private PeopleRepository repository;

    @Mock
    private CommandTerminal term;

    @Test
    public void testWipeRepositoryClean() {
        injector.wipeRepositoryClean(term);
        verify(repository, times(1)).deleteAll();
    }

    @Test
    public void testInjectRandomPeople() {
        injector.injectRandomPeople(100, term);
        verify(repository, times(100)).save(any(Person.class));
    }
}
