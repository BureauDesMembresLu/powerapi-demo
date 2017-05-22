package org.keyboardplaying.demo.injector;

import org.keyboardplaying.demo.people.PersonInjector;
import org.keyboardplaying.demo.utils.CommandTerminal;
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

    private enum InjectorCommands implements CommandTerminal.Command {
        CLEAR("Clear database", 'c'), INJECT("inject random people", 'i'), QUIT("exit application", 'x');

        private final String text;
        private final char letter;

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

    private final CommandTerminal term = new CommandTerminal(System.in, System.out, System.err);

    private final PersonInjector injector;

    private ApplicationContext context;

    @Autowired
    public CommandLineInterface(PersonInjector injector) {
        this.injector = injector;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    @PreDestroy
    public void tearDown() {
        term.close();
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            InjectorCommands command = term.promptChoice("What would you like to do?", InjectorCommands.values());
            switch (command) {
                case CLEAR:
                    injector.wipeRepositoryClean(term);
                    break;

                case INJECT:
                    injectPeople();
                    break;

                case QUIT:
                    exitApplication();
                    return;

                default:
                    term.printErr(String.format("Command <%s> is invalid", command));
            }
        }
    }

    private void injectPeople() {
        int nbToInsert = term.promptInt("How many people would you like to inject into database?");
        injector.injectRandomPeople(nbToInsert, term);
    }

    private void exitApplication() {
        SpringApplication.exit(this.context);
    }
}