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

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Cyrille Chopelet
 */
public abstract class HanoiSolverTest {

    protected static final int SOLUTION_DEPTH_EVEN = 4;
    protected static final int SOLUTION_DEPTH_ODD = 5;

    protected abstract HanoiSolver getSolver();

    @Test
    public void testSolutionWithEvenDepth() {
        testSolution(SOLUTION_DEPTH_EVEN);
    }

    @Test
    public void testSolutionWithOddDepth() {
        testSolution(SOLUTION_DEPTH_ODD);
    }

    private void testSolution(int depth) {
        List<Move> moves = this.getSolver().solveTowerOfHanoi(depth);

        Towers towers = new Towers(depth);

        for (Move move : moves) {
            towers.move(move);
            assertTrue("Towers are not sorted: " + towers.toString(), towers.areAllTowersSorted());
        }
        assertTrue("Towers are not solved: " + towers.toString(), towers.areSolved());
    }
}
