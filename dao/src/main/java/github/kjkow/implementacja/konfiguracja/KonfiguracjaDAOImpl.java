package github.kjkow.implementacja.konfiguracja;

import github.kjkow.implementacja.BazowyDAO;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utworzył Kamil Kowalczyk dnia 2017-02-11.
 */
public class KonfiguracjaDAOImpl extends BazowyDAO implements KonfiguracjaDAO {

    public KonfiguracjaDAOImpl() throws IOException {
    }

    @Override
    public String pobierzSciezkeDziennikaAplikacji() throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        String sciezka = "";
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT sciezka FROM konfiguracja_dom WHERE nazwa_sciezki=?");
            kwerenda.setString(1, "dziennik_aplikacji");
            wynikKwerendy = kwerenda.executeQuery();
            if(wynikKwerendy.next()){
                sciezka = wynikKwerendy.getString("sciezka");
            }
            zamknijPolczenie();
            return sciezka;
        }
        return sciezka;
    }
}
