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
    public void wykonajCzynnosc(String nazwaCzynnosci, Date dataWykonania) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();

        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareCall("CALL WYKONAJ_CZYNNOSC(?,?)");
            kwerenda.setString(1, nazwaCzynnosci);
            kwerenda.setDate(2, dataWykonania);
            kwerenda.executeQuery();
            zamknijPolczenie();
        }
    }

    @Override
    public void odlozCzynnosc(String nazwaCzynnosci) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();

        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareCall("CALL ODLOZ_CZYNNOSC(?)");
            kwerenda.setString(1, nazwaCzynnosci);
            kwerenda.executeQuery();
            zamknijPolczenie();
        }
    }

    @Override
    public List<Czynnosc> pobierzNajblizszeSprzatania() throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        ArrayList<Czynnosc> czynnosci = new ArrayList<>();
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT * FROM SPRZATANIE_CZYNNOSC ORDER BY DATA_NASTEPNEGO_SPRZATANIA ASC limit 10");
            wynikKwerendy = kwerenda.executeQuery();

            while (wynikKwerendy.next()){
                Czynnosc nowa = new Czynnosc();
                nowa.setNazwaCzynnosci(wynikKwerendy.getString("NAZWA"));
                nowa.setDataNastepnegoSprzatania(wynikKwerendy.getDate("DATA_NASTEPNEGO_SPRZATANIA").toLocalDate());
                nowa.setDataOstatniegoSprzatania(wynikKwerendy.getDate("DATA_OSTATNIEGO_SPRZATANIA").toLocalDate());
                nowa.setDniCzestotliwosci(wynikKwerendy.getInt("CZESTOTLIWOSC"));
                czynnosci.add(nowa);
            }
        }
        return czynnosci;
    }

    @Override
    public int dodajCzynnosc(Czynnosc czynnosc) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("INSERT INTO SPRZATANIE_CZYNNOSC(NAZWA, DATA_OSTATNIEGO_SPRZATANIA, DATA_NASTEPNEGO_SPRZATANIA, CZESTOTLIWOSC) VALUES (?, CURDATE(), CURDATE(), ?)");
            kwerenda.setString(1, czynnosc.getNazwaCzynnosci());
            kwerenda.setInt(2, czynnosc.getDniCzestotliwosci());
            int liczbaZmienionychWierszy = kwerenda.executeUpdate();
            zamknijPolczenie();
            return liczbaZmienionychWierszy;
        }
        return -1;
    }

    @Override
    public void usunCzynnosc(String nazwaCzynnosci) {
        throw new NotImplementedException();
    }

    @Override
    public int modyfikujCzynnosc(Czynnosc czynnosc) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        if(polaczenie !=null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("UPDATE SPRZATANIE_CZYNNOSC SET NAZWA=?, DATA_OSTATNIEGO_SPRZATANIA=?, DATA_NASTEPNEGO_SPRZATANIA=?, CZESTOTLIWOSC=? WHERE NAZWA=?");
            kwerenda.setString(1, czynnosc.getNazwaCzynnosci());
            kwerenda.setDate(2, Date.valueOf(czynnosc.getDataOstatniegoSprzatania()));
            kwerenda.setDate(3, Date.valueOf(czynnosc.getDataNastepnegoSprzatania()));
            kwerenda.setInt(4, czynnosc.getDniCzestotliwosci());
            kwerenda.setString(5, czynnosc.getNazwaCzynnosci());
            int liczbaZmienionychWierszy = kwerenda.executeUpdate();
            zamknijPolczenie();
            return liczbaZmienionychWierszy;
        }
        return -1;
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

    @Override
    public Czynnosc pobierzDaneCzynnosci(String nazwaCzynnosci) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        Czynnosc czynnosc = new Czynnosc();
        czynnosc.setNazwaCzynnosci(nazwaCzynnosci);

        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT * FROM SPRZATANIE_CZYNNOSC WHERE NAZWA = ?");
            kwerenda.setString(1, nazwaCzynnosci);
            wynikKwerendy = kwerenda.executeQuery();
            if(wynikKwerendy.next()){
                czynnosc.setDataOstatniegoSprzatania(wynikKwerendy.getDate("DATA_OSTATNIEGO_SPRZATANIA").toLocalDate());
                czynnosc.setDataNastepnegoSprzatania(wynikKwerendy.getDate("DATA_NASTEPNEGO_SPRZATANIA").toLocalDate());
                czynnosc.setDniCzestotliwosci(wynikKwerendy.getInt("CZESTOTLIWOSC"));
            }
        }

        return czynnosc;
    }


}
