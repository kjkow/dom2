package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.Czynnosc;
import github.kjkow.bazowe.BazowyKontroler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class SprzatanieEkranGlownyKontroler extends BazowyKontroler implements Initializable {


    @FXML public DatePicker data_nastepnego_sprzatania;
    @FXML public DatePicker data_ostatniego_sprzatania;
    @FXML public DatePicker data_wykonania;
    @FXML public ListView<String> najblizsze_sprzatania;

    private ObservableList<String> najblizszeSprzatania = FXCollections.observableArrayList();
    private Czynnosc wybranaCzynnosc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            zaladujListeNajblizszychSprzatan();
            ustawDateWykonaniaNaDzis();
            bindujPola();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }

    }

    private void bindujPola(){
        if(wybranaCzynnosc != null) {
            data_nastepnego_sprzatania.valueProperty().bindBidirectional(wybranaCzynnosc.dataNastepnegoSprzataniaProperty());
            data_ostatniego_sprzatania.valueProperty().bindBidirectional(wybranaCzynnosc.dataOstatniegoSprzataniaProperty());
        }
    }

    /**
     * Button wykonano
     * @param actionEvent
     */
    public void akcja_wykonano(ActionEvent actionEvent) {
        try{
            kontekstZwracanySprzatanieDAO = sprzatanieDAO.wykonajCzynnosc(wybranaCzynnosc.getNazwaCzynnosci(), Date.valueOf(data_wykonania.getValue()));
            if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
                obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
                return;
            }
            zapiszWykonanieWDzienniku("Wykonano czynność " + wybranaCzynnosc.getNazwaCzynnosci());
            zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zapisano wykonanie czynności." + "(" + wybranaCzynnosc.getNazwaCzynnosci() + ")");
            zaladujListeNajblizszychSprzatan();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    /**
     * ListView najblizsze sprzatania
     * klikniecie na element z listy
     * @param mouseEvent
     */
    public void akcja_najblizsze_sprzatania(MouseEvent mouseEvent) {
        try{
            kontekstZwracanySprzatanieDAO = sprzatanieDAO.pobierzDaneCzynnosci(
                    najblizsze_sprzatania.getSelectionModel().getSelectedItem().
                            substring(10, najblizsze_sprzatania.getSelectionModel().getSelectedItem().length()).trim());//nazwa z tabeli na nazwę czynności

            if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
                obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
                wybranaCzynnosc = new Czynnosc();
                return;
            }

            wybranaCzynnosc = kontekstZwracanySprzatanieDAO.getCzynnosc();
            bindujPola();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    /**
     * Button odloz
     * @param actionEvent
     */
    public void akcja_odloz(ActionEvent actionEvent) {
        try{
            kontekstZwracanySprzatanieDAO = sprzatanieDAO.odlozCzynnosc(wybranaCzynnosc.getNazwaCzynnosci());
            if(!kontekstZwracanySprzatanieDAO.isCzyBrakBledow()){
                obsluzBlad(kontekstZwracanySprzatanieDAO.getLog(), kontekstZwracanySprzatanieDAO.getBlad());
                return;
            }
            zapiszWykonanieWDzienniku("Odłożono czynność " + wybranaCzynnosc.getNazwaCzynnosci());
            zarzadcaFormatek.wyswietlOknoInformacji("Pomyślnie zapisano odłożenie czynności." + "(" + wybranaCzynnosc.getNazwaCzynnosci() + ")");
            zaladujListeNajblizszychSprzatan();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }

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

    private void ustawDateWykonaniaNaDzis(){
        data_wykonania.setValue(LocalDate.now());
    }
}
