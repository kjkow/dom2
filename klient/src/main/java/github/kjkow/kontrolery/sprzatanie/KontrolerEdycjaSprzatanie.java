package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.sprzatanie.Czynnosc;
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
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class KontrolerEdycjaSprzatanie extends BazowyKontroler implements Initializable {

    public ListView<String> lista_czynnosci;
    private ObservableList<String> listaCzynnosciPrezentacja = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zaladujListeCzynnosci();
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
        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = sprzatanieDAO.usunCzynnosc(lista_czynnosci.getSelectionModel().getSelectedItem());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        if(liczbaZmienionychWierszy > 1){
            zarzadcaFormatek.wyswietlOknoBledu("Na bazie zapisał się więcej niż jeden rekord.");
            return;
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie usunięto czynność");
        zaladujListeCzynnosci();
    }

    private void zaladujListeCzynnosci(){
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

    public void akcjaDodaj(ActionEvent actionEvent) {
        otworzNowaFormatke(new KontrolerDodajCzynnosc());
    }
}
