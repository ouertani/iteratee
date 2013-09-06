package me.ouertani.fn2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.ouertani.fn2.Input;
import me.ouertani.fn2.Iteratee;

/**
 *
 * @author slim
 */
public interface Enumerator<E> {

    <A> Iteratee<E, A> apply(Iteratee<E, A> it);

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
                Function<Iteratee<B, A>, Future<A>> step;
                step = new Function<Iteratee<B, A>, Future<A>>() {

                    @Override
                    public Future<A> apply(Iteratee<B, A> t) {
                        switch (t.onState()) {
                            case CONT:


                                return CompletableFuture.supplyAsync(new Supplier<A>() {

                                @Override
                                public A get() {
                                    Iteratee.Cont<B, A> c = (Iteratee.Cont) t;
                                    Function<Input<B>, Iteratee<B, A>> k = c.getK();
                                    Iteratee<B, A> apply = k.apply(input);
                                    return (A) apply;
                                }
                            });

                            default:
                                return CompletableFuture.supplyAsync(new Supplier<A>() {

                                @Override
                                public A get() {
                                    return (A) t;
                                }
                            });

                        }
                    }
                };
                Future<A> handle = it.handle(step);
                return (Iteratee<B, A>) handle;
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
        final Input[] in = new Input[input.length + 1];
                        System.arraycopy(input, 0, in, 0, input.length);
                        in[input.length] = Input.EOF;
        final AtomicInteger hs = new AtomicInteger(0);
        return new Enumerator<B>() {

           
            @Override
            public <A> Iteratee<B, A> apply(Iteratee<B, A> it) {

                Function<Iteratee<B, A>, Future<A>> step = new Function<Iteratee<B, A>, Future<A>>() {

                    @Override
                    public Future<A> apply(Iteratee<B, A> t) {
                        System.out.println("hs.get"+hs.get());
                

                        switch (t.onState()) {
                            case CONT:
                                if(hs.get() >= in.length){ 
                                    System.out.println("hs.get"+hs.get());
                                    return CompletableFuture.supplyAsync(new Supplier<A>() {
                                    @Override
                                    public A get() {
                                        return (A) t;
                                    }
                                });
                                }
                                
                              
                                    return CompletableFuture.supplyAsync(new Supplier<A>() {

                                    @Override
                                    public A get() {
                                        Iteratee.Cont<B, A> c = (Iteratee.Cont) t;
                                        Function<Input<B>, Iteratee<B, A>> k = c.getK();
                                        Iteratee<B, A> apply = k.apply(in[hs.getAndIncrement()]);
                                        return (A) apply;
                                    }
                                });
                           
                                

                                

                            default:
                                return CompletableFuture.supplyAsync(new Supplier<A>() {

                                @Override
                                public A get() {
                                    return (A) t;
                                }
                            });
                        }
                    }
                };
                Future<A> handle = it.handle(step);
                try {
                    return (Iteratee<B, A>) handle.get();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new RuntimeException();
                }
            }
        };

    }

}
