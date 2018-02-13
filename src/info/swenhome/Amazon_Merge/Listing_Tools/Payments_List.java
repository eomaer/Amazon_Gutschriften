package info.swenhome.Amazon_Merge.Listing_Tools;

import info.swenhome.Amazon_Merge.Supplements.Marketplace_Chooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Payments_List extends CSV_LIST {
    public Payments_List() {

    }

    public Payments_List ZAHLUNGEN_FILTERN(String currency) {
        Payments_List ergebnisliste = new Payments_List();
        int i = 0;
        for (List<String> line : this.GET_list()) {
            if (i == 0) {
                ergebnisliste.GET_list().add(line);
                i++;
            }
            if (line.size() > 7) {
                if (line.get(6).equals("Refund")) {
                    if ((line.get(12).equals("ItemPrice")) || (line.get(12).equals("Promotion"))) {
                        ergebnisliste.GET_list().add(line);
                    }
                }
            }
        }

        ZAHLUNGEN_VERARBEITEN(currency);
        return ergebnisliste;
    }

    private void ZAHLUNGEN_VERARBEITEN(String currency) {
        this.ADD_COLUMN("currency");
        int i = 0;
        for (List<String> line : this.GET_list()) {
            if (i > 0) {
                int last_element_id = line.size();
                last_element_id = last_element_id - 1;
                this.SET_entry(last_element_id, i, currency);

            }
            i++;
        }
    }

    public Payments_List FILTER_BY_DATE(String StartDate, String EndDate) {
        Payments_List ergebnisliste = new Payments_List();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date sdate = new Date();
        try {
            sdate = format.parse(StartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date edate = new Date();
        try {
            edate = format.parse(EndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int i = 0;
        for (List<String> line : this.GET_list()) {
            if (i == 0) {
                ergebnisliste.GET_list().add(line);
                i++;
            } else {
                try {
                    if (format.parse(line.get(16)).compareTo(edate) <= 0) {
                        if (format.parse(line.get(16)).compareTo(sdate) >= 0) {
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
        Payments_List ergebnisliste = new Payments_List();
        int i = 0;
        for (List<String> line : this.GET_list()) {
            if (!(i == 0)) {
                line.set(16, getdate);
            }
            ergebnisliste.GET_list().add(line);
            i++;
        }
        return ergebnisliste;
    }

    public Payments_List Zusammenfassen_mit() {
        Payments_List ergebnisliste = new Payments_List();

        Marketplace_Chooser market=new Marketplace_Chooser();
        int i = 0;
        for (List<String> line : this.GET_list()) {
            List<String> ergebnisline = new ArrayList<>();
            if (i == 0) {
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
                ergebnisline.add("Infofeld1");
                ergebnisline.add("Infofeld2");
                ergebnisline.add("Infofeld3");
                ergebnisline.add("Infofeld4");
//              ergebnisline.add("Original-Date");
                ergebnisline.add("Type");
                ergebnisline.add("desc");

            } else {
                ergebnisline.add("1");
                ergebnisline.add(line.get(7));
                ergebnisline.add(line.get(21));
                ergebnisline.add("1");
                ergebnisline.add(line.get(14));
                ergebnisline.add(line.get(16));
                ergebnisline.add(" ");
                ergebnisline.add(line.get(24));
                ergebnisline.add("0");
                ergebnisline.add("0");
                ergebnisline.add(market.GET_MARKET());
                ergebnisline.add(" ");
                ergebnisline.add(" ");
                ergebnisline.add(" ");
//              ergebnisline.add(line.get(line.size()-1));
                ergebnisline.add(line.get(12));
                ergebnisline.add(line.get(13));
            }
            i++;
            ergebnisliste.GET_list().add(ergebnisline);
        }
        return ergebnisliste;
    }

    public Payments_List Zusammenfassen_ohne() {
        Payments_List ergebnisliste = new Payments_List();
        int i = 0;
        for (List<String> line : this.GET_list()) {
            List<String> ergebnisline;
            line.set(4,line.get(4).replace('-',' '));
            line.set(4,line.get(4).replace('.',','));
            line.remove(line.size()-1);
            line.remove(line.size()-1);
            ergebnisline=line;
            i++;
            ergebnisliste.GET_list().add(ergebnisline);
        }
        return ergebnisliste;
    }


    public Payments_List Add_Date() {
        Payments_List ergebnisliste = new Payments_List();
        int i = 0;
        for (List<String> line : this.GET_list()) {

            if (!(i == 0)) {
                line.set(line.size() - 1, line.get(16));
            }
            ergebnisliste.GET_list().add(line);
            i++;
        }
        return ergebnisliste;
    }

    public Payments_List Summieren() {
        List<String> oldline = new ArrayList<>();
        Payments_List ergebnisliste = new Payments_List();
        int i = 0;
        BigDecimal wertalt;
        BigDecimal wertneu;

        for (List<String> line : this.GET_list()) {
            if (i == 0) {
                ergebnisliste.GET_list().add(line);
            }
            else if(i==1){oldline=line;}
            else{
                if(oldline.get(1).equals(line.get(1))){
                    if(oldline.get(14).equals("ItemPrice")||oldline.get(14).equals("Promotion")){
                        wertalt=new BigDecimal(oldline.get(4).replace(',','.'));
                        wertneu=new BigDecimal(line.get(4).replace(',','.'));
                        BigDecimal summe=wertalt;
                        summe=summe.add(wertneu);
                        summe=summe.setScale(2,BigDecimal.ROUND_HALF_UP);
                        oldline.set(4,summe.toString());
                        oldline.set(4,oldline.get(4).replace('.',','));
                    }
                }
                else{
                    ergebnisliste.GET_list().add(oldline);
                    oldline=line;
                }
            }
            i++;
        }
        ergebnisliste.GET_list().add(oldline);

        return ergebnisliste;
    }
    public Payments_List Auftrennen(){
        Payments_List ergebnisliste=new Payments_List();
        Payments_List teilgutschriften=new Payments_List();
        float value;
        float vergleich;
        int i=0;
        for (List<String> line: this.GET_list()) {
            if (i == 0) {
                ergebnisliste.GET_list().add(line);
                teilgutschriften.GET_list().add((line));
            } else {
                vergleich=14.50f;
                value= Float.valueOf(line.get(4).replace(',','.'));
                if (value>vergleich) {
                    ergebnisliste.GET_list().add(line);
                } else {
                    teilgutschriften.GET_list().add(line);
                }

            }
            i++;
        }
            int test=teilgutschriften.GET_list().size();
            if(teilgutschriften.GET_list().size()>1){
            JFileChooser fc_writefile=new JFileChooser();
            fc_writefile.setCurrentDirectory(new File("Y:/Amazon/Berichte/Zahlungen"));
            fc_writefile.setDialogTitle("Teilgutschriften speichern unter");
            fc_writefile.showSaveDialog(null);
            if(!fc_writefile.getSelectedFile().exists()){
                try {
                    boolean id=fc_writefile.getSelectedFile().createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            teilgutschriften.SAVE_LIST(fc_writefile.getSelectedFile());}


        return ergebnisliste;
    }

}