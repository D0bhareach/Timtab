package zxxz.timtab;

/**
 * Holder for two <code>Numbers</code> with overridden equals(Object o) method
 * and public getters for properties. Used by @see{TimtabFrame.Util.initQueue}
 * to build ArrayBlockingQueue<E>, and @see{TimtabFrame.Util.fillTail}
 * to offer new Pair to end of the Queue.
 * Than @see{TibtabFrame} use that Queue of pairs to get random and unique
 * pair of integers. Integers are used as factorials in @see{reset()}.
 * Created by zxxz on 28/06/16.
 */
public class Pair<T extends Number> {

    private final T first;
    private final T second;

    /**
     * Pair is vital object so it must be constructed or program shall exit.
     * @param a T extends Number
     * @param b T extends Number
     * @throws NullPointerException either a or b is null
     */
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
     * Checks Pairs are instances of Pair and properties of objects equals.
     * Return true if  a == other.a  and  b == other.b Also is true if
     * other objects fields in upside down order Pair(2,4) == Pair(4,2).
     * @param other cast to Pair<T extends Number>
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
