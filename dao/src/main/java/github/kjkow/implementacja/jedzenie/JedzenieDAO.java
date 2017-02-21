package github.kjkow.implementacja.jedzenie;

import github.kjkow.Przepis;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public interface JedzenieDAO {

    KontekstZwracanyJedzenieDAO pobierzDanePrzepisu(String nazwaPrzepisu);
    KontekstZwracanyJedzenieDAO usunPrzepis(String nazwaPrzepisu);
    KontekstZwracanyJedzenieDAO pobierzListePrzepisow();
    KontekstZwracanyJedzenieDAO pobierzListePrzepisowDoProcesu();
    KontekstZwracanyJedzenieDAO dodajPrzepis(Przepis przepis);
    KontekstZwracanyJedzenieDAO modyfikujPrzepis(Przepis przepis, String nazwaStaregoPrzepisu);
}
