package github.kjkow.kontrolery.jedzenie;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.kontrolery.EkranGlownyKontroler;
import github.kjkow.kontrolery.jedzenie.proces.Krok1Kontroler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public class JedzenieGlownyKontroler extends BazowyKontroler {

    public Button proces;

    /**
     * Button proces
     * @param actionEvent
     */
    public void akcja_proces(ActionEvent actionEvent) {
        otworzNowaFormatke(new Krok1Kontroler());
    }

    /**
     * Button przepisy
     * @param actionEvent
     */
    public void akcja_przepisy(ActionEvent actionEvent) {
        otworzNowaFormatke(new PrzepisyKontroler());
    }

    /**
     * Button powrot
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)proces.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/JedzenieEkranGlowny.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }

    @Override
    protected void wrocDoPoprzedniejFormatki(){
        otworzNowaFormatke(new EkranGlownyKontroler());
    }
}
