package tests;

import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Created by zxxz on 25/06/16.
 */
@Test(enabled = true, singleThreaded = true)
public class Tests {

    final MockUtil util = new MockUtil();


    /**
     * Test if random int is in specified range.
     */
    @Test(enabled = false)
    public void testRandom(){
        for(int i=0; i<1000; i++){
            int x = util.getRandom();
            Assert.assertTrue(2<=x &&x<10);
        }
    }

    /**
     * Create random numbers and compare their randomness.
     */

    @Test(enabled = false)
    public void testRandomness(){
        int[] ints = new int[7];
        for(int i=0; i<1000; i++){
            int x = util.getRandom();
            switch (x){
                case 2: ints[0] = ints[0]++;
                case 3: ints[1] = ints[1]++;
                case 4: ints[2] = ints[2]++;
                case 5: ints[3] = ints[3]++;
                case 6: ints[4] = ints[4]++;
                case 7: ints[5] = ints[5]++;
                case 8: ints[6] = ints[6]++;
                case 9: ints[7] = ints[7]++;

            }
        }

        
    }
}//Tests
