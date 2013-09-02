package haskell.data.enumerator.ex;

import haskell.data.enumerator.IterException;
import haskell.data.enumerator.Iteratee;
import haskell.data.enumerator.IterateeImpl;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

class EOL implements Predicate<Character>{
   

    @Override
    public boolean test(Character t) {
         char c = t.charValue();
        return (c == '\r')||(c == '\n');
    }
    
}


class Terminator implements Iteratee<CharBuffer,Integer>{
    public final char[] matches = {'\r','\n'};
    private Iteratee<CharBuffer,Integer> skipNewLinesBoth = new IterateeImpl<>(new HeadsStep(matches));
    private Iteratee<CharBuffer,Integer> skipNewLines = new IterateeImpl<>(new HeadsStep(new char[]{'\n'}));
    int n;


    @Override
    public Iteratee<CharBuffer, Integer> apply(Iterator<CharBuffer> stream) throws IterException {
        skipNewLinesBoth = skipNewLinesBoth.apply(stream);
        if(!skipNewLinesBoth.toContinue()){//skipLines is ready to yield
            n = skipNewLinesBoth.yield();
            if(n == 0){
                skipNewLines = skipNewLines.apply(stream);
                if(!skipNewLines.toContinue()){
                    n = skipNewLines.yield();
                }
            }
        }
        return this;
    }

    @Override
    public boolean toContinue() {
        return false;
    }

    @Override
    public Integer yield() {
        return n;
    }     
}


class Accumulator implements Function
        <   Function<Integer, String>, List<String> > , 
        Iteratee<CharBuffer, List<String>>{
    private List<String> lines;
   
    private boolean needInput;
 
    private StringBuilder strb;
    Function<Integer, String> func;
    
    public Accumulator(){
        lines = new ArrayList<>();
        strb = new StringBuilder();
        needInput = true;
    }
    
    private void eval() {
        String nextPortion = func.apply(0);
        System.out.println("nextPortion : " +nextPortion);
            if(!nextPortion .equals("0"))
                strb.append(nextPortion);
            else {
                return;
            }
  
    
        //break resulted because of a geniune line break
        lines.add(strb.toString());
        //now reset the string builder to zero
        strb.setLength(0);
    }

    
    private List<String> getValue() {
        return lines;
    }

    @Override
    public Iteratee<CharBuffer, List<String>> apply(Iterator<CharBuffer> stream)throws IterException  {
        if(!stream.hasNext()){            
            this.needInput = false;
        }
        return this;
    }

    @Override
    public boolean toContinue() {
        return this.needInput;
    }

    @Override
    public List<String> yield() {
        return this.getValue();
    }

 

    @Override
    public List<String> apply(Function<Integer, String> t) {
      this.func = t;
         eval();
         return this.getValue();
    }

    

}


/**
 * Corresponds to the readLines function on page 57.
 * @author hemantka
 */
public class ReadLines implements Iteratee<CharBuffer, List<String>> {

    public final char[] matches = {'\r','\n'};
    private  Iteratee<CharBuffer,String> breaker = new IterateeImpl<>(new BreakStep(new EOL()));
    private  Terminator terminator = new Terminator();
    private  Accumulator accumulator = new Accumulator();
    
    
    
    public ReadLines(){
    }

    @Override
    public Iteratee<CharBuffer, List<String>> apply(Iterator<CharBuffer> stream) throws IterException {
        if(!stream.hasNext()){
                  Function f =  new Function<Integer, String>  () {

                    @Override
                    public String apply(Integer t) {
                        return t.toString();
                    }

                    
       };
            List apply = accumulator.apply(f);
           
            accumulator = (Accumulator)accumulator.apply(stream);
            
            return this;
        }
            
        breaker = breaker.apply(stream);
        if(!breaker.toContinue()){
            String s = breaker.yield();
            terminator = (Terminator) terminator.apply(stream);
            if(!terminator.toContinue()){
            int i = terminator.yield();
             Function f =  new Function<Integer, String>  () {

                    @Override
                    public String apply(Integer t) {
                        return s;
                    }

                    
             };
                List apply = accumulator.apply(f);
                 
                accumulator = (Accumulator)accumulator.apply(stream);
            }
        }
        
        return this;
    }

    @Override
    public boolean toContinue() {
        return accumulator.toContinue();
    }

    @Override
    public List<String> yield() {
        return accumulator.yield();
    }

    
}
