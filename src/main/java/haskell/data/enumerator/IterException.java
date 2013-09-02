package haskell.data.enumerator;

/**
 * This is idiomatic java way of dealing with exceptions.
 * @author hemantka
 */
public class IterException extends RuntimeException{
    public IterException(Exception e){
        super(e);
    }
    public IterException(String message){
        super(message);
    }
    public IterException(){        
    }
    
}
