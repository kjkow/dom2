package github.kjkow.kontrolery.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-29.
 */
public class DodajPrzepisKontroler extends BazowyKontroler {

    public TextField nazwa;
    public TextArea skladniki;
    public TextArea przygotowanie;
    public DatePicker data;

    /**
     * Button powrot
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/Przepisy.fxml");
    }

    /**
     * Button zapisz
     * @param actionEvent
     */
    public void akcja_zapisz(ActionEvent actionEvent) {
        try{
            zapiszPrzepis();
        } catch(Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void zapiszPrzepis(){
        Przepis nowyPrzepis = new Przepis();

        if(nazwa.getText().compareTo("") != 0){
            nowyPrzepis.setNazwa(nazwa.getText());
        }else{
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wprowadzono nazwy przepisy.");
            return;
        }

        inicjujJedzenieDAO();

        if(jedzenieDAO == null){
            return;
        }

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = jedzenieDAO.dodajPrzepis(nowyPrzepis);
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        walidujZwroconaLiczbeWierszy(liczbaZmienionychWierszy, "usunięte");//todo: wywalic ten mechanizm
        zapiszWykonanieWDzienniku("Dodano nowy przepis: " + nowyPrzepis.getNazwa());
        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie dodano nowy przepis");

        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/Przepisy.fxml");
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)nazwa.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        //zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/DodajPrzepis.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }
}
