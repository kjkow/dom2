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
            czynnosc = (Czynnosc) PrzechowywaczDanych.pobierzObiekt();
            nazwa.setText(czynnosc.getNazwaCzynnosci());
            czestotliwosc.setText(String.valueOf(czynnosc.getDniCzestotliwosci()));
            dataNastepnegoSprzatania.setValue(czynnosc.getDataNastepnegoSprzatania());
            dataOstatniegoSprzatania.setValue(czynnosc.getDataOstatniegoSprzatania());
            pierwotnaNazwaCzynnosci = czynnosc.getNazwaCzynnosci();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
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
            zapiszZmodyfikowanaCzynnosc();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void zapiszZmodyfikowanaCzynnosc(){
        czynnosc.setNazwaCzynnosci(nazwa.getText());
        try{
            czynnosc.setDniCzestotliwosci(Integer.parseInt(czestotliwosc.getText()));
        }catch (NumberFormatException e){
            obsluzBlad("Niepoprawnie wprowadzona liczba", e);
            return;
        }

        try {
            czynnosc.setDataNastepnegoSprzatania(dataNastepnegoSprzatania.getValue());
            czynnosc.setDataOstatniegoSprzatania(dataOstatniegoSprzatania.getValue());
        }catch (NullPointerException e){
            obsluzBlad("Brak daty.", e);
            return;
        }

        kontekstZwracanySprzatanieDAO = sprzatanieDAO.modyfikujCzynnosc(czynnosc, pierwotnaNazwaCzynnosci);
        if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
            return;
        }

        zapiszWykonanieWDzienniku("Zmodyfikowano czynność " + czynnosc.getNazwaCzynnosci());
        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zmodyfikowano czynność.");

        otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/Czynnosci.fxml");
    }
}
