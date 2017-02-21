package github.kjkow.kontrolery.jedzenie.proces;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Utworzył Kamil Kowalczyk dnia 2017-01-05.
 */
public class Krok2Kontroler extends BazowyKontroler implements Initializable{

    public ListView obiady;
    public ListView wybraneObiady;
    private ObservableList<String> listaPrzepisowPrezentacja = FXCollections.observableArrayList();
    private ObservableList<String> wybraneObiadyLista = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            zaladujListePrzepisow();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    /**
     * Button dalej
     * @param actionEvent
     */
    public void dalej(ActionEvent actionEvent) {
        try{
            zapiszSkladniki();
            otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/proces/Krok3.fxml");
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void zapiszSkladniki(){
        String listaSkladnikow = "";
        for(String nazwaPrzepisu: wybraneObiadyLista){
            Przepis przepis;

            kontekstZwracanyJedzenieDAO = jedzenieDAO.pobierzDanePrzepisu(nazwaPrzepisu);
            if(!kontekstZwracanyJedzenieDAO.isCzyBrakBledow()){
                obsluzBlad(kontekstZwracanyJedzenieDAO.getLog(), kontekstZwracanyJedzenieDAO.getBlad());
                return;
            }
            przepis = kontekstZwracanyJedzenieDAO.getPrzepis();

            listaSkladnikow += przepis.getNazwa() +"\n";
            listaSkladnikow += przepis.getSkladniki() +"\n";
            listaSkladnikow += "-----------------------" + "\n\n";
        }
        PrzechowywaczDanych.zapiszObiekt(listaSkladnikow);
    }

    /**
     * Button dodaj do listy
     * @param actionEvent
     */
    public void dodajDoListy(ActionEvent actionEvent) {
        try{
            dodajObiadDoListy();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void dodajObiadDoListy(){
        String obiad = obiady.getSelectionModel().getSelectedItem().toString();
        if(obiad !=null) wybraneObiadyLista.add(obiad);
        wybraneObiady.setItems(wybraneObiadyLista);
    }

    /**
     * Button usun z listy
     * @param actionEvent
     */
    public void usunZListy(ActionEvent actionEvent) {
        try{
            usunZListyObiadow();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void usunZListyObiadow(){
        String obiad = wybraneObiady.getSelectionModel().getSelectedItem().toString();
        if(obiad !=null) wybraneObiadyLista.remove(obiad);
        wybraneObiady.setItems(wybraneObiadyLista);
    }

    private void zaladujListePrzepisow(){
        listaPrzepisowPrezentacja.clear();

        kontekstZwracanyJedzenieDAO = jedzenieDAO.pobierzListePrzepisowDoProcesu();
        if(!kontekstZwracanyJedzenieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyJedzenieDAO.getLog(), kontekstZwracanyJedzenieDAO.getBlad());
            return;
        }

        for(String nazwaPrzepisu: kontekstZwracanyJedzenieDAO.getLista()){
            listaPrzepisowPrezentacja.add(nazwaPrzepisu);
        }

        obiady.setItems(listaPrzepisowPrezentacja);
    }
}
