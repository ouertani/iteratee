package enumeratee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 *
 * @author slim
 * @param <E>
 * @param <A>
 */
public interface Iteratee<E, A> {

    public <B> Future<B> fold(Function< Step<E, A>, Future<B>> folder);

    public default Future<A> run() {return null;}
    public default  Object a(){
        if (this instanceof Step.Done) {
            Step.Done<E, A> d = (Step.Done) this;
            return CompletableFuture.supplyAsync(() -> d.getA());
        } else if (this instanceof Step.Error) {
            Step.Error e = (Step.Error) this;
            String msg = e.getMsg();
            return CompletableFuture.supplyAsync(() -> {
                throw new RuntimeException(msg);
            });
        } else {
            Step.Cont<E, A> c = (Step.Cont) this;

            Function< Step<E, A>, Future<A>> fs = (Step<E, A> t) -> {
                if (t instanceof Step.Done) {
                    Step.Done<E, A> d = (Step.Done) t;
                    return CompletableFuture.supplyAsync(() -> d.getA());
                } else if (t instanceof Step.Error) {
                    Step.Error e1 = (Step.Error) t;
                    String msg = e1.getMsg();
                    return CompletableFuture.supplyAsync(() -> {
                        throw new RuntimeException(msg);
                    });
                } else {
                    return CompletableFuture.supplyAsync(() -> {
                        throw new RuntimeException("diverging iteratee after Input.EOF");
                    });
                }
            };
            Function<Input<E>, Iteratee<E, A>> f = c.getNext();
            return f.apply(new Input.EOF()).fold(fs);
        }
    }

}
