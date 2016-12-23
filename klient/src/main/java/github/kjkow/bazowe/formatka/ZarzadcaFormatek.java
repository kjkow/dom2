package github.kjkow.bazowe.formatka;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.powiadomienia.IPowiadomienia;
import github.kjkow.powiadomienia.Powiadomienia;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class ZarzadcaFormatek implements IZarzadcaFormatek {

    private IPowiadomienia powiadomienia;

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
            powiadomienia = new Powiadomienia();
            powiadomienia.wyswietlOknoBledu("Błąd podczas wyświetlania formatki.");
            e.printStackTrace();
            //TODO: obsluzyc
        }
    }

    private void podmienRodzicaSceny(Stage scena, Parent nowyRodzic){
        scena.getScene().setRoot(nowyRodzic);
    }
}
