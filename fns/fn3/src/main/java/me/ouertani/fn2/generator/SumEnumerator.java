/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ouertani.fn2.generator;

import java.util.function.Function;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;
import me.ouertani.fn2.Step;

/**
 *
 * @author slim
 */
public class SumEnumerator implements Enumerator<Integer>{

    final Input<Integer> value;
    Iteratee<Integer, Integer> it;

    public SumEnumerator() {
        this.value = Input.EOF;
        this.it = null;
    }
    
    

    public SumEnumerator(Iteratee<Integer, Integer> it, Input<Integer> value) {
        this.value = value;
        this.it = it;
    }

    public Input<Integer> getValue() {
        return value;
    }


    @Override
    public <A> Step<Integer, A> apply(Step<Integer, A> t) {
       switch (t.onState()) {
            case CONT:
                Step.Cont<Integer, Integer> k = (Step.Cont) t;
                switch (value.state()) {
                    case EL:
                        Input.El<Integer> el = (Input.El<Integer>) value;
                        Function<Input<Integer>, Iteratee<Integer, Integer>> ks = new Function<Input<Integer>, Iteratee<Integer, Integer>>() {

                            @Override
                            public Iteratee<Integer, Integer> apply(Input<Integer> t) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                        };
                        return new Step.Cont(ks);

                    default:
                        return null;
                }
//                  
//                 // Integer handle = k.handle(this);
//            
//            
//                   System.out.println(value+"----"+k);
//                  return 1;

            case Done:
                Step.Done<Integer, A> d = (Step.Done<Integer, A>) t;
                return d;
            case ERROR:
                Step.Error e = (Step.Error) t;
                return e;
            default:
                throw new IllegalStateException();
        }
    }

}
