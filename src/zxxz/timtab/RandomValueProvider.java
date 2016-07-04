package zxxz.timtab;

/**
 * Public API for Util Class.
 * Created by zxxz on 03/07/16.
 */

public interface RandomValueProvider<T>{
    int getRandom();
    int getRandomButton();
    int getRandomOffset();
    T getRandomData();
    boolean getRandomBoolean();
    void reset();
}
