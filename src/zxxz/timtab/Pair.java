package zxxz.timtab;

/**
 * Used to hold two Numbers.
 * Holder for two numbers with overridden equals method
 * and public getters for properties.
 * Used by TimtabFrame.Util to build ArrayBlockingQueue<Pair<T>>,
 * TibtabFrame use that Queue of Pair to get random pair of integers
 * as factorials in CreateText() method.
 * Created by zxxz on 28/06/16.
 */
public class Pair<T extends Number> {
    private final T first;
    private final T second;

    public Pair(T a, T b) throws NullPointerException {
        if(a == null && b == null) {
            throw new NullPointerException();
        } else {
            first = a; second = b;
        }
    }

    public T getFirst() {return first;}

    public T getSecond() {return second;}

    @Override
    public int hashCode() {
        int a = first.intValue();
        int b = second.intValue();
        int t;
        if(a > b) {
            t = b;
            b = a;
            a = t;
        }
        if(a == 0) a = 1;
        return ((b * b) + 7134) / a;

    }


    /**
     * If objects are instances of the same Class and check if properties of objects equals.
     * Return true if  a == other.a  and  b == other.b Also is true if other  object is
     * in upside down order Pair(2,4) == Pair(4,2). Check it as a pair of factors.
     *
     * @param other <T extends Number>
     * @return true if other Pair and this Pair are equal.
     */
    @Override
    public boolean equals(Object other) {
        Pair<T> o;
        if(other == null && !other.getClass().isInstance(this)) {
            return false;
        } else {
            o = (Pair<T>) other;
            return (first.equals(o.getFirst()) || first.equals(o.getSecond())) &&
                    (second.equals(o.getSecond()) || second.equals(o.getFirst())) ?
                    true : false;
        }
    }

}
