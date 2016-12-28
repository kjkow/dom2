package github.kjkow.kontrolery.sprzatanie;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.sprzatanie.Czynnosc;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public class KontrolerDodajCzynnosc extends BazowyKontroler {


    public TextField nazwa;
    public TextField czestotliwosc;

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)czestotliwosc.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/DodajCzynnosc.fxml");
    }

    public void powrot(ActionEvent actionEvent) {
        otworzNowaFormatke(new KontrolerEdycjaSprzatanie());
    }

    public void zapiszCzynnosc(ActionEvent actionEvent) {
        Czynnosc nowaCzynnosc = new Czynnosc();

        if(nazwa.getText().compareTo("") != 0){
            nowaCzynnosc.setNazwaCzynnosci(nazwa.getText());
        }else{
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wprowadzono nazwy czynnosci.");
            return;
        }

        try{
            nowaCzynnosc.setDniCzestotliwosci(Integer.parseInt(czestotliwosc.getText()));
        }catch (NumberFormatException e){
            obsluzBlad("Niepoprawnie wprowadzona liczba", e);
            return;
        }

        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        int liczbaZmienionychWierszy;

        try {
            liczbaZmienionychWierszy = sprzatanieDAO.dodajCzynnosc(nowaCzynnosc);
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        if(liczbaZmienionychWierszy > 1){
            zarzadcaFormatek.wyswietlOknoBledu("Na bazie zapisał się więcej niż jeden rekord.");
            otworzNowaFormatke(new KontrolerEdycjaSprzatanie());
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie dodano nową częstotliwość.");
        otworzNowaFormatke(new KontrolerEdycjaSprzatanie());
    }
}
