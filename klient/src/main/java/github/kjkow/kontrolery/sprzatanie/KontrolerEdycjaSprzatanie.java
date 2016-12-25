package github.kjkow.kontrolery.sprzatanie;


import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.implementacja.sprzatanie.SprzatanieDAO;
import github.kjkow.implementacja.sprzatanie.SprzatanieDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class KontrolerEdycjaSprzatanie extends BazowyKontroler implements Initializable {

    public ListView lista_czynnosci;
    private ObservableList<String> listaCzynnosciPrezentacja = FXCollections.observableArrayList();

    private SprzatanieDAO sprzatanieDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zaladujListeCzynnosci();
    }



    /**
     * Button Powrot
     * @param actionEvent
     */
    public void akcja_powrot(ActionEvent actionEvent) {
        otworzNowaFormatke(new KontrolerSprzatanieEkranGlowny());
    }

    /**
     * Button Dodaj czynnosc
     * @param actionEvent
     */
    /*public void akcja_dodaj_czynnosc(ActionEvent actionEvent) {
        Czynnosc nowaCzynnosc = new Czynnosc();

        if(nazwa_czynnosci.getText().compareTo("") != 0){
            nowaCzynnosc.setNazwaCzynnosci(nazwa_czynnosci.getText());
        }else{
            zarzadcaFormatek.wyswietlOknoInformacji("Nie wprowadzono nazwy czynnosci.");
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
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
        }

        przeladujFormatke();
    }*/

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


    private void zaladujListeCzynnosci(){
        inicjujDAO();
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
        lista_czynnosci.setItems(listaCzynnosciPrezentacja);
    }

    private void inicjujDAO(){
        try {
            sprzatanieDAO = new SprzatanieDAOImpl();
        } catch (IOException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONSTRUKTORA_DAO, e);
        }
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)lista_czynnosci.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/SprzatanieEdycja.fxml");
    }

    public void akcjaModyfikacja(ActionEvent actionEvent) {
        //TODO: implementacja
    }

    public void akcjaDodaj(ActionEvent actionEvent) {
        //TODO: implementacja
    }
}
