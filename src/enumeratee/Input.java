/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumeratee;

/**
 *
 * @author slim
 */
public interface Input<E> {

    /**
     * An input element
     */
    public class El<E> implements Input<E> {

        private final E e;

        public El(E e) {
            this.e = e;
        }

        public E getE() {
            return e;
        }
    }

    /**
     * An empty input
     */
    public static class Empty implements Input {
    }
    /**
     * An end of file input
     */
    public static class EOF implements Input {
    }
}
