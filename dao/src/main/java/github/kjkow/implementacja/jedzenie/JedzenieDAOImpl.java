package github.kjkow.implementacja.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.implementacja.BazowyDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public class JedzenieDAOImpl extends BazowyDAO implements JedzenieDAO {

    private KontekstZwracanyJedzenieDAO kontekst;

    @Override
    public KontekstZwracanyJedzenieDAO pobierzDanePrzepisu(String nazwaPrzepisu) {
        kontekst = new KontekstZwracanyJedzenieDAO();

        Przepis przepis = new Przepis();
        try{
            otworzPolaczenie();
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT * FROM JEDZENIE_PRZEPISY WHERE NAZWA=?");
            kwerenda.setString(1, nazwaPrzepisu);

            wynikKwerendy = kwerenda.executeQuery();
            if(!wynikKwerendy.next()) throw new NullPointerException("Nie znaleziono przepisu");
            przepis.setNazwa(wynikKwerendy.getString("NAZWA"));
            przepis.setDataOstatniegoPrzygotowania(wynikKwerendy.getDate("DATA_OSTATNIEGO_PRZYGOTOWANIA").toLocalDate());
            przepis.setSkladniki(wynikKwerendy.getString("SKLADNIKI"));
            przepis.setSposobPrzygotowania(wynikKwerendy.getString("SPOSOB_PRZYGOTOWANIA"));
            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        kontekst.setPrzepis(przepis);
        return kontekst;
    }

    @Override
    public KontekstZwracanyJedzenieDAO usunPrzepis(String nazwaPrzepisu)  {
        kontekst = new KontekstZwracanyJedzenieDAO();

        try {
            otworzPolaczenie();
            PreparedStatement kwerenda = polaczenie.prepareStatement("DELETE FROM JEDZENIE_PRZEPISY WHERE NAZWA = ?");
            kwerenda.setString(1, nazwaPrzepisu);
            kwerenda.executeUpdate();
            zamknijPolczenie();
        } catch (Exception e) {
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        return kontekst;
    }

    @Override
    public KontekstZwracanyJedzenieDAO pobierzListePrzepisow(){
        kontekst = new KontekstZwracanyJedzenieDAO();

        List<String> przepisy = new ArrayList<>();
        try {
            otworzPolaczenie();
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT NAZWA FROM JEDZENIE_PRZEPISY");
            wynikKwerendy = kwerenda.executeQuery();
            while (wynikKwerendy.next()){
                przepisy.add(wynikKwerendy.getString("NAZWA"));
            }
            zamknijPolczenie();
        } catch (Exception e) {
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        kontekst.setLista(przepisy);
        return kontekst;
    }

    @Override
    public KontekstZwracanyJedzenieDAO pobierzListePrzepisowDoProcesu(){
        kontekst = new KontekstZwracanyJedzenieDAO();

        List<String> przepisy = new ArrayList<>();
        try {
            otworzPolaczenie();
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT NAZWA FROM JEDZENIE_PRZEPISY ORDER BY DATA_OSTATNIEGO_PRZYGOTOWANIA ASC");
            wynikKwerendy = kwerenda.executeQuery();
            while (wynikKwerendy.next()){
                przepisy.add(wynikKwerendy.getString("NAZWA"));
            }
            zamknijPolczenie();
        } catch (Exception e) {
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        kontekst.setLista(przepisy);
        return kontekst;
    }

    @Override
    public KontekstZwracanyJedzenieDAO dodajPrzepis(Przepis przepis){
        kontekst = new KontekstZwracanyJedzenieDAO();

        try{
            otworzPolaczenie();
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
            kwerenda.executeUpdate();
            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }
        return kontekst;
    }

    @Override
    public KontekstZwracanyJedzenieDAO modyfikujPrzepis(Przepis przepis, String nazwaStaregoPrzepisu) {
        kontekst = new KontekstZwracanyJedzenieDAO();

        try{
            otworzPolaczenie();
            PreparedStatement kwerenda = polaczenie.prepareStatement("UPDATE JEDZENIE_PRZEPISY SET NAZWA=?, DATA_OSTATNIEGO_PRZYGOTOWANIA=?, SKLADNIKI=?, SPOSOB_PRZYGOTOWANIA=? WHERE NAZWA=?");
            kwerenda.setString(1, przepis.getNazwa());
            kwerenda.setDate(2, Date.valueOf(przepis.getDataOstatniegoPrzygotowania()));
            kwerenda.setString(3, przepis.getSkladniki());
            kwerenda.setString(4, przepis.getSposobPrzygotowania());
            kwerenda.setString(5, nazwaStaregoPrzepisu);

            kwerenda.executeUpdate();
            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        return kontekst;
    }
}
