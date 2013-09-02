package haskell.data.enumerator.ex;

 
import haskell.data.enumerator.Step;
import java.nio.CharBuffer;

/**
 * This class corresponds to the {@code peek} function on page 47.
 * @author hemantka
 */
public class PeekStep implements Step<CharBuffer,Character> {
    private char c;
    private IterateeState state = IterateeState.CONTINUE;
  

    @Override
    public Character getResult() {
        return c;
    }

    @Override
    public IterateeState getState() {
        return state;
    }

    @Override
    public void readBuffer(CharBuffer cb) {
        if (cb.hasRemaining()) {
            cb.mark();
            c = cb.get();
            cb.reset();
            this.state = IterateeState.DONE;
        }
        
    }

    @Override
    public void reset() {
        this.state = IterateeState.CONTINUE;
        this.c = '\0';
    }
    

}
