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
package org.keyboardplaying.demo.controller;

import java.util.List;

/**
 * This class wraps the response from a proxied service and adds monitoring information to it.
 *
 * @author Cyrille Chopelet
 */
public class ProxiedResponse {

    /**
     * The proxied response.
     * <p/>
     * Let Spring Boot decide what is to be put here, it should handle it all right.
     */
    private Object proxied;

    private List<Double> power;

    private long processingTime;

    public Object getProxied() {
        return proxied;
    }

    public void setProxied(Object proxied) {
        this.proxied = proxied;
    }

    public List<Double> getPower() {
        return power;
    }

    public void setPower(List<Double> power) {
        this.power = power;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }
}
