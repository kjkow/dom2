package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.Czynnosc;
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
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class KontrolerEdycjaSprzatanie extends BazowyKontroler implements Initializable {

    public ListView<String> lista_czynnosci;
    public Button modyfikacja;
    public Button usun_czynnosc;
    private ObservableList<String> listaCzynnosciPrezentacja = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zaladujListeCzynnosci();
    }

    /**
     * Button modyfikuj
     * @param actionEvent
     */
    public void akcjaModyfikacja(ActionEvent actionEvent) {
        Czynnosc czynnosc;

        try {
            czynnosc = sprzatanieDAO.pobierzDaneCzynnosci(lista_czynnosci.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }
        PrzechowywaczDanych.zapiszObiekt(czynnosc);
        otworzNowaFormatke(new KontrolerModyfikujCzynnosc());
    }

    /**
     * Button dodaj
     * @param actionEvent
     */
    public void akcjaDodaj(ActionEvent actionEvent) {
        otworzNowaFormatke(new KontrolerDodajCzynnosc());
    }

    /**
     * Button Powrot
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    /**
     * Button Usun czynnosc
     * @param actionEvent
     */
    public void akcja_usun_czynnosc(ActionEvent actionEvent) {
        //TODO: to jest poprawnie obsluzone odpytanie bazy. zastosowac wszedzie
        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        String nazwaCzynnosci = lista_czynnosci.getSelectionModel().getSelectedItem();
        if(nazwaCzynnosci == null) return;

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = sprzatanieDAO.usunCzynnosc(nazwaCzynnosci);

        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        if(liczbaZmienionychWierszy > 1){
            zarzadcaFormatek.wyswietlOknoBledu("Z bazy usunął się więcej niż jeden rekord.");
            return;
        }

        if(liczbaZmienionychWierszy == 0){
            zarzadcaFormatek.wyswietlOknoInformacji("Nic nie zostało usunięte");
            return;
        }

        try {
            dziennik.zapiszInformacje("Usunięto czynność " + nazwaCzynnosci);
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoBledu(KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            return;
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie usunięto czynność");
        zaladujListeCzynnosci();
        modyfikacja.setDisable(true);
        usun_czynnosc.setDisable(true);
    }

    private void zaladujListeCzynnosci(){
        listaCzynnosciPrezentacja.clear();
        inicjujSprzatanieDAO();
        if(sprzatanieDAO == null){
            return;
        }
        try {
            for(String nazwaCzynnosci: sprzatanieDAO.pobierzNazwyCzynnosci()){
                listaCzynnosciPrezentacja.add(nazwaCzynnosci);
            }
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }
        lista_czynnosci.setItems(listaCzynnosciPrezentacja);
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)lista_czynnosci.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/SprzatanieEdycja.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }

    @Override
    protected void wrocDoPoprzedniejFormatki(){
        otworzNowaFormatke(new KontrolerSprzatanieEkranGlowny());
    }

    /**
     * ListView czynnosci
     * @param event
     */
    public void akcja_lista(Event event) {
        modyfikacja.setDisable(false);
        usun_czynnosc.setDisable(false);
    }
}
