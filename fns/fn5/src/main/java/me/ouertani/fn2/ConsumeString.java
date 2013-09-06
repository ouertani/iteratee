package me.ouertani.fn2;

import java.util.Arrays;
import java.util.function.Function;

/**
 *
 * @author slim
 */
public class ConsumeString implements Iteratee<String, String[]> {

    final String[] s;
    final ConsumeString self;

    public ConsumeString() {
        this(new String[0]);
    }

    public ConsumeString(String[] s) {
        this.s = s;
        this.self = this;
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

//    public <B> B handleé(Function<Function<Input<String>, Iteratee<String>>, B> cont) {
//       Function<Input<String>, Iteratee<String>> f =  new  Function<Input<String>, Iteratee<String>> () {
//
//           @Override
//           public Iteratee<String> apply(Input<String> e) {
//              switch (e.state()) {
//            case EL:
//                Input.El<String> el = (Input.El) e;
//                String elem = el.getE();
//                String[] copyOf = Arrays.copyOf(s, s.length + 1);
//                copyOf[copyOf.length-1] = elem;
//                return new ConsumeString(copyOf);
//            default:
//                return self;
//        }
//           }
//           
//       };
//       return cont.apply(f);
//    }
    @Override
    public <B> B handle(Function<Iteratee<String, String[]>, B> step) {
        Function<Input<String>, Iteratee<String, String[]>> k = new Function<Input<String>, Iteratee<String, String[]>>() {

            @Override
            public Iteratee<String, String[]> apply(Input<String> e) {
                switch (e.state()) {
                    case EL:
                        Input.El<String> el = (Input.El) e;
                        String elem = el.getE();
                        String[] copyOf = Arrays.copyOf(s, s.length + 1);
                        copyOf[copyOf.length - 1] = elem;
                        return new ConsumeString(copyOf);
                    case EOF:
                        return new Iteratee.Done(s);
                    case Empty:
                    default:
                        return new ConsumeString(s);
                }
            }

        };
        Iteratee.Cont<String, String[]> stCont = new Iteratee.Cont<String, String[]>(k);
        return step.apply(stCont);
    }

}
