package zxxz.timtab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class TimtabFrame extends JFrame {

    Random random = new Random();
    Set multiplicationsSet;
    Set repetitionsSet = new HashSet();
    int correctAnswer;
    private int correct = 0;
    private int incorrect = 0;
    private JPanel buttonPanel = new JPanel();
    private JPanel questionPanel = new JPanel();
    private JLabel status = new JLabel();
    private JLabel question = new JLabel();
    private JButton answer1 = new JButton();
    private JButton answer2 = new JButton();
    private JButton answer3 = new JButton();
    Container contentPane = this.getContentPane();
    JButton[] buttonsArray = {answer1, answer2, answer3}; //gui constructor use only.
    ArrayList buttonsArrayList = new ArrayList(); //program use.

    public TimtabFrame() {
        this.setTitle("TimTab");
        this.setSize(450, 200);
        this.relocate();
        this.setResizable(false);
        contentPane.setLayout(new BorderLayout());
        initButtonPanel();
        Font questionFont = new Font("Arial", Font.BOLD, 48);
        question.setFont(questionFont);
        question.setHorizontalAlignment(JLabel.CENTER);



// itereate through buttons and put them in ArrayList
        for (JButton button : buttonsArray) {
            Font answerFont = new Font("Arial", Font.PLAIN, 36);
            button.setFont(answerFont);
            button.setHorizontalAlignment(JButton.CENTER);
            button.addActionListener(answerListener);
            buttonsArrayList.add(button);//// TODO: 23/06/16 use Arraylist constructor 
            buttonsArrayList.trimToSize();
        }// end for
        setCorrect(0);
        setIncorrect(0);
        status.setText(this.setStatusText());

        contentPane.add(question, BorderLayout.NORTH);
        contentPane.add(buttonPanel, BorderLayout.CENTER);
        contentPane.add(status, BorderLayout.SOUTH);

        multiplicationsSet = this.createSet(30);
        this.createText();
    }// end constructor
    private void initButtonPanel() {
        Dimension d = new Dimension(138, 80);
        answer1.setMinimumSize(d);
        answer2.setMinimumSize(d);
        answer3.setMinimumSize(d);
        GroupLayout layout = new GroupLayout(buttonPanel);
        buttonPanel.setLayout(layout);
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
    }
//set and get methods

    public void setCorrect(int i) {
        if (i >= 0) {
            correct = i;
        }
    }

    public int getCorrect() {
        return correct;
    }

    public void setIncorrect(int i) {
        if (i >= 0) {
            incorrect = i;
        }
    }

    public int getIncorrect() {
        return incorrect;
    }

    public String setStatusText() {
        /*return String.format("  Правельных ответов: %d\t        "
        + "   Неправельных ответов: %d", correct, incorrect);
         * 
         */

        return String.format("  Correct: %d\t        "
                + "   Incorrect: %d", correct, incorrect);
    }

// methods to make text for Label and Buttons
    private List createList(int size) {
        List list = new ArrayList(size);
        for (int i = 0; i <= size; i++) {
            list.add(new Multiplication());// TODO: 23/06/16 try to use lambda here 
        }// end for
        return list;
    }

    private Set createSet(int size) {
        return new HashSet(createList(size));
        
    }
/* todo recreate random selection of button
perhaps i will use small enum of integers for each button and than when number in that
region choose that button. read about random and enum.
 */
    private void createText() {
        int bigIndex = (random.nextInt(3));
        int mediumIndex = (random.nextInt(2));
        boolean flag = random.nextBoolean();
        Iterator iterator = multiplicationsSet.iterator();
        if (iterator.hasNext() && multiplicationsSet.size() > 1) {
            if (iterator.next() != null) {
                Multiplication m = (Multiplication) iterator.next();
                repetitionsSet.add(m);
                iterator.remove();

                ///start of creating text
                JButton b1 = (JButton) buttonsArrayList.get(bigIndex);
                b1.setText(m.setCorrectText());
                correctAnswer = new Integer(m.setCorrectText()).intValue();
                buttonsArrayList.remove(bigIndex);
                buttonsArrayList.trimToSize();

                JButton b2 = (JButton) buttonsArrayList.get(mediumIndex);
                b2.setText(m.setIncorrectText(flag));
                buttonsArrayList.remove(mediumIndex);
                buttonsArrayList.trimToSize();

                JButton b3 = (JButton) buttonsArrayList.get(0);
                b3.setText(m.setIncorrectText(invertBoolean(flag)));
                buttonsArrayList.remove(0);
                if (!buttonsArrayList.isEmpty()) {
                    buttonsArrayList.removeAll(buttonsArrayList);
                }
                createButtonArray();
                question.setText(m.setQuestionText());


                //end of creating text
            }//fi

        }// end if
        else {
            createUnique();
            createText();
        }//end else
    }

    // TODO: 23/06/16  again could be rewriten as Arrays.to list()  
    private void createButtonArray() {
        for (JButton button : buttonsArray) {
            buttonsArrayList.add(button);
        }
    }

    private boolean invertBoolean(boolean flag) {
        return flag?false:true;
    }

    private void createUnique() {
        Set mSet = createSet(50);
        if (repetitionsSet != null && repetitionsSet.size() > 0) {
            mSet.removeAll(repetitionsSet);
        }//if
        repetitionsSet.clear();
        multiplicationsSet = mSet;
        //System.out.println(multiplicationsSet.size());
    }

    //gui utils
    private void relocate() {
        // TODO: 23/06/16 dimensions set as final private int 
        Dimension frame = this.getSize();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int mX = random.nextInt(screen.width - frame.width);
        int mY = random.nextInt(screen.height - frame.height);
        this.setLocation(new Point(mX, mY));
    }

    // TODO: 23/06/16 move action listener as anaimous class to constructor 
    ActionListener answerListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            JButton mButton;
            mButton = (JButton) e.getSource();
            int i = new Integer(mButton.getText()).intValue();
            if (i != correctAnswer) {
                mButton.setForeground(Color.red);
                incorrect++;

                TimtabFrame.this.relocate();
                TimtabFrame.this.repaint();


            }// end if
            else {

                correct++;

                for (JButton button : buttonsArray) {
                    button.setForeground(Color.black);
                }
                createText();
            }// end else
            status.setText(setStatusText());
        } // end action
    };// end ActionListener

    private  class Util{
        private Util(){}
        private final int arr_size = 100;
        private final int[] arr = new int[arr_size];
        private final Random rand = new Random(10);

        {
            int i = 0;
            while (i++ < arr_size) {
                int t = rand.nextInt(10);
                if (t < 2) continue;
                arr[i] = t;
            }

        }

        int getRandom() {
            return arr[rand.nextInt(arr_size)];
        }

        int getRandomButton(int i) {
            switch (i) {
                case 9: case 5: case 3: i = 0; break;
                case 4: case 2: case 7: i = 1; break;
                case 6: case 8: case 1: i = 2; break;
                default: i = 0;
            }
            return i;
        }

    }// inner class
} // end class