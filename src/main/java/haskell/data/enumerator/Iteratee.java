package haskell.data.enumerator;

 
import java.util.Iterator;


 
public interface Iteratee<I,O>{
 
    Iteratee<I,O> apply(Iterator<I> stream) throws IterException;
    
    boolean toContinue();
 
    O yield();
    
}
