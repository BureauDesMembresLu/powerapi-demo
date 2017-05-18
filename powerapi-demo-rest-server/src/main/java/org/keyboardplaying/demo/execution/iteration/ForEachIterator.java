package org.keyboardplaying.demo.execution.iteration;

import java.util.List;

/**
 * Implementation of the {@link Iterator} that relies on the foreach syntax.
 *
 * @param <T> the type of elements in the list being iterated upon
 * @author Cyrille Chopelet
 */
public class ForEachIterator<T> implements Iterator<T> {
    @Override
    public void iterate(List<T> list, ElementProcessor<T> executor) {
        for (T element : list) {
            executor.process(element);
        }
    }
}
