package github.kjkow;

/**
 * Created by Kamil.Kowalczyk on 2016-12-20.
 */
public class KontekstZwracany {

    private boolean czyBrakBledow;
    private String log;
    private Exception blad;

    public KontekstZwracany(){
        czyBrakBledow = true;
        log = "";
    }

    public boolean isCzyBrakBledow() {
        return czyBrakBledow;
    }

    public void setCzyBrakBledow(boolean czyBrakBledow) {
        this.czyBrakBledow = czyBrakBledow;
    }

    public String getLog() {
        return log;
    }

    public void dodajDoLogu(String dodatek){
        if(dodatek.compareTo("") == 0){
            log += dodatek;
            return;
        }
        log += dodatek + "\n";
    }

    public Exception getBlad() {
        return blad;
    }

    public void setBlad(Exception blad) {
        this.blad = blad;
    }
}
