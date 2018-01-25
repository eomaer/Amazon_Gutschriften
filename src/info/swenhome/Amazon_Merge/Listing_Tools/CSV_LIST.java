package info.swenhome.Amazon_Merge.Listing_Tools;

import info.swenhome.Amazon_Merge.Supplements.Ladebalken;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSV_LIST {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //diese klasse stellt ein Tool zum Umwandeln von CSV/TXT dateien in List of StringArrays dar.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private List<List<String>> list=new ArrayList<>();
    private File file = null;
    private String path = null;
    private int anzahl_zeilen = 0;
    private int anzahl_spalten =0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktoren, man kann nichts übergeben, dann muss man die Datei auswählen,
    //man kann einen pfad übergeben, oder ein file oder eine liste.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    CSV_LIST(){

    }

    public CSV_LIST(String path){
        this.path=path;
        this.file = new File(path);
        FILE_TO_LIST(this.file);

    }

    public CSV_LIST(File file){
        this.file=file;
        this.path=file.getAbsolutePath();
        FILE_TO_LIST(file);
    }

    public CSV_LIST(List<List<String>> list){
        this.list=list;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Setter-Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean SET_list(List<List<String>>list){
        boolean status=false;
        this.list=list;
        status=true;
        return status;
    }

    public boolean SET_file(File file){
        boolean status=false;
        this.file = file;
        status=true;
        return status;
    }

    public boolean SET_path(){
        boolean status=false;

        status=true;
        return status;
    }

    public void SET_entry(int col, int row, String entry){
        List<String>line=this.list.get(row);
        line.set(col, entry);
        this.list.set(row,line);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Getter-Methods
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<List<String>>GET_list(){
        return this.list;
    }

    public File GET_file(){
        return this.file;
    }

    public String GET_path(){
        return this.path;
    }

    public int GET_zeilenanzahl(){
        return this.anzahl_zeilen;
    }

    public int GET_spaltenanzahl(){
        return this.anzahl_spalten;
    }

    public String GET_ENTRY(int col, int line){
        int xline = 1;
        for(List<String> lin:this.list){
            if (xline==line){
                int xcol = 1;
                for (String entry:lin){
                    if(xcol==col) {
                        return (entry);
                    }
                    xcol++;
                }
            }
            xline++;
        }
        return "NOENTRY";
    }

    public List<String> GET_LINE(int line){
        int xline = 1;
        for(List<String> lin:this.list){
            if (xline==line){
                return (lin);
            }
            xline++;
        }
        return null;
    }

    public List<String> GET_COL (int col){
        String returnval="";
        List<String> col_val=new ArrayList<>();
        for (List<String> lin:this.list){
            int xentry=1;
            for (String entry:lin) {
                if (xentry == col) {
                    col_val.add(entry);
                }
                xentry++;
            }
        }
        return col_val;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Class-Methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean UPDATE_VALUES(){
        boolean status=false;
        this.anzahl_zeilen =this.list.size();
        for (List<String> line:this.list){
            if(this.anzahl_spalten<line.size()){
                this.anzahl_spalten=line.size();
            }
        }
        status=true;
        return status;
    }

    public String ERSETZE_SONDERZEICHEN(String eingabe){
        String ausgabe=null;
        ausgabe=eingabe.replace("ä","ae");
        ausgabe=ausgabe.replace("ö","oe");
        ausgabe=ausgabe.replace("ü","ue");
        ausgabe=ausgabe.replace("Ä","Ae");
        ausgabe=ausgabe.replace("Ö","Oe");
        ausgabe=ausgabe.replace("Ü","Ue");
        ausgabe=ausgabe.replace("ß","ss");
        ausgabe=ausgabe.replace(';',',');
        ausgabe=ausgabe.replace('\t',';');
        ausgabe=ausgabe.replace('\u0009',';');
        return ausgabe;
    }

    public void FILE_TO_LIST(File file){
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line=null;
            int table_length=0;
            int i=0;
            while ((line=br.readLine())!=null){
                line=this.ERSETZE_SONDERZEICHEN(line);
                String[] entries=line.split(";");
                List<String> lentries = new ArrayList<>();


                if (i==0){
                    table_length=entries.length;
                }
                for(String entrie:entries) {
                    lentries.add(entrie);
                    i++;
                }
                if(entries.length<table_length){
                    int j=table_length-entries.length;
                    for (int z=0;z<j;z++){
                        lentries.add(" ");
                    }
                }
                this.list.add(lentries);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.UPDATE_VALUES();
    }

    public void ADD_COLUMN(String name){
        int i=0;
        for(List<String> line:this.list){
            if (i==0){line.add(name);}
            else{ line.add("");}
            i++;
        }
    }

    public void ADD_LINE(List<String> line){
        this.list.add(line);
    }

    public void PRINT_LIST(){
        int linecounter=0;
        for(List<String> line:this.list){
            int colcounter=0;
            for (String entry:line){
                String out="";
                if(entry.length()>10){
                    out=entry.substring(0,7)+"...";
                }
                if(entry.length()==10){
                    out=entry;
                }
                if(entry.length()<10){
                    int i=10-entry.length();
                    out=entry;
                    for (int j=0;j<i;j++){
                        out=out+" ";
                    }
                }
                out=out+" ";
                System.out.print(out);
            }

            System.out.println(" ");
            linecounter++;
        }
    }
    public void SAVE_LIST(File file) {
//HIER MUSS DIE SAVE-FUNKTION ANGEPASST WERDEN; DA DIE FILES VIEL ZU GROß WERDEN!!!
        String inhalt = "";
        int i=0;
        Ladebalken ld=new Ladebalken("Speichern");
        this.UPDATE_VALUES();
        for (List<String> line : this.GET_list()) {
            String collect;
            collect = line.stream().collect(Collectors.joining(String.valueOf('\t')));
            try (FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(collect);
                out.close();
                //more code
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            ld.SET_VALUE(i*100/this.GET_zeilenanzahl());
            i++;

        }
        ld.SET_VISIBLE(false);
    }

}
