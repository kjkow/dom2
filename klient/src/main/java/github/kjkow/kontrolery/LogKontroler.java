package github.kjkow.kontrolery;

import github.kjkow.DziennikAplikacji;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.KontekstAplikacji;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-08-19.
 */
public class LogKontroler extends BazowyKontroler implements Initializable {

    @FXML public TextArea obszar_logu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            inicjujOknoLogu();
        }catch (Exception e){
            obsluzBlad("Błąd podczas pobierania zczytywania dziennika aplikacji", e);
        }
    }

    private void inicjujOknoLogu() throws SQLException, IOException, ClassNotFoundException {
        obszar_logu.appendText(DziennikAplikacji.zwrocDziennik(KontekstAplikacji.pobierzSciezkeDziennikaAplikacji()));
    }
}
