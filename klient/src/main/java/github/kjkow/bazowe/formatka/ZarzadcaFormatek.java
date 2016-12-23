package github.kjkow.bazowe.formatka;

import github.kjkow.bazowe.BazowyKontroler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class ZarzadcaFormatek implements IZarzadcaFormatek {

    private Alert alert;

    @Override
    public void wyswietlNowaFormatke(BazowyKontroler pKontroler, Stage scena) {
        try {
            Parent nowyRodzic = FXMLLoader.load(pKontroler.pobierzZrodloFormatki());
            if(scena.getScene() != null){
                podmienRodzicaSceny(scena, nowyRodzic);
            }else{
                scena.setScene(new Scene(nowyRodzic));
                podmienRodzicaSceny(scena, nowyRodzic);
            }
        } catch (IOException | NullPointerException e) {
            wyswietlOknoBledu("Błąd podczas wyświetlania formatki.");
            e.printStackTrace();
            //TODO: obsluzyc
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

    private void podmienRodzicaSceny(Stage scena, Parent nowyRodzic){
        scena.getScene().setRoot(nowyRodzic);
    }
}
