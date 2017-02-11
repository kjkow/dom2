package github.kjkow.implementacja.konfiguracja;

import java.sql.SQLException;

/**
 * Utworzył Kamil Kowalczyk dnia 2017-02-11.
 */
public interface KonfiguracjaDAO {
    String pobierzSciezkeDziennikaAplikacji() throws SQLException, ClassNotFoundException;
}
