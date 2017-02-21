package github.kjkow.bazowe.formatka;

import github.kjkow.bazowe.KontekstAplikacji;
import github.kjkow.bazowe.ObslugaBledu;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class ZarzadcaFormatek implements IZarzadcaFormatek {

    private Alert alert;

    @Override
    public void wyswietlNowaFormatke(String sciezkaDoFormatki) {
        try {
            URL nowaFormatkaUrl = getClass().getClassLoader().getResource(sciezkaDoFormatki);
            AnchorPane nowaFormatka = FXMLLoader.load(nowaFormatkaUrl);
            BorderPane borderPane = KontekstAplikacji.pobierzKorzenFormatek();
            borderPane.setCenter(nowaFormatka);
        } catch (IOException e) {
            ObslugaBledu.obsluzBlad("Wystąpił błąd podczas otwierania nowej formatki", e);
        }
    }

    @Override
    public void wyswietlOknoBledu(String tresc) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd!");
        alert.setContentText(tresc);
        alert.showAndWait();
    }

    @Override
    public boolean wyswietlOknoPotwierdzenia(String tresc) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź");
        alert.setContentText(tresc);
        alert.showAndWait();

        return alert.getResult() == ButtonType.OK;
    }

    @Override
    public void wyswietlOknoInformacji(String tresc) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setContentText(tresc);
        alert.showAndWait();
    }
}
