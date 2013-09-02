package haskell.data.enumerator;

import java.util.function.Function;


public interface IterEnumerator<I,O> extends Function<Iteratee<I,O>, Iteratee<I,O>> {
     
}
