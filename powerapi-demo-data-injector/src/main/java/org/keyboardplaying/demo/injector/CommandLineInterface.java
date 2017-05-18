package org.keyboardplaying.demo.injector;

import org.keyboardplaying.demo.people.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Scanner;

/**
 * The command-line interface to control the injector.
 *
 * @author Cyrille Chopelet
 */
@Component
public class CommandLineInterface implements ApplicationContextAware, CommandLineRunner {

    private final Scanner scanner = new Scanner();

    private ApplicationContext context;

    @Autowired
    private PeopleRepository repository;

    @Autowired
    private PersonGenerator personGenerator;

    @PreDestroy
    public void tearDown() {
        scanner.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            System.out.println("What would you like to do: [c]lear database, [i]nject data or e[x]it injector?");

            String command = scanner.next();
            switch (command.toLowerCase().charAt(0)) {
                case 'c':
                    repository.deleteAll();
                    System.out.println("Repository has been cleaned");
                    break;

                case 'i':
                    injectPeople();
                    break;

                case 'x':
                    exitApplication();
                    break;

                default:
                    System.out.println(String.format("Command <%s> is invalid", command));
            }
        }
    }

    private void injectPeople() {
        System.out.println("How many people would you like to inject into database?");
        try {
            int max = scanner.nextInt();
            for (int i = 0; i < max; ++i) {
                try {
                    repository.save(personGenerator.randomPerson());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Skipping record, going on");
                }
                if (i % 1000 == 0) {
                    System.out.println();
                } else if (i % 100 == 0) {
                    System.out.print("|");
                } else if (i % 10 == 0) {
                    System.out.print(".");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please provide an integer value.");
            injectPeople();
        }
    }

    private void exitApplication() {
        SpringApplication.exit(this.context);
    }
}