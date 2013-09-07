package me.ouertani.fn2.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author ouertani
 */
public interface CollectionUtils {
    
   static <A,B> B leftFold(List<A> l,B initialValue, final Function<Tuple<A,B>,B> folder) {
      B acc = initialValue; 
       for (A a : l) {
         acc = folder.apply(Tuple.of(a, acc));   
       }
      return acc;
    }
}
