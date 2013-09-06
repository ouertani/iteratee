package me.ouertani.fn2;

import java.util.function.Function;

/**
 *
 * @author slim
 * @param <E>
 * @param <A>
 */
public interface Step<E,A>  extends  Iteratee<E,A>{
    enum StepState {
        Done, CONT, ERROR
    }
    StepState onState();
    
    class Done<E,A> implements Step<E,A> , Iteratee<E,A>  {

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
        public <B> B handle(Function<Step<E, A>, B> folder) {
           Step<E, A> done = new Step.Done(a, input);
           return folder.apply(done);
        }

        @Override
        public String toString() {
            return "Done{" + "a=" + a + ", input=" + input + '}';
        }
        
    }
    class Cont<E,A> implements Step<E,A> {

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
        public <B> B handle(Function<Step<E, A>, B> folder) {
           Step<E, A> s =new Step.Cont(k);
           return folder.apply(s);
        }
        
        
    }
    class Error<E> implements Step<E,Object>, Iteratee<E,Object> {

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
        public <B> B handle(Function<Step<E, Object>, B> folder) {
          Step<E, Object> s = new   Step.Error(msg, input);
          return folder.apply(s);
        }

        @Override
        public String toString() {
            return "Error{" + "msg=" + msg + ", input=" + input + '}';
        }
        
    }
}
