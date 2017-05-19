package org.keyboardplaying.demo.injector;

import org.keyboardplaying.demo.people.PeopleRepository;
import org.keyboardplaying.demo.utils.CommandPrompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * The command-line interface to control the injector.
 *
 * @author Cyrille Chopelet
 */
@Component
public class CommandLineInterface implements ApplicationContextAware, CommandLineRunner {

    private enum InjectorCommands implements CommandPrompt.Command {
        CLEAR("Clear database", 'c'), INJECT("inject random people", 'i'), QUIT("exit application", 'x');

        private String text;
        private char letter;

        InjectorCommands(String text, char letter) {
            this.text = text;
            this.letter = letter;
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public char getCommandLetter() {
            return letter;
        }
    }

    private final CommandPrompt prompt = new CommandPrompt(System.in, System.out, System.err);

    private ApplicationContext context;

    @Autowired
    private PeopleRepository repository;

    @Autowired
    private PersonGenerator personGenerator;

    @PreDestroy
    public void tearDown() {
        prompt.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            InjectorCommands command = prompt.promptChoice("What would you like to do?", InjectorCommands.values());
            switch (command) {
                case CLEAR:
                    prompt.println("Cleaning under progress...");
                    repository.deleteAll();
                    prompt.println("Success! Repository has been cleaned");
                    break;

                case INJECT:
                    injectPeople();
                    break;

                case QUIT:
                    exitApplication();
                    return;

                default:
                    prompt.printErr(String.format("Command <%s> is invalid", command));
            }
        }
    }

    private void injectPeople() {
        int max = prompt.promptInt("How many people would you like to inject into database?");
        for (int i = 0; i < max; ++i) {
            try {
                repository.save(personGenerator.randomPerson());
            } catch (Exception e) {
                prompt.printErr(e);
                prompt.printErr("Skipping record, going on");
            }
            if (i % 1000 == 0) {
                System.out.println();
            } else if (i % 100 == 0) {
                System.out.print("|");
            } else if (i % 10 == 0) {
                System.out.print(".");
            }
        }
    }

    private void exitApplication() {
        SpringApplication.exit(this.context);
    }
}