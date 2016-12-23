package github.kjkow.implementacja.sprzatanie;

import github.kjkow.sprzatanie.Czynnosc;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public interface SprzatanieDAO {
    void wykonajCzynnosc(String nazwaCzynnosci, Date dataWykonania);
    void odlozCzynnosc(String nazwaCzynnosci);
    List<Czynnosc> pobierzNajblizszeSprzatania();

    //TODO: może zrobić osobną formatke - edycja czynnosci i tam wszystkie opcje, bo tu nie ma np. zmien przypisanie do kategorii
    void dodajCzynnosc(Czynnosc czynnosc) throws SQLException, ClassNotFoundException;
    void usunCzynnosc(String nazwaCzynnosci);
    void zmienNazweCzynnosci(String staraNazwa, String nowaNazwa);
    void zmienCzestotliwoscCzynnosci(String nazwaCzynnosci, int nowaCzestotliwosc);
    List<String> pobierzNazwyCzynnosci() throws SQLException, ClassNotFoundException;
}
