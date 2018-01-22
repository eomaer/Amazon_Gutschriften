package info.swenhome.Amazon_Merge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Currency_Chooser extends JFrame implements ActionListener {
    private JButton abschickenButton=new JButton("Abschicken");
    private String[] currencies = {"EUR", "GBP"};
    private String selected_item="";
    private JList list1 = new JList(this.currencies);

    public Currency_Chooser()
    {
        setBounds(100,100,150,150);
        setLayout(new BorderLayout());
        setUndecorated(true);
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
    public static void main(String[] args){
        new Currency_Chooser();
    }
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("Abschicken"))
        {
            int index = list1.getSelectedIndex();
            System.out.println("Index Selected: " + index);
            this.selected_item = (String) list1.getSelectedValue();
            System.out.println("Value Selected: " + this.selected_item);
            this.setVisible(false);
            System.exit(0);
        }
    }
}
