package me.ouertani.fn2.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ouertani
 */
public interface FutureUtils {

    static <T> Future<T> toFuture(T t) {
     return CompletableFuture.supplyAsync(new Supplier<T>() {

            @Override
            public T get() {
                return t;
            }
        });
    };
    
    static <T> T fetch(Future<T> f) {
        try {
            return f.get();
        } catch (InterruptedException | ExecutionException ex) {
            ExceptionUtils.<RuntimeException>handle(ex);
            throw  new IllegalStateException();
        } 
    }
}
