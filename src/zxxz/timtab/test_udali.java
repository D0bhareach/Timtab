package zxxz.timtab;

import java.util.Random;

import static java.lang.Math.random;

/**
 * Created by zxxz on 23/06/16.
 */


public class test_udali {
    static int arr_size = 100;
    static int[] arr = new int[arr_size];
    private final Random rand = new Random(arr_size);

public test_udali(){}
    {
        int i = 0;
        while (i++ < arr_size) {
            int t = (int) (random() * 10);
            if (t < 2) continue;
                arr[i] = t;
        }

    }

    int getRandom() {
        return arr[rand.nextInt()];
    }

    int getRandomButton(int i) {
        switch (i) {
            case 9:
            case 5:
            case 3:
                i = 0;
                break;
            case 4:
            case 2:
            case 7:
                i = 1;
                break;
            case 6:
            case 8:
            case 1:
                i = 2;
                break;
            default:
                i = 0;
        }
        return i;
    }


    public static void main(String[] a) {
        test_udali test = new test_udali();
        int c2 = 0;
        int c3 = 0;
        int c4 = 0;
        int c5 = 0;
        int c6 = 0;
        int c7 = 0;
        int c8 = 0;
        int c9 = 0;

        for (int i = 0;i<arr.length;i++) {
            int t = arr[i];
            switch(t){
                case 2: c2++;
                case 3: c3++;
                case 4: c4++;
                case 5: c5++;
                case 6: c6++;
                case 7: c7++;
                case 8: c8++;
                case 9: c9++;
            }

        }
        System.out.println("2: "+c2);
        System.out.println("3: "+c3);
        System.out.println("4: "+c4);
        System.out.println("5: "+c5);
        System.out.println("6: "+c6);
        System.out.println("7: "+c7);
        System.out.println("8: "+c8);
        System.out.println("9: "+c9);
        System.out.println(test_udali.arr.length);


    }
}
// Implementing Fisher–Yates shuffle
    /*
        static void shuffleArray(int[] ar)
        {
            // If running on Java 6 or older, use `new Random()` on RHS here
            Random rnd = ThreadLocalRandom.current();
            for (int i = ar.length - 1; i > 0; i--)
            {
                int index = rnd.nextInt(i + 1);
                // Simple swap
                int a = ar[index];
                ar[index] = ar[i];
                ar[i] = a;
            }
        }
    }*/

