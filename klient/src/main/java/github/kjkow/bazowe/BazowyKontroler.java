package github.kjkow.bazowe;


import github.kjkow.automaty.excel.AutomatDoExcela;
import github.kjkow.automaty.excel.IAutomatDoExcela;
import github.kjkow.bazowe.formatka.IZarzadcaFormatek;
import github.kjkow.bazowe.formatka.ZarzadcaFormatek;
import github.kjkow.dziennik.Dziennik;
import github.kjkow.dziennik.IDziennik;
import github.kjkow.implementacja.jedzenie.JedzenieDAO;
import github.kjkow.implementacja.jedzenie.JedzenieDAOImpl;
import github.kjkow.implementacja.sprzatanie.SprzatanieDAO;
import github.kjkow.implementacja.sprzatanie.SprzatanieDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class BazowyKontroler {

    protected IZarzadcaFormatek zarzadcaFormatek;
    protected IAutomatDoExcela automatDoExcela;
    protected IDziennik dziennik;

    protected static final String KOMUNIKAT_BLEDU_KONSTRUKTORA_DAO = "Wystąpił błąd podczas wczytywania konfiguracji bazy danych z pliku";
    protected static final String KOMUNIKAT_BLEDU_SQL = "Wystąpił błąd na bazie danych";
    protected static final String KOMUNIKAT_BLEDU_KONEKTORA_JDBC = "Wystąpił błąd sterownika bazy danych";
    protected static final String KOMUNIKAT_BLEDU_IO = "Wystąpił błąd podczas wczytywania pliku.";
    protected static final String KOMUNIKAT_AMBIWALENCJI_DZIENNIKA = "Nastąpił poprawny zapis na bazie, ale nie udało się zapisać informacji w dzienniku aplikacji.";
    protected static final String KOMUNIKAT_NIEOCZEKIWANY = "Wystąpił nieoczekiwany błąd aplikacji";

    protected SprzatanieDAO sprzatanieDAO;
    protected JedzenieDAO jedzenieDAO;



    public BazowyKontroler(){
        inicjujNarzedzia();
    }

    private void inicjujNarzedzia(){
        if(zarzadcaFormatek == null){
            zarzadcaFormatek = new ZarzadcaFormatek();
        }
        if(automatDoExcela == null){
            automatDoExcela = new AutomatDoExcela();
        }
        if(dziennik == null){
            try {
                dziennik = new Dziennik();
            } catch (SQLException | IOException | ClassNotFoundException e) {
                obsluzBlad("Błąd podczas inicjowania dziennika aplikacji", e);
            }
        }
    }

    protected void obsluzBlad(String trescKomunikatu, Exception e){
        ObslugaBledu.obsluzBlad(trescKomunikatu, e);
    }

    protected void otworzNowaFormatke(String sciezka){
        try {
            zarzadcaFormatek.wyswietlNowaFormatke(sciezka);
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }


    protected void inicjujSprzatanieDAO(){
        if(sprzatanieDAO != null) return;

        try {
            sprzatanieDAO = new SprzatanieDAOImpl();
        } catch (IOException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONSTRUKTORA_DAO, e);
        }
    }

    protected void inicjujJedzenieDAO(){
        if(jedzenieDAO != null) return;

        try{
            jedzenieDAO = new JedzenieDAOImpl();
        } catch (IOException e){
            obsluzBlad(KOMUNIKAT_BLEDU_KONSTRUKTORA_DAO, e);
        }
    }

    protected void zapiszWykonanieWDzienniku(String tresc){
        try {
            dziennik.zapiszInformacje(tresc);
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoInformacji(KOMUNIKAT_AMBIWALENCJI_DZIENNIKA + "\n" +
                    KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
        }
    }

    protected void walidujZwroconaLiczbeWierszy(int liczbaZmienionychWierszy, String nicNieZostalo){
        if(liczbaZmienionychWierszy > 1){
            zarzadcaFormatek.wyswietlOknoBledu("Na bazie zapisał się więcej niż jeden rekord.");
        }else if(liczbaZmienionychWierszy == 0){
            zarzadcaFormatek.wyswietlOknoInformacji("Nic nie zostało " + nicNieZostalo);
        }
    }
}
