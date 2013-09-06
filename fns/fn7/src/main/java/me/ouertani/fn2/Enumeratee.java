package me.ouertani.fn2;

/**
 *
 * @author slim
 * @param <To>
 * @param <From>
 */
public interface Enumeratee<To,From> {
    <A> Iteratee<To,From> apply(Iteratee<From,Iteratee<To, A> >inner);
}
