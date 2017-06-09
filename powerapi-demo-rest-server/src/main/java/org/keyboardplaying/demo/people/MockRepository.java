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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyrille Chopelet
 */
@Component
public class MockRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MockRepository.class);

    private final List<Person> people = new ArrayList<>();

    @Value("${sample.maxAge}")
    private int maxAge;
    @Value("${sample.size}")
    private int size;

    @PostConstruct
    public void loadMockPeople() throws IOException, URISyntaxException {
        LOG.info("Injecting test data (0%)");
        final int step = Math.max(size / 20, 1);
        PersonGenerator generator = new PersonGenerator(this.maxAge);
        for (int i = 0; i < size; ++i) {
            people.add(generator.generateRandomPerson());
            if (i % step == 0) {
                LOG.info("Injecting test data ({}%)", i / step);
            }
        }
    }

    public List<Person> findAll() {
        return this.people;
    }
}
