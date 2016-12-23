package github.kjkow.powiadomienia;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by Kamil.Kowalczyk on 2016-12-22.
 */
public class Powiadomienia implements IPowiadomienia{

    private Alert alert;

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
