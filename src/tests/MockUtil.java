package tests;

import zxxz.timtab.Pair;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;


public class MockUtil {

    private final int arrSize = 1000;
    /**
     * Array of integers used to select random integers ranged from 2 to 9
     * inclusive. Used by @see{getRandom}, @see{setRandomButton}
     */
    private final int[] arr = new int[arrSize];
    /**
     * Size of ArrayBlockingQueue used by Util.fillTail and init block
     * of TimtabFrame.
     */
    private final int queueSize;
    /**
     * Pairs is BlockingQueue of unique instances of Pairs.
     * Initialized in init block of TimtabFrame for size used field of
     * Timtab @see{queueSize}.
     */
    private final ArrayBlockingQueue<Pair> pairs;
    private final Random rand;

    MockUtil() {
        rand = new Random();
        queueSize = 5;
        pairs = new ArrayBlockingQueue<>(queueSize, true);
        initQueue(pairs); //pairs are ready to roll
        int i = 0;
        while(i < arrSize) {
            int t = rand.nextInt(10);
            if(t < 2) continue;
            arr[i] = t;
            i++;
        }

    }


    /**
     * Fill BlockingQueue with Pair objects.
     * Check Pair if it is unique, not equal to any of Pair instances
     * held in BlockingQueue passed as parameter. Doesn't offer Pair if
     * no space is available.
     * @param q ArrayBlockingQueue pairs
     */
    private void initQueue(ArrayBlockingQueue q) {

        Thread t = new Thread() {
            @Override
            public void run() {
                try{
                    if (Thread.interrupted()) throw new InterruptedException() ;
                    int i = 0;
                    while(i < q.size()) {
                        Pair<Integer> pair = null;
                        try {
                            pair = new Pair<>(getRandom(), getRandom());
                        } catch(NullPointerException e) {
                            System.exit(1);
                        }
                        if(!q.contains(pair))
                            if(q.offer(pair))
                                i++;
                    }
                }catch(InterruptedException e){
                    return;
                }
            }
        };
        t.start();
    }

    /**
     *Offer new Pair to the end of Queue if queue size is less than
     * field of TimtabFrame @see{queueSize}.
     * @param queue ArrayBlockingQueue
     */
    private void fillTail(ArrayBlockingQueue<Pair> queue) {
        new Thread() {
            @Override
            public void run() {
                boolean b = false;
                while(!b && queue.size() < queueSize) {
                    try {
                        if(interrupted()) throw new InterruptedException();
                        Pair<Integer> pair = null;
                        try {
                            pair = new Pair<>(getRandom(), getRandom());
                        } catch(NullPointerException e) {
                            System.exit(1);
                        }
                        if(!queue.contains(pair)) {
                            b = queue.offer(pair);
                        }
                    } catch(InterruptedException e) {
                        return;
                    }
                }



            }
        }.start();

    }

    /**
     * Returns randomly selected number from array of [2.&nbsp.&nbsp9].
     * Uses util.Random.nextInt() for randomly select number from array.
     *
     * @return random integer ranged between 2 and 9 inclusive.
     */
    public int getRandom() {
        return arr[rand.nextInt(arrSize)];
    }


    /**
     * <p>Prevent one button usage for correct answer.
     * Set @code{randomButton} integer property of Util class.
     * randomButton used for randomly selection of JButton which
     * will be the correct answer for a question. randomButton
     * is used to access JButton with correct answer from TimtabFrame
     * and IncorrectAnswerActionListener.</p>
     * <p>Random selections are totally unpredictable.
     * Some times it could be several exactly the same selections
     * in the row.<p/>
     */
    public int getRandomButton(int t) {
        int i = getRandom();
        switch(i) {
            case 9: case 5: case 1: i = 0; break;
            case 4: case 2: case 7: i = 1; break;
            case 6: case 8: case 3: i = 2; break;
            default: i = 0;
        }
        if(i == t) {
            i = rand.nextInt(10);
            switch(i) {
                case 9: case 5: case 1: i = 2; break;
                case 4: case 2: case 7: i = 1; break;
                case 6: case 8: case 3: i = 0; break;
                default: i = 0;
            }

        }

        return i;
    }

    /**
     * Create integer offset by which correct answer will be changed.
     *
     * @return Integer representation of Random boolean selection. Returns 1 or 2.
     */
    public int getRandomOffset() {
        if(rand.nextBoolean()) return 2;
        return 1;
    }

    public boolean getRandomBoolean(){
        return rand.nextBoolean();
    }

    public Pair getRandomData(){
        return pairs.poll();

    }

    public void reset() {
        fillTail(pairs);
    }
}

