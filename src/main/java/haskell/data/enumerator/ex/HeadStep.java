package haskell.data.enumerator.ex;

 
import haskell.data.enumerator.Step;
import java.nio.CharBuffer;

/**
 * This corresponds to the {@code head} function on page 47.
 * @author hemantka
 */
public class HeadStep implements Step<CharBuffer,Character> {
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
            c = cb.get();
            this.state = IterateeState.DONE;
        }
    }

    @Override
    public void reset() {
        this.state = IterateeState.CONTINUE;
        this.c = '\0';      
    }
    
    

}
