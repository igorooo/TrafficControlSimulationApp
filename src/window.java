import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class window extends JFrame {

    private JPanel RootPanel;
    private JButton submitButton;
    private JTextField UST;
    private JTextField AOC;
    private JTextField VARZ;
    private JTextField LST;
    private JTextArea log1;
    private JTextArea log2;
    private JTextArea log3;
    private JButton STARTSIMULATIONButton;
    private JTextArea result2;
    private JTextArea result3;
    private JTextArea result1;
    private JButton STOPSIMULATIONANDGETButton;
    private JButton INFOSButton;

    int AMOUNT_OF_CPUS,UPPER_LIMIT,LOWER_LIMIT,Z_VAR;
    Scheduler first,second,third;
    Thread FirstThr,SecondThr,ThirdThr;

    public window() {

        super("SO LAB 5");
        this.setSize(1000,1000);
        this.setResizable(true);
        this.setSize(900,500);
        this.setContentPane(RootPanel);


        AMOUNT_OF_CPUS = 10;
        UPPER_LIMIT = 80;
        LOWER_LIMIT = 40;
        Z_VAR = 10;

        UST.setText("Default: 80");
        LST.setText("Default: 40");
        AOC.setText("Default: 10");
        VARZ.setText("Default: 10");


        usage frame = new usage();
        frame.setContentPane(frame.RootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    UPPER_LIMIT = Integer.parseInt(UST.getText());
                    LOWER_LIMIT = Integer.parseInt(LST.getText());
                    Z_VAR = Integer.parseInt(VARZ.getText());
                    AMOUNT_OF_CPUS = Integer.parseInt(AOC.getText());
                }

                catch(Exception a){
                    a.printStackTrace();
                    UST.setText("Wrong number");

                }
            }
        });
        STARTSIMULATIONButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                first = new Scheduler(AMOUNT_OF_CPUS,UPPER_LIMIT,LOWER_LIMIT,Z_VAR,1,log1,result1,frame.usage1);
                second = new Scheduler(AMOUNT_OF_CPUS,UPPER_LIMIT,LOWER_LIMIT,Z_VAR,2,log2,result2,frame.usage2);
                third = new Scheduler(AMOUNT_OF_CPUS,UPPER_LIMIT,LOWER_LIMIT,Z_VAR,3,log3,result3,frame.usage3);



                FirstThr = new Thread(first);
                SecondThr = new Thread(second);
                ThirdThr = new Thread(third);

                FirstThr.start();
                SecondThr.start();
                ThirdThr.start();

            }
        });
        STOPSIMULATIONANDGETButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                first.STOP();
                second.STOP();
                third.STOP();


            }
        });
        INFOSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame2 = new JFrame("Informations");
                frame2.setSize(500,150);
                frame2.setLocation(300,300);
                frame2.setLayout(new GridBagLayout());
                JLabel label = new JLabel("Program made by \n\nIgor Czeczot   ");
                JLabel label2 = new JLabel("Simulate traffic control algorithms");
                frame2.add(label);
                frame2.add(label2);
                frame2.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }


}
