package haskell.data.enumerator.ex;

 
import haskell.data.enumerator.Step;
import java.nio.Buffer;
import java.nio.CharBuffer;

/**
 * This corresponds to the {@code heads} iteratee on page 49.
 * @author hemantka
 */
public class HeadsStep implements Step<CharBuffer,Integer> {
    public final char[] heads;
    private int i;
    private IterateeState state;
    
    public HeadsStep(char[] matches){
        heads = new char[matches.length];
        System.arraycopy(matches, 0, heads, 0, matches.length);
        state = IterateeState.CONTINUE;       
        i = 0;
    }
 
    @Override
    public Integer getResult() {
        return i;
    }

    @Override
    public IterateeState getState() {
        return state;
    }

    @Override
    public void readBuffer(CharBuffer cb) {
        while (cb.hasRemaining() && (i < heads.length)) {
            int pos = cb.position();
            char next = cb.get();
            if (next == heads[i]) {
                i++;
            } else {
                cb.position(pos);//put the next character back.
                break;
            }

        }
        this.state = IterateeState.DONE;
            
    }

    @Override
    public void reset() {
        state = IterateeState.CONTINUE;      
        i = 0;
    }
}
