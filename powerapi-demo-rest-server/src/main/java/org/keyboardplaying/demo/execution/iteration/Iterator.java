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
 * This interface describes possible implementations for iterating over a {@link List}.
 * <p/>
 * An {@link ElementProcessor} should be passed in order to keep the iteration and the processing of each element
 * separate.
 *
 * @param <T> the type of elements in the list being iterated upon
 * @author Cyrille Chopelet
 */
public interface Iterator<T> {

    /**
     * Iterates over a {@link List} and executes the {@link ElementProcessor} for each element.
     *
     * @param list the list being iterated over
     * @param executor the executor to be run for each element
     */
    void iterate(List<T> list, ElementProcessor<T> executor);
}
