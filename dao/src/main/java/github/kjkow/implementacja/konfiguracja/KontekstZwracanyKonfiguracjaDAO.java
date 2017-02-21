package github.kjkow.implementacja.konfiguracja;

import github.kjkow.KontekstZwracany;

/**
 * Created by Kamil.Kowalczyk on 2017-02-20.
 */
public class KontekstZwracanyKonfiguracjaDAO extends KontekstZwracany {
    String sciezkaKonfiguracji;

    public String getSciezkaKonfiguracji() {
        return sciezkaKonfiguracji;
    }

    public void setSciezkaKonfiguracji(String sciezkaKonfiguracji) {
        this.sciezkaKonfiguracji = sciezkaKonfiguracji;
    }
}
