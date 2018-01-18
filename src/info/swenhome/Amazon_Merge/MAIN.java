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

    public static void main (String[] args) {
       // CSV_LIST orders=new CSV_LIST();
        Payments_List payments=new Payments_List();
        //JFileChooser fc = new JFileChooser();
        //fc.showOpenDialog(null);
        //orders.FILE_TO_LIST(fc.getSelectedFile());
        JFileChooser fc_payments = new JFileChooser();
        fc_payments.showOpenDialog(null);
        payments.FILE_TO_LIST(fc_payments.getSelectedFile());
        payments=payments.ZAHLUNGEN_FILTERN();
        payments.PRINT_LIST();
        //CONNECT_LIST(orders,payments);


    }

}
