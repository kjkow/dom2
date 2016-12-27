package github.kjkow.implementacja.sprzatanie;

import github.kjkow.sprzatanie.Czynnosc;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public interface SprzatanieDAO {
    void wykonajCzynnosc(String nazwaCzynnosci, Date dataWykonania) throws SQLException, ClassNotFoundException;
    void odlozCzynnosc(String nazwaCzynnosci) throws SQLException, ClassNotFoundException;
    List<Czynnosc> pobierzNajblizszeSprzatania() throws SQLException, ClassNotFoundException;
    void dodajCzynnosc(Czynnosc czynnosc) throws SQLException, ClassNotFoundException;
    void usunCzynnosc(String nazwaCzynnosci);
    void zmienNazweCzynnosci(String staraNazwa, String nowaNazwa);
    void zmienCzestotliwoscCzynnosci(String nazwaCzynnosci, int nowaCzestotliwosc);
    List<String> pobierzNazwyCzynnosci() throws SQLException, ClassNotFoundException;
    Czynnosc pobierzDaneCzynnosci(String nazwaCzynnosci) throws SQLException, ClassNotFoundException;
}
