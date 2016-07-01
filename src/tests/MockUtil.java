package tests;

import zxxz.timtab.Pair;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MockUtil {

    private final int arr_size = 1000;
    private final int queue_size = 5;
    private final int[] arr = new int[arr_size];
    private int tempButNum = 0;
    private final Random rand = new Random();
    private final BlockingQueue<Pair> queue;

    MockUtil() {
        queue = initQueue(queue_size);
    }

    {
        int i = 0;
        while(i < arr_size) {
            int t = rand.nextInt(10);
            if(t < 2) continue;
            arr[i] = t;
            i++;
        }

    }

    BlockingQueue<Pair> getQueue() {return this.queue;}

    BlockingQueue<Pair> initQueue(int size) {
        BlockingQueue<Pair> q = new ArrayBlockingQueue<>(size);
        Thread t = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while(i < size) {
                    Pair<Integer> pair = new Pair<>(getRandom(), getRandom());
                    if(!q.contains(pair))
                        if(q.offer(pair))
                            i++;
                }
            }
        };
        t.start();
        return q;
    }


    void fillTail() {
        new Thread() {
            @Override
            public void run() {
                boolean b = false;
                while(!b) {
                    Pair<Integer> pair = new Pair<>(getRandom(), getRandom());
                    if(!queue.contains(pair))
                        b = queue.offer(pair);
                }
            }
        }.start();

    }


    int getRandom() {
        return arr[rand.nextInt(arr_size)];
    }


    int getRandomButton() {
        int i = getRandom();

        switch(i) {
            case 9: case 5: case 1: i = 0; break;
            case 4: case 2: case 7: i = 1; break;
            case 6: case 8: case 3: i = 2; break;
            default: i = 0;
        }
        if(i == this.tempButNum) {
            i = rand.nextInt(10);
            switch(i) {
                case 9: case 5: case 1: i = 2; break;
                case 4: case 2: case 7: i = 1; break;
                case 6: case 8: case 3: i = 0; break;
                default: i = 0;
            }

        }

        tempButNum = i;
        return i;


    }

}

