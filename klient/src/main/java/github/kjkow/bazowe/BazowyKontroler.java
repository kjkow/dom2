package github.kjkow.bazowe;


import github.kjkow.automaty.excel.AutomatDoExcela;
import github.kjkow.automaty.excel.IAutomatDoExcela;
import github.kjkow.bazowe.formatka.IZarzadcaFormatek;
import github.kjkow.bazowe.formatka.ZarzadcaFormatek;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public abstract class BazowyKontroler {

    protected IZarzadcaFormatek zarzadcaFormatek;
    protected IAutomatDoExcela automatDoExcela;
    protected static final String KOMUNIKAT_BLEDU_KONSTRUKTORA_DAO = "Błąd podczas wczytywania konfiguracji bazy danych z pliku";
    protected static final String KOMUNIKAT_BLEDU_SQL = "Wystąpił błąd na bazie danych";
    protected static final String KOMUNIKAT_BLEDU_KONEKTORA_JDBC = "Wystąpił błąd sterownika bazy danych";

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
    }

    protected void obsluzBlad(String trescKomunikatu, Exception e){
        ObslugaBledu.obsluzBlad(trescKomunikatu, e);
    }

    protected void otworzNowaFormatke(BazowyKontroler pKontroler){
        zarzadcaFormatek.wyswietlNowaFormatke(pKontroler, zwrocSceneFormatki());
    }

    public URL pobierzZrodloFormatki(){
        return zrodloFormatki;
    }

    /**
     * ***********potrzebne do zmiany formatki*************
     */
    protected abstract Stage zwrocSceneFormatki();
    protected abstract void ustawZrodloFormatki();

}
