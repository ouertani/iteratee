package me.ouertani.fn2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;
import me.ouertani.fn2.utils.CollectionUtils;
import me.ouertani.fn2.utils.ExceptionUtils;
import me.ouertani.fn2.utils.FutureUtils;
import me.ouertani.fn2.utils.Tuple;
import static me.ouertani.fn2.utils.FutureUtils.*;

/**
 *
 * @author slim
 */
public interface Enumerator<E> {

    <A> Future<Iteratee<E, A>> apply(Iteratee<E, A> it);

    static <E> Enumerator<E> empty() {
        return new Enumerator<E>() {
            @Override
            public <A> Future<Iteratee<E, A>> apply(Iteratee<E, A> it) {
                return toFuture(it);
            }
        };
    }

    default <B> Input<B> run(Future<Iteratee<E, B>> fit)  {
        System.out.println("run it" + fit);
        Iteratee<E, B>  it = FutureUtils.fetch(fit);
       System.out.println("run it" + it);
        switch (it.onState()) {
            case CONT:
                Future<Iteratee<E, B>> apply = this.apply(it);
                return run(apply);
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
            public <A> Future<Iteratee<B, A>> apply(Iteratee<B, A> it) {
                Function<Iteratee<B, A>, Future<Iteratee<B, A>>> step = new Function<Iteratee<B, A>, Future<Iteratee<B, A>>>() {

                    @Override
                    public Future<Iteratee<B, A>> apply(Iteratee<B, A> t) {
                        switch (t.onState()) {
                            case CONT:
                                Iteratee.Cont<B, A> c = (Iteratee.Cont) t;
                                Function<Input<B>, Iteratee<B, A>> k = c.getK();
                                Iteratee<B, A> apply = k.apply(input);
                                return toFuture(apply);

                            default:
                                return toFuture(t);
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
                public <A> Future<Iteratee<B, A>> apply(Iteratee<B, A> i) {
                    Function< Iteratee<B, A>, Future<Iteratee<B, A>>> step = new Function<Iteratee<B, A>, Future<Iteratee<B, A>>>() {

                        @Override
                        public Future<Iteratee<B, A>> apply(Iteratee<B, A> t) {

                            switch (t.onState()) {
                                case CONT:

                                    return CompletableFuture.supplyAsync(new Supplier<Iteratee<B, A>>() {

                                    @Override
                                    public Iteratee<B, A> get() {
                                        Iteratee.Cont<B, A> c = (Iteratee.Cont) t;
                                        Function<Input<B>, Iteratee<B, A>> k = c.getK();
                                        Iteratee<B, A> apply = k.apply(input[0]);
                                        return apply;
                                    }
                                });
                                default:
                                    return toFuture(t);
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
                    public <A> Future<Iteratee<B, A>> apply(Iteratee<B, A> it) {
                        return enumSeq(of, it);
                    }
                };
        }

    }

    static <E, A> Future<Iteratee<E, A>> enumSeq(List<Input<E>> l, Iteratee<E, A> i) {
        Function<Tuple<Input<E>, Iteratee<E, A>>, Future<Iteratee<E, A>>> f = new Function<Tuple<Input<E>, Iteratee<E, A>>, Future<Iteratee<E, A>>>() {

            @Override
            public Future<Iteratee<E, A>> apply(Tuple<Input<E>, Iteratee<E, A>> t) {
                Input<E> a = t.getA();
                Iteratee<E, A> it = t.getB();
                switch (it.onState()) {
                    case CONT:

                        Function<Input<E>, Iteratee<E, A>> k = it.handler();
                        if (k == null) {
                            System.out.println("it Enu it " + it);
                        }
                        System.out.println("kkkk Enu" + k);
                        Iteratee<E, A> apply = k.apply(a);
                        return toFuture(apply);
                    default:
                        return toFuture(it);
                }
            }

        };
        Iteratee<E, A> leftFoldM = CollectionUtils.leftFoldM(l, i, f);
        return toFuture(leftFoldM);
    }
}
