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

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link Person}.
 *
 * @author Cyrille Chopelet
 */
public class PersonTest {
    @Test
    public void testConstructorAndGetters() {
        String uuid = UUID.randomUUID().toString();
        String firstName = "Cyrille";
        String lastName = "Chopelet";
        Person.Gender gender = Person.Gender.MALE;
        LocalDate birthDate = LocalDate.of(1985, Month.OCTOBER, 24);

        Person person = new Person(uuid, firstName, lastName, gender, birthDate);
        assertEquals(uuid, person.getUuid());
        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(gender, person.getGender());
        assertEquals(birthDate, person.getBirthDate());
    }
}
