package me.ouertani.fn2;

import java.util.function.Function;

/**
 *
 * @author slim
 * @param <E>
 */
public interface Iteratee<E,A> {  
    <B>  B handle(Function<Iteratee<E, A >  , B> step);  
     enum StepState {
        Done, CONT, ERROR
    }
   default StepState onState() {
       return StepState.CONT;
    }
   
    Function<Input<E>, Iteratee<E, A>> handler();
  
   default Cont<E,A> asCont(){
       return (Cont<E,A>) this;
   }
    
    class Done<E,A> implements  Iteratee<E,A>  {

       final A a;
       final Input<E> input;
        @Override
        public StepState onState() {
          return StepState.Done;
        }

        public A getA() {
            return a;
        }

        public Input<E> getInput() {
            return input;
        }

        public Done(A a, Input<E> e) {
            this.a = a;
            this.input = e;
        }

        public Done(A a) {
            this(a,Input.EMPTY);
        }
        

        @Override
        public <B> B handle(Function<Iteratee<E, A>, B> folder) {
           Iteratee<E, A> done = new Iteratee.Done(a, input);
           return folder.apply(done);
        }

        @Override
        public String toString() {
            return "Done{" + "a=" + a + ", input=" + input + '}';
        }

        @Override
        public Function<Input<E>, Iteratee<E, A>> handler() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    class Cont<E,A> implements Iteratee<E,A> {

        Function<Input<E>, Iteratee<E,A>> k;

        public Cont(Function<Input<E>, Iteratee<E, A>> k) {
            this.k = k;
        }

        public Function<Input<E>, Iteratee<E, A>> getK() {
            return k;
        }

        public void setK(Function<Input<E>, Iteratee<E, A>> k) {
            this.k = k;
        }
        
        
         
        @Override
        public StepState onState() {
          return StepState.CONT;
        }

        @Override
        public String toString() {
            return "Cont{" + "k=" + k + '}';
        }

        @Override
        public <B> B handle(Function<Iteratee<E, A>, B> folder) {
           Iteratee<E, A> s =new Iteratee.Cont(k);
           return folder.apply(s);
        }

        @Override
        public Function<Input<E>, Iteratee<E, A>> handler() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        
    }
    class Error<E> implements  Iteratee<E,Object> {

        final String msg;
        final Input<E> input;
        
        public Error(String msg, Input<E> input) {
            this.msg = msg;
            this.input = input;
        }
        @Override
        public StepState onState() {
          return StepState.ERROR;
        }

        public String getMsg() {
            return msg;
        }

        public Input<E> getInput() {
            return input;
        }

        @Override
        public <B> B handle(Function<Iteratee<E, Object>, B> folder) {
          Iteratee<E, Object> s = new   Iteratee.Error(msg, input);
          return folder.apply(s);
        }

        @Override
        public String toString() {
            return "Error{" + "msg=" + msg + ", input=" + input + '}';
        }

        @Override
        public Function<Input<E>, Iteratee<E, Object>> handler() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
