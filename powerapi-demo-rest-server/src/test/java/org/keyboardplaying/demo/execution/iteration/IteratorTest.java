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
package org.keyboardplaying.demo.execution.iteration;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A generic testing class for all {@link Iterator} implementations.
 *
 * @param <I> the type of {@link Iterator} being tested
 * @author Cyrille Chopelet
 */
public abstract class IteratorTest<I extends Iterator<String>> {

    private static final class ToListAppender implements ElementProcessor<String> {

        private final List<String> elements = new ArrayList<>();

        @Override
        public void process(String element) {
            elements.add(element);
        }

        public List<String> getProcessedElements() {
            return this.elements;
        }
    }

    protected abstract I makeNewIterator();

    @Test
    public void testEmptyList() {
        final ToListAppender processor = new ToListAppender();
        I tested = this.makeNewIterator();
        tested.iterate(new ArrayList<>(), processor);
        assertTrue(processor.getProcessedElements().isEmpty());
    }

    @Test
    public void testNonEmptyList() {
        final ToListAppender processor = new ToListAppender();
        I tested = this.makeNewIterator();
        List<String> list = Arrays.asList("1", "2", "3");
        tested.iterate(list, processor);

        List<String> processed = processor.getProcessedElements();
        int size = list.size();
        assertEquals(size, processed.size());
        for (int i = 0; i < size; i++) {
            assertEquals(list.get(i), processed.get(i));
        }
    }
}
