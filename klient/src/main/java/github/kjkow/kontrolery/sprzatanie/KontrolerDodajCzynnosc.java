package github.kjkow.kontrolery.sprzatanie;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.Czynnosc;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public class KontrolerDodajCzynnosc extends BazowyKontroler {

    public TextField nazwa;
    public TextField czestotliwosc;

    /**
     * Button anuluj
     * @param actionEvent
     */
    public void powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    /**
     * Button zapisz
     * @param actionEvent
     */
    public void zapiszCzynnosc(ActionEvent actionEvent) {
        Czynnosc nowaCzynnosc = new Czynnosc();

        if(nazwa.getText().compareTo("") != 0){
            nowaCzynnosc.setNazwaCzynnosci(nazwa.getText());
        }else{
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wprowadzono nazwy czynnosci.");
            return;
        }

        try{
            nowaCzynnosc.setDniCzestotliwosci(Integer.parseInt(czestotliwosc.getText()));
        }catch (NumberFormatException e){
            obsluzBlad("Niepoprawnie wprowadzona liczba", e);
            return;
        }

        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = sprzatanieDAO.dodajCzynnosc(nowaCzynnosc);
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        if(liczbaZmienionychWierszy > 1){
            zarzadcaFormatek.wyswietlOknoBledu("Na bazie zapisał się więcej niż jeden rekord.");
            wrocDoPoprzedniejFormatki();
        }else if(liczbaZmienionychWierszy == 0){
            zarzadcaFormatek.wyswietlOknoInformacji("Nic nie zostało dodane");
            return;
        }

        try {
            dziennik.zapiszInformacje("Dodano nową czynność " + nowaCzynnosc.getNazwaCzynnosci());
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoInformacji(KOMUNIKAT_AMBIWALENCJI_DZIENNIKA + "\n" +
                    KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            wrocDoPoprzedniejFormatki();
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie dodano nową czynność.");
        wrocDoPoprzedniejFormatki();
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)czestotliwosc.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/DodajCzynnosc.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }
}
