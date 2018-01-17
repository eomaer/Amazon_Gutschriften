import javax.swing.*;
import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class CSV_LIST {
    //diese klasse stellt ein Tool zum Umwandeln von CSV/TXT dateien in List of StringArrays dar.
    private List<List<String>> list=null;
    private File file = null;
    private String path = null;
    int anzahl_zeilen = 0;
    int anzahl_spalten =0;

    //Konstruktoren, man kann nichts übergeben, dann muss man die Datei auswählen,
    //man kann einen pfad übergeben, oder ein file oder eine liste.
    public CSV_LIST(){

    }
    public CSV_LIST(String path){
        this.path=path;
        this.file = new File(path);

    }
    public CSV_LIST(File file){
        this.file=file;
    }
    public CSV_LIST(List<List<String>> list){
        this.list=list;
    }

    //Setter-Methods
    public boolean SET_list(){
        boolean status=false;

        status=true;
        return status;
    }
    public boolean SET_file(){
        boolean status=false;

        status=true;
        return status;
    }
    public boolean SET_path(){
        boolean status=false;

        status=true;
        return status;
    }

    //Getter-Methods

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
    // Class-Methods
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
        ausgabe=eingabe.replace("ö","oe");
        ausgabe=eingabe.replace("ü","ue");
        ausgabe=eingabe.replace("Ä","Ae");
        ausgabe=eingabe.replace("Ö","Oe");
        ausgabe=eingabe.replace("Ü","Ue");
        ausgabe=eingabe.replace("ß","ss");
        ausgabe=eingabe.replace(';',',');
        ausgabe=eingabe.replace('\t',';');
        ausgabe=eingabe.replace('\u0009',';');
        return ausgabe;
    }
    public void FILE_TO_LIST(File file){
        try(BufferedReader br=new BufferedReader(new FileReader(file))){
            String line=null;
            while ((line=br.readLine())!=null){
                line=this.ERSETZE_SONDERZEICHEN(line);
                String[] entries=line.split(";");
                List<String> lentries = null;
                int i=0;
                for(String entrie:entries) {
                    lentries.add(entrie);
                    i++;
                }
                this.list.add(lentries);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void PRINT_LIST(List<List<String>> list){
        //Verschachteltes foreach
    }
    public static void main(String[] args){
        CSV_LIST test=new CSV_LIST();
        final JFileChooser fc=new JFileChooser();
        fc.showOpenDialog(null);
        test.FILE_TO_LIST(fc.getSelectedFile());
    }
}
