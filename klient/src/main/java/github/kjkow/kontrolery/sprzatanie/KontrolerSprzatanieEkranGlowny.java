package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.formatka.EnumOknoAplikacji;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class KontrolerSprzatanieEkranGlowny extends BazowyKontroler implements Initializable {
    @FXML
    public ComboBox<String> kategoria;
    @FXML public ComboBox<String> czynnosc;
    @FXML public TextField aktualna_czynnosc;
    @FXML public TextField data_nastepnego_sprzatania;
    @FXML public TextField data_ostatniego_sprzatania;
    @FXML public DatePicker data_wykonania;
    @FXML public Button wykonano;
    @FXML public Button powrot;
    @FXML public ListView<String> najblizsze_sprzatania;
    @FXML public Button odloz;
    @FXML public Button edycja;

    //private Kategoria wybranaKategoria;
    //private Czynnosc wybranaCzynnosc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //kategoria.setItems(model.pobierzModelSprzatanie().pobierzModelListaKategorii().pobierzNazwyKategorii());
//        wybranaKategoria = null;
//        wybranaCzynnosc = null;
        //odswiezNajblizszeSprzatania();
        data_wykonania.setValue(LocalDate.now());
    }


    //---------------------------------------------------------------------//
    //------------------------AKCJE FORMATKI-------------------------------//
    //---------------------------------------------------------------------//

    public void akcja_kategoria(ActionEvent actionEvent) {
//        String wartoscZCombo = kategoria.getValue();
//        wybranaKategoria = model.pobierzModelSprzatanie().pobierzModelListaKategorii().pobierzKategorie(wartoscZCombo);
//        if(wybranaKategoria != null) {
//            czynnosc.setItems(wybranaKategoria.pobierzCzynnosciPrezentacja());
//        }
//        wyczyscPolaCzynnosci();
    }

    public void akcja_czynnosc(ActionEvent actionEvent) {
//        wybranaCzynnosc = wybranaKategoria.pobierzCzynnosc(czynnosc.getValue());
//        if(wybranaCzynnosc != null){
//            ustawDatyCzynnosci();
//            aktualna_czynnosc.setText(wybranaCzynnosc.pobierzNazweCzynnosci());
//        }else{
//            wyczyscPolaCzynnosci();
//        }
    }

    public void akcja_wykonano(ActionEvent actionEvent) {
//        if (wykonanieCzynnosciWalidacja() && op.PokazOknoPotwierdzenia("Wykonywanie czynności", "Wykonano czynność " + wybranaCzynnosc.pobierzNazweCzynnosci().toLowerCase() + "?")) {
//            LocalDate noweOstatnieSprzatanie = data_wykonania.getValue();
//            LocalDate noweNastepneSprzatanie = noweOstatnieSprzatanie.plusDays(wybranaCzynnosc.pobierzLiczbeDniCzestotliwosci());
//
//            //zapisanie stanu przed próbą zapisu na baze
//            LocalDate tmpOstatnieSprzatanie = wybranaCzynnosc.pobierzDateOstatniegoSprzatania();
//            LocalDate tmpNastepneSprzatanie = wybranaCzynnosc.pobierzDateNastepnegoSprzatania();
//
//            //zmiana na modelu
//            wybranaCzynnosc.ustawDateNastepnegoSprzatania(noweNastepneSprzatanie);
//            wybranaCzynnosc.ustawDateOstatniegoSprzatania(noweOstatnieSprzatanie);
//
//            if (model.pobierzModelSprzatanie().pobierzModelListaKategorii().wykonajCzynnosc(wybranaCzynnosc.pobierzNazweCzynnosci(), konwerujLocalDateNaSqlDate(data_wykonania.getValue()))) {
//                ustawDatyCzynnosci();
//                log.zapiszWykonanieCzynnosci(wybranaCzynnosc.pobierzNazweCzynnosci());
//                zapiszLogNaBazie("AKCJA", "Wykonanie czynnosci: " + wybranaCzynnosc.pobierzNazweCzynnosci());
//            }else{
//                //przywrócenie danych po nieudanym zapisie na bazie
//                wybranaCzynnosc.ustawDateOstatniegoSprzatania(tmpNastepneSprzatanie);
//                wybranaCzynnosc.ustawDateNastepnegoSprzatania(tmpOstatnieSprzatanie);
//            }
//        }
//        odswiezNajblizszeSprzatania();
//        data_wykonania.setValue(null);
    }

    public void akcja_powrot(ActionEvent actionEvent) {
        zarzadcaFormatek.wyswietlNowaFormatke(EnumOknoAplikacji.GLOWNE, zwrocSceneFormatki());

    }

    public void akcja_najblizsze_sprzatania(MouseEvent mouseEvent) {
//        String nazwaCzynnosci = najblizsze_sprzatania.getSelectionModel().getSelectedItem();
//
//        //obcinanie nazwy tabelarycznej
//        int dlugoscNazwy = nazwaCzynnosci.length();
//        nazwaCzynnosci = nazwaCzynnosci.substring(10,dlugoscNazwy);
//        nazwaCzynnosci = nazwaCzynnosci.trim();
//
//        wybranaKategoria = model.pobierzModelSprzatanie().pobierzModelListaKategorii().pobierzKategoriePoCzynnosci(nazwaCzynnosci);
//        if(wybranaKategoria == null){
//            op.PokazOknoInformacji("", "Nie znaleziono kategorii odpowiadającej czynności " + nazwaCzynnosci);
//            return;
//        }
//        wybranaCzynnosc = model.pobierzModelSprzatanie().pobierzModelListaKategorii().pobierzKategorie(wybranaKategoria.pobierzNazweKategorii()).pobierzCzynnosc(nazwaCzynnosci);
//        if(wybranaCzynnosc == null){
//            op.PokazOknoInformacji("", "Nie znaleziono czynności " + nazwaCzynnosci);
//            return;
//        }
//        ustawDatyCzynnosci();
//        aktualna_czynnosc.setText(wybranaCzynnosc.pobierzNazweCzynnosci());
    }

    public void akcja_odloz(ActionEvent actionEvent) {
//        if (wybranaCzynnosc != null && op.PokazOknoPotwierdzenia("Odkładanie czynności", "Odłożyć czynność " + wybranaCzynnosc.pobierzNazweCzynnosci().toLowerCase() + "?")) {
//            LocalDate stareNastepneSprzatanie = konwertujStringNaLocalDate(data_nastepnego_sprzatania.getText());
//            LocalDate noweNastepneSprzatanie = stareNastepneSprzatanie.plusDays(7);
//
//            //zapisanie stanu przed próbą zapisu na baze
//            LocalDate tmpNastepneSprzatanie = wybranaCzynnosc.pobierzDateNastepnegoSprzatania();
//
//            //zmiana na modelu
//            wybranaCzynnosc.ustawDateNastepnegoSprzatania(noweNastepneSprzatanie);
//
//            if(model.pobierzModelSprzatanie().pobierzModelListaKategorii().odlozCzynnosc(wybranaCzynnosc.pobierzNazweCzynnosci())){
//                ustawDatyCzynnosci();
//                log.zapiszOdlozenieCzynnosci(wybranaCzynnosc.pobierzNazweCzynnosci());
//                zapiszLogNaBazie("AKCJA", "Odlozenie czynnosci: " + wybranaCzynnosc.pobierzNazweCzynnosci());
//            }else{
//                //przywrócenie danych po nieudanym zapisie na bazie
//                wybranaCzynnosc.ustawDateOstatniegoSprzatania(tmpNastepneSprzatanie);
//            }
//        }else if(wybranaCzynnosc==null){
//            op.PokazOknoInformacji("Błąd walidacji", "Nie wybrano czynności.");
//        }
//        odswiezNajblizszeSprzatania();
    }


    public void akcja_edycja(ActionEvent actionEvent) {
        zarzadcaFormatek.wyswietlNowaFormatke(EnumOknoAplikacji.SPRZATANIEEDYCJA, zwrocSceneFormatki());
    }

    //---------------------------------------------------------------------//
    //--------------------KONIEC AKCJI FORMATKI----------------------------//
    //---------------------------------------------------------------------//



    //-----------------------metody pomocnicze-----------------------------//

    private void wyczyscPolaCzynnosci(){
        aktualna_czynnosc.clear();
        data_nastepnego_sprzatania.clear();
        data_ostatniego_sprzatania.clear();
    }

    private void odswiezNajblizszeSprzatania(){
//        model.pobierzModelSprzatanie().pobierzModelNajblizszeSprzatania().odswiezWarstwePrezentacji();
//        najblizsze_sprzatania.setItems(model.pobierzModelSprzatanie().pobierzModelNajblizszeSprzatania().pobierzNajblizszeCzynnosciPrezentacja());
    }

    private void ustawDatyCzynnosci(){
//        data_nastepnego_sprzatania.setText(wybranaCzynnosc.pobierzDateNastepnegoSprzatania().toString());
//        data_ostatniego_sprzatania.setText(wybranaCzynnosc.pobierzDateOstatniegoSprzatania().toString());
    }

    private Date konwerujLocalDateNaSqlDate(LocalDate data) {
        return Date.valueOf(data);
    }

    private boolean wykonanieCzynnosciWalidacja(){
//        String tytulBledu = "Błąd walidacji";
//        if(wybranaCzynnosc == null){
//            op.PokazOknoInformacji(tytulBledu, "Nie wybrano czynności.");
//            return false;
//        }
//        if(data_wykonania.getValue()==null){
//            op.PokazOknoInformacji(tytulBledu, "Nie wybrano daty wykonania.");
//            return false;
//        }
        return true;
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)edycja.getScene().getWindow();
    }
}
