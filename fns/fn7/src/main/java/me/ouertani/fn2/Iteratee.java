package me.ouertani.fn2;

import java.util.concurrent.Future;
import java.util.function.Function;

/**
 *
 * @author slim
 * @param <E>
 * @param <A>
 */
public interface Iteratee<E, A> {

    <B> Future<B> handle(Function<Iteratee<E, A>, Future<B>> step);

    enum StepState {

        Done, CONT, ERROR
    }

    default StepState onState() {
        return StepState.CONT;
    }

    class Done<E, A> implements Iteratee<E, A> {

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
            this(a, Input.EMPTY);
        }

        @Override
        public <B> Future<B> handle(Function<Iteratee<E, A>, Future<B>> folder) {
            Iteratee<E, A> done = new Iteratee.Done(a, input);
            return folder.apply(done);
        }

        @Override
        public String toString() {
            return "Done{" + "a=" + a + ", input=" + input + '}';
        }

    }

    class Cont<E, A> implements Iteratee<E, A> {

        Function<Input<E>, Iteratee<E, A>> k;

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
        public <B> Future<B> handle(Function<Iteratee<E, A>, Future<B>> folder) {
            Iteratee<E, A> s = new Iteratee.Cont(k);
            return folder.apply(s);
        }

    }

    class Error<E> implements Iteratee<E, Object> {

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
        public <B> Future<B> handle(Function<Iteratee<E, Object>, Future<B>> folder) {
            Iteratee<E, Object> s = new Iteratee.Error(msg, input);
            return folder.apply(s);
        }

        @Override
        public String toString() {
            return "Error{" + "msg=" + msg + ", input=" + input + '}';
        }

    }
}
