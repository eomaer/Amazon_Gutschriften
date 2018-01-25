package info.swenhome.Amazon_Merge.Supplements;


import javax.swing.*;

public class Ladebalken {
    // JProgressBar-Objekt wird erzeugt
    private JProgressBar meinLadebalken = new JProgressBar(0, 100);
    private JFrame meinJFrame = new JFrame();
    public Ladebalken(String titel) {

        this.meinJFrame.setBounds(300,300,300, 100);
        this.meinJFrame.setTitle(titel);
        JPanel meinPanel = new JPanel();



        // Wert für den Ladebalken wird gesetzt
        this.meinLadebalken.setValue(0);

        // Der aktuelle Wert wird als
        // Text in Prozent angezeigt
        this.meinLadebalken.setStringPainted(true);

        // JProgressBar wird Panel hinzugefügt
        meinPanel.add(this.meinLadebalken);

        this.meinJFrame.add(meinPanel);
        this.meinJFrame.setVisible(true);

        // Wert des Ladebalkens wird in der Schleife
        // bei jedem Durchgang um 1 erhöht bis der
        // maximale Wert erreicht ist

    }

    public void SET_VALUE(int val){
        this.meinLadebalken.setValue(val);
    }
    public void SET_VISIBLE(boolean b){
        this.meinJFrame.setVisible(b);
    }

}
