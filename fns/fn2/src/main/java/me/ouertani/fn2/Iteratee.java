package me.ouertani.fn2;

/**
 *
 * @author slim
 * @param <E>
 */
public interface Iteratee<E> {
    Iteratee<E> handle(Input<E> e);   
}
