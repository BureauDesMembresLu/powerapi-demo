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

/**
 * This interface describes possible implementations for constructing a {@link String} by concatenation over many
 * iterations.
 * <p/>
 * Implementations should hold the current state of the value and append any {@link String} passed to it.
 *
 * @author Cyrille Chopelet
 */
public interface Appender {

    /**
     * Appends a {@link String} to the current held value.
     *
     * @param string the string to append
     * @return the current instance to allow for chained calling
     */
    Appender append(String string);

    /**
     * Returns the result of all concatenations.
     *
     * @return the current held value
     */
    String getResult();
}
