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

/**
 * This interface describes the implementation of a processing to be executed on each element of a collection during an
 * iteration.
 *
 * @param <T> the type of element in the collection being iterated upon
 * @author Cyrille Chopelet
 */
public interface ElementProcessor<T> {

    /**
     * Executes the processing of the current element.
     *
     * @param element the current element of the iteration.
     */
    void process(T element);
}
