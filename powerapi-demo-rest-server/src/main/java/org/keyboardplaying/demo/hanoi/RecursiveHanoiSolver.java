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
import java.util.Collections;
import java.util.List;

/**
 * A recursive Hanoi solver found in many forms on the Internet.
 *
 * @author Cyrille Chopelet
 */
public class RecursiveHanoiSolver implements HanoiSolver {

    @Override
    public List<Move> solveTowerOfHanoi(int nbDiscs) {
        return solveTowersOfHanoi(nbDiscs, 1, 2, 3);
    }

    private List<Move> solveTowersOfHanoi(int n, int start, int temp, int end) {
        List<Move> moves;

        if (n == 1) {
            moves = Collections.singletonList(new Move(start, end));
        } else {
            moves = new ArrayList<>();
            moves.addAll(solveTowersOfHanoi(n - 1, start, end, temp));
            moves.add(new Move(start, end));
            moves.addAll(solveTowersOfHanoi(n - 1, temp, start, end));
        }

        return moves;
    }
}
