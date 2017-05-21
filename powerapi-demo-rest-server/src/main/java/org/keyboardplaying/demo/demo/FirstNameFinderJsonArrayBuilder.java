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
import org.keyboardplaying.demo.execution.iteration.ElementProcessor;
import org.keyboardplaying.demo.people.Person;

/**
 * Builds a JSON array by appending bits of String together (willingly inefficient). Only the people with the supplied
 * needle as first name will be included inside the array.
 *
 * @author Cyrille Chopelet
 */
public class FirstNameFinderJsonArrayBuilder implements ElementProcessor<Person> {

    private final String needle;
    private final Appender appender;
    private boolean first = true;

    public FirstNameFinderJsonArrayBuilder(String needle, Appender appender) {
        this.needle = needle;
        this.appender = appender;
    }

    public void initialize() {
        appender.append("[");
    }

    @Override
    public void process(Person element) {
        if (needle.equals(element.getFirstName())) {
            if (!first) {
                appender.append(",");
            } else {
                first = false;
            }
            appender.append("\"")
                    .append(element.getFirstName())
                    .append(" ")
                    .append(element.getLastName())
                    .append("\"");
        }
    }

    public void terminate() {
        appender.append("]");
    }
}
