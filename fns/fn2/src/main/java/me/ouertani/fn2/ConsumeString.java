package me.ouertani.fn2;

import java.util.Arrays;


/**
 *
 * @author slim
 */
public class ConsumeString implements Iteratee<String> {

    final String[] s;
    public ConsumeString() {
        this(new String[0]);
    }

    public ConsumeString(String[] s) {
        this.s = s;
    }

    @Override
    public Iteratee<String> handle(Input<String> e) {
        switch (e.state()) {
            case EL:
                Input.El<String> el = (Input.El) e;
                String elem = el.getE();
                String[] copyOf = Arrays.copyOf(s, s.length + 1);
                copyOf[copyOf.length-1] = elem;
                return new ConsumeString(copyOf);
            default:
                return this;
        }
    }

    public String[] getS() {
        return s;
    }

    @Override
    public String toString() {
        System.out.println("----------------------------");
        for (String string : s) {
            System.out.println(string); 
        }
        System.out.println("----------------------------");
        return "Consume";
    }
    
    

}
