package org.psutil4j.utils.tuples;


import lombok.Getter;

/**
 * Convenience class for returning multiple objects from methods.
 *
 * @param <A> Type of the first element
 * @param <B> Type of the second element
 * @param <C> Type of the third element
 * @param <D> Type of the fourth element
 * @param <E> Type of the fifth element
 */
@Getter
public class Quintet<A, B, C, D, E> {

    private final A a;
    private final B b;
    private final C c;
    private final D d;
    private final E e;

    /**
     * Create a quintet and store five objects.
     *
     * @param a the first object to store
     * @param b the second object to store
     * @param c the third object to store
     * @param d the fourth object to store
     * @param e the fifth object to store
     */
    public Quintet(A a, B b, C c, D d, E e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }

}
