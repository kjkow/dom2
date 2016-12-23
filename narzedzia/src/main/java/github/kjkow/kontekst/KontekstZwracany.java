package github.kjkow.kontekst;

/**
 * Created by Kamil.Kowalczyk on 2016-12-20.
 */
public class KontekstZwracany {

    private boolean czyBrakBledow;
    private String log;

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
        log += "\n" + dodatek + "\n";
    }
}
