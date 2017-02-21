package github.kjkow.implementacja.sprzatanie;

import github.kjkow.Czynnosc;
import github.kjkow.KontekstZwracany;

import java.util.List;

/**
 * Created by Kamil.Kowalczyk on 2017-02-20.
 */
public class KontekstZwracanySprzatanieDAO extends KontekstZwracany {
    private List<Czynnosc> listaCzynnosci;
    private List<String> nazwyCzynnosci;
    Czynnosc czynnosc;

    public List<Czynnosc> getListaCzynnosci() {
        return listaCzynnosci;
    }

    public void setListaCzynnosci(List<Czynnosc> listaCzynnosci) {
        this.listaCzynnosci = listaCzynnosci;
    }

    public List<String> getNazwyCzynnosci() {
        return nazwyCzynnosci;
    }

    public void setNazwyCzynnosci(List<String> nazwyCzynnosci) {
        this.nazwyCzynnosci = nazwyCzynnosci;
    }

    public Czynnosc getCzynnosc() {
        return czynnosc;
    }

    public void setCzynnosc(Czynnosc czynnosc) {
        this.czynnosc = czynnosc;
    }
}
