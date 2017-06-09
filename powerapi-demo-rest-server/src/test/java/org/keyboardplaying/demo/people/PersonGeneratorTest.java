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

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link PersonGenerator}.
 *
 * @author Cyrille Chopelet
 */
public class PersonGeneratorTest {

    private static final int MAX_AGE = 100;

    private PersonGenerator generator;

    @Before
    public void setUp() throws IOException, URISyntaxException {
        generator = new PersonGenerator(MAX_AGE);
    }

    @Test
    public void testRandomPerson() {
        final int earliestPossibleBirthYear = PersonGenerator.CURRENT_YEAR - MAX_AGE;
        final LocalDate earliestPossibleBirthDate = LocalDate.of(earliestPossibleBirthYear, Month.JANUARY, 1);


        final Person person = generator.generateRandomPerson();

        assertEquals(person.getGender() == Person.Gender.FEMALE ? "Jane" : "John", person.getFirstName());
        assertEquals("Smith", person.getLastName());
        assertTrue(earliestPossibleBirthDate.isBefore(person.getBirthDate()));
    }
}
