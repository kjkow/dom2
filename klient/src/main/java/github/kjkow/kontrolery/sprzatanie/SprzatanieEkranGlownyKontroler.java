package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.Czynnosc;
import github.kjkow.DziennikAplikacji;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.KontekstAplikacji;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class SprzatanieEkranGlownyKontroler extends BazowyKontroler implements Initializable {


    @FXML public TextField data_nastepnego_sprzatania;
    @FXML public TextField data_ostatniego_sprzatania;
    @FXML public DatePicker data_wykonania;
    @FXML public ListView<String> najblizsze_sprzatania;

    private ObservableList<String> najblizszeSprzatania = FXCollections.observableArrayList();
    private ObservableList<String> listaCzynnosciPrezentacja = FXCollections.observableArrayList();
    private Czynnosc wybranaCzynnosc; //wybrana może być albo z combosa albo z najbliższych sprzątań

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            zaladujListeNajblizszychSprzatan();
            ustawDateWykonaniaNaDzis();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }

    }

    private void przypiszDoWybranejCzynnosci(String nazwa){
        kontekstZwracanySprzatanieDAO = sprzatanieDAO.pobierzDaneCzynnosci(nazwa);

        wybranaCzynnosc = kontekstZwracanySprzatanieDAO.getCzynnosc();
    }//todo: binding?

    /**
     * Button wykonano
     * @param actionEvent
     */
    public void akcja_wykonano(ActionEvent actionEvent) {
        try{
            wykonajCzynnosc();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void wykonajCzynnosc(){
        if(data_wykonania.getValue() == null){
            zarzadcaFormatek.wyswietlOknoInformacji("Pole data wykonania nie może być puste.");
            return;
        }
        if(wybranaCzynnosc.getNazwaCzynnosci() == null || wybranaCzynnosc.getNazwaCzynnosci().compareTo("") == 0){
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wybrano czynności do wykonania.");
            return;
        }

        kontekstZwracanySprzatanieDAO = sprzatanieDAO.wykonajCzynnosc(wybranaCzynnosc.getNazwaCzynnosci(), konwerujLocalDateNaSqlDate(data_wykonania.getValue()));
        if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
            return;
        }

        przypiszDoWybranejCzynnosci(wybranaCzynnosc.getNazwaCzynnosci());//przepisanie do zmiennej dla ustawienia dat czynnosci

        try {
            DziennikAplikacji.zapiszInformacje(KontekstAplikacji.pobierzSciezkeDziennikaAplikacji(), "Wykonano czynność " + wybranaCzynnosc.getNazwaCzynnosci());
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
     * ListView najblizsze sprzatania
     * klikniecie na element z listy
     * @param mouseEvent
     */
    public void akcja_najblizsze_sprzatania(MouseEvent mouseEvent) {
        try{
            pobierzDaneCzynnosciZListyNajblizszychSprzatan();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void pobierzDaneCzynnosciZListyNajblizszychSprzatan(){
        String nazwaCzynnosci = najblizsze_sprzatania.getSelectionModel().getSelectedItem();

        if(nazwaCzynnosci == null){
            zarzadcaFormatek.wyswietlOknoInformacji("Nie zidentyfikowano czynności.");
            return;
        }

        przypiszDoWybranejCzynnosci(nazwaCzynnosci.substring(10,nazwaCzynnosci.length()).trim());

        ustawDatyCzynnosci();
    }

    /**
     * Button odloz
     * @param actionEvent
     */
    public void akcja_odloz(ActionEvent actionEvent) {
        try{
            odlozCzynnosc();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }

    }

    private void odlozCzynnosc(){
        if(najblizsze_sprzatania.getSelectionModel().getSelectedItem() != null){
            przypiszDoWybranejCzynnosci(najblizsze_sprzatania.getSelectionModel().getSelectedItem().substring(10, najblizsze_sprzatania.getSelectionModel().getSelectedItem().length()).trim());
            if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
                obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
                return;
            }
        }else{
            zarzadcaFormatek.wyswietlOknoBledu("Nie wybrano czynności.");
            return;
        }

        if(wybranaCzynnosc == null){
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wybrano czynności.");
            return;
        }

        kontekstZwracanySprzatanieDAO = sprzatanieDAO.odlozCzynnosc(wybranaCzynnosc.getNazwaCzynnosci());
        if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
            return;
        }

        try {
            DziennikAplikacji.zapiszInformacje(KontekstAplikacji.pobierzSciezkeDziennikaAplikacji(), "Odłożono czynność " + wybranaCzynnosc.getNazwaCzynnosci());
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoInformacji(KOMUNIKAT_AMBIWALENCJI_DZIENNIKA + "\n" +
                    KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            zaladujListeNajblizszychSprzatan();
            return;
        }

        zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zapisano odłożenie czynności.");
        zaladujListeNajblizszychSprzatan();
    }

    private void zaladujListeNajblizszychSprzatan(){
        najblizszeSprzatania.clear();

        kontekstZwracanySprzatanieDAO = sprzatanieDAO.pobierzNajblizszeSprzatania();
        if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
            return;
        }

        for(Czynnosc czynnosc: kontekstZwracanySprzatanieDAO.getListaCzynnosci()){
            najblizszeSprzatania.add(czynnosc.getDataNastepnegoSprzatania().toString() + "\t" + czynnosc.getNazwaCzynnosci());
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
}
