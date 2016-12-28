package github.kjkow.kontrolery.sprzatanie;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.Czynnosc;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public class KontrolerModyfikujCzynnosc extends BazowyKontroler implements Initializable{

    public TextField nazwa;
    public TextField czestotliwosc;
    private Czynnosc czynnosc;

    //TODO: daty na formatce

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        czynnosc = (Czynnosc)PrzechowywaczDanych.pobierzObiekt();
        nazwa.setText(czynnosc.getNazwaCzynnosci());
        czestotliwosc.setText(String.valueOf(czynnosc.getDniCzestotliwosci()));
    }

    /**
     * Button anuluj
     * @param actionEvent
     */
    public void powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    /**
     * Button zapisz
     * @param actionEvent
     */
    public void zapiszCzynnosc(ActionEvent actionEvent) {
        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        czynnosc.setNazwaCzynnosci(nazwa.getText());
        try{
            czynnosc.setDniCzestotliwosci(Integer.parseInt(czestotliwosc.getText()));
        }catch (NumberFormatException e){
            obsluzBlad("Niepoprawnie wprowadzona liczba", e);
            return;
        }
        //TODO: daty

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = sprzatanieDAO.modyfikujCzynnosc(czynnosc);
            dziennik.zapiszInformacje("Zmodyfikowano czynność " + czynnosc.getNazwaCzynnosci());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoBledu(KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            return;
        }

        if(liczbaZmienionychWierszy > 1){
            zarzadcaFormatek.wyswietlOknoBledu("Na bazie zapisał się więcej niż jeden rekord.");
            wrocDoPoprzedniejFormatki();
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zmodyfikowano czynność.");
        wrocDoPoprzedniejFormatki();
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)czestotliwosc.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/ModyfikujCzynnosc.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }


}
