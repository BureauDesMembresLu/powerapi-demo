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
package org.keyboardplaying.demo.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for {@link CommandPrompt}.
 *
 * @author Cyrille Chopelet
 */
@RunWith(MockitoJUnitRunner.class)
public class CommandPromptTest {

    private enum TestCommands implements CommandPrompt.Command {
        CMD1("Command 1", 'c'), CMD2("Command 2", 'm'), CMD3("Command 3", 'X');

        private String text;
        private char letter;

        TestCommands(String text, char letter) {
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


    private static final String PROMPT = "> ";

    private PseudoConsoleInputStream in = new PseudoConsoleInputStream();
    @Mock
    private PrintStream out;
    @Mock
    private PrintStream err;

    private CommandPrompt prompt;

    @Before
    public void setUp() {
        prompt = new CommandPrompt(in, out, err);
    }

    @After
    public void tearDown() throws IOException {
        prompt.close();
        in.close();
    }

    @Test
    public void testPrompt() {
        final String promptMessage = "Hello?";
        final String answer = "World";
        in.setInputLines(answer);

        assertEquals(answer, prompt.prompt(promptMessage));
        verify(out, times(1)).println(promptMessage);
        verify(out, times(1)).print(PROMPT);
    }

    @Test
    public void testLowercaseChar() {
        final String promptMessage = "What do you say to World?";
        in.setInputLines("Hello");

        assertEquals('h', prompt.promptLowercaseChar(promptMessage));
        verify(out, times(1)).println(promptMessage);
        verify(out, times(1)).print(PROMPT);
    }

    @Test
    public void testPromptInt() {
        final String promptMessage = "How many fingers?";
        final int answer = 3;
        in.setInputLines(String.valueOf(answer));

        assertEquals(answer, prompt.promptInt(promptMessage));
        verify(out, times(1)).println(promptMessage);
        verify(out, times(1)).print(PROMPT);
    }

    @Test
    @Ignore
    public void testPromptIntWithIncorrectAnswer() {
        final String promptMessage = "How many fingers?";
        final int answer = 3;
        in.setInputLines("What?", String.valueOf(answer));

        assertEquals(answer, prompt.promptInt(promptMessage));
        // the input commands should have appeared twice (once before incorrect input, once before correct format)
        verify(out, times(2)).println(promptMessage);
        verify(out, times(2)).print(PROMPT);
        // an error message should have been printed
        verify(err, times(1)).println(any(String.class));
    }

    @Test
    public void testPromptChoice() {
        final String message = "What do you wanna do?";

        in.setInputLines("Mand");

        assertEquals(TestCommands.CMD2, prompt.promptChoice(message, TestCommands.values()));
        verify(out, times(1)).println(message);
        verify(out, times(1)).println("[C]ommand 1, Co[m]mand 2, Command 3 ([X])?");
        verify(out, times(1)).print(PROMPT);
    }
}
