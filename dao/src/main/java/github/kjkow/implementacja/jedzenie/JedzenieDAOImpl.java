package github.kjkow.implementacja.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.implementacja.BazowyDAO;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public class JedzenieDAOImpl extends BazowyDAO implements JedzenieDAO {

    public JedzenieDAOImpl() throws IOException {
    }

    @Override
    public Przepis pobierzDanePrzepisu(String nazwaPrzepisu) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        Przepis przepis = new Przepis();
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT * FROM JEDZENIE_PRZEPISY WHERE NAZWA=?");
            kwerenda.setString(1, nazwaPrzepisu);

            wynikKwerendy = kwerenda.executeQuery();

            przepis.setNazwa(wynikKwerendy.getString("NAZWA"));
            przepis.setDataOstatniegoPrzygotowania(wynikKwerendy.getDate("DATA_OSTATNIEGO_SPRZATANIA").toLocalDate());
            przepis.setSkladniki(wynikKwerendy.getString("SKLADNIKI"));
            przepis.setSposobPrzygotowania(wynikKwerendy.getString("SPOSOB_PRZYGOTOWANIA"));
        }
        return przepis;
    }

    @Override
    public int usunPrzepis(String nazwaPrzepisu) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("DELETE FROM JEDZENIE_PRZEPISY WHERE NAZWA = ?");
            kwerenda.setString(1, nazwaPrzepisu);
            int liczbaZmienionychWierszy = kwerenda.executeUpdate();
            zamknijPolczenie();
            return liczbaZmienionychWierszy;
        }
        return -1;
    }

    @Override
    public List<Przepis> pobierzListePrzepisow() {
        return null;
    }
}
