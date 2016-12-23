package github.kjkow;

import java.time.LocalDate;

/**
 * Created by Kamil.Kowalczyk on 2016-12-23.
 */
public class Uroczystosc {

    private String osoba;
    private LocalDate dataUroczystosci;
    private String rodzajUroczystosci;

    public String getOsoba() {
        return osoba;
    }

    public void setOsoba(String osoba) {
        this.osoba = osoba;
    }

    public LocalDate getDataUroczystosci() {
        return dataUroczystosci;
    }

    public void setDataUroczystosci(LocalDate dataUroczystosci) {
        this.dataUroczystosci = dataUroczystosci;
    }

    public String getRodzajUroczystosci() {
        return rodzajUroczystosci;
    }

    public void setRodzajUroczystosci(String rodzajUroczystosci) {
        this.rodzajUroczystosci = rodzajUroczystosci;
    }
}
