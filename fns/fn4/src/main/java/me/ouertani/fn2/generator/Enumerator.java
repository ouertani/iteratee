
package me.ouertani.fn2.generator;

import java.util.function.Function;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;
import me.ouertani.fn2.Step;

/**
 *
 * @author slim
 */
public interface Enumerator<E> {
   <A> Step<E, A> apply(Step<E,A> it) ;
   
   default <B> Input<B> run(Step<E,B> it){
       switch(it.onState()){
           case CONT :
               return run(this.apply(it));
           case ERROR :
               Step.Error e =(Step.Error)it;
               throw new RuntimeException(e.getMsg());
           case Done :
             Step.Done<E,B> d =(Step.Done) it;  
             B a = d.getA();
             
             
             return Input.el(a);
           default:
               throw  new IllegalStateException();
       }
   }
   
   static <B> Enumerator<B> enumInput(Input<B> input) {
       return new Enumerator<B>() {

           @Override
           public <A> Step<B, A> apply(Step<B, A> it) {
              Function<Step<B, A >  , A> step = new Function<Step<B, A>, A>() {

                  @Override
                  public A apply(Step<B, A> t) {
                   switch(t.onState()){
                       case CONT : 
                           Step.Cont<B, A> c = (Step.Cont)t;
                           Function<Input<B>, Iteratee<B, A>> k = c.getK();
                           Iteratee<B, A> apply = k.apply(input);
                           return (A)apply;
                       default: 
                           return (A)t;
                   }
                  }
              };
              A handle = it.handle(step);
              return (Step<B, A>)handle;
           }
       };
   }

    /**
     *
     * @param <B>
     * @param input
     * @return
     */
    static <B> Enumerator<B> enumInput(Input<B>[] input) {
      
        final Input[] in =  new Input[input.length+1];
        System.arraycopy(input, 0, in, 0, input.length);
        in[input.length]= Input.EOF;
       return new Enumerator<B>() {
           int hs =0;
           @Override
           public <A> Step<B, A> apply(Step<B, A> it) {
              Function<Step<B, A >  , A> step = new Function<Step<B, A>, A>() {

                  @Override
                  public A apply(Step<B, A> t) {
                      System.out.println("hs"+ hs);
                   switch(t.onState()){
                       case CONT : 
                           if(hs >= in.length){
                                return (A)t;
                           }
                           Step.Cont<B, A> c = (Step.Cont)t;
                           Function<Input<B>, Iteratee<B, A>> k = c.getK();
                           Iteratee<B, A> apply = k.apply(in[hs++]);
                           return (A)apply;
                       default: 
                           return (A)t;
                   }
                  }
              };
              A handle = it.handle(step);
              return (Step<B, A>)handle;
           }
       };
       
   }
   
}
