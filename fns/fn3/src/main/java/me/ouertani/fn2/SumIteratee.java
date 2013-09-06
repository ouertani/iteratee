package me.ouertani.fn2;

import java.util.Arrays;
import java.util.function.Function;

/**
 *
 * @author slim
 */
public class SumIteratee implements Step<Integer, Integer> {

    final Integer sum;
 

    public SumIteratee(Integer sum) {
        this.sum = sum;       
    }

   

    public SumIteratee() {
        this(0);
    }

    public Iteratee<Integer, Integer> handles(Input<Integer> e) {
        switch (e.state()) {
            case EL:
                Input.El<Integer> el = (Input.El) e;
                Integer elem = el.getE();

                return new SumIteratee(sum + elem);
            default:
                return this;
        }
    }

    public Integer getSum() {
        return sum;
    }


    @Override
    public <B> B handle(Function<Step<Integer, Integer>, B> step) {
        Function<Input<Integer>, Iteratee<Integer, Integer>> k = new Function<Input<Integer>, Iteratee<Integer, Integer>>() {

            @Override
            public Iteratee<Integer, Integer> apply(Input<Integer> e) {
                switch (e.state()) {
                    case EL:
                        Input.El<Integer> el = (Input.El) e;
                        Integer elem = el.getE();                      
                        return  new SumIteratee(sum + elem);
                    case EOF:
                        return new Step.Done(sum) ;
                    case Empty:
                    default:
                        return new SumIteratee(sum);
                    
                }

            }

        };
        Step.Cont<Integer, Integer> stCont = new Step.Cont<Integer, Integer>(k);
        return step.apply(stCont);
    }

    @Override
    public StepState onState() {
       return StepState.CONT;
    }

    @Override
    public String toString() {
        return "SumIteratee{" + "sum=" + sum + '}';
    }
    
    

}
