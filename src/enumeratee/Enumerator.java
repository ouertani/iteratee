package enumeratee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 *
 * @author slim
 */
public interface Enumerator<E> {
  public <A> Future<Iteratee<E, A>> apply(Iteratee<E, A> f);
  
  static <T> Enumerator<T> apply(Iterable<T> e) {
      return new Enumerator<T> () {

          @Override
          public <A> Future<Iteratee<T, A>> apply(Iteratee<T, A> f) {
             return CompletableFuture.supplyAsync(() -> f);
          }
          
      };
  }
}
