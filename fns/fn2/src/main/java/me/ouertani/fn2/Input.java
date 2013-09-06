package me.ouertani.fn2;

/**
 *
 * @author slim
 */
public interface Input<E> {

    enum InputState {

        EOF, Empty, EL
    }

    InputState state();

    Input EOF = new Input() {

        @Override
        public InputState state() {
            return InputState.EOF;
        }
    };
    Input EMPTY = new Input() {

        @Override
        public InputState state() {
            return InputState.Empty;
        }
    };
    
    static <E> Input<E> el(E e ) {
        return new El(e);
    };

    class El<E> implements Input<E> {

        final E e;

        public El(E e) {
            this.e = e;
        }

        @Override
        public InputState state() {
            return InputState.EL;
        }

        public E getE() {
            return e;
        }
    };
}
