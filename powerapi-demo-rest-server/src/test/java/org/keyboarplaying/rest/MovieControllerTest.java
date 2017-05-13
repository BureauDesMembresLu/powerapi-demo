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
package org.keyboarplaying.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keyboardplaying.rest.DemoApplication;
import org.keyboardplaying.rest.movie.Movie;
import org.keyboardplaying.rest.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A test class to ensure {@link MovieRepository} exposes a REST controller.
 *
 * @author Cyrille Chopelet
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class MovieControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private MovieRepository repository;

    @Before
    public void clearRepo() {
        repository.deleteAll();
    }

    @Test
    public void testGetAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/movies").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$._embedded.movies", is(repository.findAll())));
    }

    @Test
    public void testGetById() throws Exception {
        Movie movie = new Movie();
        movie.setImdbID("myBigId");
        movie.setTitle("The Matrix");
        movie.setImdbRating(8.7F);
        repository.insert(movie);

        mvc.perform(MockMvcRequestBuilders.get("/movies/myBigId").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("The Matrix")))
                .andExpect(jsonPath("$.imdbRating", is(8.7)));
    }
}
