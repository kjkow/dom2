package github.kjkow.implementacja.uroczystosc;

import github.kjkow.Uroczystosc;
import github.kjkow.implementacja.BazowyDAO;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2016-12-23.
 */
public class UroczystoscDAOImpl extends BazowyDAO implements UroczystoscDAO {

    private KontekstZwracanyUroczystoscDAO kontekst;

    @Override
    public KontekstZwracanyUroczystoscDAO pobierzNajblizszeUroczystosci(){
        kontekst = new KontekstZwracanyUroczystoscDAO();
        ArrayList<Uroczystosc> uroczystosci = new ArrayList<>();

        try {
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT * FROM UROCZYSTOSCI WHERE DATA_UROCZYSTOSCI < DATE_ADD(CURDATE(), INTERVAL 2 MONTH) AND DATA_UROCZYSTOSCI > CURDATE() ORDER BY DATA_UROCZYSTOSCI ASC");

            wynikKwerendy = kwerenda.executeQuery();
            while (wynikKwerendy.next()) {
                Uroczystosc uroczystosc = new Uroczystosc();

                uroczystosc.setOsoba(wynikKwerendy.getString("OSOBA"));
                uroczystosc.setDataUroczystosci(wynikKwerendy.getDate("DATA_UROCZYSTOSCI").toLocalDate());
                uroczystosc.setRodzajUroczystosci(wynikKwerendy.getString("RODZAJ"));

                uroczystosci.add(uroczystosc);
            }

            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        kontekst.setListaUroczystosci(uroczystosci);
        return kontekst;
    }
}
