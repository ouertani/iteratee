package haskell.data.enumerator.ex;


import haskell.data.enumerator.Input;
import haskell.data.enumerator.IterEnumerator;
import haskell.data.enumerator.IterException;
import haskell.data.enumerator.Iteratee;
import java.nio.CharBuffer;
import java.util.Iterator;

/**
 * An simple Enumerator wrapper around a String
 *
 * @author hemantka
 * @param <T>
 */
public class StringIterEnumerator<T> implements IterEnumerator<CharBuffer, T> {

    public final CharBuffer cbuff;
    private Iteratee<CharBuffer, T> iteratee;

    public StringIterEnumerator(String src) {
        cbuff = CharBuffer.wrap(src);
    }

    private void runIteratee(Iterator<CharBuffer> stream) throws IterException {        
            this.iteratee.apply(stream);        
    }

    private void eval() {
        boolean endTry = true;

        while (cbuff.hasRemaining() && this.iteratee.toContinue()) {
            try {
                runIteratee(Input.el(cbuff));
            } catch (IterException e) {
                endTry = false;
                break;
            }
        }
        if (endTry && iteratee.toContinue()) {
            try {
                runIteratee( Input.EOF);
            } catch (IterException e) {
            }
        }

    }

    @Override
    public Iteratee<CharBuffer, T> apply(Iteratee<CharBuffer, T> t) {
        this.iteratee = t;
        eval();
        return this.iteratee;
    }

}
