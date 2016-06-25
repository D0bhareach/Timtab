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
        return String.format("Correct: %d %s Incorrect: %d"
                , correctAnswers,"\t", incorrectAnswers);
    }

    private void createText() {

        int a = util.getRandom();
        int b = util.getRandom();
        correct = a*b;
        util.getRandomButton();
        int offset = createOffset(correct);
        boolean flag = util.rand.nextBoolean();

        for(int i =0;i<buttonsArray.length;i++){
            if(i==util.tempButNum){buttonsArray[i].setText(String.valueOf(correct));}
            else if(flag){buttonsArray[i].setText(String.valueOf(correct + offset)); flag = !flag;}
            else{buttonsArray[i].setText(String.valueOf(correct - offset));flag = !flag;}
        }
        question.setText(a+"x"+b);
    }

    // TODO: 25/06/16 clean it up after decision do I really need a parameter?

    private int createOffset(int c){
        /*
        if(c%2==0) return 2;
        //else if(c<=9||util.rand.nextBoolean())return 1;
        return 1;
        */
        if(util.rand.nextBoolean()) return 2;
        //else if(c<=9||util.rand.nextBoolean())return 1;
        return 1;
    }




    private void relocate() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int mX = util.rand.nextInt(screen.width - frameSize.width);
        int mY = util.rand.nextInt(screen.height - frameSize.height);
        this.setLocation(new Point(mX, mY));
    }


   public class Util{
        public Util(){}
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
       // TODO: 25/06/16 Change it to package private after test
        public int getRandom() {
            return arr[rand.nextInt(arr_size)];
        }

       // TODO: 25/06/16 Change it to package private after test
        public void getRandomButton() {
            int i = getRandom();
            rand.setSeed(System.nanoTime());
            boolean flag = rand.nextBoolean();

            System.out.println("Enter method: temp = "+tempButNum+"\ti = "+i);


            switch (i) {
                case 9: case 5: case 3: i = 0; break;
                case 4: case 2: case 7: i = 1; break;
                case 6: case 8: case 1: i = 2; break;
                default: i = 0;
            }

            if(i != tempButNum && flag) {
                tempButNum = i;
                System.out.println("Exit method: temp = "+tempButNum+"\ti = "+i+".\n");
                return ;
            }
            else if(i != tempButNum && !flag){return;}

            System.out.println("Go to recursion: temp = "+tempButNum+"\ti = "+i+".\n");
            getRandomButton();

        }

   }
}