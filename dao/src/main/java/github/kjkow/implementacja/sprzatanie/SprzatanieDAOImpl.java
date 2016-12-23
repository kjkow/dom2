package github.kjkow.implementacja.sprzatanie;

import github.kjkow.implementacja.BazowyDAO;
import github.kjkow.sprzatanie.Czynnosc;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class SprzatanieDAOImpl extends BazowyDAO implements SprzatanieDAO {

    public SprzatanieDAOImpl() throws IOException {}
    //TODO:implementacja

    @Override
    public void wykonajCzynnosc(String nazwaCzynnosci, Date dataWykonania) {
        throw new NotImplementedException();
    }

    @Override
    public void odlozCzynnosc(String nazwaCzynnosci) {
        throw new NotImplementedException();
    }

    @Override
    public List<Czynnosc> pobierzNajblizszeSprzatania() {
        throw new NotImplementedException();
    }

    @Override
    public void dodajCzynnosc(Czynnosc czynnosc) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("INSERT INTO SPRZATANIE_CZYNNOSC(NAZWA, DATA_OSTATNIEGO_SPRZATANIA, DATA_NASTEPNEGO_SPRZATANIA, CZESTOTLIWOSC) VALUES (?, CURDATE(), CURDATE(), ?)");
            kwerenda.setString(1, czynnosc.getNazwaCzynnosci());
            kwerenda.setInt(2, czynnosc.getDniCzestotliwosci());
            kwerenda.executeUpdate();
            zamknijPolczenie();
        }
    }

    @Override
    public void usunCzynnosc(String nazwaCzynnosci) {
        throw new NotImplementedException();
    }

    @Override
    public void zmienNazweCzynnosci(String staraNazwa, String nowaNazwa) {
        throw new NotImplementedException();
    }

    @Override
    public void zmienCzestotliwoscCzynnosci(String nazwaCzynnosci, int nowaCzestotliwosc) {
        throw new NotImplementedException();
    }

    @Override
    public List<String> pobierzNazwyCzynnosci() throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        ArrayList<String> czynnosci = new ArrayList<>();

        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT NAZWA FROM SPRZATANIE_CZYNNOSC");
            wynikKwerendy = kwerenda.executeQuery();

            while (wynikKwerendy.next()){
                czynnosci.add(wynikKwerendy.getString("NAZWA"));
            }
            zamknijPolczenie();
        }

        return czynnosci;
    }


}
