package enumeratee;

import java.util.function.Function;

/**
 *
 * @author slim
 * @param <E>
 * @param <A>
 */
public interface Step<E, A> {

    public class Done<E, A> implements Step<E, A> {

        private final A a;
        private final Input<E> remaining;

        public Done(A a, Input<E> remaining) {
            this.a = a;
            this.remaining = remaining;
        }

        public A getA() {
            return a;
        }

        public Input<E> getRemaining() {
            return remaining;
        }

    }

    public class Error<E> implements Step<E, Object> {

        private final String msg;
        private final Input<E> remaining;

        public Error(String msg, Input<E> remaining) {
            this.msg = msg;
            this.remaining = remaining;
        }

        public String getMsg() {
            return msg;
        }

        public Input<E> getRemaining() {
            return remaining;
        }

    }

    public class Cont<E, A> implements Step<E, A> {

        private final Function<Input<E>, Iteratee<E, A>> next;

        public Cont(Function<Input<E>, Iteratee<E, A>> next) {
            this.next = next;
        }

        public Function<Input<E>, Iteratee<E, A>> getNext() {
            return next;
        }
        
    }
    
}
