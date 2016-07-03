package zxxz.timtab;

public class Timtab{


    /**
     * Main class. Used to create and start instance of main frame.
     * Timtab is acronym of Time Table.
     * @param args the command line arguments
     */

    public static void main(String[] args) {
       TimtabFrame application = new TimtabFrame();
       application.setDefaultCloseOperation(application.EXIT_ON_CLOSE);
       application.setVisible(true);
    }

}
