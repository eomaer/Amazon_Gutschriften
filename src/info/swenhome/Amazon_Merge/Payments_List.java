package info.swenhome.Amazon_Merge;

import java.util.List;

public class Payments_List extends CSV_LIST{
    public Payments_List(){

    }
    public Payments_List ZAHLUNGEN_FILTERN(){
        Payments_List ergebnisliste=new Payments_List();
        int i=0;
        for(List<String> line:this.GET_list()){
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

        ZAHLUNGEN_VERARBEITEN("EUR");
        return ergebnisliste;
    }
    public Payments_List ZAHLUNGEN_VERARBEITEN(String currency){
        this.ADD_COLUMN("currency");
        int i=0;
        for(List<String> line:this.GET_list()){
            if(i>0) {
                int last_element_id = line.size();
                last_element_id = last_element_id - 1;
                this.SET_entry(last_element_id, i, currency);

            }
            i++;
        }
        return this;
    }
}
