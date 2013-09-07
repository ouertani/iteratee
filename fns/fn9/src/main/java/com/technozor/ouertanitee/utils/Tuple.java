package me.ouertani.fn2.utils;

/**
 *
 * @author ouertani
 */
public class Tuple<A,B> {
    private A a;
    private B b;

    static <A,B> Tuple<A,B> of(A a, B b) {
       return new Tuple(a,b);
    }
    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
    
    
}
