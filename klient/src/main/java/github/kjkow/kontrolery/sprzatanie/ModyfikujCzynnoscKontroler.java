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
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public class ModyfikujCzynnoscKontroler extends BazowyKontroler implements Initializable{

    public TextField nazwa;
    public TextField czestotliwosc;
    public DatePicker dataOstatniegoSprzatania;
    public DatePicker dataNastepnegoSprzatania;

    private Czynnosc czynnosc;
    private String pierwotnaNazwaCzynnosci;

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
        kontekstZwracanySprzatanieDAO = sprzatanieDAO.pobierzDaneCzynnosci((String)PrzechowywaczDanych.pobierzObiekt());
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
            pierwotnaNazwaCzynnosci = czynnosc.getNazwaCzynnosci();
        }
    }

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
        try {
            kontekstZwracanySprzatanieDAO = sprzatanieDAO.modyfikujCzynnosc(czynnosc, pierwotnaNazwaCzynnosci);
            if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
                obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
                return;
            }

            zapiszWykonanieWDzienniku("Zmodyfikowano czynność: " + czynnosc.getNazwaCzynnosci());
            zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zmodyfikowano czynność.");
            otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/Czynnosci.fxml");
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }
}
