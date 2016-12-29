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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public abstract class BazowyKontroler {

    protected IZarzadcaFormatek zarzadcaFormatek;
    protected IAutomatDoExcela automatDoExcela;
    protected IDziennik dziennik;

    protected static final String KOMUNIKAT_BLEDU_KONSTRUKTORA_DAO = "Wystąpił błąd podczas wczytywania konfiguracji bazy danych z pliku";
    protected static final String KOMUNIKAT_BLEDU_SQL = "Wystąpił błąd na bazie danych";
    protected static final String KOMUNIKAT_BLEDU_KONEKTORA_JDBC = "Wystąpił błąd sterownika bazy danych";
    protected static final String KOMUNIKAT_BLEDU_IO = "Wystąpił błąd podczas wczytywania pliku.";
    protected static final String KOMUNIKAT_AMBIWALENCJI_DZIENNIKA = "Nastąpił poprawny zapis na bazie, ale nie udało się zapisać informacji w dzienniku aplikacji.";

    protected SprzatanieDAO sprzatanieDAO;
    protected JedzenieDAO jedzenieDAO;

    protected URL zrodloFormatki;

    public BazowyKontroler(){
        inicjujNarzedzia();
        ustawZrodloFormatki();
    }

    private void inicjujNarzedzia(){
        if(zarzadcaFormatek == null){
            zarzadcaFormatek = new ZarzadcaFormatek();
        }
        if(automatDoExcela == null){
            automatDoExcela = new AutomatDoExcela();
        }
        if(dziennik == null){
            dziennik = new Dziennik();
        }
    }

    protected void obsluzBlad(String trescKomunikatu, Exception e){
        ObslugaBledu.obsluzBlad(trescKomunikatu, e);
    }

    protected void otworzNowaFormatke(BazowyKontroler pKontroler){
        zapametajPowrot();
        zarzadcaFormatek.wyswietlNowaFormatke(pKontroler, zwrocSceneFormatki());
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
            wrocDoPoprzedniejFormatki();
        }
    }

    /**
     * Formatka ktora ma jakas formatke wstecz(powrot) i jakas formatke dalej musi nadpisac ta metode,
     * inaczej moze utknac pomiedzy soba a ta formatka wprzod
     */
    protected void wrocDoPoprzedniejFormatki(){
        otworzNowaFormatke(PrzechowywaczDanych.pobierzWyjscie());
    }

    public URL pobierzZrodloFormatki(){
        return zrodloFormatki;
    }

    /**
     * ***********potrzebne do zmiany formatki*************
     */
    protected abstract Stage zwrocSceneFormatki();
    protected abstract void ustawZrodloFormatki();
    protected abstract void zapametajPowrot();

}
