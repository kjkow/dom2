package github.kjkow.kontrolery.sprzatanie;

import github.kjkow.Czynnosc;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2017-01-04.
 */
public class PokazCzynnoscKontroler extends BazowyKontroler implements Initializable {


    public TextField nazwa;
    public TextField czestotliwosc;
    public DatePicker dataOstatniegoSprzatania;
    public DatePicker dataNastepnegoSprzatania;

    private Czynnosc czynnosc;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            pobierzCzynnosc();
            bindujPola();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void pobierzCzynnosc(){
        kontekstZwracanySprzatanieDAO = sprzatanieDAO.pobierzDaneCzynnosci((String) PrzechowywaczDanych.pobierzObiekt());
        if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
        }
        czynnosc = kontekstZwracanySprzatanieDAO.getCzynnosc();
    }

    private void bindujPola(){
        if(czynnosc != null) {
            nazwa.textProperty().bindBidirectional(czynnosc.nazwaCzynnosciProperty());
            dataNastepnegoSprzatania.valueProperty().bindBidirectional(czynnosc.dataNastepnegoSprzataniaProperty());
            dataOstatniegoSprzatania.valueProperty().bindBidirectional(czynnosc.dataOstatniegoSprzataniaProperty());
            czestotliwosc.textProperty().bindBidirectional(czynnosc.dniCzestotliwosciProperty());
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
