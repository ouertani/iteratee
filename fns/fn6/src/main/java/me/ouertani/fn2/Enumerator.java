
package me.ouertani.fn2;

import java.util.function.Function;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;

/**
 *
 * @author slim
 */
public interface Enumerator<E> {
   <A> Iteratee<E, A> apply(Iteratee<E,A> it) ;
   
   default <B> Input<B> run(Iteratee<E,B> it){
       System.out.println("run it" + it);
       switch(it.onState()){
           case CONT :
               return run(this.apply(it));
           case ERROR :
               Iteratee.Error e =(Iteratee.Error)it;
               throw new RuntimeException(e.getMsg());
           case Done :
             Iteratee.Done<E,B> d =(Iteratee.Done) it;  
             B a = d.getA();
               System.out.println("run a"+ a);
             
             return Input.el(a);
           default:
               throw  new IllegalStateException();
       }
   }
   
   static <B> Enumerator<B> enumInput(Input<B> input) {
       return new Enumerator<B>() {

           @Override
           public <A> Iteratee<B, A> apply(Iteratee<B, A> it) {
              Function<Iteratee<B, A >  , A> step = new Function<Iteratee<B, A>, A>() {

                  @Override
                  public A apply(Iteratee<B, A> t) {
                   switch(t.onState()){
                       case CONT : 
                           Iteratee.Cont<B, A> c = (Iteratee.Cont)t;
                           Function<Input<B>, Iteratee<B, A>> k = c.getK();
                           Iteratee<B, A> apply = k.apply(input);
                           return (A)apply;
                       default: 
                           return (A)t;
                   }
                  }
              };
              A handle = it.handle(step);
              return (Iteratee<B, A>)handle;
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
           public <A> Iteratee<B, A> apply(Iteratee<B, A> it) {
              Function<Iteratee<B, A >  , A> step = new Function<Iteratee<B, A>, A>() {

                  @Override
                  public A apply(Iteratee<B, A> t) {
                      System.out.println("hs"+ hs);
                   switch(t.onState()){
                       case CONT : 
                           if(hs >= in.length){
                                return (A)t;
                           }
                           Iteratee.Cont<B, A> c = (Iteratee.Cont)t;
                           Function<Input<B>, Iteratee<B, A>> k = c.getK();
                           Iteratee<B, A> apply = k.apply(in[hs++]);
                           return (A)apply;
                       default: 
                           return (A)t;
                   }
                  }
              };
              A handle = it.handle(step);
              return (Iteratee<B, A>)handle;
           }
       };
       
   }
   
}
