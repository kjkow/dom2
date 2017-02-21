package github.kjkow.implementacja.uroczystosc;

import github.kjkow.KontekstZwracany;
import github.kjkow.Uroczystosc;

import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2017-02-20.
 */
public class KontekstZwracanyUroczystoscDAO extends KontekstZwracany {
    ArrayList<Uroczystosc> listaUroczystosci;

    public ArrayList<Uroczystosc> getListaUroczystosci() {
        return listaUroczystosci;
    }

    public void setListaUroczystosci(ArrayList<Uroczystosc> listaUroczystosci) {
        this.listaUroczystosci = listaUroczystosci;
    }
}
