package org.keyboardplaying.demo.injector;

import org.keyboardplaying.demo.DemoApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Cyrille Chopelet
 */
@SpringBootApplication
@Import(DemoApplication.class)
public class InjectorApplication {

    public static void main(String... args) {
        SpringApplication.run(InjectorApplication.class);
    }
}
