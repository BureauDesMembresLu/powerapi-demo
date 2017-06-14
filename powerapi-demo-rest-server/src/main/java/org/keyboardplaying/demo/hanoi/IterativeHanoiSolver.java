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
package org.keyboardplaying.demo.hanoi;

import java.util.ArrayList;
import java.util.List;

/**
 * An optimized iterative implementation of the Tower of Hanoi solver, using iteration and without using any stack.
 * <p>
 * This code was adapted from Shantaku Khan's (http://0code.blogspot.com/).
 *
 * @author Cyrille Chopelet
 */
public class IterativeHanoiSolver implements HanoiSolver {

    @Override
    public List<Move> solveTowerOfHanoi(int nbDiscs) {
        int limit = (int) Math.pow(2, nbDiscs) - 1; // NUMBER OF ITERATIONS = 2^n - 1
        List<Move> moves = new ArrayList<>(limit);

        for (int i = 0; i < limit; ++i) {
            int disc = disc(i); // DISK TO BE MOVED
            int from = (movements(i, disc) * direction(disc, nbDiscs)) % 3;
            int to = (from + direction(disc, nbDiscs)) % 3;
            moves.add(new Move(from, to, true));
        }

        return moves;
    }

    private int disc(int i) { // RETURNS THE DISK TO BE MOVED IN i
        int c;
        int x = i + 1; // SINCE FOR STARTS WITH 0, ADDING 1
        for (c = 0; x % 2 == 0; ++c) { // CONTINUOUS DIVISION BY 2 UNTIL ODD OCCURS
            x /= 2;
        }
        return c; // RETURNS THE COUNTER C
    }

    private int movements(int i, int d) { // HOW MANY TIMES DISK d HAS MOVED BEFORE STAGE i
        int dbis = d;
        int ibis = i;
        while (dbis-- != 0) {
            ibis /= 2;
        }
        return (ibis + 1) / 2;
    }

    private int direction(int d, int n) { // EACH DISK MOVES IN SAME DIRECTION CW=1, ACW=2
        return (n + d) % 2 == 0 ? 1 : 2;
    }
}
