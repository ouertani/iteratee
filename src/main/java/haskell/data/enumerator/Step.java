package haskell.data.enumerator;

import java.nio.Buffer;

/**
 * Abstraction over what happens at each step.
 * <p>
 * This corresponds to
 * {@link http://hackage.haskell.org/packages/archive/enumerator/0.4.18/doc/html/Data-Enumerator-Internal.html#t:Step haskell Step}.
 * This object has a state and is the one that actually acts reads the input
 * buffer.
 *
 * <p>
 * @author hemantka
 * @param <I>
 * @param <O>
 */
public interface Step<I, O> {

    public enum IterateeState {
        CONTINUE,
        DONE,
        ERROR
    }

    default String getErrorMessage() {
        return null;
    }

    /**
     * If the step has reached state where it
     * {@link haskell.data.enumerator.IterateeState#DONE can yield} a
     * result,this method will return the result.
     * <p>
     * Behavior is undefined if the step is not at the said state.</p>
     *
     * @return
     */
    O getResult();

    /**
     * The state of the step
     *
     * @return
     */
    default IterateeState getState() {
        return IterateeState.DONE;
    }

    default void readBuffer(I i) {
    }

    /**
     * This silly utility method allows a step object to let the state to
     * beginning.
     * <p>
     * Perils of imperative programming. This one has no bearing on the main
     * functionality. It has no related function in the haskell world.</p>
     */
    default void reset() {
    }

}
