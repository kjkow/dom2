package github.kjkow.kontrolery.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public class PokazPrzepisKontroler extends BazowyKontroler implements Initializable {

    public TextField nazwa;
    public TextArea skladniki;
    public TextArea przygotowanie;
    public TextField data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Przepis przepis = (Przepis) PrzechowywaczDanych.pobierzObiekt();
            nazwa.setText(przepis.getNazwa());
            skladniki.setText(przepis.getSkladniki());
            przygotowanie.setText(przepis.getSposobPrzygotowania());
            data.setText(String.valueOf(przepis.getDataOstatniegoPrzygotowania()));
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    /**
     * Button
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)nazwa.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/PokazPrzepis.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }

}
