package zxxz.timtab;

/**
 * Public API for Util Class.
 * Created by zxxz on 03/07/16.
 */

public interface RandomValueProvider{

        /**
         * @return randomly created integer value
         */
    int getRandom();
    int getRandomButton();
    int getRandomOffset();
    Pair getRandomData();
    boolean getRandomBoolean();
    void reset();
}
