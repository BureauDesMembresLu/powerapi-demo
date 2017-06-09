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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

/**
 * This bean loads a list of common first and last names in the United States and generates random persons based on this
 * data.
 *
 * @author Cyrille Chopelet
 */
public class PersonGenerator {

    static final int CURRENT_YEAR = LocalDate.now().getYear();

    private final int maxAge;
    private final Random random = new Random();

    private List<String> maleFirstNames;
    private List<String> femaleFirstNames;
    private List<String> lastNames;

    public PersonGenerator(int maxAge) throws IOException, URISyntaxException {
        this.maxAge = maxAge;

        maleFirstNames = loadFile("male_firstnames.txt");
        femaleFirstNames = loadFile("female_firstnames.txt");
        lastNames = loadFile("lastnames.txt");
    }

    private List<String> loadFile(String fileName) throws IOException, URISyntaxException {
        List<String> result = new ArrayList<>();

        URI fileUri = this.getClass().getResource("/people/" + fileName).toURI();
        try (Scanner scanner = new Scanner(new File(fileUri))) {
            while (scanner.hasNext()) {
                result.add(scanner.next());
            }
        }

        return result;
    }

    public Person generateRandomPerson() {
        Person.Gender gender = pickRandomGender();
        String firstName = pickRandomName(gender == Person.Gender.FEMALE ? femaleFirstNames : maleFirstNames);
        String lastName = pickRandomName(lastNames);
        return new Person(UUID.randomUUID().toString(), firstName, lastName, gender, generateRandomBirthDate());
    }

    private String pickRandomName(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    private Person.Gender pickRandomGender() {
        return random.nextBoolean() ? Person.Gender.FEMALE : Person.Gender.MALE;
    }

    private LocalDate generateRandomBirthDate() {
        int year = CURRENT_YEAR - random.nextInt(maxAge);
        int month = random.nextInt(Month.DECEMBER.getValue() - 1) + 1;
        int day = random.nextInt(Month.of(month).length(Year.isLeap(year)) - 1) + 1;
        return LocalDate.of(year, month, day);
    }
}
