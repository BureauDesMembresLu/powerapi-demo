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

import org.keyboardplaying.demo.execution.iteration.*;
import org.keyboardplaying.demo.people.Person;

/**
 * The possible solutions for iterating over a {@link java.util.List} for demonstration.
 *
 * @author Cyrille Chopelet
 */
public enum IteratingSolution {
    METHOD_IN_CONDITION {
        @Override
        public Iterator<Person> getIterator() {
            return new MethodInConditionIterator<>();
        }
    },
    METHOD_BEFORE_CONDITION_IPP {
        @Override
        public Iterator<Person> getIterator() {
            return new KeepCountIterator<>();
        }
    },
    METHOD_BEFORE_CONDITION_PPI {
        @Override
        public Iterator<Person> getIterator() {
            return new KeepCountPPIterator<>();
        }
    },
    ITERATOR {
        @Override
        public Iterator<Person> getIterator() {
            return new IteratorIterator<>();
        }
    },
    FOREACH {
        @Override
        public Iterator<Person> getIterator() {
            return new MethodInConditionIterator<>();
        }
    };

    public abstract Iterator<Person> getIterator();
}
