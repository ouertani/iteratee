package me.ouertani.fn2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;
import me.ouertani.fn2.utils.CollectionUtils;
import me.ouertani.fn2.utils.Tuple;

/**
 *
 * @author slim
 */
public interface Enumerator<E> {

    <A> Iteratee<E, A> apply(Iteratee<E, A> it);

    static <E> Enumerator<E> empty() {
        return new Enumerator<E>() {
            @Override
            public <A> Iteratee<E, A> apply(Iteratee<E, A> it) {
                return it;
            }
        };
    }

    default <B> Input<B> run(Iteratee<E, B> it) {
        System.out.println("run it" + it);
        switch (it.onState()) {
            case CONT:
                return run(this.apply(it));
            case ERROR:
                Iteratee.Error e = (Iteratee.Error) it;
                throw new RuntimeException(e.getMsg());
            case Done:
                Iteratee.Done<E, B> d = (Iteratee.Done) it;
                B a = d.getA();
                System.out.println("run a" + a);

                return Input.el(a);
            default:
                throw new IllegalStateException();
        }
    }

    static <B> Enumerator<B> enumInput(Input<B> input) {
        return new Enumerator<B>() {

            @Override
            public <A> Iteratee<B, A> apply(Iteratee<B, A> it) {
                Function<Iteratee<B, A>, Iteratee<B, A>> step = new Function<Iteratee<B, A>, Iteratee<B, A>>() {

                    @Override
                    public Iteratee<B, A> apply(Iteratee<B, A> t) {
                        switch (t.onState()) {
                            case CONT:
                                Iteratee.Cont<B, A> c = (Iteratee.Cont) t;
                                Function<Input<B>, Iteratee<B, A>> k = c.getK();
                                return k.apply(input);
                                
                            default:
                                return t;
                        }
                    }
                };
               return it.handle(step);
            }
        };
    }

    /**
     *
     * @param <B>
     * @param input
     * @return
     */
    static <B> Enumerator<B> enumInput(final Input<B>[] input) {

        switch (input.length) {
            case 0:
                return Enumerator.empty();
            case 1:
                return new Enumerator<B>() {
                @Override
                public <A> Iteratee<B, A> apply(Iteratee<B, A> i) {
                    Function<Iteratee<B, A>, Iteratee<B, A>> step = new Function<Iteratee<B, A>, Iteratee<B, A>>() {

                        @Override
                        public Iteratee<B, A> apply(Iteratee<B, A> t) {

                            switch (t.onState()) {
                                case CONT:
                                    Iteratee.Cont<B, A> c = (Iteratee.Cont) t;
                                    Function<Input<B>, Iteratee<B, A>> k = c.getK();
                                    Iteratee<B, A> apply = k.apply(input[0]);
                                    return apply;
                                default:
                                    return t;
                            }
                        }
                    };
                    return i.handle(step);
                }
            };
            default:
                
                List<Input<B>> of = Arrays.asList(input);
                return new Enumerator<B>() {
                    @Override
                    public <A> Iteratee<B, A> apply(Iteratee<B, A> it) {
                        return enumSeq(of, it);
                    }
                };
        }

    }

    static <E, A> Iteratee<E, A> enumSeq(List<Input<E>> l, Iteratee<E, A> i) {
        Function<Tuple<Input<E>, Iteratee<E, A>>, Iteratee<E, A>> f = new Function<Tuple<Input<E>, Iteratee<E, A>>, Iteratee<E, A>>() {

            @Override
            public Iteratee<E, A> apply(Tuple<Input<E>, Iteratee<E, A>> t) {
                Input<E> a = t.getA();
                Iteratee<E, A> it = t.getB();
                switch (it.onState()) {
                    case CONT:
                        
                        Function<Input<E>, Iteratee<E, A>> k = it.handler();
                        if(k == null)   System.out.println("it Enu it "+ it);
                        System.out.println("kkkk Enu"+ k);
                        Iteratee<E, A> apply = k.apply(a);
                        return apply;
                    default:
                        return it;
                }
            }

        };
        Iteratee<E, A> leftFold = CollectionUtils.leftFold(l, i, f);
        return leftFold;
    }
}
