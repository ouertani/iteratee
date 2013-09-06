package me.ouertani.fn2;

import java.util.function.Function;

/**
 *
 * @author slim
 * @param <E>
 */
public interface Iteratee<E,A> {
  //  Iteratee<E> handle(Input<E> e);   
    <B>  B handle(Function<Step<E, A >  , B> step);  
}
