package github.kjkow.kontrolery.jedzenie.proces;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
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

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)obiady.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
       // zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/proces/Krok2.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
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
        inicjujJedzenieDAO();
        if(jedzenieDAO == null) return;

        String listaSkladnikow = "";
        for(String nazwaPrzepisu: wybraneObiadyLista){
            Przepis przepis;
            try {
                przepis = jedzenieDAO.pobierzDanePrzepisu(nazwaPrzepisu);
            } catch (SQLException e) {
                obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
                break;
            } catch (ClassNotFoundException e) {
                obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
                break;
            }
            listaSkladnikow += przepis.getNazwa() +"\n";
            listaSkladnikow += przepis.getSkladniki() +"\n";
            listaSkladnikow += "-----------------------" + "\n\n";
        }
        PrzechowywaczDanych.zapiszObiekt(listaSkladnikow);
    }

    /**
     * Button zakoncz proces
     * @param actionEvent
     */
    public void zakoncz(ActionEvent actionEvent) {
        //todo:zamkniecie formatki
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

    /**
     * Button dodaj przepis
     * @param actionEvent
     */
    public void dodajPrzepis(ActionEvent actionEvent) {
        //todo:przycisk do wywalenia?
    }

    private void zaladujListePrzepisow(){
        listaPrzepisowPrezentacja.clear();
        inicjujJedzenieDAO();
        if(jedzenieDAO == null) return;

        try {
            for(String nazwaPrzepisu: jedzenieDAO.pobierzListePrzepisowDoProcesu()){
                listaPrzepisowPrezentacja.add(nazwaPrzepisu);
            }
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        obiady.setItems(listaPrzepisowPrezentacja);
    }
}
