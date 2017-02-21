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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-28.
 */
public class PrzepisyKontroler extends BazowyKontroler implements Initializable {

    public ListView<String> przepisy;
    public Button usun;
    public Button szczegoly;
    public Button modyfikuj;

    private ObservableList<String> listaPrzepisowPrezentacja = FXCollections.observableArrayList();
    private Przepis przepis;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            zaladujListePrzepisow();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void zaladujListePrzepisow(){
        listaPrzepisowPrezentacja.clear();

        kontekstZwracanyJedzenieDAO = jedzenieDAO.pobierzListePrzepisow();
        if(!kontekstZwracanyJedzenieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyJedzenieDAO.getLog(), kontekstZwracanyJedzenieDAO.getBlad());
            return;
        }

        for(String nazwaPrzepisu: kontekstZwracanyJedzenieDAO.getLista()){
            listaPrzepisowPrezentacja.add(nazwaPrzepisu);
        }

        przepisy.setItems(listaPrzepisowPrezentacja);
    }

    /**
     * Button modyfikuj
     * @param actionEvent
     */
    public void akcja_modyfikuj(ActionEvent actionEvent) {
        try{
            przejdzDoModyfikacji();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void przejdzDoModyfikacji(){
        String nazwaPrzepisu = przepisy.getSelectionModel().getSelectedItem();
        if(nazwaPrzepisu == null) return;

        kontekstZwracanyJedzenieDAO = jedzenieDAO.pobierzDanePrzepisu(nazwaPrzepisu);
        if(!kontekstZwracanyJedzenieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyJedzenieDAO.getLog(), kontekstZwracanyJedzenieDAO.getBlad());
            return;
        }
        przepis = kontekstZwracanyJedzenieDAO.getPrzepis();

        PrzechowywaczDanych.zapiszObiekt(przepis);
        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/ModyfikujPrzepis.fxml");
    }

    /**
     * Button dodaj
     * @param actionEvent
     */
    public void akcja_dodaj(ActionEvent actionEvent) {
        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/DodajPrzepis.fxml");
    }

    /**
     * Button Pokaż
     * @param actionEvent
     */
    public void akcja_szczegoly(ActionEvent actionEvent) {
        try {
            pokazSzczegolyPrzepisu();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void pokazSzczegolyPrzepisu(){
        String nazwaPrzepisu = przepisy.getSelectionModel().getSelectedItem();
        if(nazwaPrzepisu == null) return;

        kontekstZwracanyJedzenieDAO = jedzenieDAO.pobierzDanePrzepisu(nazwaPrzepisu);
        if(!kontekstZwracanyJedzenieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyJedzenieDAO.getLog(), kontekstZwracanyJedzenieDAO.getBlad());
            return;
        }
        przepis = kontekstZwracanyJedzenieDAO.getPrzepis();

        PrzechowywaczDanych.zapiszObiekt(przepis);
        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/PokazPrzepis.fxml");
    }

    /**
     * ListView przepisy
     * @param event
     */
    public void akcja_przepisy(Event event) {
        if(przepisy.getSelectionModel().getSelectedItem()!= null) {
            odblokujEdytowalnoscPrzyciskow();
        }
    }

    //w obydwu ponizszych metodach chodzi o przyciski zwiazane z wyborem elementu z listy
    private void odblokujEdytowalnoscPrzyciskow(){
        usun.setDisable(false);
        szczegoly.setDisable(false);
        modyfikuj.setDisable(false);
    }

    private void zablokujEdytowalnoscPrzyciskow(){
        usun.setDisable(true);
        szczegoly.setDisable(true);
        modyfikuj.setDisable(true);
    }

    /**
     * Button  Usuń
     * @param actionEvent
     */
    public void akcja_usun(ActionEvent actionEvent) {
        try{
            usunPrzepis();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void usunPrzepis(){
        String nazwaPrzepisu = przepisy.getSelectionModel().getSelectedItem();
        if(nazwaPrzepisu == null) return;

        kontekstZwracanyJedzenieDAO = jedzenieDAO.usunPrzepis(nazwaPrzepisu);
        if(!kontekstZwracanyJedzenieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyJedzenieDAO.getLog(), kontekstZwracanyJedzenieDAO.getBlad());
            return;
        }

        zapiszWykonanieWDzienniku("Usunięto przepis " + nazwaPrzepisu);
        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie usunięto przepis");
        zaladujListePrzepisow();
        zablokujEdytowalnoscPrzyciskow();
    }
}
