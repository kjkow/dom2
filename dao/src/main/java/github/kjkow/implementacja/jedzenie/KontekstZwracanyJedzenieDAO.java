package github.kjkow.implementacja.jedzenie;

import github.kjkow.KontekstZwracany;
import github.kjkow.Przepis;

import java.util.List;

/**
 * Created by Kamil.Kowalczyk on 2017-02-20.
 */
public class KontekstZwracanyJedzenieDAO extends KontekstZwracany {
    Przepis przepis;
    List<String> lista;

    public Przepis getPrzepis() {
        return przepis;
    }

    public void setPrzepis(Przepis przepis) {
        this.przepis = przepis;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }
}
