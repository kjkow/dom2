package github.kjkow.implementacja.sprzatanie;

import github.kjkow.Czynnosc;

import java.sql.Date;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public interface SprzatanieDAO {

    KontekstZwracanySprzatanieDAO wykonajCzynnosc(String nazwaCzynnosci, Date dataWykonania);
    KontekstZwracanySprzatanieDAO odlozCzynnosc(String nazwaCzynnosci);
    KontekstZwracanySprzatanieDAO pobierzNajblizszeSprzatania();
    KontekstZwracanySprzatanieDAO dodajCzynnosc(Czynnosc czynnosc);
    KontekstZwracanySprzatanieDAO usunCzynnosc(String nazwaCzynnosci);
    KontekstZwracanySprzatanieDAO pobierzNazwyCzynnosci();
    KontekstZwracanySprzatanieDAO pobierzDaneCzynnosci(String nazwaCzynnosci);
    KontekstZwracanySprzatanieDAO modyfikujCzynnosc(Czynnosc czynnosc, String nazwaStarejCzynnosci);
}
