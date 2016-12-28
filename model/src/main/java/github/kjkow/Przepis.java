package github.kjkow;

import java.time.LocalDate;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public class Przepis {

    private String nazwa;
    private LocalDate dataOstatniegoPrzygotowania;
    private String skladniki;
    private String sposobPrzygotowania;

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public LocalDate getDataOstatniegoPrzygotowania() {
        return dataOstatniegoPrzygotowania;
    }

    public void setDataOstatniegoPrzygotowania(LocalDate dataOstatniegoPrzygotowania) {
        this.dataOstatniegoPrzygotowania = dataOstatniegoPrzygotowania;
    }

    public String getSkladniki() {
        return skladniki;
    }

    public void setSkladniki(String skladniki) {
        this.skladniki = skladniki;
    }

    public String getSposobPrzygotowania() {
        return sposobPrzygotowania;
    }

    public void setSposobPrzygotowania(String sposobPrzygotowania) {
        this.sposobPrzygotowania = sposobPrzygotowania;
    }
}
