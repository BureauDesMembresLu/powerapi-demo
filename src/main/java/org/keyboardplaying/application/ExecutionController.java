package org.keyboardplaying.application;

import org.keyboardplaying.scala.ScalaTester;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExecutionController {

    @RequestMapping("/")
    public String index() {
        new ScalaTester().runScala("Spring Boot");
        return "Greetings from Spring Boot!";
    }

}