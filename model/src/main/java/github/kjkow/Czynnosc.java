package github.kjkow;

import java.time.LocalDate;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class Czynnosc {
    private String nazwaCzynnosci;
    private LocalDate dataOstatniegoSprzatania;
    private LocalDate dataNastepnegoSprzatania;
    private int dniCzestotliwosci;

    public String getNazwaCzynnosci() {
        return nazwaCzynnosci;
    }

    public void setNazwaCzynnosci(String nazwaCzynnosci) {
        this.nazwaCzynnosci = nazwaCzynnosci;
    }

    public LocalDate getDataOstatniegoSprzatania() {
        return dataOstatniegoSprzatania;
    }

    public void setDataOstatniegoSprzatania(LocalDate dataOstatniegoSprzatania) {
        this.dataOstatniegoSprzatania = dataOstatniegoSprzatania;
    }

    public LocalDate getDataNastepnegoSprzatania() {
        return dataNastepnegoSprzatania;
    }

    public void setDataNastepnegoSprzatania(LocalDate dataNastepnegoSprzatania) {
        this.dataNastepnegoSprzatania = dataNastepnegoSprzatania;
    }

    public int getDniCzestotliwosci() {
        return dniCzestotliwosci;
    }

    public void setDniCzestotliwosci(int dniCzestotliwosci) {
        this.dniCzestotliwosci = dniCzestotliwosci;
    }

}
