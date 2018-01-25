package info.swenhome.Amazon_Merge.Listing_Tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Payments_List extends CSV_LIST{
    public Payments_List(){

    }
    public Payments_List ZAHLUNGEN_FILTERN(String currency){
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

        ZAHLUNGEN_VERARBEITEN(currency);
        return ergebnisliste;
    }

    private Payments_List ZAHLUNGEN_VERARBEITEN(String currency){
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

    public Payments_List FILTER_BY_DATE(String StartDate, String EndDate){
        Payments_List ergebnisliste=new Payments_List();
        DateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        Date sdate= new Date();
        try {sdate=format.parse(StartDate);} catch(ParseException e){e.printStackTrace();}
        Date edate=new Date();
        try {edate=format.parse(EndDate);} catch (ParseException e) {e.printStackTrace();}

        int i=0;
        for(List<String> line:this.GET_list()){
            if(i==0){
                ergebnisliste.GET_list().add(line);
                i++;
            }
            else{
            try {
                if(format.parse(line.get(16)).compareTo(edate)<=0){
                    if(format.parse(line.get(16)).compareTo(sdate)>=0){
                        ergebnisliste.GET_list().add(line);
                    }
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        }
        return ergebnisliste;
    }

    public Payments_List REPLACE_Date(String getdate) {
        Payments_List ergebnisliste=new Payments_List();
        int i=0;
        for(List<String> line:this.GET_list()){
            if(!(i==0)) {
                line.set(16, getdate);
            }
            ergebnisliste.GET_list().add(line);
            i++;
        }
        return ergebnisliste;
    }

    public Payments_List Zusammenfassen(){
        Payments_List ergebnisliste=new Payments_List();
        int i=0;
        for(List<String> line:this.GET_list()){
            List<String> ergebnisline=new ArrayList<>();
            if(i==0){
                ergebnisline.add("Mandant");
                ergebnisline.add("VerkaufsprotokollNummer");
                ergebnisline.add("ArtikelNR");
                ergebnisline.add("MENGE");
                ergebnisline.add("Preis");
                ergebnisline.add("NeuesGUDatum");
                ergebnisline.add("Gefunden");
                ergebnisline.add("Waehrung");
                ergebnisline.add("Umrechnungskurs");
                ergebnisline.add("Betrag in Euro");
                ergebnisline.add("Original-Date");
            }
            else{
                ergebnisline.add("1");
                ergebnisline.add(line.get(7));
                ergebnisline.add(line.get(21));
                if(!line.get(22).equals("")){ergebnisline.add(line.get(22));}else{ergebnisline.add("1");}
                ergebnisline.add(line.get(14));
                ergebnisline.add(line.get(16));
                ergebnisline.add(" ");
                ergebnisline.add(line.get(24));
                ergebnisline.add("0");
                ergebnisline.add("0");
                ergebnisline.add(line.get(line.size()-1));
            }
            i++;
            ergebnisliste.GET_list().add(ergebnisline);
        }
        return ergebnisliste;
    }

    public Payments_List Add_Date() {
        Payments_List ergebnisliste = new Payments_List();
        int i=0;
        for(List<String> line:this.GET_list()){

            if(!(i==0)){
            line.set(line.size()-1, line.get(16));
            }
            ergebnisliste.GET_list().add(line);
            i++;
        }
        return ergebnisliste;
    }
}
