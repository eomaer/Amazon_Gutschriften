package info.swenhome.Amazon_Merge.Supplements;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.impl.*;
public class Action_Chooser extends JFrame implements ActionListener, WindowListener {
        private boolean action_performed=false;
        private int action_choosed=0;
        private JButton RefundButton=new JButton("Refunds vorbereiten Mit Datum");
        private JButton RefundButton2=new JButton("Refunds vorbereiten ohne Datum");
        private JButton AuftragButton=new JButton("Aufträge vorbereiten");
        private JButton PaymentsButton=new JButton("Payments kombinieren");
        public Action_Chooser(String declaration){
            this.AuftragButton.addActionListener(this);
            this.RefundButton.addActionListener(this);
            this.PaymentsButton.addActionListener(this);
            this.RefundButton2.addActionListener(this);
            //Erklärung schreiben
            JLabel declarationPanel=new JLabel("Bitte Aktion wählen",SwingConstants.CENTER);
            JPanel declarationSpace=new JPanel();
            declarationSpace.add(declarationPanel);

            //Spacer deklarieren
            JPanel space=new JPanel();
            space.setLayout(new GridLayout(5,5,5,5));
            space.add(new JLabel(" ",SwingConstants.CENTER));

            JPanel buttons=new JPanel();
            buttons.setLayout(new GridLayout(2,2));
            buttons.add(RefundButton);
            buttons.add(RefundButton2);
            buttons.add(AuftragButton);
            buttons.add(PaymentsButton);

            //Fenster Aufbauen
            setBounds(100,100,400,150);
            setLayout(new BorderLayout());
            //setUndecorated(true);
            add(BorderLayout.NORTH,declarationSpace);

            add(BorderLayout.CENTER,buttons);


            setTitle("Aktion Auswahl");
            addWindowListener((WindowListener) this);
            setVisible(true);

        }

        public boolean GET_ACTION(){
            return this.action_performed;
        }
        public int GET_ACT() {
        return this.action_choosed;
        }

        public void actionPerformed(ActionEvent e){
                this.setVisible(false);
                if (e.getSource() == AuftragButton) {
                    this.action_choosed = 1;
                } else if (e.getSource() == RefundButton) {
                    this.action_choosed = 2;
                }else if(e.getSource()==PaymentsButton){
                    this.action_choosed=4;
                }else if(e.getSource()==RefundButton2){
                    this.action_choosed=5;
                }
                this.action_performed=true;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e){
            this.action_choosed=3;
            this.action_performed=true;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        this.action_choosed=3;
        this.action_performed=true;
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
