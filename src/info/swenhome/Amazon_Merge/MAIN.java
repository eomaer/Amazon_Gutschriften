package info.swenhome.Amazon_Merge;

import info.swenhome.Amazon_Merge.Listing_Tools.CSV_LIST;
import info.swenhome.Amazon_Merge.Listing_Tools.Orders_List;
import info.swenhome.Amazon_Merge.Listing_Tools.Payments_List;
import info.swenhome.Amazon_Merge.Supplements.Action_Chooser;
import info.swenhome.Amazon_Merge.Supplements.Currency_Chooser;
import info.swenhome.Amazon_Merge.Supplements.Date_Chooser;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MAIN {
    public static CSV_LIST CONNECT_LIST(CSV_LIST orders, CSV_LIST payments){
       CSV_LIST ergebnisliste=new CSV_LIST();
        //aus orders muss "amazon-order-id" (index 1) mit der spalte "order-id" (index 8) aus payments verglichen werden
        //jede zeile aus orders muss in ergebnisliste, zusätzlich letzte spalte mit ORDER
        //wenn order-id in der orders tab gefunden wird und transaction-type == refund und amount-type== Item-Price, soll die zeile an die orders-tab angehängt werden
        // jedoch die spalte ITEM-PRICE wird durch die spalte amount aus der payments gesetzt.
        return ergebnisliste;
    }
    public static void wait_on_date (Date_Chooser cl){
        while(!cl.GET_ACTION()){
          try{ Thread.sleep(1000);}
          catch (InterruptedException ex){Thread.currentThread().interrupt();}
        }
    }
    public static void wait_on_curr (Currency_Chooser cl){
        while(!cl.GET_ACTION()){
            try{ Thread.sleep(1000);}
            catch (InterruptedException ex){Thread.currentThread().interrupt();}
        }
    }
    public static void wait_on_action (Action_Chooser cl){
        while(!cl.GET_ACTION()){
            try{ Thread.sleep(1000);}
            catch (InterruptedException ex){Thread.currentThread().interrupt();}
        }
    }
    public static void Refund_Verarbeiten(){
        Orders_List orders=new Orders_List();
        Payments_List payments=new Payments_List();
        JFileChooser fc_payments = new JFileChooser();
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
        payments=payments.FILTER_BY_DATE(first_date.GETDATE(),end_date.GETDATE());
        payments=payments.REPLACE_Date(buchungsdatum.GETDATE());
        payments.PRINT_LIST();
        JFileChooser fc_writefile=new JFileChooser();
        fc_writefile.setDialogTitle("Datei speichern unter");
        fc_writefile.showSaveDialog(null);
        if(!fc_writefile.getSelectedFile().exists()){
            try {
                fc_writefile.getSelectedFile().createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        payments=payments.Zusammenfassen();
        payments.SAVE_LIST(fc_writefile.getSelectedFile());
    }

    public static Orders_List Orders_Abgleichen(){
        Orders_List orders=new Orders_List();
        Payments_List payments=new Payments_List();
        //Listen Einlesen
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Orders_Liste auswählen");
        fc.showOpenDialog(null);
        orders.FILE_TO_LIST(fc.getSelectedFile());

        JFileChooser fc_payments = new JFileChooser();
        fc_payments.setDialogTitle("Payments_Liste auswählen");
        fc_payments.showOpenDialog(null);
        payments.FILE_TO_LIST(fc_payments.getSelectedFile());

        //Daten einbinden
        Currency_Chooser currency=new Currency_Chooser();
        wait_on_curr(currency);
        //Date_Chooser buchungsdatum=new Date_Chooser("Bitte Buchungsdatum wählen:");
        //wait_on_date(buchungsdatum);
        //Date_Chooser first_date=new Date_Chooser("Bitte frühestes Datum wählen:");
        //wait_on_date(first_date);
        //Date_Chooser end_date=new Date_Chooser("Bitte letztes Datum wählen");
        //wait_on_date(end_date);
        //Listen verarbeiten
        payments=payments.ZAHLUNGEN_FILTERN(currency.GET_CURRENCY());
        //payments=payments.FILTER_BY_DATE(first_date.GETDATE(),end_date.GETDATE());
        //payments=payments.REPLACE_Date(buchungsdatum.GETDATE());
        payments.PRINT_LIST();
        orders.ADD_COLUMN("SatzArt");
        for(List<String> line:orders.GET_list()){
            line.set(line.size()-1,"ORDER");
        }
        Orders_List verarbeitete_Orders=new Orders_List();
        verarbeitete_Orders=orders;
        for(List<String> line:payments.GET_list()){
            String order_id=line.get(7);
            int i=0;
            for(List<String> oline:orders.GET_list()){
                i++;
                if(order_id.equals(oline.get(0))){
                    if(line.get(21).equals(oline.get(13))) {
                        List<String> ergebnis_line = new ArrayList<>();
                        for (String entry : oline) {
                            ergebnis_line.add(entry);
                        }
                        ergebnis_line.set(16,line.get(7));
                        ergebnis_line.set(17,line.get(4));
                        ergebnis_line.set(ergebnis_line.size()-1,"REFUND");
                        verarbeitete_Orders.GET_list().add(ergebnis_line);
                        break;
                    }
                }

            }
        }
        return verarbeitete_Orders;
    }

    public static void main (String[] args) {
        //Was Tun?
        Action_Chooser ac=new Action_Chooser("Bitte Aktion wählen:");
        wait_on_action(ac);

        switch (ac.GET_ACT()){
            case 1:
                Orders_List verarbeitet=Orders_Abgleichen();
                JFileChooser fc_writefile=new JFileChooser();
                fc_writefile.setDialogTitle("Datei speichern unter");
                fc_writefile.showSaveDialog(null);
                if(!fc_writefile.getSelectedFile().exists()){
                    try {
                        fc_writefile.getSelectedFile().createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                verarbeitet.SAVE_LIST(fc_writefile.getSelectedFile());
                break;
            case 2:
                Refund_Verarbeiten();
                break;
            default:
                JOptionPane.showMessageDialog(new JFrame(),"Man kann nicht keine Aktion ausführen!","Keine Aktion gewählt!", JOptionPane.ERROR_MESSAGE);
                break;
        }

        //Orders_List orders=new Orders_List();
        //Payments_List payments=new Payments_List();
        //Listen Einlesen
        //JFileChooser fc = new JFileChooser();
        //fc.setDialogTitle("Orders_Liste auswählen");
        //fc.showOpenDialog(null);
        //orders.FILE_TO_LIST(fc.getSelectedFile());

        //JFileChooser fc_payments = new JFileChooser();
        //fc_payments.setDialogTitle("Payments_Liste auswählen");
        //fc_payments.showOpenDialog(null);
        //payments.FILE_TO_LIST(fc_payments.getSelectedFile());

        //Listen verarbeiten
        //payments=payments.ZAHLUNGEN_FILTERN();

        //Daten einbinden
        //Currency_Chooser currency=new Currency_Chooser();
        //wait_on_curr(currency);
        //Date_Chooser buchungsdatum=new Date_Chooser("Bitte Buchungsdatum wählen:");
        //wait_on_date(buchungsdatum);
        //Date_Chooser first_date=new Date_Chooser("Bitte frühestes Datum wählen:");
        //wait_on_date(first_date);
        //Date_Chooser end_date=new Date_Chooser("Bitte letztes Datum wählen");
        //wait_on_date(end_date);
        //payments.PRINT_LIST();

        //CONNECT_LIST(orders,payments);
        //haltepunkt am ende :)
        System.out.print("alles fäddisch");
        System.exit(0);

    }

}
