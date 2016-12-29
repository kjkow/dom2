package github.kjkow.kontrolery.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-29.
 */
public class ModyfikujPrzepisKontroler extends BazowyKontroler implements Initializable{

    public DatePicker data;
    public TextArea przygotowanie;
    public TextArea skladniki;
    public TextField nazwa;

    private Przepis przepis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        przepis = (Przepis)PrzechowywaczDanych.pobierzObiekt();
    }

    public void akcja_zapisz(ActionEvent actionEvent) {
        inicjujJedzenieDAO();

        if(jedzenieDAO == null){
            return;
        }

        przepis.setNazwa(nazwa.getText());
        przepis.setDataOstatniegoPrzygotowania(data.getValue());
        przepis.setSposobPrzygotowania(przygotowanie.getText());
        przepis.setSkladniki(skladniki.getText());

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = jedzenieDAO.modyfikujPrzepis(przepis);
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }
        walidujZwroconaLiczbeWierszy(liczbaZmienionychWierszy, "zmodyfikowane");
        zapiszWykonanieWDzienniku("Zmodyfikowano przepis " + przepis.getNazwa());
        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zmodyfikowano przepis.");
        wrocDoPoprzedniejFormatki();
    }

    public void akcja_powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)data.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/ModyfikujPrzepis.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }


}
