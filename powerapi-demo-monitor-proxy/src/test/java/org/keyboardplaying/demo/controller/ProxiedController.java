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

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proxied")
public class ProxiedController {

    public static class Message {
        private final String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @RequestMapping(path = "/greeting/{helloWorld}", method = RequestMethod.GET)
    public Message greet(@PathVariable("helloWorld") String helloWorld,
                         @RequestParam(value="name", defaultValue = "World") String name) {
        return new Message(String.format("%s, %s!", helloWorld, name));
    }
}
