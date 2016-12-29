package github.kjkow.kontrolery.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public class KontrolerPrzepisy extends BazowyKontroler implements Initializable {

    public ListView<String> przepisy;
    public Button usun;
    public Button szczegoly;
    public Button modyfikuj;

    private ObservableList<String> listaPrzepisowPrezentacja = FXCollections.observableArrayList();
    private Przepis przepis;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zaladujListePrzepisow();
    }

    private void zaladujListePrzepisow(){
        listaPrzepisowPrezentacja.clear();
        inicjujJedzenieDAO();
        if(jedzenieDAO == null) return;

        try {
            for(String nazwaPrzepisu: jedzenieDAO.pobierzListePrzepisow()){
                listaPrzepisowPrezentacja.add(nazwaPrzepisu);
            }
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        przepisy.setItems(listaPrzepisowPrezentacja);
    }

    /**
     * Button
     * @param actionEvent
     */
    public void akcja_modyfikuj(ActionEvent actionEvent) {
        inicjujJedzenieDAO();
        if(jedzenieDAO == null) return;

        try {
            przepis = jedzenieDAO.pobierzDanePrzepisu(przepisy.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        PrzechowywaczDanych.zapiszObiekt(przepis);
        otworzNowaFormatke(new KontrolerModyfikujPrzepis());
    }

    /**
     * Button
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    /**
     * Button
     * @param actionEvent
     */
    public void akcja_dodaj(ActionEvent actionEvent) {
        otworzNowaFormatke(new KontrolerDodajPrzepis());
    }

    /**
     * Button
     * @param actionEvent
     */
    public void akcja_szczegoly(ActionEvent actionEvent) {
        inicjujJedzenieDAO();

        if(jedzenieDAO == null) return;

        try {
            przepis = jedzenieDAO.pobierzDanePrzepisu(przepisy.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        PrzechowywaczDanych.zapiszObiekt(przepis);
        otworzNowaFormatke(new KontrolerPokazPrzepis());
    }

    /**
     * ListView przepisy
     * @param event
     */
    public void akcja_przepisy(Event event) {
        //TODO: przypisanie wybranego przepisu(jeśli potrzebne)
        odblokujEdytowalnoscPrzyciskow();
    }

    //w obydwu ponizszych metodach chodzi o przyciski zwiazane z wyborem elementu z listy
    private void odblokujEdytowalnoscPrzyciskow(){
        usun.setDisable(false);
        szczegoly.setDisable(false);
        modyfikuj.setDisable(true);
    }

    private void zablokujEdytowalnoscPrzyciskow(){
        usun.setDisable(true);
        szczegoly.setDisable(true);
        modyfikuj.setDisable(true);
    }

    /**
     * Button
     * @param actionEvent
     */
    public void akcja_usun(ActionEvent actionEvent) {
        inicjujJedzenieDAO();

        if(jedzenieDAO == null){
            return;
        }

        String nazwaPrzepisu = przepisy.getSelectionModel().getSelectedItem();
        if(nazwaPrzepisu == null) return;

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = jedzenieDAO.usunPrzepis(nazwaPrzepisu);
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        walidujZwroconaLiczbeWierszy(liczbaZmienionychWierszy, "usunięte");
        zapiszWykonanieWDzienniku("Usunięto przepis " + nazwaPrzepisu);
        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie usunięto przepis");
        zaladujListePrzepisow();
        zablokujEdytowalnoscPrzyciskow();
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)przepisy.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/Przepisy.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }

    @Override
    protected void wrocDoPoprzedniejFormatki(){
        otworzNowaFormatke(new KontrolerJedzenieGlowny());
    }
}
