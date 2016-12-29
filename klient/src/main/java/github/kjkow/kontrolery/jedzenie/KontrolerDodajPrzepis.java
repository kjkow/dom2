package github.kjkow.kontrolery.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-29.
 */
public class KontrolerDodajPrzepis extends BazowyKontroler {

    public TextField nazwa;
    public TextArea skladniki;
    public TextArea przygotowanie;
    public TextField data;

    public void akcja_powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    public void akcja_zapisz(ActionEvent actionEvent) {
        Przepis nowyPrzepis = new Przepis();

        if(nazwa.getText().compareTo("") != 0){
            nowyPrzepis.setNazwa(nazwa.getText());
        }else{
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wprowadzono nazwy przepisy.");
            return;
        }

        inicjujJedzenieDAO();

        if(jedzenieDAO == null){
            return;
        }

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = jedzenieDAO.dodajPrzepis(nowyPrzepis);
            dziennik.zapiszInformacje("Dodano nowy przepis: " + nowyPrzepis.getNazwa());
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

        if(liczbaZmienionychWierszy > 1){
            zarzadcaFormatek.wyswietlOknoBledu("Na bazie zapisał się więcej niż jeden rekord.");
            wrocDoPoprzedniejFormatki();
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie dodano nowy przepis");
        wrocDoPoprzedniejFormatki();
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)nazwa.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/DodajPrzepis.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }
}
