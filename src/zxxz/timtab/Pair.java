package zxxz.timtab;

/**
 * Created by zxxz on 28/06/16.
 */
public class Pair<T extends Number> {
    private final T first;
    private final T second;
 public Pair(T a, T b){
     this.first = a;
     this.second = b;
 }
    public T getFirst(){return first;}
    public T getSecond(){return second;}
    @Override
    public int hashCode(){
       return (41+ first.intValue() * second.intValue() +
               first.intValue() + second.intValue());
    }



    @Override
    public boolean equals(Object other){
        Pair<T> o;
        if (other == null && !other.getClass().isInstance(this)) {
            return false;
        } else {
            o = (Pair<T>) other;
            return (first.equals(o.getFirst()) || first.equals(o.getSecond())) &&
                    (second.equals(o.getSecond()) || second.equals(o.getFirst())) ?
                    true : false;
        }// main loop
    }

}
