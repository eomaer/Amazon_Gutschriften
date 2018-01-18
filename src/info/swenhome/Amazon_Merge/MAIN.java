package info.swenhome.Amazon_Merge;

import javax.swing.*;
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
    public static CSV_LIST ZAHLUNGEN_FILTERN(CSV_LIST payments){
        CSV_LIST ergebnisliste=new CSV_LIST();
        int i=0;
        for(List<String> line:payments.GET_list()){
            if(i==0){
                ergebnisliste.GET_list().add(line);
                i++;
            }
            if(line.size()>7){
                if(line.get(6).equals("Refund")){
                    if(line.get(13).equals("Principal")) {
                        ergebnisliste.GET_list().add(line);
                    }
                }
            }
        }
        ZAHLUNGEN_VERARBEITEN(ergebnisliste,"EUR");
        return ergebnisliste;
    }
    public static CSV_LIST ZAHLUNGEN_VERARBEITEN(CSV_LIST payments, String currency){
        payments.ADD_COLUMN("currency");
        int i=0;
        for(List<String> line:payments.GET_list()){
            if(i>0) {
                int last_element_id = line.size();
                last_element_id = last_element_id - 1;
                payments.SET_entry(last_element_id, i, currency);

            }
            i++;
        }
        return payments;
    }
    public static void main (String[] args) {
       // CSV_LIST orders=new CSV_LIST();
        CSV_LIST payments=new CSV_LIST();
        //JFileChooser fc = new JFileChooser();
        //fc.showOpenDialog(null);
        //orders.FILE_TO_LIST(fc.getSelectedFile());
        JFileChooser fc_payments = new JFileChooser();
        fc_payments.showOpenDialog(null);
        payments.FILE_TO_LIST(fc_payments.getSelectedFile());
        payments=ZAHLUNGEN_FILTERN(payments);
        payments.PRINT_LIST();
        //CONNECT_LIST(orders,payments);


    }

}
