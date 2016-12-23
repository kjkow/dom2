package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.implementacja.sprzatanie.SprzatanieDAO;
import github.kjkow.implementacja.sprzatanie.SprzatanieDAOImpl;
import github.kjkow.bazowe.formatka.EnumOknoAplikacji;
import github.kjkow.sprzatanie.Czynnosc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class KontrolerEdycjaSprzatanie extends BazowyKontroler implements Initializable {

    @FXML public ComboBox<String> czynnosc;
    @FXML public Button powrot;
    @FXML public Button dodaj_czynnosc;
    @FXML public TextField nazwa_czynnosci;
    @FXML public Button usun_czynnosc;
    @FXML public Button zmien_nazwe_czynnosci;
    @FXML public TextField nazwa_czynnosci_do_zmiany;
    @FXML public TextField czestotliwosc;
    @FXML public Button zmien_czestotliwosc;
    @FXML public TextField czestotliwosc_nowa;

    private SprzatanieDAO sprzatanieDAO;

    private final ObservableList<String> czynnosciPrezentacja = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        przeladujComboCzynnosci();
        ustawEdytowalnosci();
    }

    /**
     * Combobox Czynnosci
     * @param actionEvent
     */
    public void akcja_czynnosc(ActionEvent actionEvent) {
        /* edycja czynnosci
        ----po wejsciu na formatke
        * przycisk usun disable
        * nazwa czynnosci uneditable
        * nazwa czynnosci tekst prompt wybierz czynność
        * zmien nazwe disable
        * czestotliwosc nieedytowalne
        * zmien czestotliwosc disable
        *
        * ----po wybraniu czynnosci
        * usun enable
        * nazwa editable
        * nazwa prompt text - wpisz nazwe do zmiany
        * zmien nazwe enable
        * czestotliwosc wypelnic obecna wartoscia
        * czestotliwosc editable
        * zmien czestotliwosc enable
        * */
//        usun_czynnosc.setDisable(false);
//        nazwa_czynnosci_do_zmiany.setEditable(true);
//        nazwa_czynnosci_do_zmiany.setPromptText("Wpisz nową nazwę czynności");
//        zmien_nazwe_czynnosci.setDisable(false);
//        czestotliwosc.setEditable(true);
//        zmien_czestotliwosc.setDisable(false);
//        czestotliwosc.setText(String.valueOf(model.pobierzModelSprzatanie().pobierzModelListaKategorii().pobierzCzynnosc(czynnosc.getValue()).pobierzLiczbeDniCzestotliwosci()));
    }

    /**
     * Button Powrot
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        zarzadcaFormatek.wyswietlNowaFormatke(EnumOknoAplikacji.SPRZATANIE, zwrocSceneFormatki());
    }

    /**
     * Button Dodaj czynnosc
     * @param actionEvent
     */
    public void akcja_dodaj_czynnosc(ActionEvent actionEvent) {
        Czynnosc nowaCzynnosc = new Czynnosc();

        if(nazwa_czynnosci.getText().compareTo("") != 0){
            nowaCzynnosc.setNazwaCzynnosci(nazwa_czynnosci.getText());
        }else{
            powiadomienia.wyswietlOknoInformacji("Nie wprowadzono nazwy czynnosci.");
            return;
        }

        try{
            nowaCzynnosc.setDniCzestotliwosci(Integer.parseInt(czestotliwosc_nowa.getText()));
        }catch (NumberFormatException e){
            obsluzBlad("Niepoprawnie wprowadzona liczba", e);
            return;
        }

        inicjujDAO();
        if(sprzatanieDAO == null){
            return;
        }

        try {
            sprzatanieDAO.dodajCzynnosc(nowaCzynnosc);
        } catch (SQLException e) {
            obsluzBlad("Problem na bazie danych", e);
        } catch (ClassNotFoundException e) {
            obsluzBlad("Problem z konektorem bazy danych", e);
        }

        przeladujFormatke();
    }

    /**
     * Button Usun czynnosc
     * @param actionEvent
     */
    public void akcja_usun_czynnosc(ActionEvent actionEvent) {
//        String nazwaCzynnosci = czynnosc.getValue();
//
//        if(!op.PokazOknoPotwierdzenia("", "Czy na pewno usunąć czynność " + nazwaCzynnosci.toLowerCase() + "?")) return;
//        if(!model.pobierzModelSprzatanie().pobierzModelListaKategorii().usunCzynnosc(nazwaCzynnosci)) return;
//        op.PokazOknoInformacji("", "Pomyślnie usunięto " + nazwaCzynnosci.toLowerCase() + ".");
//
//        opis = "Usunięto czynność: " + nazwaCzynnosci;
//        log.zapiszModyfikacjeSprzatania(opis);
//        zapiszLogNaBazie(rodzajZapisuLog, opis);
//        przeladujFormatke();
    }

    /**
     * Button Zmien nazwe czynnosci
     * @param actionEvent
     */
    public void akcja_zmien_nazwe_czynnosci(ActionEvent actionEvent) {
//        if(nazwa_czynnosci_do_zmiany.getText().compareTo("") == 0){
//            op.PokazOknoInformacji(tytulBleduWalidacji, "Brak nazwy czynności do zmiany.");
//            return;
//        }
//
//        String nowaNazwa = nazwa_czynnosci_do_zmiany.getText();
//        String staraNazwa = czynnosc.getValue();
//
//        if(!model.pobierzModelSprzatanie().pobierzModelListaKategorii().zmienNazweCzynnosci(staraNazwa, nowaNazwa)) return; // dodaj na bazę
//
//        Czynnosc czynnosc = model.pobierzModelSprzatanie().pobierzModelListaKategorii().pobierzCzynnosc(staraNazwa); // dodaj do modelu
//        czynnosc.ustawNazwe(nowaNazwa);
//        op.PokazOknoInformacji("", "Pomyślnie zmieniono nazwę czynności.");
//
//        opis = "Zmieniono nazwę czynności. Stara nazwa: " + staraNazwa + ", nowa nazwa: " + nowaNazwa;
//        log.zapiszModyfikacjeSprzatania(opis);
//        zapiszLogNaBazie(rodzajZapisuLog, opis);
//        przeladujFormatke();
    }

    /**
     * Button Zmien czestotliwosc czynnosci
     * @param actionEvent
     */
    public void akcja_zmien_czestotliwosc(ActionEvent actionEvent) {
//        String dni = czestotliwosc.getText();
//        long dniLong = Long.valueOf(dni);
//        Czynnosc czynnosc = model.pobierzModelSprzatanie().pobierzModelListaKategorii().pobierzCzynnosc(this.czynnosc.getValue());
//        long staraCzestotliwosc = czynnosc.pobierzLiczbeDniCzestotliwosci();
//        if(dniLong == staraCzestotliwosc){
//            op.PokazOknoInformacji(tytulBleduWalidacji, "Liczba dni nie została zmieniona!");
//            return;
//        }
//        if(!model.pobierzModelSprzatanie().pobierzModelListaKategorii().zmienCzestotliwosc(czynnosc.pobierzNazweCzynnosci(), (int) dniLong)) return; //zmiana na bazie
//
//        czynnosc.ustawczestotliwosc((int) dniLong); // zmiana na modelu
//        op.PokazOknoInformacji("", "Pomyślnie zmieniono częstotliwość.");
//
//        opis = "Zmieniono częstotliwość czynności " + czynnosc.pobierzNazweCzynnosci() + " z " + staraCzestotliwosc + " na " + dni;
//        log.zapiszModyfikacjeSprzatania(opis);
//        zapiszLogNaBazie(rodzajZapisuLog, opis);
//        przeladujFormatke();
    }

    private void ustawEdytowalnosci(){
        //dodaj nową czynność
        nazwa_czynnosci.setPromptText("Wpisz nazwę nowej czynności");

        //edycja czynnosci
        usun_czynnosc.setDisable(true);
        nazwa_czynnosci_do_zmiany.setEditable(false);
        nazwa_czynnosci_do_zmiany.setPromptText("Wybierz czynność");
        zmien_nazwe_czynnosci.setDisable(true);
        czestotliwosc.setEditable(false);
        zmien_czestotliwosc.setDisable(true);
    }

    private void przeladujFormatke(){
        zarzadcaFormatek.wyswietlNowaFormatke(EnumOknoAplikacji.SPRZATANIEEDYCJA, zwrocSceneFormatki());
    }

    private void przeladujComboCzynnosci(){
        inicjujDAO();
        if(sprzatanieDAO == null){
            return;
        }
        try {
            for(String nazwaCzynnosci: sprzatanieDAO.pobierzNazwyCzynnosci()){
                czynnosciPrezentacja.add(nazwaCzynnosci);
            }
        } catch (SQLException e) {
            obsluzBlad("Problem na bazie danych", e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad("Problem z konektorem bazy danych", e);
            return;
        }
        czynnosc.setItems(czynnosciPrezentacja);
        czynnosc.getSelectionModel().clearSelection();
    }

    private void inicjujDAO(){
        try {
            sprzatanieDAO = new SprzatanieDAOImpl();
        } catch (IOException e) {
            obsluzBlad("Problem z wczytywaniem danych z pliku", e);
        }
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)zmien_nazwe_czynnosci.getScene().getWindow();
    }
}
