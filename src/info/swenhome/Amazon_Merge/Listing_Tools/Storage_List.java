package info.swenhome.Amazon_Merge.Listing_Tools;

import java.util.ArrayList;
import java.util.List;

public class Storage_List extends CSV_LIST{
    public Storage_List (){

    }
    public Storage_List Verarbeiten(){
        Storage_List ergebnisliste=new Storage_List();
        int i = 0;
        for (List<String> line : this.GET_list()) {
            List<String> ergebnisline = new ArrayList<>();
            if (i == 0) {
                ergebnisline.add("ID");
                ergebnisline.add("snapshot-date");
                ergebnisline.add("fnsku");
                ergebnisline.add("sku");
                ergebnisline.add("product-name");
                ergebnisline.add("quantity");
                ergebnisline.add("fulfillment-center-id");
                ergebnisline.add("detailed-disposition");
                ergebnisline.add("country");

            } else {
                ergebnisline.add(String.valueOf(i-1));
                ergebnisline.add(line.get(0));
                ergebnisline.add(line.get(1));
                if(line.get(2).equals("00-ICEM-D352")){ergebnisline.add("8013912");}else{ergebnisline.add(line.get(2));}
                ergebnisline.add(line.get(3));
                ergebnisline.add(line.get(5));
                ergebnisline.add(line.get(6));
                ergebnisline.add(line.get(7));
                ergebnisline.add(line.get(8));
            }
            i++;
            ergebnisliste.GET_list().add(ergebnisline);
        }
        return ergebnisliste;
    }
}
