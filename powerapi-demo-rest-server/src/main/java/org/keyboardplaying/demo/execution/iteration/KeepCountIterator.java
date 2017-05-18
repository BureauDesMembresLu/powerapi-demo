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

import java.util.List;

/**
 * Implementation of the {@link Iterator} that relies on index but saves the length of the list before looping.
 *
 * @param <T> the type of elements in the list being iterated upon
 * @author Cyrille Chopelet
 */
public class KeepCountIterator<T> implements Iterator<T> {
    @Override
    public void iterate(List<T> list, ElementProcessor<T> executor) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            T element = list.get(i);
            executor.process(element);
        }
    }
}
