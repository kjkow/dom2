package github.kjkow.kontrolery.sprzatanie;

import github.kjkow.Czynnosc;
import github.kjkow.bazowe.BazowyKontroler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public class DodajCzynnoscKontroler extends BazowyKontroler implements Initializable {

    public TextField nazwa;
    public TextField czestotliwosc;

    private Czynnosc czynnosc;

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
        try{
            kontekstZwracanySprzatanieDAO = sprzatanieDAO.dodajCzynnosc(czynnosc);
            if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
                obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
                return;
            }

            zapiszWykonanieWDzienniku("Dodano nową czynność " + czynnosc.getNazwaCzynnosci());
            zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie dodano nową czynność.");

            otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/Czynnosci.fxml");
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            bindujPola();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void bindujPola(){
        czynnosc = new Czynnosc();
        nazwa.textProperty().bindBidirectional(czynnosc.nazwaCzynnosciProperty());
        czestotliwosc.textProperty().bindBidirectional(czynnosc.dniCzestotliwosciProperty());
    }
}
