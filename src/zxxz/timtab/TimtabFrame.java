package zxxz.timtab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TimtabFrame extends JFrame {
// TODO: 02/07/16 write good and comprehensive doc for class

    private int correct = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int randomButton = 0;//reference to randomly selected button with correct answer.
    private final Dimension frameSize = new Dimension(450, 200);
    private final Dimension buttonSize = new Dimension(138, 80);


    private final JLabel status = new JLabel();
    private final JLabel question = new JLabel();
    private final JButton answer1 = new JButton();
    private final JButton answer2 = new JButton();
    private final JButton answer3 = new JButton();
    private final Color buttons_Color = answer3.getBackground(); //reference to original Color

    private final JButton[] buttonsArray = {answer1, answer2, answer3};
    private final Util util = new Util();
    private BlockingQueue<Pair> pairs = util.getQueue();

    public TimtabFrame() {
        this.setTitle("TimTab");
        this.setSize(frameSize);
        relocate();
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

        reset();
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
        }

    }

    /**
     * Set up JPanel properties, adds JButtons to panel.
     *
     * @param panel JPanel is used as Container
     */

    private void initButtonPanel(JPanel panel) {
        Dimension d = buttonSize;
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
     * Creates and set text for each JButton. Resets listeners of JButtons.
     * Set text of question JLabel. Set common variable randomButton which
     * is used by Incorrect AL and this method itself to reference a button
     * with correct answer.
     * This method use both Listeners, ArrayBlockingQueue pairs,
     * setRandomButton(), fillTail() and createOffset() methods of Util Class.
     */

    private void reset() {
        ActionListener correctAnswer = new CorrectAnswerActionListener();
        ActionListener incorrectAnswer = new IncorrectAnswerActionListener();
        //removing listeners
        for (JButton b : buttonsArray){
            ActionListener[] listeners = b.getActionListeners();
            for(ActionListener l: listeners)
                b.removeActionListener(l);

        }//end of removing listeners
        Pair p = pairs.poll();
        int a = p.getFirst().intValue();
        int b = p.getSecond().intValue();
        correct = a * b;
        util.setRandomButton();
        int offset = util.createOffset();
        boolean flag = util.rand.nextBoolean();

        for(int i = 0; i < buttonsArray.length; i++) {
            if(i == randomButton) {
                buttonsArray[i].setText(String.valueOf(correct));
                buttonsArray[i].addActionListener(correctAnswer);
            } else if(flag) {
                buttonsArray[i].setText(String.valueOf(correct + offset)); flag = !flag;
                buttonsArray[i].addActionListener(incorrectAnswer);
            } else {
                buttonsArray[i].setText(String.valueOf(correct - offset)); flag = !flag;
                buttonsArray[i].addActionListener(incorrectAnswer);
            }
        }
        util.fillTail();
        question.setText(a + "x" + b);
    }



    /**
     * Set frame location center of the Screen
     */
    private void relocate() {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int mX = ((screen.width - frameSize.width)/2);
        int mY = ((screen.height - frameSize.height)/2);
        setLocation(new Point(mX, mY));
    }

    /**
     * Utility class used for creating random data for TimtabFrame class.
     * Main properties of Util class are an int[1000] arr of
     * randomly created integers and ArrayBlockingQueue queue. For all numbers
     * in array (i>=2 & i<10) will return true. ArrayBlockingQueue queue holds
     * five unique Pairs<T extends Number>. The queue used to maintain uniques
     * of five last pairs of factorials.
     *
     */
    private class Util {
        private final int arr_size = 1000;
        private final int queue_size = 5;
        private final int[] arr = new int[arr_size];
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
            BlockingQueue<Pair> q = new ArrayBlockingQueue<>(size, true);
            Thread t = new Thread() {
                @Override
                public void run() {
                    try{
                        if (Thread.interrupted()) throw new InterruptedException() ;
                    int i = 0;
                    while(i < size) {
                        Pair<Integer> pair = new Pair<>(getRandom(), getRandom());
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
            return q;
        }


        private void fillTail() {
            new Thread() {
                @Override
                public void run() {
                    boolean b = false;
                    while(!b && queue.size() < queue_size) {
                        try {
                            if(interrupted()) throw new InterruptedException();
                            Pair<Integer> pair = new Pair<>(getRandom(), getRandom());
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
        private int getRandom() {
            return arr[rand.nextInt(arr_size)];
        }


        /**
         * <p>Prevent one button usage for correct answer.
         * Set @code{randomButton} integer property of Util class.
         * @code{randomButton} used for randomly selection of JButton which
         * will be the correct answer for a question. @code{randomButton}
         * is used to access JButton with correct answer from TimtabFrame
         * and IncorrectAnswerActionListener.</p>
         * <p>Random selections are totally unpredictable.
         * Some times it could be several exactly the same selections
         * in the row.<p/>
         */
        private void setRandomButton() {
            int i = getRandom();

            switch(i) {
                case 9: case 5: case 1: i = 0; break;
                case 4: case 2: case 7: i = 1; break;
                case 6: case 8: case 3: i = 2; break;
                default: i = 0;
            }
            if(i == randomButton) {
                i = rand.nextInt(10);
                switch(i) {
                    case 9: case 5: case 1: i = 2; break;
                    case 4: case 2: case 7: i = 1; break;
                    case 6: case 8: case 3: i = 0; break;
                    default: i = 0;
                }

            }

            randomButton = i;


        }

        /**
         * Create integer offset by which correct answer will be changed.
         *
         * @return Integer representation of Random boolean selection. Returns 1 or 2.
         */
        private int createOffset() {
            if(rand.nextBoolean()) return 2;
            return 1;
        }

    }

    private class IncorrectAnswerActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           JButton corr  = buttonsArray[randomButton];
                incorrectAnswers++;
                corr.setBackground(new Color(70, 255, 74, 255));
                for(JButton b : buttonsArray)
                    if(b!=corr)
                        b.setEnabled(false);
        }
    }

    private class CorrectAnswerActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton)e.getSource();
if(source.getBackground() == buttons_Color)
    correctAnswers++;

            for(JButton b : buttonsArray) {
                b.setBackground(buttons_Color);
                b.setEnabled(true);
            }
            reset();
            status.setText(makeStatusText());
        }
    }

}//Frame