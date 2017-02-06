package github.kjkow.implementacja.sprzatanie;

import github.kjkow.Czynnosc;

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

    /**
     * @param czynnosc
     * @return zwraca liczbę zmienionych wierszy, zwraca -1 jesli nie udalo sie ustalic polaczenia z baza
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    int dodajCzynnosc(Czynnosc czynnosc) throws SQLException, ClassNotFoundException;

    /**
     *
     * @param nazwaCzynnosci
     * @return zwraca liczbę zmienionych wierszy, zwraca -1 jesli nie udalo sie ustalic polaczenia z baza
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    int usunCzynnosc(String nazwaCzynnosci) throws SQLException, ClassNotFoundException;

    /**
     * @param czynnosc
     * @return zwraca liczbę zmienionych wierszy, zwraca -1 jesli nie udalo sie ustalic polaczenia z baza
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    int modyfikujCzynnosc(Czynnosc czynnosc, String nazwaStarejCzynnosci) throws SQLException, ClassNotFoundException;

    List<String> pobierzNazwyCzynnosci() throws SQLException, ClassNotFoundException;

    Czynnosc pobierzDaneCzynnosci(String nazwaCzynnosci) throws SQLException, ClassNotFoundException;
}
