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
 
    final Function<Input<Integer>, Iteratee<Integer, Integer>> handler;
   

    public SumIteratee(Integer sum ,  Function<Input<Integer>, Iteratee<Integer, Integer>> handler) {
        this.sum = sum;   
        this.handler=handler;
        
    }
    public SumIteratee(Integer sum) {
        this(0, handler(sum));
    }
   

   

    public SumIteratee() {
        this(0);
    }

    public Integer getSum() {
        return sum;
    }


    @Override
    public <B> Future<B>  handle(Function<Iteratee<Integer, Integer>, Future<B> > step) {
        Iteratee.Cont<Integer, Integer> stCont = new Iteratee.Cont<>(handler);
        return step.apply(stCont);
    }


    @Override
    public String toString() {
        return "SumIteratee{" + "sum=" + sum + '}';
    }

    @Override
    public Function<Input<Integer>, Iteratee<Integer, Integer>> handler() {
       return handler;
    }
    private static Function<Input<Integer>, Iteratee<Integer, Integer>> handler(int initValue) {
     return  new Function<Input<Integer>, Iteratee<Integer, Integer>>() {
            @Override
            public Iteratee<Integer, Integer> apply(Input<Integer> e) {
                switch (e.state()) {
                    case EL:
                        Input.El<Integer> el = (Input.El) e;
                        Integer elem = el.getE();                      
                        return  new SumIteratee(initValue + elem);
                    case EOF:
                        return new Iteratee.Done(initValue) ;
                    case Empty:
                    default:
                        return new SumIteratee(initValue);
                }

            }

        } ;
    }

}
