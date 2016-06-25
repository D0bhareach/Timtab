package tests;

import org.testng.annotations.Test;
import zxxz.timtab.TimtabFrame;


/**
 * Created by zxxz on 25/06/16.
 */
@Test(enabled = true, singleThreaded = true)
public class Tests {
    final TimtabFrame frame = new TimtabFrame();
   // final TimtabFrame.Util util = frame.new Util();
    // TODO: 25/06/16  after tests make Util private




    @Test(enabled = false)
    public void testRandom(){
        for(int i=0; i<1000; i++){
           // int x = util.getRandom();
            //Assert.assertTrue(2<=x &&x<10);
        }
    }
   }
