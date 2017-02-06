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
    private String staraNazwa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            przepis = (Przepis) PrzechowywaczDanych.pobierzObiekt();
            inicjujPola();
            staraNazwa = przepis.getNazwa();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void inicjujPola(){
        data.setValue(przepis.getDataOstatniegoPrzygotowania());
        przygotowanie.setText(przepis.getSposobPrzygotowania());
        skladniki.setText(przepis.getSkladniki());
        nazwa.setText(przepis.getNazwa());
    }

    /**
     * Button zapisz
     * @param actionEvent
     */
    public void akcja_zapisz(ActionEvent actionEvent) {
        try{
            zapiszPrzepis();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void zapiszPrzepis(){
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
            liczbaZmienionychWierszy = jedzenieDAO.modyfikujPrzepis(przepis, staraNazwa);
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

        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/Przepisy.fxml");
    }

    /**
     * Button powrot
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/Przepisy.fxml");
    }
}
