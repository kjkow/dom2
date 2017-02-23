package github.kjkow.implementacja.sprzatanie;

import github.kjkow.Czynnosc;
import github.kjkow.implementacja.BazowyDAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class SprzatanieDAOImpl extends BazowyDAO implements SprzatanieDAO {

    private KontekstZwracanySprzatanieDAO kontekst;

    @Override
    public KontekstZwracanySprzatanieDAO wykonajCzynnosc(String nazwaCzynnosci, Date dataWykonania){
        kontekst = new KontekstZwracanySprzatanieDAO();

        try{
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareCall("CALL WYKONAJ_CZYNNOSC(?,?)");
            kwerenda.setString(1, nazwaCzynnosci);
            kwerenda.setDate(2, dataWykonania);

            kwerenda.executeQuery();
            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        return kontekst;
    }

    @Override
    public KontekstZwracanySprzatanieDAO odlozCzynnosc(String nazwaCzynnosci) {
        kontekst = new KontekstZwracanySprzatanieDAO();

        try{
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareCall("CALL ODLOZ_CZYNNOSC(?)");
            kwerenda.setString(1, nazwaCzynnosci);

            kwerenda.executeQuery();
            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        return kontekst;
    }

    @Override
    public KontekstZwracanySprzatanieDAO pobierzNajblizszeSprzatania(){
        kontekst = new KontekstZwracanySprzatanieDAO();

        ArrayList<Czynnosc> czynnosci = new ArrayList<>();
        try{
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT * FROM SPRZATANIE_CZYNNOSCI ORDER BY DATA_NASTEPNEGO_SPRZATANIA ASC");
            wynikKwerendy = kwerenda.executeQuery();

            while (wynikKwerendy.next()){
                Czynnosc nowa = new Czynnosc();
                nowa.setNazwaCzynnosci(wynikKwerendy.getString("NAZWA"));
                nowa.setDataNastepnegoSprzatania(wynikKwerendy.getDate("DATA_NASTEPNEGO_SPRZATANIA").toLocalDate());
                nowa.setDataOstatniegoSprzatania(wynikKwerendy.getDate("DATA_OSTATNIEGO_SPRZATANIA").toLocalDate());
                nowa.setDniCzestotliwosci(String.valueOf(wynikKwerendy.getInt("CZESTOTLIWOSC")));
                czynnosci.add(nowa);
            }

            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        kontekst.setListaCzynnosci(czynnosci);
        return kontekst;
    }

    @Override
    public KontekstZwracanySprzatanieDAO dodajCzynnosc(Czynnosc czynnosc){
        kontekst = new KontekstZwracanySprzatanieDAO();

        try{
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareStatement("INSERT INTO SPRZATANIE_CZYNNOSCI(NAZWA, DATA_OSTATNIEGO_SPRZATANIA, DATA_NASTEPNEGO_SPRZATANIA, CZESTOTLIWOSC) VALUES (?, CURDATE(), CURDATE(), ?)");
            kwerenda.setString(1, czynnosc.getNazwaCzynnosci());
            kwerenda.setInt(2, Integer.parseInt(czynnosc.getDniCzestotliwosci()));

            kwerenda.executeUpdate();
            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        return kontekst;
    }

    @Override
    public KontekstZwracanySprzatanieDAO usunCzynnosc(String nazwaCzynnosci){
        kontekst = new KontekstZwracanySprzatanieDAO();

        try{
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareStatement("DELETE FROM SPRZATANIE_CZYNNOSCI WHERE NAZWA=?");
            kwerenda.setString(1, nazwaCzynnosci);

            kwerenda.executeUpdate();
            zamknijPolczenie();
        } catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        return kontekst;
    }

    @Override
    public KontekstZwracanySprzatanieDAO pobierzNazwyCzynnosci(){
        kontekst = new KontekstZwracanySprzatanieDAO();

        ArrayList<String> czynnosci = new ArrayList<>();
        try{
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT NAZWA FROM SPRZATANIE_CZYNNOSCI");
            wynikKwerendy = kwerenda.executeQuery();

            while (wynikKwerendy.next()){
                czynnosci.add(wynikKwerendy.getString("NAZWA"));
            }

            zamknijPolczenie();
        } catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        kontekst.setNazwyCzynnosci(czynnosci);
        return kontekst;
    }

    @Override
    public KontekstZwracanySprzatanieDAO pobierzDaneCzynnosci(String nazwaCzynnosci){
        kontekst = new KontekstZwracanySprzatanieDAO();

        try{
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT * FROM SPRZATANIE_CZYNNOSCI WHERE NAZWA = ?");
            kwerenda.setString(1, nazwaCzynnosci);
            wynikKwerendy = kwerenda.executeQuery();

            if(wynikKwerendy.next()){
                Czynnosc czynnosc = new Czynnosc();
                czynnosc.setNazwaCzynnosci(nazwaCzynnosci);
                czynnosc.setDataOstatniegoSprzatania(wynikKwerendy.getDate("DATA_OSTATNIEGO_SPRZATANIA").toLocalDate());
                czynnosc.setDataNastepnegoSprzatania(wynikKwerendy.getDate("DATA_NASTEPNEGO_SPRZATANIA").toLocalDate());
                czynnosc.setDniCzestotliwosci(String.valueOf(wynikKwerendy.getInt("CZESTOTLIWOSC")));
                kontekst.setCzynnosc(czynnosc);
            }else{
                kontekst.setCzyBrakBledow(false);
                kontekst.setBlad(new NullPointerException());
                kontekst.setCzynnosc(null);
                kontekst.dodajDoLogu("Nie znaleziono czynności: " + nazwaCzynnosci);
            }

            zamknijPolczenie();
        } catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        return kontekst;
    }

    @Override
    public KontekstZwracanySprzatanieDAO modyfikujCzynnosc(Czynnosc czynnosc, String nazwaStarejCzynnosci) {
        kontekst = new KontekstZwracanySprzatanieDAO();

        try{
            otworzPolaczenie();

            PreparedStatement kwerenda = polaczenie.prepareStatement("UPDATE SPRZATANIE_CZYNNOSCI SET NAZWA=?, DATA_OSTATNIEGO_SPRZATANIA=?, DATA_NASTEPNEGO_SPRZATANIA=?, CZESTOTLIWOSC=? WHERE NAZWA=?");
            kwerenda.setString(1, czynnosc.getNazwaCzynnosci());
            kwerenda.setDate(2, Date.valueOf(czynnosc.getDataOstatniegoSprzatania()));
            kwerenda.setDate(3, Date.valueOf(czynnosc.getDataNastepnegoSprzatania()));
            kwerenda.setInt(4, Integer.parseInt(czynnosc.getDniCzestotliwosci()));
            kwerenda.setString(5, nazwaStarejCzynnosci);

            kwerenda.executeUpdate();
            zamknijPolczenie();
        } catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        return kontekst;
    }
}
