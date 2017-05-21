package org.keyboardplaying.demo.execution.iteration;

import java.util.List;

/**
 * Implementation of the {@link Iterator} that relies on index and checking the size on each iteration.
 *
 * @param <T> the type of elements in the list being iterated upon
 * @author Cyrille Chopelet
 */
public class MethodInConditionIterator<T> implements Iterator<T> {
    @Override
    public void iterate(List<T> list, ElementProcessor<T> executor) {
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            executor.process(element);
        }
    }
}
