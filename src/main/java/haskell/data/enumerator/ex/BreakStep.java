package haskell.data.enumerator.ex;


import haskell.data.enumerator.IterException;
 
import haskell.data.enumerator.Step;

import java.nio.CharBuffer;
import java.util.function.Predicate;

/**
 * This corresponds to {@code break} function in page 49.
 * @author hemantka
 */
public class BreakStep implements Step<CharBuffer,String> {
    
    private final StringBuilder strb;
    public final Predicate<Character> charToBool;
    private IterateeState state;
    
    public BreakStep(Predicate<Character> charPredicate){
        strb = new StringBuilder();
        state = IterateeState.CONTINUE;
        charToBool = charPredicate;
    }

    @Override
    public String getResult() {
        return this.strb.toString();
    }

    @Override
    public IterateeState getState() {
        return this.state;
    }

    @Override
    public void readBuffer(CharBuffer cb) {
        while (cb.hasRemaining()) {
            cb.mark();
            int pos = cb.position();
            char c = cb.get();
            if (charToBool.test(c)) {
                cb.position(pos);
                this.state = IterateeState.DONE;
                break;
            } else {
                strb.append(c);
            }
        }
    }

    @Override
    public void reset() {
        strb.setLength(0);
        this.state = IterateeState.CONTINUE;
    }
}
