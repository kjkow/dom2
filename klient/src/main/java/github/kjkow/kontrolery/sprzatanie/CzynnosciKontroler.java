package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
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
            przejdzDalejZWybranaCzynnoscia(true);
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void przejdzDalejZWybranaCzynnoscia(boolean czyDoModyfikacji){
        String nazwaCzynnosci = lista_czynnosci.getSelectionModel().getSelectedItem();
        if(nazwaCzynnosci != null){
            PrzechowywaczDanych.zapiszObiekt(nazwaCzynnosci);

            if(czyDoModyfikacji) {
                otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/ModyfikujCzynnosc.fxml");
            }else{
                otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/PokazCzynnosc.fxml");
            }
        }
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
        String nazwaCzynnosci = lista_czynnosci.getSelectionModel().getSelectedItem();
        if(nazwaCzynnosci == null) return;

        kontekstZwracanySprzatanieDAO = sprzatanieDAO.usunCzynnosc(nazwaCzynnosci);
        if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
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

        kontekstZwracanySprzatanieDAO = sprzatanieDAO.pobierzNazwyCzynnosci();
        if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
            return;
        }
        for(String nazwaCzynnosci: kontekstZwracanySprzatanieDAO.getNazwyCzynnosci()){
            listaCzynnosciPrezentacja.add(nazwaCzynnosci);
        }

        lista_czynnosci.setItems(listaCzynnosciPrezentacja);
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
            przejdzDalejZWybranaCzynnoscia(false);
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }
}
