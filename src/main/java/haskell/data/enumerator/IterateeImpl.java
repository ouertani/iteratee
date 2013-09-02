package haskell.data.enumerator;

import java.util.Iterator;


public class IterateeImpl<I,O> implements Iteratee<I,O> {
    /**
     * Reference to a step. 
     */
    public final Step<I,O> step;
    
    private O result;
    private boolean todo;

    
    public IterateeImpl(Step<I,O> step){
        this.step = step;
    }

    @Override
    public Iteratee<I,O> apply( Iterator<I> stream) throws IterException {
        if(!stream.hasNext())
            throw new IterException("Unexpected EOF");
        step.readBuffer(stream.next());
        
        if(step.getState() == Step.IterateeState.ERROR){
            throw new IterException(step.getErrorMessage());
        }
        if(step.getState() == Step.IterateeState.DONE){
            this.result = step.getResult();
            this.todo = false;
            this.step.reset();
        }else{
            this.todo = true;
        }
        return this;  
    }

    @Override
    public boolean toContinue() {
        return todo;
    }

    @Override
    public O yield() {
        return result;
    }

}
