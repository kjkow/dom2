package github.kjkow.kontrolery.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
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
        przepis.setNazwa(nazwa.getText());
        przepis.setDataOstatniegoPrzygotowania(data.getValue());
        przepis.setSposobPrzygotowania(przygotowanie.getText());
        przepis.setSkladniki(skladniki.getText());

        kontekstZwracanyJedzenieDAO =  jedzenieDAO.modyfikujPrzepis(przepis, staraNazwa);
        if(!kontekstZwracanyJedzenieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyJedzenieDAO.getLog(), kontekstZwracanyJedzenieDAO.getBlad());
            return;
        }

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
