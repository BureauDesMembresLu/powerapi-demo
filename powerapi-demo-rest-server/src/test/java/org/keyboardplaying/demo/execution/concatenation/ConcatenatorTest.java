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
package org.keyboardplaying.demo.execution.concatenation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A generic testing class for all {@link Appender} implementations.
 *
 * @param <C> the type of {@link Appender} being tested
 * @author Cyrille Chopelet
 */
public abstract class ConcatenatorTest<C extends Appender> {

    protected abstract C makeNewConcatenator();

    @Test
    public void testEmpty() {
        C tested = this.makeNewConcatenator();
        assertEquals("", tested.getResult());
    }

    @Test
    public void testAppend() {
        C tested = this.makeNewConcatenator();
        tested.append("1");
        tested.append("2").append("3");
        assertEquals("123", tested.getResult());
    }
}
