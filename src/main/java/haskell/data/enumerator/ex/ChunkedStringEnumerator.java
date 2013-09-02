package haskell.data.enumerator.ex;


import haskell.data.enumerator.Input;
import haskell.data.enumerator.IterEnumerator;
import haskell.data.enumerator.IterException;
import haskell.data.enumerator.Iteratee;
import java.nio.CharBuffer;
import java.util.Iterator;


/**
 * An Enumerator that runs the iterator in chunks of given size
 *
 * @author hemantka
 * @param <T>
 */
public class ChunkedStringEnumerator<T> implements IterEnumerator<CharBuffer, T> {

    private final CharBuffer cbuff;
    /**
     * releases chunks of maximum size
     */
    public final int chunkSize;
    public final int[] maxPos;
    private final int strLen;
    private Iteratee<CharBuffer, T> iteratee;

    public ChunkedStringEnumerator(String src, int size) {
        chunkSize = size;
        cbuff = CharBuffer.wrap(src);
        strLen = src.length();
        int maxPosSize = (src.length() / size) + 1;
        maxPos = new int[maxPosSize];
        int c = size;
        for (int i = 0; i < maxPosSize; i++) {
            maxPos[i] = c;
            c = c + chunkSize;
        }
    }

    private void runIteratee(Iterator<CharBuffer> stream) throws IterException {
        try {
            this.iteratee.apply(stream);
        } catch (IterException e) {
            throw e;
        }
    }

    public void eval() {

        int start = 0;
        for (int j : maxPos) {
            if (!iteratee.toContinue()) {
                break;
            }
            int limit = (j > strLen) ? strLen : j;
            cbuff.position(start);
            cbuff.limit(limit);
            while (cbuff.hasRemaining() && this.iteratee.toContinue()) {
                try {
                    runIteratee(Input.el(cbuff));
                } catch (IterException e) {
                    return;
                }
            }

            start = limit;
        }

        if (iteratee.toContinue()) {
            try {
                runIteratee(Input.EOF);
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
