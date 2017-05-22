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

import org.keyboardplaying.demo.utils.CommandTerminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The bean in charge of clearing the repository or injecting test data.
 *
 * @author Cyrille Chopelet
 */
// This class should use the {@link CommandTerminal} for output only. A logger would be cleaner (maybe later, or propose
// a pull request if you'd like).
@Component
public class PersonInjector {

    private final PeopleRepository repository;
    private final PersonGenerator generator;

    @Autowired
    public PersonInjector(PeopleRepository repository, PersonGenerator generator) {
        this.repository = repository;
        this.generator = generator;
    }

    public void wipeRepositoryClean(CommandTerminal out) {
        out.println("Cleaning under progress...");
        repository.deleteAll();
        out.println("Success! Repository has been cleaned");
    }

    public void injectRandomPeople(int nbToInsert, CommandTerminal out) {
        for (int i = 0; i < nbToInsert; ++i) {
            repository.save(generator.randomPerson());
            if (i % 1000 == 0) {
                out.println();
            } else if (i % 100 == 0) {
                out.print("|");
            } else if (i % 10 == 0) {
                out.print(".");
            }
        }
    }
}
