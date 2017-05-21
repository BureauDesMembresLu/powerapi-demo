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

import org.keyboardplaying.demo.execution.concatenation.Appender;
import org.keyboardplaying.demo.execution.iteration.Iterator;
import org.keyboardplaying.demo.people.PeopleRepository;
import org.keyboardplaying.demo.people.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * A controller to demonstrate the difference of performance and efficience of several approaches over the same task.
 *
 * @author Cyrille Chopelet
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    private static final String SEARCH_FIRST_NAME_CYRIL = "Cyril";

    private PeopleRepository repository;

    @Autowired
    public DemoController(PeopleRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/fetch-cyrils-as-should-be")
    public List<String> fetchCyrils() {
        List<Person> cyrils = repository.findAllByFirstName(SEARCH_FIRST_NAME_CYRIL);
        List<String> result = new ArrayList<>(cyrils.size());

        for (Person cyril : cyrils) {
            result.add(cyril.getFirstName() + ' ' + cyril.getLastName());
        }

        return result;
    }

    @RequestMapping(value = "/fetch-cyrils/{appending}/{iterating}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String fetchCyrils(@PathVariable("appending") AppendingSolution appendingSolution,
                              @PathVariable("iterating") IteratingSolution iteratingSolution) {
        // Prepare tools to build the list
        Appender appender = (
                appendingSolution == null
                        ? AppendingSolution.PLAIN_STRING_APPENDING
                        : appendingSolution
        ).getAppender();
        FirstNameFinderJsonArrayBuilder builder = new FirstNameFinderJsonArrayBuilder(SEARCH_FIRST_NAME_CYRIL, appender);
        Iterator<Person> iterator = (
                iteratingSolution == null
                        ? IteratingSolution.METHOD_IN_CONDITION
                        : iteratingSolution
        ).getIterator();

        return findAndBuildArrayString(appender, builder, iterator);
    }

    private String findAndBuildArrayString(Appender appender, FirstNameFinderJsonArrayBuilder builder, Iterator<Person> iterator) {
        List<Person> allPeople = repository.findAll();

        builder.initialize();
        iterator.iterate(allPeople, builder);
        builder.terminate();

        return appender.getResult();
    }
}
