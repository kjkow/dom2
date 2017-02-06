package github.kjkow.implementacja.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.implementacja.BazowyDAO;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
            if(!wynikKwerendy.next()) return przepis;
            przepis.setNazwa(wynikKwerendy.getString("NAZWA"));
            przepis.setDataOstatniegoPrzygotowania(wynikKwerendy.getDate("DATA_OSTATNIEGO_PRZYGOTOWANIA").toLocalDate());
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
    public List<String> pobierzListePrzepisow() throws SQLException, ClassNotFoundException {
        List<String> przepisy = new ArrayList<>();
        otworzPolaczenie();
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT NAZWA FROM JEDZENIE_PRZEPISY");
            wynikKwerendy = kwerenda.executeQuery();
            while (wynikKwerendy.next()){
                przepisy.add(wynikKwerendy.getString("NAZWA"));
            }
            zamknijPolczenie();
        }
        return przepisy;
    }

    @Override
    public List<String> pobierzListePrzepisowDoProcesu() throws SQLException, ClassNotFoundException {
        List<String> przepisy = new ArrayList<>();
        otworzPolaczenie();
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT NAZWA FROM JEDZENIE_PRZEPISY ORDER BY DATA_OSTATNIEGO_PRZYGOTOWANIA ASC");
            wynikKwerendy = kwerenda.executeQuery();
            while (wynikKwerendy.next()){
                przepisy.add(wynikKwerendy.getString("NAZWA"));
            }
            zamknijPolczenie();
        }
        return przepisy;
    }

    @Override
    public int dodajPrzepis(Przepis przepis) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        if(polaczenie != null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("INSERT INTO JEDZENIE_PRZEPISY(NAZWA, DATA_OSTATNIEGO_PRZYGOTOWANIA, SKLADNIKI, SPOSOB_PRZYGOTOWANIA) VALUES(?, ?, ?, ?)");

            if (przepis.getNazwa() != null) {
                kwerenda.setString(1, przepis.getNazwa());
            }else{
                kwerenda.setString(1, null);
            }

            if(przepis.getDataOstatniegoPrzygotowania() != null){
                kwerenda.setDate(2, Date.valueOf(przepis.getDataOstatniegoPrzygotowania()));
            }else {
                kwerenda.setDate(2, Date.valueOf(LocalDate.now()));
            }

            if(przepis.getSkladniki() != null){
                kwerenda.setString(3, przepis.getSkladniki());
            }else{
                kwerenda.setString(3, "nie wpisano żadnych składników");
            }

            if(przepis.getSposobPrzygotowania() != null){
                kwerenda.setString(4, przepis.getSposobPrzygotowania());
            }else{
                kwerenda.setString(4, "nie wpisano sposobu przygotowania");
            }

            int liczbaZmienionychWierszy = kwerenda.executeUpdate();

            zamknijPolczenie();
            return liczbaZmienionychWierszy;
        }
        return -1;
    }

    @Override
    public int modyfikujPrzepis(Przepis przepis, String nazwaStaregoPrzepisu) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        if(polaczenie !=null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("UPDATE JEDZENIE_PRZEPISY SET NAZWA=?, DATA_OSTATNIEGO_PRZYGOTOWANIA=?, SKLADNIKI=?, SPOSOB_PRZYGOTOWANIA=? WHERE NAZWA=?");
            kwerenda.setString(1, przepis.getNazwa());
            kwerenda.setDate(2, Date.valueOf(przepis.getDataOstatniegoPrzygotowania()));
            kwerenda.setString(3, przepis.getSkladniki());
            kwerenda.setString(4, przepis.getSposobPrzygotowania());
            kwerenda.setString(5, nazwaStaregoPrzepisu);
            int liczbaZmienionychWierszy = kwerenda.executeUpdate();
            zamknijPolczenie();
            return liczbaZmienionychWierszy;
        }
        return -1;
    }

    @Override
    public void zapiszSciezkeDoExcelaZProduktami(String sciezka) throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        if(polaczenie !=null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("UPDATE KONFIGURACJA_DOM SET SCIEZKA=? WHERE NAZWA_SCIEZKI=?");
            kwerenda.setString(1, sciezka);
            kwerenda.setString(2, "excel_produkty");
            kwerenda.executeUpdate();
            zamknijPolczenie();
        }
    }

    @Override
    public String pobierzSciezkeDoExcelaZProduktami() throws SQLException, ClassNotFoundException {
        otworzPolaczenie();
        if(polaczenie !=null){
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT SCIEZKA FROM KONFIGURACJA_DOM WHERE NAZWA_SCIEZKI=?");
            kwerenda.setString(1, "excel_produkty");
            wynikKwerendy = kwerenda.executeQuery();
            if(wynikKwerendy.next()){
                return wynikKwerendy.getString("SCIEZKA");
            }
        }
        return "Nie znaleziono ściezki";
    }
}
