/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumeratee;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 *
 * @author slim
 */
public class NewClass {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> numbers = Arrays.asList(1,2,3);
        Enumerator<Integer> sumEnumerator = Enumerator.apply(numbers);
        Iteratee<Integer, Integer> it = new Iteratee<Integer, Integer>() {

            @Override
            public <B> Future<B> fold(Function<Step<Integer, Integer>, Future<B>> folder) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        Future<Iteratee<Integer, Integer>> apply = sumEnumerator.apply(it);
        apply.get();
    }
    
}
