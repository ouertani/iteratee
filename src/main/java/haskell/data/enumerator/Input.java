/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haskell.data.enumerator;

import java.util.Iterator;

/**
 *
 * @author slim
 */
public interface Input<E> extends Iterator<E> {

    static final Input EOF = new EOF();

    static <T> Input<T> el(T t) {
        return new El(t);
    }

    /**
     * An input element
     */
    public class El<E> implements Input<E> {

        private final E e;

        public El(E e) {
            this.e = e;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public E next() {
            return e;
        }
    }

    /**
     * An end of file input
     */
    public static class EOF implements Input {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new UnsupportedOperationException("No Elm when EOF.");
        }
    }
}
