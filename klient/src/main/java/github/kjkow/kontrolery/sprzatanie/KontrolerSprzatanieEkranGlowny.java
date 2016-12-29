package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.Czynnosc;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.kontrolery.KontrolerEkranGlowny;
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

import java.io.IOException;
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

    /**
     * Combobox czynnosc
     * klikniecie w element z listy combo
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

    /**
     * Button wykonano
     * @param actionEvent
     */
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
            wybranaCzynnosc = sprzatanieDAO.pobierzDaneCzynnosci(wybranaCzynnosc.getNazwaCzynnosci()); //przepisanie do zmiennej dla ustawienia dat czynnosci
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        try {
            dziennik.zapiszInformacje("Wykonano czynność " + wybranaCzynnosc.getNazwaCzynnosci());
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoInformacji(KOMUNIKAT_AMBIWALENCJI_DZIENNIKA + "\n" +
                    KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            ustawDateWykonaniaNaDzis();
            zaladujListeNajblizszychSprzatan();
            ustawDatyCzynnosci();
            return;
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zapisano wykonanie czynności.");
        ustawDateWykonaniaNaDzis();
        zaladujListeNajblizszychSprzatan();
        ustawDatyCzynnosci();
    }

    /**
     * Button powrot
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    /**
     * ListView najblizsze sprzatania
     * klikniecie na element z listy
     * @param mouseEvent
     */
    public void akcja_najblizsze_sprzatania(MouseEvent mouseEvent) {
        String nazwaCzynnosci = najblizsze_sprzatania.getSelectionModel().getSelectedItem();

        inicjujSprzatanieDAO();

        if(sprzatanieDAO == null){
            return;
        }

        if(nazwaCzynnosci == null){
            zarzadcaFormatek.wyswietlOknoInformacji("Nie zidentyfikowano czynności.");
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

    /**
     * Button odloz
     * @param actionEvent
     */
    public void akcja_odloz(ActionEvent actionEvent) {

        if(wybranaCzynnosc == null){
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wybrano czynności.");
            return;
        }

        inicjujSprzatanieDAO();
        if(sprzatanieDAO == null) return;

        try {
            sprzatanieDAO.odlozCzynnosc(wybranaCzynnosc.getNazwaCzynnosci());
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        try {
            dziennik.zapiszInformacje("Odłożono czynność " + wybranaCzynnosc.getNazwaCzynnosci());
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoInformacji(KOMUNIKAT_AMBIWALENCJI_DZIENNIKA + "\n" +
                    KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            zaladujListeNajblizszychSprzatan();
            return;
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zapisano odłożenie czynności.");
        zaladujListeNajblizszychSprzatan();
    }

    /**
     * Button edytuj
     * @param actionEvent
     */
    public void akcja_edycja(ActionEvent actionEvent) {
        otworzNowaFormatke(new KontrolerEdycjaSprzatanie());
    }

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

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }

    @Override
    protected void wrocDoPoprzedniejFormatki(){
        otworzNowaFormatke(new KontrolerEkranGlowny());
    }
}
