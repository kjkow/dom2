package github.kjkow.kontrolery.sprzatanie;

import github.kjkow.Czynnosc;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2017-01-04.
 */
public class PokazCzynnoscKontroler extends BazowyKontroler implements Initializable {


    public TextField nazwa;
    public TextField czestotliwosc;
    public TextField dataOstatniegoSprzatania;
    public TextField dataNastepnegoSprzatania;

    private Czynnosc czynnosc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            czynnosc = (Czynnosc) PrzechowywaczDanych.pobierzObiekt();
            nazwa.setText(czynnosc.getNazwaCzynnosci());
            czestotliwosc.setText(String.valueOf(czynnosc.getDniCzestotliwosci()));
            dataNastepnegoSprzatania.setText(czynnosc.getDataNastepnegoSprzatania().toString());
            dataOstatniegoSprzatania.setText(czynnosc.getDataOstatniegoSprzatania().toString());
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    /**
     * Button powrot
     * @param actionEvent
     */
    public void powrot(ActionEvent actionEvent) {
       otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/Czynnosci.fxml");
    }
}
