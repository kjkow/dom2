package github.kjkow.implementacja.jedzenie;

import github.kjkow.Przepis;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public interface JedzenieDAO {

    Przepis pobierzDanePrzepisu(String nazwaPrzepisu) throws SQLException, ClassNotFoundException;

    /**
     *
     * @param nazwaPrzepisu
     * @return zwraca liczbę zmienionych wierszy, zwraca -1 jesli nie udalo sie ustalic polaczenia z baza
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    int usunPrzepis(String nazwaPrzepisu) throws SQLException, ClassNotFoundException;

    List<String> pobierzListePrzepisow() throws SQLException, ClassNotFoundException;

    /**
     *
     * @param przepis
     * @return zwraca liczbę zmienionych wierszy, zwraca -1 jesli nie udalo sie ustalic polaczenia z baza
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    int dodajPrzepis(Przepis przepis) throws SQLException, ClassNotFoundException;
}
