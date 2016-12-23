package github.kjkow.bazowe;


import github.kjkow.automaty.excel.AutomatDoExcela;
import github.kjkow.automaty.excel.IAutomatDoExcela;
import github.kjkow.bazowe.formatka.IZarzadcaFormatek;
import github.kjkow.bazowe.formatka.ZarzadcaFormatek;
import github.kjkow.dziennik.Dziennik;
import github.kjkow.dziennik.IDziennik;
import github.kjkow.powiadomienia.IPowiadomienia;
import github.kjkow.powiadomienia.Powiadomienia;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public abstract class BazowyKontroler {

    protected IZarzadcaFormatek zarzadcaFormatek;
    protected IDziennik dziennik;
    protected IAutomatDoExcela automatDoExcela;
    protected IPowiadomienia powiadomienia;

    protected URL zrodloFormatki;

    public BazowyKontroler(){
        inicjujNarzedzia();
        ustawZrodloFormatki();
    }

    private void inicjujNarzedzia(){
        if(zarzadcaFormatek == null){
            zarzadcaFormatek = new ZarzadcaFormatek();
        }

        if(dziennik == null){
            dziennik = new Dziennik();
        }

        if(automatDoExcela == null){
            automatDoExcela = new AutomatDoExcela();
        }

        if(powiadomienia == null){
            powiadomienia = new Powiadomienia();
        }
    }

    protected void obsluzBlad(String trescKomunikatu, Exception e){
        dziennik.zapiszBlad(trescKomunikatu, e);
        powiadomienia.wyswietlOknoBledu(trescKomunikatu);
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
