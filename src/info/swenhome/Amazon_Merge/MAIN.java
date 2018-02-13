package info.swenhome.Amazon_Merge;


import info.swenhome.Amazon_Merge.Listing_Tools.Orders_List;
import info.swenhome.Amazon_Merge.Listing_Tools.Payments_List;
import info.swenhome.Amazon_Merge.Listing_Tools.Storage_List;
import info.swenhome.Amazon_Merge.Supplements.Action_Chooser;
import info.swenhome.Amazon_Merge.Supplements.Currency_Chooser;
import info.swenhome.Amazon_Merge.Supplements.Date_Chooser;
import info.swenhome.Amazon_Merge.Supplements.Ladebalken;

import javax.swing.*;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MAIN {
    private static void wait_on_date(Date_Chooser cl){
        while(!cl.GET_ACTION()){
          try{ Thread.sleep(1000);}
          catch (InterruptedException ex){Thread.currentThread().interrupt();}
        }
    }

    private static void wait_on_curr(Currency_Chooser cl){
        while(!cl.GET_ACTION()){
            try{ Thread.sleep(1000);}
            catch (InterruptedException ex){Thread.currentThread().interrupt();}
        }
    }

    private static void wait_on_action(Action_Chooser cl){
        while(!cl.GET_ACTION()){
            try{ Thread.sleep(1000);}
            catch (InterruptedException ex){Thread.currentThread().interrupt();}
        }
    }

    private static void Refund_Verarbeiten(){

        Payments_List payments=new Payments_List();
        JFileChooser fc_payments = new JFileChooser();
        fc_payments.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc_payments.setDialogTitle("Payments_Liste auswählen");
        fc_payments.showOpenDialog(null);
        payments.FILE_TO_LIST(fc_payments.getSelectedFile());

        //Daten einbinden
        Currency_Chooser currency=new Currency_Chooser();
        wait_on_curr(currency);
        Date_Chooser buchungsdatum=new Date_Chooser("Bitte Buchungsdatum wählen:");
        wait_on_date(buchungsdatum);
        Date_Chooser first_date=new Date_Chooser("Bitte frühestes Datum wählen:");
        wait_on_date(first_date);
        Date_Chooser end_date=new Date_Chooser("Bitte letztes Datum wählen");
        wait_on_date(end_date);
        //Listen verarbeiten
        payments=payments.ZAHLUNGEN_FILTERN(currency.GET_CURRENCY());
        payments.ADD_COLUMN("Original-Date");
        payments=payments.Add_Date();
        payments=payments.FILTER_BY_DATE(first_date.GETDATE(),end_date.GETDATE());
        payments=payments.REPLACE_Date(buchungsdatum.GETDATE());
        //payments.PRINT_LIST();
        JFileChooser fc_writefile=new JFileChooser();
        fc_writefile.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc_writefile.setDialogTitle("Datei speichern unter");
        fc_writefile.showSaveDialog(null);
        if(!fc_writefile.getSelectedFile().exists()){
            try {
                boolean id=fc_writefile.getSelectedFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        payments=payments.Zusammenfassen_mit();
        payments=payments.Summieren();
        payments=payments.Zusammenfassen_ohne();
        payments=payments.Auftrennen();
        payments.SAVE_LIST(fc_writefile.getSelectedFile());
    }

    private static void Refund_Verarbeiten2(){
        Orders_List orders=new Orders_List();
        Payments_List payments=new Payments_List();
        JFileChooser fc_payments = new JFileChooser();
        fc_payments.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc_payments.setDialogTitle("Payments_Liste auswählen");
        fc_payments.showOpenDialog(null);
        payments.FILE_TO_LIST(fc_payments.getSelectedFile());

        //Daten einbinden
        Currency_Chooser currency=new Currency_Chooser();
        wait_on_curr(currency);
        Date_Chooser first_date=new Date_Chooser("Bitte frühestes Datum wählen:");
        wait_on_date(first_date);
        Date_Chooser end_date=new Date_Chooser("Bitte letztes Datum wählen");
        wait_on_date(end_date);
        //Listen verarbeiten
        payments=payments.ZAHLUNGEN_FILTERN(currency.GET_CURRENCY());
        payments.ADD_COLUMN("Original-Date");
        payments=payments.Add_Date();
        payments=payments.FILTER_BY_DATE(first_date.GETDATE(),end_date.GETDATE());

        JFileChooser fc_writefile=new JFileChooser();
        fc_writefile.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc_writefile.setDialogTitle("Datei speichern unter");
        fc_writefile.showSaveDialog(null);
        if(!fc_writefile.getSelectedFile().exists()){
            try {
                boolean id=fc_writefile.getSelectedFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        payments=payments.Zusammenfassen_mit();
        payments=payments.Summieren();
        payments=payments.Zusammenfassen_ohne();
        payments=payments.Auftrennen();
        payments.SAVE_LIST(fc_writefile.getSelectedFile());
    }

    private static Orders_List Orders_Abgleichen(){
        Orders_List orders=new Orders_List();
        Payments_List payments=new Payments_List();
        //Listen Einlesen
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc.setDialogTitle("Orders_Liste auswählen");
        fc.showOpenDialog(null);
        orders.FILE_TO_LIST(fc.getSelectedFile());

        JFileChooser fc_payments = new JFileChooser();
        fc_payments.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc_payments.setDialogTitle("Payments_Liste auswählen");
        fc_payments.showOpenDialog(null);
        payments.FILE_TO_LIST(fc_payments.getSelectedFile());

        //Daten einbinden
        Currency_Chooser currency=new Currency_Chooser();
        wait_on_curr(currency);

        //Listen verarbeiten
        payments=payments.ZAHLUNGEN_FILTERN(currency.GET_CURRENCY());

        orders.ADD_COLUMN("SatzArt");
        for(List<String> line:orders.GET_list()){
            line.set(line.size()-1,"ORDER");
        }
        for(List<String> line:payments.GET_list()){
            String order_id=line.get(7);
            int i=0;
            for(List<String> oline:orders.GET_list()){
                i++;
                if(order_id.equals(oline.get(0))){
                    if(line.get(21).equals(oline.get(11))) {
                        List<String> ergebnis_line = new ArrayList<>(oline);
                        ergebnis_line.set(15,line.get(24).replace(',','.'));
                        ergebnis_line.set(16,line.get(14).replace(',','.'));
                        ergebnis_line.set(ergebnis_line.size()-1,"REFUND");
                        orders.GET_list().add(ergebnis_line);
                        break;
                    }
                }

            }
        }
        return orders;
    }

    private static Payments_List CSV_Merge(){
        Payments_List payments=new Payments_List();
        Payments_List payments2=new Payments_List();
        JFileChooser fc_payments = new JFileChooser();
        fc_payments.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc_payments.setDialogTitle("Erste Payments_Liste auswählen");
        fc_payments.showOpenDialog(null);
        payments.FILE_TO_LIST(fc_payments.getSelectedFile());
        JFileChooser fc_payments2 = new JFileChooser();
        fc_payments2.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc_payments2.setDialogTitle("Zweite Payments_Liste auswählen");
        fc_payments2.showOpenDialog(null);
        payments2.FILE_TO_LIST(fc_payments2.getSelectedFile());
        Payments_List ergebnisliste =new Payments_List();
        Ladebalken ld=new Ladebalken("Verarbeitung");
        int i=0;
        int ges=payments.GET_zeilenanzahl()+payments2.GET_zeilenanzahl();
        for(List<String> line:payments.GET_list()){
            if(i==0){
                ergebnisliste.GET_list().add(line);
            }
            if(line.get(2).isEmpty()){
                if(!line.get(6).equals("other-transaction")) {
                    ergebnisliste.GET_list().add(line);
                }
            }
            i++;
            ld.SET_VALUE(i/ges);
        }
        for(List<String> line2:payments2.GET_list()){
            if(line2.get(2).isEmpty()){
                if(!line2.get(6).equals("other-transaction")) {
                    ergebnisliste.GET_list().add(line2);
                }
            }
            i++;
            ld.SET_VALUE(i/ges);
        }
        ld.SET_VISIBLE(false);

        return ergebnisliste;

    }
    static void Lager_Verarbeiten(){
        Storage_List storage=new Storage_List();
        //Listen Einlesen
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc.setDialogTitle("Orders_Liste auswählen");
        fc.showOpenDialog(null);
        storage.FILE_TO_LIST(fc.getSelectedFile());
        storage=storage.Verarbeiten();
        JFileChooser fc_writefile=new JFileChooser();
        fc_writefile.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
        fc_writefile.setDialogTitle("Datei speichern unter");
        fc_writefile.showSaveDialog(null);
        if(!fc_writefile.getSelectedFile().exists()){
            try {
                boolean id=fc_writefile.getSelectedFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        storage.SAVE_LIST(fc_writefile.getSelectedFile());
    }
    public static void main (String[] args) {
        //Was Tun?
        Action_Chooser ac=new Action_Chooser("Bitte Aktion wählen:");
        wait_on_action(ac);

        switch (ac.GET_ACT()){
            case 1:
                Orders_List verarbeitet=Orders_Abgleichen();
                JFileChooser fc_writefile_ca1=new JFileChooser();
                fc_writefile_ca1.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));

                fc_writefile_ca1.setDialogTitle("Datei speichern unter");
                fc_writefile_ca1.showSaveDialog(null);
                if(!fc_writefile_ca1.getSelectedFile().exists()){
                    try {
                        boolean id = fc_writefile_ca1.getSelectedFile().createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                verarbeitet.SAVE_LIST(fc_writefile_ca1.getSelectedFile());
                break;
            case 2:
                Refund_Verarbeiten();
                break;
            case 4:
                Payments_List merged = CSV_Merge();
                JFileChooser fc_writefile_ca4=new JFileChooser();
                fc_writefile_ca4.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
                fc_writefile_ca4.setDialogTitle("Datei speichern unter");
                fc_writefile_ca4.showSaveDialog(null);
                if(!fc_writefile_ca4.getSelectedFile().exists()){
                    try {
                        boolean id=fc_writefile_ca4.getSelectedFile().createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                merged.SAVE_LIST(fc_writefile_ca4.getSelectedFile());
                break;
            case 5:
                Refund_Verarbeiten2();
                break;
            case 6:
                Lager_Verarbeiten();
                break;
            default:
                JOptionPane.showMessageDialog(new JFrame(),"Man kann nicht keine Aktion ausführen!","Keine Aktion gewählt!", JOptionPane.ERROR_MESSAGE);
                break;
        }


        System.out.print("alles fäddisch");
        System.exit(0);

    }

}
