package zxxz.timtab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class TimtabFrame extends JFrame {
    /**
     * Number of correct answers. Used in text for status JLabel.
     * Assigned in CorrectAnswerActonListener.
      */
    private int correctAnswers;

    /**
     * Number of incorrect answers. Used in text for status JLabel.
     * Assigned in IncorrectAnswerActonListener.
      */
    private int incorrectAnswers;
    /**
     * Randomly selected int used to assign button with correct answer.
     * <code>ButtonsArray[randomButton]</code>. Used by @see{reset},
     * as well as @see{serRandomButton} and @see{IncorrectAnswerListener}
     * */
    /*
    The whole API looks bad, shall find some replacement for this global variable,
    but it very handy to access buttons from array by reference number.
     */
    private int randomButton;

    private final RandomValueProvider util;


    private final Dimension frameSize;
    private final Dimension buttonSize;
    private final JLabel status;
    private final JLabel question;

    /**
     * Array of JButtons used for receiving user answer.
     * Used @see{initButtons}, @see{reset}, @see{initButtonPanel},
     * CorrectAnswerActionListener, IncorrectAnswerActionListener.
     */
    private final JButton[] buttonsArray = new JButton[3];
    //init block
    {
        correctAnswers = 0;
        incorrectAnswers = 0;
        frameSize = new Dimension(450, 200);
        buttonSize = new Dimension(138, 80);
        status = new JLabel();
        question = new JLabel();
        for(int i = 0;i<buttonsArray.length; i++){
            buttonsArray[i] = new JButton();
        }
    }

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
        util = new Util();
        reset(util);
    }


    /**
     * Set up buttons properties.
     * @param buttons Array of JButtons
     */
    private void initButtons(JButton[] buttons) {
        Dimension d = buttonSize;
        for(JButton button : buttons) {
            button.setMinimumSize(d);
            Font answerFont = new Font("Arial", Font.PLAIN, 36);
            button.setFont(answerFont);
            button.setHorizontalAlignment(JButton.CENTER);
        }

    }

    /**
     * Set up JPanel properties, adds JButtons to panel.
     *Set GroupLayout as layout for JPanel.
     * @param panel JPanel which is used as Container.
     */
    private void initButtonPanel(JPanel panel) {

        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        for(JButton aButtonsArray : buttonsArray) {
            hGroup.addGroup(layout.createParallelGroup().
                    addComponent(aButtonsArray));
        }

        layout.setHorizontalGroup(hGroup);

        GroupLayout.ParallelGroup parallelGroup =
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        for(JButton aButtonsArray : buttonsArray) {
            parallelGroup.addComponent(aButtonsArray);
        }
        vGroup.addGroup(parallelGroup);
        layout.setVerticalGroup(vGroup);
        panel.setLayout(layout);
    }

    /**
     * Convenience method format String correct and incorrect answers.
     * Used by JFrame constructor to set JLabel status text and
     * used by CorrectAnswerActionListener to update text of
     * <code>status</code> JLabel.
     *
     * @return Formatted Sting.
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
    private void reset(RandomValueProvider util) {
        ActionListener correctAnswer = new CorrectAnswerActionListener();
        ActionListener incorrectAnswer = new IncorrectAnswerActionListener();
        int correct;

        //removing listeners
        for (JButton b : buttonsArray){
            ActionListener[] listeners = b.getActionListeners();
            for(ActionListener l: listeners)
                b.removeActionListener(l);

        }//end of removing listeners
        Pair p = util.getRandomData();
        int a = p.getFirst().intValue();
        int b = p.getSecond().intValue();
        correct = a * b;
        randomButton = util.getRandomButton();
        int offset = util.getRandomOffset();
        boolean flag = util.getRandomBoolean();

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
        util.reset();
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
     *Util implements RandomValueProvider.
     */
    @SuppressWarnings("UnnecessaryReturnStatement")
    private class Util implements RandomValueProvider{
        /**
         * Size of int[] arr. Need to be big for randomness.
         */
        private final int arrSize = 1000;
        /**
         * Array of integers used to select random integers ranged from 2 to 9
         * inclusive. Used by @see{getRandom}, @see{setRandomButton}
         */
        private final int[] arr = new int[arrSize];
        /**
         * Size of ArrayBlockingQueue used by Util.fillTail.
         */
        private final int queueSize = 5;

        /**
         * Number of Button selected previously
         */
        private int previousButton;
        /**
         * Pairs is BlockingQueue of unique instances of Pairs.
         */
        private ArrayBlockingQueue<Pair> pairs;
        private final Random rand;

        Util() {
            rand = new Random();
            int i = 0;
            while(i < arrSize) {
                int t = rand.nextInt(10);
                if(t < 2) continue;
                arr[i] = t;
                i++;
            }
            //init queue
            pairs = new ArrayBlockingQueue<>(queueSize,true);

        int c = 0;
        while(c < queueSize)

        {
            Pair<Integer> pair = new Pair<>(getRandom(), getRandom());
            if(!pairs.contains(pair))
                if(pairs.offer(pair))
                    c++;
        }

        }//constructor


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
        public int getRandomButton() {
            int i = getRandom();
            switch(i) {
                case 9: case 5: case 1: i = 0; break;
                case 4: case 2: case 7: i = 1; break;
                case 6: case 8: case 3: i = 2; break;
                default: i = 0;
            }
            if(i == previousButton) {
                i = rand.nextInt(10);
                switch(i) {
                    case 9: case 5: case 1: i = 2; break;
                    case 4: case 2: case 7: i = 1; break;
                    case 6: case 8: case 3: i = 0; break;
                    default: i = 0;
                }

            }
            previousButton = i;
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



    /**
     * Listener to events on buttons with incorrect answers.
     * User by @see{reset}.
     */
    private class IncorrectAnswerActionListener implements ActionListener{

        /**
         * Set button's with correct answer Background to green and disable
         * buttons with incorrect answers. Increase incorrectAnswers field.
         * @param e ActionEvent
         */
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

    /**
     * Listener to events on buttons with correct answers.
     * User by @see{reset}.
     */
    private class CorrectAnswerActionListener implements ActionListener{
        /**
         * Increase <code>correctAnswers<code/> set buttons Background color
         * to original value enables buttons and call @see{reset} method.
         * Update text of status JLabel.
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Color color = new JButton().getBackground();
            JButton source = (JButton)e.getSource();
            if(source.getBackground() == color)
                correctAnswers++;

            for(JButton b : buttonsArray) {
                b.setBackground(color);
                b.setEnabled(true);
            }
            reset(util);
            status.setText(makeStatusText());
        }
    }

}