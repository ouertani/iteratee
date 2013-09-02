package haskell.data.enumerator;

 

/**
 * Enumeratee acts as an {@link haskell.data.enumerator.Iteratee iteratee} for outer stream, while acting as 
 * an {@link haskell.data.enumerator.IterEnumerator enumerator} for an inner stream.
 * <p> According to Oleg, this is for <em>vertical</em> composition of the iteratees.
 * </p>
 * 
 * @author hemantka
 * @param <I>
 * @param <O>
 * @param <E>
 */
public interface Enumeratee<I,O,E> 
            extends Iteratee<I,O>, 
                    IterEnumerator<I, E>
{
    
}
