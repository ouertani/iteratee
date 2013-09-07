package me.ouertani.fn2.utils;

import java.util.concurrent.Future;
import java.util.function.Function;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;

/**
 *
 * @author slim
 */
public class MaxIteratee implements Iteratee<Integer, Integer>{
   final  Function<Input<Integer>, Iteratee<Integer, Integer>> handler;
    final Integer max;

    public MaxIteratee(Integer max) {
        this.max = max;
        this.handler=handler(max);
    }

    public MaxIteratee() {
        this(Integer.MIN_VALUE);
    }
    
    @Override
    public <B> Future<B>  handle(Function<Iteratee<Integer, Integer>, Future<B> > step) {
       
        Iteratee.Cont<Integer, Integer> stCont = new Iteratee.Cont<Integer, Integer>(handler);
        return step.apply(stCont);
    }
     static Integer max(Integer a, Integer b) {
         if(a > b) return a;
         else return b;
     }

    @Override
    public String toString() {
        return "MaxIteratee{" + "max=" + max + '}';
    }

    @Override
    public Function<Input<Integer>, Iteratee<Integer, Integer>> handler() {
        return handler;
    }
     
    
    private Function<Input<Integer>, Iteratee<Integer, Integer>> handler(int max) {
        return new Function<Input<Integer>, Iteratee<Integer, Integer>>() {

            @Override
            public Iteratee<Integer, Integer> apply(Input<Integer> e) {
                switch (e.state()) {
                    case EL:
                        Input.El<Integer> el = (Input.El) e;
                        Integer elem = el.getE(); 
                        System.out.println("max"+max +"  ? "+ elem);
                        return  new MaxIteratee( max(elem,max) );
                    case EOF:
                        return new Iteratee.Done(max) ;
                    case Empty:
                    default:
                        return new MaxIteratee(max);
                    
                }

            }

        };
    }
}
