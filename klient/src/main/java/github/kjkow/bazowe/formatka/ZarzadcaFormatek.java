package github.kjkow.bazowe.formatka;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class ZarzadcaFormatek implements IZarzadcaFormatek {
    private Map<EnumOknoAplikacji, String> mapaFormatek;

    public ZarzadcaFormatek(){
        mapaFormatek = new HashMap<>();
        inicjujMapeFormatek();
    }

    private void inicjujMapeFormatek(){
        mapaFormatek.put(EnumOknoAplikacji.GLOWNE, "StartProgramu.fxml");
        mapaFormatek.put(EnumOknoAplikacji.SPRZATANIE, "SprzatanieEkranGlowny.fxml");
        mapaFormatek.put(EnumOknoAplikacji.SPRZATANIEEDYCJA, "SprzatanieEdycja.fxml");
//        mapaFormatek.put(EnumOknoAplikacji.LOG, "Log.fxml");
//        mapaFormatek.put(EnumOknoAplikacji.JEDZENIE, "JedzenieEkranGlowny.fxml");
//        mapaFormatek.put(EnumOknoAplikacji.PRZEPISY, "Przepisy.fxml");
//        mapaFormatek.put(EnumOknoAplikacji.DODAJPRZEPIS, "DodajPrzepis.fxml");
//        mapaFormatek.put(EnumOknoAplikacji.SZCZEGOLYPRZEPISU, "SzczegolyPrzepisu.fxml");
//        mapaFormatek.put(EnumOknoAplikacji.PROCESKROK1, "Krok1.fxml");
//        mapaFormatek.put(EnumOknoAplikacji.PROCESKROK2, "Krok2.fxml");
//        mapaFormatek.put(EnumOknoAplikacji.PROCESKROK3, "Krok3.fxml");
        mapaFormatek.put(EnumOknoAplikacji.EXCEL, "AutomatExcel.fxml");
    }

    public void wyswietlNowaFormatke(EnumOknoAplikacji nowaFormatka, Stage scena) {
        try {
            URL resource = getClass().getClassLoader().getResource(mapaFormatek.get(nowaFormatka));
            Parent nowyRodzic = FXMLLoader.load(resource);
            if(scena.getScene() != null){
                podmienRodzicaSceny(scena, nowyRodzic);
            }else{
                scena.setScene(new Scene(nowyRodzic));
                podmienRodzicaSceny(scena, nowyRodzic);
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            //TODO: obsluzyc
        }
    }

    private void podmienRodzicaSceny(Stage scena, Parent nowyRodzic){
        scena.getScene().setRoot(nowyRodzic);
    }
}
