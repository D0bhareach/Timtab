package zxxz.timtab;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TimtabFrame extends JFrame {


    private int correct = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private final Dimension frameSize = new Dimension(450, 200);
    private final JLabel status = new JLabel();
    private final JLabel question = new JLabel();
    private final JButton answer1 = new JButton();
    private final JButton answer2 = new JButton();
    private final JButton answer3 = new JButton();
    private final JButton[] buttonsArray = {answer1, answer2, answer3};
    private final Util util = new Util();
    private BlockingQueue<Pair> pairs = util.getQueue();

    public TimtabFrame() {
        this.setTitle("TimTab");
        this.setSize(frameSize);
        this.relocate();
        this.setResizable(false);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        initButtons(buttonsArray);
        JPanel buttonPanel = new JPanel();
        initButtonPanel(buttonPanel);
        Font questionFont = new Font("Arial", Font.BOLD, 48);
        question.setFont(questionFont);
        question.setHorizontalAlignment(JLabel.CENTER);

        status.setText(makeStatusText());

        contentPane.add(question, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        contentPane.add(status, BorderLayout.SOUTH);

        createText();
    }// end constructor

    /**
     * Set up buttons properties and add ActionListener to each.
     * Listener is anonymous class.
     *
     * @param buttons Array of JButtons
     */
    private void initButtons(JButton[] buttons) {
        for(JButton button : buttons) {
            Font answerFont = new Font("Arial", Font.PLAIN, 36);
            button.setFont(answerFont);
            button.setHorizontalAlignment(JButton.CENTER);
            button.addActionListener((e) -> {

                        JButton bu;
                        bu = (JButton) e.getSource();
                        int i = Integer.parseInt(bu.getText());
                        if(i != correct) {
                            bu.setForeground(Color.red);
                            incorrectAnswers++;
                            relocate();
                            repaint();
                        }// end if
                        else {
                            correctAnswers++;

                            for(JButton b : buttonsArray) {
                                b.setForeground(Color.black);
                            }
                            createText();
                        }
                        status.setText(makeStatusText());
                    }
            );
        }

    }

    /**
     * Set up JPanel properties, adds JButtons to panel.
     *
     * @param panel JPanel is used as Container
     */
    private void initButtonPanel(JPanel panel) {
        Dimension d = new Dimension(138, 80);
        answer1.setMinimumSize(d);
        answer2.setMinimumSize(d);
        answer3.setMinimumSize(d);
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(answer1));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(answer2));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(answer3));
        layout.setHorizontalGroup(hGroup);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(answer1).addComponent(answer2).addComponent(answer3));
        layout.setVerticalGroup(vGroup);
        panel.setLayout(layout);
    }

    /**
     * Convenience method used for wrapping String.&nbspformat
     * Used by JFrame constructor to set JLabel status text and
     * used by action listener to change text of status JLabel
     * when some answer is given.
     *
     * @return Formatted Sting for JLabel status.
     */

    private String makeStatusText() {
        return String.format("Correct: %d %s Incorrect: %d"
                , correctAnswers, "\t", incorrectAnswers);
    }

    /**
     * Creates and set text for each JButton.
     * All work for choosing the button, text creation
     * and setting up the text is made in this method.
     */
    private void createText() {
        Pair p = pairs.poll();
        int a = p.getFirst().intValue();
        int b = p.getSecond().intValue();
        correct = a * b;
        util.getRandomButton();
        int offset = createOffset();
        boolean flag = util.rand.nextBoolean();

        for(int i = 0; i < buttonsArray.length; i++) {
            if(i == util.tempButNum) {
                buttonsArray[i].setText(String.valueOf(correct));
            } else if(flag) {
                buttonsArray[i].setText(String.valueOf(correct + offset)); flag = !flag;
            } else {
                buttonsArray[i].setText(String.valueOf(correct - offset)); flag = !flag;
            }
        }
        util.fillTail();
        question.setText(a + "x" + b);
    }

    /**
     * Create integer offset by which correct answer will be changed.
     *
     * @return Integer representation of Random boolean selection. Returns 1 or 2.
     */
    private int createOffset() {
        if(util.rand.nextBoolean()) return 2;
        return 1;
    }

    /**
     * Reset frame location by randomly selected values.
     * Makes frame appear jumping from place to place
     * when wrong answer is given.
     */
    private void relocate() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int mX = util.rand.nextInt(screen.width - frameSize.width);
        int mY = util.rand.nextInt(screen.height - frameSize.height);
        setLocation(new Point(mX, mY));
    }

    /**
     * Utility class used for creating random data for TimtabFrame class.
     * Main properties of Util class is an array[100] arr of
     * randomly created integers. For all numbers in array
     * (i>=2 & i<10) will return true, and ArrayBlockingQueue queue where
     * five unique pairs of integers are kept.
     */
    private class Util {
        private final int arr_size = 1000;
        private final int queue_size = 5;
        private final int[] arr = new int[arr_size];
        private int tempButNum = 0;
        private final Random rand = new Random();
        private final BlockingQueue<Pair> queue;

        Util() {
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

        private BlockingQueue<Pair> getQueue() {return this.queue;}

        private BlockingQueue<Pair> initQueue(int size) {
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


        private void fillTail() {
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

        /**
         * Returns randomly selected number from array of [2.&nbsp.&nbsp9].
         * Uses util.Random.nextInt() for randomly select number from array.
         *
         * @return random integer ranged between 2 and 9 inclusive.
         */
        private int getRandom() {
            return arr[rand.nextInt(arr_size)];
        }


        /**
         * Prevent one button usage for correct answer.
         * Set tempButNum integer property of Util class.
         * tempButNum used for randomly selection of
         * JButton which will be the correct answer for a
         * question. tempButNum is used for temp storage
         * of int property and for accessing it from TimtabFrame.
         * Main thing it act as a random selector for
         * button and allows the selection in randomly
         * manner. Random selections are totally unpredictable.
         * Some times it could be several exactly the same selections
         * in the row.
         */
        private void getRandomButton() {
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

            this.tempButNum = i;


        }

    }
}