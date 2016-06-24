package zxxz.timtab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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



    private String makeStatusText() {
        return String.format("Correct: %d\t"
                + "Incorrect: %d", correctAnswers, incorrectAnswers);
    }

    private void createText() {

        int a = util.getRandom();
        int b = util.getRandom();
        int bn = util.getRandomButton(util.getRandom());
        boolean flag = util.rand.nextBoolean();
        correct = a*b;

        for(int i =0;i<buttonsArray.length;i++){
            if(i==bn){buttonsArray[i].setText(String.valueOf(correct));}
            if(flag){
                buttonsArray[i].setText(String.valueOf(correct +2));
                flag = invertBoolean(flag);
            }
            else{buttonsArray[i].setText(String.valueOf(correct -2));}
        }
        question.setText(String.valueOf(correct));
    }








    private boolean invertBoolean(boolean flag) {
        return !flag;
    }



    private void relocate() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int mX = util.rand.nextInt(screen.width - frameSize.width);
        int mY = util.rand.nextInt(screen.height - frameSize.height);
        this.setLocation(new Point(mX, mY));
    }

    // TODO: 23/06/16 move action listener as ananimos class to constructor
    ActionListener answerListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            JButton button;
            button = (JButton) e.getSource();
            int i = Integer.parseInt(button.getText()) ;
            if (i != correct) {
                button.setForeground(Color.red);
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
        } // end action
    };// end ActionListener

   private class Util{
        Util(){}
        private final int arr_size = 100;
        private final int[] arr = new int[arr_size];
        final Random rand = new Random(10);

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

    }
}