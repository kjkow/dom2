package github.kjkow.kontrolery.sprzatanie;

import github.kjkow.Czynnosc;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public class DodajCzynnoscKontroler extends BazowyKontroler {

    public TextField nazwa;
    public TextField czestotliwosc;

    /**
     * Button anuluj
     * @param actionEvent
     */
    public void powrot(ActionEvent actionEvent) {
        otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/Czynnosci.fxml");
    }

    /**
     * Button zapisz
     * @param actionEvent
     */
    public void zapiszCzynnosc(ActionEvent actionEvent) {
        try{
            zapiszCzynnosc();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void zapiszCzynnosc(){
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

        walidujZwroconaLiczbeWierszy(liczbaZmienionychWierszy, "dodane");
        zapiszWykonanieWDzienniku("Dodano nową czynność " + nowaCzynnosc.getNazwaCzynnosci());
        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie dodano nową czynność.");

        otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/Czynnosci.fxml");
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)czestotliwosc.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        //zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/DodajCzynnosc.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }
}
