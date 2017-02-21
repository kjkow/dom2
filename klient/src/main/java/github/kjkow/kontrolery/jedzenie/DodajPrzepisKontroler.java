package github.kjkow.kontrolery.jedzenie;

import github.kjkow.Przepis;
import github.kjkow.bazowe.BazowyKontroler;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Created by Kamil.Kowalczyk on 2016-12-29.
 */
public class DodajPrzepisKontroler extends BazowyKontroler {

    public TextField nazwa;
    public TextArea skladniki;
    public TextArea przygotowanie;
    public DatePicker data;

    /**
     * Button powrot
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/Przepisy.fxml");
    }

    /**
     * Button zapisz
     * @param actionEvent
     */
    public void akcja_zapisz(ActionEvent actionEvent) {
        try{
            zapiszPrzepis();
        } catch(Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void zapiszPrzepis(){
        Przepis nowyPrzepis = new Przepis();

        if(nazwa.getText().compareTo("") != 0){
            nowyPrzepis.setNazwa(nazwa.getText());
        }else{
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wprowadzono nazwy przepisy.");
            return;
        }

        kontekstZwracanyJedzenieDAO = jedzenieDAO.dodajPrzepis(nowyPrzepis);
        if(!kontekstZwracanyJedzenieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyJedzenieDAO.getLog(), kontekstZwracanyJedzenieDAO.getBlad());
            return;
        }

        zapiszWykonanieWDzienniku("Dodano nowy przepis: " + nowyPrzepis.getNazwa());
        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie dodano nowy przepis");

        otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/Przepisy.fxml");
    }
}
