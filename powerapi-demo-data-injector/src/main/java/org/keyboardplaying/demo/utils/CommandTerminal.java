/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file dimessageibuted with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * dimessageibuted under the License is dimessageibuted on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keyboardplaying.demo.utils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * A terminal printing and prompting utility.
 *
 * @author Cyrille Chopelet
 */
public class CommandTerminal implements AutoCloseable {

    public interface Command {
        String getText();

        char getCommandLetter();
    }

    private static final String PROMPT = "> ";
    private static final String INCORRECT_CHOICE = "/!\\ Invalid command letter";
    private static final String INCORRECT_INT = "/!\\ Incorrect format, please enter digits only.";

    private final PrintStream out;
    private final PrintStream err;
    private final Scanner scanner;

    public CommandTerminal(InputStream in, PrintStream out, PrintStream err) {
        this.out = out;
        this.err = err;
        this.scanner = new Scanner(in);
    }

    @SafeVarargs
    public final <T extends Command> T promptChoice(String message, T... commands) {
        final String commandChoice = createCommandChoice(commands);
        while (true) {
            println(message);
            char ch = promptLowercaseChar(commandChoice);
            for (T command : commands) {
                if (ch == String.valueOf(command.getCommandLetter()).toLowerCase().charAt(0)) {
                    return command;
                }
            }
            printErr(INCORRECT_CHOICE);
        }
    }

    private String createCommandChoice(Command[] commands) {
        List<String> strCommands = new ArrayList<>(commands.length);

        for (Command command : commands) {
            strCommands.add(commandAsString(command));
        }

        return String.join(", ", strCommands) + "?";
    }

    private String commandAsString(Command command) {
        final StringBuilder result = new StringBuilder();

        final String text = command.getText();
        final char ch = command.getCommandLetter();

        int indexOfCommandLetter = text.toLowerCase().indexOf(String.valueOf(ch).toLowerCase());
        if (indexOfCommandLetter == -1) {
            result.append(text).append(" ([").append(ch).append("])");
        } else {
            result.append(text.substring(0, indexOfCommandLetter))
                    .append("[")
                    .append(text.substring(indexOfCommandLetter, indexOfCommandLetter + 1))
                    .append("]")
                    .append(text.substring(indexOfCommandLetter + 1, text.length()));
        }
        return result.toString();
    }

    public String prompt(String message) {
        printPrompt(message);
        return scanner.next();
    }

    public char promptLowercaseChar(String message) {
        printPrompt(message);
        return scanner.next().toLowerCase().charAt(0);
    }

    public int promptInt(String message) {
        printPrompt(message);
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            printErr(INCORRECT_INT);
            return promptInt(message);
        }
    }

    private void printPrompt(String message) {
        println(message);
        print(PROMPT);
    }

    public void print(String message) {
        out.print(message);
    }

    public void println() {
        out.println();
    }

    public void println(String message) {
        out.println(message);
    }

    public void printErr(String message) {
        err.println(message);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
