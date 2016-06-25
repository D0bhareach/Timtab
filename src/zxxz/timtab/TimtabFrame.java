package zxxz.timtab;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

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

        status.setText(this.makeStatusText());

        contentPane.add(question, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        contentPane.add(status, BorderLayout.SOUTH);

        createText();
    }// end constructor

    /**
     * Set up buttons properties and add ActionListener to each.
     * Listener is anonymous class.
     * @param buttons Array of JButtons
     */
    private void initButtons(JButton[] buttons){
        for (JButton button : buttons) {
            Font answerFont = new Font("Arial", Font.PLAIN, 36);
            button.setFont(answerFont);
            button.setHorizontalAlignment(JButton.CENTER);
            button.addActionListener((e)-> {
              {
                        JButton bu;
                        bu = (JButton) e.getSource();
                        int i = Integer.parseInt(bu.getText());
                        if (i != correct) {
                            bu.setForeground(Color.red);
                            incorrectAnswers++;
                            relocate();
                            repaint();
                        }// end if
                        else {
                            correctAnswers++;

                            for (JButton b : buttonsArray) {
                                b.setForeground(Color.black);
                            }
                            createText();
                        }// end else
                        status.setText(makeStatusText());
                }
            }
        );}

    }

    /**
     * Initial setting up panel properties adds JButtons to it.
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
     * @return Formatted Sting for bottom counter.
     */

    private String makeStatusText() {
        return String.format("Correct: %d %s Incorrect: %d"
                , correctAnswers,"\t", incorrectAnswers);
    }

    /**
     * Creates and set text for each JButton.
     * All work for choosing the button, text creation
     * and setting up the text made in this method.
     */
    private void createText() {
        int a = util.getRandom();
        int b = util.getRandom();
        correct = a*b;
        util.getRandomButton();
        int offset = createOffset();
        boolean flag = util.rand.nextBoolean();

        for(int i =0;i<buttonsArray.length;i++){
            if(i==util.tempButNum){buttonsArray[i].setText(String.valueOf(correct));}
            else if(flag){buttonsArray[i].setText(String.valueOf(correct + offset)); flag = !flag;}
            else{buttonsArray[i].setText(String.valueOf(correct - offset));flag = !flag;}
        }
        question.setText(a+"x"+b);
    }

    /**
     * Set offset by which correct answer will be changed.
     * @return Integer representation of Random boolean selection. Returns 1 or 2.
     */
    private int createOffset(){
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
        this.setLocation(new Point(mX, mY));
    }

    // TODO: 25/06/16 Create method to catch the same pairs of множителей at least for three last ones.
    /*
    the method should use array[3][2] three pairs.
    and int parameter - the pair of numbers to check against.
    Check each pair if it is the same as parameter it it is not
    use parameter send boolean, if it is the same create random pair again.
    Perhaps it is a good idea to create array of such pairs before start of
    program. Use multithreading for it. When array becomes empty recreate it again.
    edition#2
    why not to use background thread to construct cache value for next pair.
    it possible to make it some level of unique say for 3-4 previous  selections.
     */

    /**
     * Utility class used for creating random data for TimtabFrame class.
     */
  private class Util{
       /**
        * Use default constructor.
        * All construction goes inside of initialization block.
        * Main property of Util class is an array[100] of
        * randomly created integers. For all numbers in array
        * (i>=2&&i<10) will return true.
        */
        Util(){}
        private final int arr_size = 100;
        private final int[] arr = new int[arr_size];
        private int tempButNum = 0;
        final Random rand = new Random();

        {
            int i = 0;
            while (i< arr_size) {
                int t = rand.nextInt(10);
                if (t < 2) continue;
                arr[i] = t;
                i++;
            }

        }

       /**
        * Returns randomly selected number from array of [2.&nbsp.&nbsp9].
        * Uses util.Random.nextInt() for randomly select number from array.
        * @return random integer
        */
        int getRandom() {
            return arr[rand.nextInt(arr_size)];
        }

       // TODO: 25/06/16 Write test to compare how often are the same buttons selected.

        /**
         * Set tempButNum integer filed of Util class.
         * tempButNum used for randomly selection of
         * JButton which will be the correct answer for a
         * question. Class use getRandom method to  get
         * random number from array of 2-9.
         * Main thing it act as a  random selector for
         * button and allows the same selection in randomly
         * manner. Random selections are totally unpredictable.
         * Some times it could be several exactly the same selections
         * in the row.
         */
        void getRandomButton() {
            int i = getRandom();

            switch (i) {
                case 9: case 5: case 1: i = 0; break;
                case 4: case 2: case 7: i = 1; break;
                case 6: case 8: case 3: i = 2; break;
                default: i = 0;
            }
            if(i==this.tempButNum){
                    i = rand.nextInt(10);
                switch (i) {
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