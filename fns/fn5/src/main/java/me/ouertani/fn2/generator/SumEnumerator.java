/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ouertani.fn2.generator;

import java.util.function.Function;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;


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
    public <A> Iteratee<Integer, A> apply(Iteratee<Integer, A> t) {
       switch (t.onState()) {
            case CONT:
                Iteratee.Cont<Integer, Integer> k = (Iteratee.Cont) t;
                switch (value.state()) {
                    case EL:
                        Input.El<Integer> el = (Input.El<Integer>) value;
                        Function<Input<Integer>, Iteratee<Integer, Integer>> ks = new Function<Input<Integer>, Iteratee<Integer, Integer>>() {

                            @Override
                            public Iteratee<Integer, Integer> apply(Input<Integer> t) {
                                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                        };
                        return new Iteratee.Cont(ks);

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
                Iteratee.Done<Integer, A> d = (Iteratee.Done<Integer, A>) t;
                return d;
            case ERROR:
                Iteratee.Error e = (Iteratee.Error) t;
                return e;
            default:
                throw new IllegalStateException();
        }
    }

}
