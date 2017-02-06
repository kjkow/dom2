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

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class CzynnosciKontroler extends BazowyKontroler implements Initializable {

    public ListView<String> lista_czynnosci;
    public Button modyfikacja;
    public Button usun_czynnosc;
    public Button pokaz;
    private ObservableList<String> listaCzynnosciPrezentacja = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            zaladujListeCzynnosci();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    /**
     * Button modyfikuj
     * @param actionEvent
     */
    public void akcjaModyfikacja(ActionEvent actionEvent) {
        try{
            przejdzDalejZWybranaCzynnoscia(new ModyfikujCzynnoscKontroler());
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void przejdzDalejZWybranaCzynnoscia(BazowyKontroler pKontroler){
        inicjujSprzatanieDAO();
        if(sprzatanieDAO == null) return;

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
        otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/ModyfikujCzynnosc.fxml");
    }

    /**
     * Button dodaj
     * @param actionEvent
     */
    public void akcjaDodaj(ActionEvent actionEvent) {
        otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/DodajCzynnosc.fxml");
    }

    /**
     * Button Usun czynnosc
     * @param actionEvent
     */
    public void akcja_usun_czynnosc(ActionEvent actionEvent) {
        try{
            usunCzynnosc();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void usunCzynnosc(){
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
        }else if(liczbaZmienionychWierszy == 0){
            zarzadcaFormatek.wyswietlOknoInformacji("Nic nie zostało usunięte");
            return;
        }

        zapiszWykonanieWDzienniku("Usunięto czynność " + nazwaCzynnosci);
        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie usunięto czynność");
        zaladujListeCzynnosci();
        modyfikacja.setDisable(true);
        usun_czynnosc.setDisable(true);
        pokaz.setDisable(true);
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
        //zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/Czynnosci.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }



    /**
     * ListView czynnosci
     * @param event
     */
    public void akcja_lista(Event event) {
        modyfikacja.setDisable(false);
        usun_czynnosc.setDisable(false);
        pokaz.setDisable(false);
    }

    /**
     * Button Pokaz czynnosc
     * @param actionEvent
     */
    public void akcjaPokazCzynnosc(ActionEvent actionEvent) {
        try {
            przejdzDalejZWybranaCzynnoscia(new PokazCzynnoscKontroler());
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }
}
