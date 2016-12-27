package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.kontrolery.KontrolerEkranGlowny;
import github.kjkow.sprzatanie.Czynnosc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class KontrolerSprzatanieEkranGlowny extends BazowyKontroler implements Initializable {


    @FXML public ComboBox<String> czynnosc;
    @FXML public TextField data_nastepnego_sprzatania;
    @FXML public TextField data_ostatniego_sprzatania;
    @FXML public DatePicker data_wykonania;
    @FXML public ListView<String> najblizsze_sprzatania;

    private ObservableList<String> najblizszeSprzatania = FXCollections.observableArrayList();
    private ObservableList<String> listaCzynnosciPrezentacja = FXCollections.observableArrayList();
    private Czynnosc wybranaCzynnosc; //wybrana może być albo z combosa albo z najbliższych sprzątań

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zaladujListeNajblizszychSprzatan();
        ustawDateWykonaniaNaDzis();
        zaladujComboCzynnosci();
    }

    //---------------------------------------------------------------------//
    //------------------------AKCJE FORMATKI-------------------------------//
    //---------------------------------------------------------------------//

    /**
     * Combobox
     * @param actionEvent
     */
    public void akcja_czynnosc(ActionEvent actionEvent) {
        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        try {
            wybranaCzynnosc = sprzatanieDAO.pobierzDaneCzynnosci(czynnosc.getValue());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }
        ustawDatyCzynnosci();
    }

    public void akcja_wykonano(ActionEvent actionEvent) {
        if(data_wykonania.getValue() == null){
            zarzadcaFormatek.wyswietlOknoInformacji("Pole data wykonania nie może być puste.");
            return;
        }
        if(wybranaCzynnosc.getNazwaCzynnosci() == null || wybranaCzynnosc.getNazwaCzynnosci().compareTo("") == 0){
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wybrano czynności do wykonania.");
            return;
        }

        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        try {
            sprzatanieDAO.wykonajCzynnosc(wybranaCzynnosc.getNazwaCzynnosci(), konwerujLocalDateNaSqlDate(data_wykonania.getValue()));
            dziennik.zapiszInformacje("Wykonano czynność " + wybranaCzynnosc.getNazwaCzynnosci());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zapisano wykonanie czynności.");
        ustawDateWykonaniaNaDzis();
        zaladujListeNajblizszychSprzatan();

        //przepisanie do zmiennej dla ustawienia dat czynnosci
        try {
            wybranaCzynnosc = sprzatanieDAO.pobierzDaneCzynnosci(wybranaCzynnosc.getNazwaCzynnosci());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
        }

        ustawDatyCzynnosci();
    }

    public void akcja_powrot(ActionEvent actionEvent) {
       zarzadcaFormatek.wyswietlNowaFormatke(new KontrolerEkranGlowny(), zwrocSceneFormatki());

    }

    /**
     * ListView
     * @param mouseEvent
     */
    public void akcja_najblizsze_sprzatania(MouseEvent mouseEvent) {
        String nazwaCzynnosci = najblizsze_sprzatania.getSelectionModel().getSelectedItem();

        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null || nazwaCzynnosci == null){
            return;
        }

        try {
            wybranaCzynnosc = sprzatanieDAO.pobierzDaneCzynnosci(nazwaCzynnosci.substring(10,nazwaCzynnosci.length()).trim());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        ustawDatyCzynnosci();
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
        zarzadcaFormatek.wyswietlNowaFormatke(new KontrolerEdycjaSprzatanie(), zwrocSceneFormatki());
    }

    //---------------------------------------------------------------------//
    //--------------------KONIEC AKCJI FORMATKI----------------------------//
    //---------------------------------------------------------------------//



    //-----------------------metody pomocnicze-----------------------------//

    private void zaladujListeNajblizszychSprzatan(){
        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        try {
            for(Czynnosc czynnosc: sprzatanieDAO.pobierzNajblizszeSprzatania()){
                najblizszeSprzatania.add(czynnosc.getDataNastepnegoSprzatania().toString() + "\t" + czynnosc.getNazwaCzynnosci());
            }
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
        }
        najblizsze_sprzatania.setItems(najblizszeSprzatania);
    }

    private void ustawDatyCzynnosci(){
        data_nastepnego_sprzatania.setText(wybranaCzynnosc.getDataNastepnegoSprzatania() != null ? wybranaCzynnosc.getDataNastepnegoSprzatania().toString() : "");
        data_ostatniego_sprzatania.setText(wybranaCzynnosc.getDataOstatniegoSprzatania() != null ? wybranaCzynnosc.getDataOstatniegoSprzatania().toString() : "");
    }

    private Date konwerujLocalDateNaSqlDate(LocalDate data) {
        return Date.valueOf(data);
    }


    private void ustawDateWykonaniaNaDzis(){
        data_wykonania.setValue(LocalDate.now());
    }

    private void zaladujComboCzynnosci(){
        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        try {
            for(String nazwaCzynnosci: sprzatanieDAO.pobierzNazwyCzynnosci()){
                listaCzynnosciPrezentacja.add(nazwaCzynnosci);
            }
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }
        czynnosc.setItems(listaCzynnosciPrezentacja);
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)czynnosc.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/SprzatanieEkranGlowny.fxml");
    }
}
