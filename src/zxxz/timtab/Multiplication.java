package zxxz.timtab;

import java.util.Random;

public class Multiplication {
    // fields
    private Random random = new Random();
    private int a;
    private int b;
// constructor
    public Multiplication() {
        setA();
        setB();
    }
// methods
    private void setA() {
        a = Math.abs(2 + random.nextInt(8));
    }

    private void setB() {
        b = Math.abs(2 + random.nextInt(8));
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public String setIncorrectText(boolean flag) {
        int mTempInt;
        int x = (a * b);
        if (flag) {
            if (x <= 6) {
                mTempInt = x + 2;
            } else {
                mTempInt = ((a * b) - (1 + random.nextInt(3)));
            }
        }//if flag
        else {
            if (x <= 6) {
                int tryMinus = random.nextInt(2);
                if (x - (tryMinus) > 0 && x - tryMinus != x) {
                    mTempInt = x - tryMinus;
                } else {
                    mTempInt = x + 1;//+random.nextInt(1)));
                }
            } else {
                mTempInt = ((a * b) + (1 + random.nextInt(3)));
            }
        }

        return String.valueOf(Math.abs(mTempInt));
    }//end setIncorectText

    public String setCorrectText() {
        int t = a * b;
        return String.valueOf(Math.abs(t));
    }

    public String setQuestionText() {

        return a + "X" + b;
    }

    @Override
    public boolean equals(Object m) {
        boolean mFlag = false;
        if (m != null && m instanceof Multiplication) {
            Multiplication t = (Multiplication) m;
            if (this.a == (t.a) && this.b == (t.b)) {
                mFlag = true;
            } else if (this.a == t.b) {
                if (this.b == t.a) {
                    mFlag = true;
                }
            } else if (this.setQuestionText().equals(t.setQuestionText())) {
                mFlag = true;
            } else if (this.hashCode() == t.hashCode()) {
                mFlag = true;
            }
        }
        return mFlag;

    }// check what collection use for comparing

    @Override
    public int hashCode() {
        int hash = 15 * (a + b);
        return hash;
    }

    @Override
    public String toString() {
        return this.setCorrectText() + ":\t" + this.hashCode();
    }
}// end class
