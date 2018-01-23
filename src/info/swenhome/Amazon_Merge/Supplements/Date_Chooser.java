package info.swenhome.Amazon_Merge.Supplements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.impl.*;

public class Date_Chooser extends JFrame implements ActionListener {
    private boolean action_performed=false;
    private String date="";
    private Date ddate=null;
    private JButton abschickenButton=new JButton("Abschicken");
    private JDatePanelImpl datePanel;
    private JDatePickerImpl datePicker;
    private String declare="";
    public Date_Chooser(String declaration){
        this.declare=declaration;
        this.abschickenButton.addActionListener(this);
        UtilDateModel model = new UtilDateModel();
        //Das Datumsformat festlegen
        Properties p=new Properties();
        p.put("text.today","Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        this.datePanel = new JDatePanelImpl(model, p);
        // Don't know about the formatter, but there it is...
        this.datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());


        //Erklärung schreiben
        JLabel declarationPanel=new JLabel(this.declare,SwingConstants.CENTER);
        JPanel declarationSpace=new JPanel();
        declarationSpace.add(declarationPanel);

        //Spacer deklarieren
        JPanel space=new JPanel();
        space.setLayout(new GridLayout(5,5,5,5));
        space.add(new JLabel(" ",SwingConstants.CENTER));
        space.add(datePicker);

        //Fenster Aufbauen
        setBounds(100,100,250,300);
        setLayout(new BorderLayout());
        //setUndecorated(true);
        add(BorderLayout.NORTH,declarationSpace);
        add(BorderLayout.CENTER,space);
        add(BorderLayout.SOUTH,abschickenButton);

        setTitle("Datum Auswahl");
        setVisible(true);
    }
    public void SET_DECLARE(String decl){
        this.declare=decl;
    }
    public Object GET_DATE_OBJ(){
        return this.datePicker.getModel().getValue();
    }

    public void SET_VARS (){
        this.ddate=(Date) GET_DATE_OBJ();
        DateFormat df=new SimpleDateFormat("dd.MM.YYYY");
        this.date=df.format(GET_DATE_OBJ());
        this.action_performed=true;
    }
    public String GETDATE(){
        return this.date;
    }
    public boolean GET_ACTION(){
        return this.action_performed;
    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Abschicken"))
        {
            this.setVisible(false);
            this.SET_VARS();
        }
    }


}
