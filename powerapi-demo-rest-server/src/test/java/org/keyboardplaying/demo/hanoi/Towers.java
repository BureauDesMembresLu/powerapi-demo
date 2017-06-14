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

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Cyrille Chopelet
 */
public class Towers {
    private final List<LinkedList<Disc>> towers;

    public Towers(int nbDiscs) {
        this.towers = Arrays.asList(new LinkedList<>(), new LinkedList<>(), new LinkedList<>());

        LinkedList<Disc> tower1 = this.getTower(1);

        for (int i = 1; i <= nbDiscs; ++i) {
            tower1.add(new Disc(i));
        }
    }

    public void move(Move move) {
        this.getTower(move.getTo()).addLast(this.getTower(move.getFrom()).removeLast());
    }

    public boolean areAllTowersSorted() {
        for (LinkedList<Disc> tower : this.towers) {
            if (!isTowerSorted(tower)) {
                return false;
            }
        }
        return true;
    }

    private boolean isTowerSorted(LinkedList<Disc> tower) {
        if (!tower.isEmpty()) {
            Iterator<Disc> iterator = tower.iterator();

            Disc previous;
            Disc current = iterator.next();

            while (iterator.hasNext()) {
                previous = current;
                current = iterator.next();
                if (current.compareTo(previous) < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean areSolved() {
        return this.getTower(1).isEmpty()
                && this.getTower(2).isEmpty()
                && this.isTowerSorted(this.getTower(3));
    }

    private LinkedList<Disc> getTower(int index) {
        return this.towers.get(index - 1);
    }

    @Override
    public String toString() {
        return towers.toString();
    }
}
