package github.kjkow.implementacja.konfiguracja;

import github.kjkow.implementacja.BazowyDAO;

import java.sql.PreparedStatement;

/**
 * Utworzył Kamil Kowalczyk dnia 2017-02-11.
 */
public class KonfiguracjaDAOImpl extends BazowyDAO implements KonfiguracjaDAO {

    private KontekstZwracanyKonfiguracjaDAO kontekst;
    private String sciezka = "";

    @Override
    public KontekstZwracanyKonfiguracjaDAO pobierzSciezkeDziennikaAplikacji(){
        kontekst = new KontekstZwracanyKonfiguracjaDAO();

        try{
            otworzPolaczenie();
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT sciezka FROM konfiguracja_dom WHERE nazwa_sciezki=?");
            kwerenda.setString(1, "dziennik_aplikacji");
            wynikKwerendy = kwerenda.executeQuery();
            if(wynikKwerendy.next()){
                sciezka = wynikKwerendy.getString("sciezka");
            }
            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        kontekst.setSciezkaKonfiguracji(sciezka);
        return kontekst;
    }

    @Override
    public KontekstZwracanyKonfiguracjaDAO pobierzSciezkeDoExcelaZProduktami(){
        kontekst = new KontekstZwracanyKonfiguracjaDAO();

        try{
            otworzPolaczenie();
            PreparedStatement kwerenda = polaczenie.prepareStatement("SELECT SCIEZKA FROM KONFIGURACJA_DOM WHERE NAZWA_SCIEZKI=?");
            kwerenda.setString(1, "excel_produkty");
            wynikKwerendy = kwerenda.executeQuery();
            if(wynikKwerendy.next()){
                sciezka = wynikKwerendy.getString("SCIEZKA");
            }
            zamknijPolczenie();
        }catch (Exception e){
            przepakujBladDoKontekstu(KOMUNIKAT_BLEDU_TRANSAKCJI, e, kontekst);
        }

        kontekst.setSciezkaKonfiguracji(sciezka);
        return kontekst;
    }
}
