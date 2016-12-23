package github.kjkow.implementacja.uroczystosc;

import github.kjkow.Uroczystosc;
import github.kjkow.implementacja.BazowyDAO;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2016-12-23.
 */
public class UroczystoscDAOImpl extends BazowyDAO implements UroczystoscDAO {

    public UroczystoscDAOImpl() throws IOException {
    }

    @Override
    public ArrayList<Uroczystosc> pobierzNajblizszeUroczystosci() throws SQLException, ClassNotFoundException {
        ArrayList<Uroczystosc> uroczystosci = new ArrayList<>();

        otworzPolaczenie();

        PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT * FROM UROCZYSTOSCI ORDER BY DATA_UROCZYSTOSCI ASC");
        //TODO: w tym selectcie dodac jakies ograniczenie zeby tylko np dwa miesiace wprzod

        wynikKwerendy = kwerenda.executeQuery();
        while (wynikKwerendy.next()){
            Uroczystosc uroczystosc = new Uroczystosc();

            uroczystosc.setOsoba(wynikKwerendy.getString("OSOBA"));
            uroczystosc.setDataUroczystosci(wynikKwerendy.getDate("DATA_UROCZYSTOSCI").toLocalDate());
            uroczystosc.setRodzajUroczystosci(wynikKwerendy.getString("RODZAJ"));

            uroczystosci.add(uroczystosc);
        }

        zamknijPolczenie();
        return uroczystosci;
    }
}
