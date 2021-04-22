package org.psutil4j.utils.tuples;

import lombok.Getter;

/**
 * Convenience class for returning multiple objects from methods.
 *
 * @param <A> Type of the first element
 * @param <B> Type of the second element
 */
@Getter
public class Pair<A, B> {

    private final A a;
    private final B b;

    /**
     * Create a pair and store two objects.
     *
     * @param a the first object to store
     * @param b the second object to store
     */
    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

}
