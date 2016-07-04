package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import zxxz.timtab.Pair;

import java.util.Arrays;
import java.util.Random;


@Test(enabled = true, singleThreaded = true)
public class Tests {

    final MockUtil util = new MockUtil();


    /**
     * Test if random int is in specified range.
     */
    @Test(enabled = true)
    public void testRandom() {
        for(int i = 0; i < 1000; i++) {
            int x = util.getRandom();
            Assert.assertTrue(2 <= x && x < 10);
        }
    }

    /**
     * Create random numbers and compare their randomness.
     */

    @Test(enabled = false)
    public void testRandomness() {
        int[] ints = new int[8];
        for(int i = 0; i < 8000; i++) {
            int x = util.getRandom();
            switch(x) {
                case 2: ints[0] = ints[0] + 1; break;
                case 3: ints[1] = ints[1] + 1; break;
                case 4: ints[2] = ints[2] + 1; break;
                case 5: ints[3] = ints[3] + 1; break;
                case 6: ints[4] = ints[4] + 1; break;
                case 7: ints[5] = ints[5] + 1; break;
                case 8: ints[6] = ints[6] + 1; break;
                case 9: ints[7] = ints[7] + 1; break;

            }
        }
        Assert.assertTrue(
                Arrays.stream(ints)
                        .filter(x -> x > 1200)
                        .count() <= 2);
    }

    @Test(enabled = true)
    public void testRandomButton() {
        int[] ints = new int[3];
        for(int i = 0; i < 3000; i++) {
            int x = util.getRandomButton(new Random().nextInt(3));
            switch(x) {
                case 0: ints[0] = ints[0] + 1; break;
                case 1: ints[1] = ints[1] + 1; break;
                case 2: ints[2] = ints[2] + 1; break;
            }
        }
        Assert.assertTrue(
                Arrays.stream(ints)
                        .filter(x -> x > 1050)
                        .count() < 2);
    }

    /**
     * Test Pair.equals(Object)method.
     * Test for exact Equality and flip object Equality.
     * Test for not Equality with not equal Object.
     * Test for Equality when all parameters of Objects are
     * exactly the same.
     */
    @Test(enabled = false)
    public void testPairs() {
        Pair<Integer> p = new Pair<>(2, 4);
        //must be true
        Assert.assertTrue(p.equals(new Pair<>(2, 4)));
        Assert.assertTrue(p.equals(new Pair<>(4, 2)));
        //must be false
        Assert.assertFalse(p.equals(new Pair<>(2, 3)));
        Assert.assertFalse(p.equals(new Pair<>(3, 2)));
        Assert.assertFalse(p.equals(new Pair<>(3, 6)));
        //must be true
        p = new Pair<>(2, 2);
        Assert.assertTrue(p.equals(new Pair<>(2, 2)));
    }
}//Tests
