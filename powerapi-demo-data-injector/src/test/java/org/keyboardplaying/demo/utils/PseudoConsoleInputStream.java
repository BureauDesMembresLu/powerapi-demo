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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A utility {@link InputStream} for testing a command-line interface by setting content during test.
 *
 * @author Cyrille Chopelet
 */
public class PseudoConsoleInputStream extends InputStream {
    private InputStream input = new ByteArrayInputStream(new byte[] {});
    
    public void setInputLines(String... lines) {
        try {
            this.input.close();
        } catch (IOException e) {
            // that's OK, do nothing
        }
        this.input = new ByteArrayInputStream(String.join("\n", lines).getBytes());
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        return input.read(bytes);
    }

    @Override
    public int read(byte[] bytes, int i, int i1) throws IOException {
        return input.read(bytes, i, i1);
    }

    @Override
    public long skip(long l) throws IOException {
        return input.skip(l);
    }

    @Override
    public int available() throws IOException {
        return input.available();
    }

    @Override
    public void close() throws IOException {
        input.close();
    }

    @Override
    public synchronized void mark(int i) {
        input.mark(i);
    }

    @Override
    public synchronized void reset() throws IOException {
        input.reset();
    }

    @Override
    public boolean markSupported() {
        return input.markSupported();
    }

    @Override
    public int read() throws IOException {
        return input.read();
    }
}
