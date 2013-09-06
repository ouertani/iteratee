package me.ouertani.fn2.utils;

import java.util.concurrent.Future;
import java.util.function.Function;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;

/**
 *
 * @author slim
 */
public class SumIteratee implements Iteratee<Integer, Integer> {

    final Integer sum;
 

    public SumIteratee(Integer sum) {
        this.sum = sum;       
    }

   

    public SumIteratee() {
        this(0);
    }

   

    public Integer getSum() {
        return sum;
    }


    @Override
    public <B> Future<B> handle(Function<Iteratee<Integer, Integer>, Future<B>> step) {
        Function<Input<Integer>, Iteratee<Integer, Integer>> k = new Function<Input<Integer>, Iteratee<Integer, Integer>>() {

            @Override
            public Iteratee<Integer, Integer> apply(Input<Integer> e) {
                switch (e.state()) {
                    case EL:
                        Input.El<Integer> el = (Input.El) e;
                        Integer elem = el.getE();                      
                        return  new SumIteratee(sum + elem);
                    case EOF:
                        return new Iteratee.Done(sum) ;
                    case Empty:
                    default:
                        return new SumIteratee(sum);
                    
                }

            }

        };
        Iteratee.Cont<Integer, Integer> stCont = new Iteratee.Cont<Integer, Integer>(k);
        return step.apply(stCont);
    }


    @Override
    public String toString() {
        return "SumIteratee{" + "sum=" + sum + '}';
    }

}
