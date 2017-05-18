package org.keyboardplaying.demo.execution.iteration;

/**
 * This interface describes the implementation of a processing to be executed on each element of a collection during an
 * iteration.
 *
 * @param <T> the type of element in the collection being iterated upon
 * @author Cyrille Chopelet
 */
public interface ElementProcessor<T> {

    /**
     * Executes the processing of the current element.
     *
     * @param element the current element of the iteration.
     */
    void process(T element);
}
