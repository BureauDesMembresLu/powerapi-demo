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

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the {@link ProxyController}.
 *
 * @author Cyrille Chopelet
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProxyControllerTestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ProxyControllerTest {

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private ProxiedController spyController;

    @Test
    public void testGetStaticResource() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/index.html").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_HTML));
    }

    @Test
    public void testGetWithoutParameter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/proxy/1/greeting/Hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.proxied.message", is("Hello, World!")));
    }

    @Test
    public void testGetWithParameter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/proxy/1/greeting/Hello?name=Test").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.proxied.message", is("Hello, Test!")));
    }

    @Test
    public void testSingleIteration() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/proxy/1/greeting/Hello").accept(MediaType.APPLICATION_JSON));
        verify(spyController, times(1)).greet(any(String.class), any(String.class));
    }

    @Test
    public void testMultipleIterations() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/proxy/3/greeting/Hello").accept(MediaType.APPLICATION_JSON));
        verify(spyController, times(3)).greet(any(String.class), any(String.class));
    }

    @Test
    public void testAtLeastOneIteration() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/proxy/0/greeting/Hello").accept(MediaType.APPLICATION_JSON));
        verify(spyController, times(1)).greet(any(String.class), any(String.class));
    }

    // FIXME enhance proxy to correctly transmit the http status
    @Test
    @Ignore
    public void testProxifyingNotExistingUrl() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/proxy/1/im-not-there").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
