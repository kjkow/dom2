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

import java.io.IOException;
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

    //TODO: modyfikuj przepis(mamy tylko pokaz) - to samo z czynnością

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
        usun.setDisable(false);
        szczegoly.setDisable(false);
    }

    /**
     * Button
     * @param actionEvent
     */
    public void akcja_usun(ActionEvent actionEvent) {
        inicjujJedzenieDAO();

        if(jedzenieDAO == null) return;

        String nazwaPrzepisu = przepisy.getSelectionModel().getSelectedItem();

        try {
            jedzenieDAO.usunPrzepis(nazwaPrzepisu);
            dziennik.zapiszInformacje("Usunięto przepis " + nazwaPrzepisu);
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoBledu(KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            return;
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie usunięto przepis.");
        zaladujListePrzepisow();
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
