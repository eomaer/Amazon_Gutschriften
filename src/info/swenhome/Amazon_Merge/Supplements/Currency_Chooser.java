package info.swenhome.Amazon_Merge.Supplements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Currency_Chooser extends JFrame implements ActionListener {
    private boolean action_performed=false;
    private int currencyindex=0;
    private JButton abschickenButton=new JButton("Abschicken");
    private String[] currencies = {"EUR", "GBP"};
    private String selected_item="";
    private JList list1 = new JList(this.currencies);

    public Currency_Chooser()
    {   //Hier wird ein JFrame erzeugt, in welchem die  Währung ausgewählt wird.
        //Diese Kann über den String-Array currencies erweitert oder verkürzt werden.
        //Der Button löst aus, dass die entsprechenden werte in der Klasse gespeichert werden und über die GET-er abrufbar sind
        setBounds(100,100,150,150);
        setLayout(new BorderLayout());
        //setUndecorated(true);
        this.abschickenButton.addActionListener(this);
        JPanel panel=new JPanel();
        panel.add(this.list1);
        JLabel spacer=new JLabel(" ",SwingConstants.CENTER);
        JPanel space=new JPanel();
        space.setLayout(new GridLayout(3,1));
        space.add(spacer);
        space.add(spacer);
        space.add(spacer);
        add(BorderLayout.NORTH,space);
        add(BorderLayout.CENTER,panel);
        add(BorderLayout.SOUTH,abschickenButton);
        setTitle("Währungsauswahl");
        this.list1.setSelectedIndex(0);
        setVisible(true);
    }
    public int GET_CURRENCYINDEX(){
        return this.currencyindex;
    }
    public String GET_CURRENCY(){
        return this.selected_item;
    }
    public boolean GET_ACTION(){
        return this.action_performed;
    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Abschicken"))
        {

            int index = list1.getSelectedIndex();
            this.currencyindex=index;
            this.selected_item = (String) list1.getSelectedValue();
            this.setVisible(false);
            this.action_performed=true;
        }
    }
}
