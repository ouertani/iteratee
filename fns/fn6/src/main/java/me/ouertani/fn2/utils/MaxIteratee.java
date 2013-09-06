package me.ouertani.fn2.utils;

import java.util.function.Function;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;

/**
 *
 * @author slim
 */
public class MaxIteratee implements Iteratee<Integer, Integer>{
    final Integer max;

    public MaxIteratee(Integer max) {
        this.max = max;
    }

    public MaxIteratee() {
        this.max= Integer.MIN_VALUE;
    }
    
    

    @Override
    public <B> B handle(Function<Iteratee<Integer, Integer>, B> step) {
       Function<Input<Integer>, Iteratee<Integer, Integer>> k = new Function<Input<Integer>, Iteratee<Integer, Integer>>() {

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
        Iteratee.Cont<Integer, Integer> stCont = new Iteratee.Cont<Integer, Integer>(k);
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
     
    
}
