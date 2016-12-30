package github.kjkow.kontrolery.jedzenie.proces;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.kontrolery.jedzenie.DodajPrzepisKontroler;
import github.kjkow.kontrolery.jedzenie.JedzenieGlownyKontroler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-29.
 */
public class Krok1Kontroler extends BazowyKontroler implements Initializable {


    public Label pon;
    public Label wt;
    public Label sr;
    public Label czw;
    public Label pt;
    public Label sb;
    public Label nd;
    public ComboBox<String> poniedzialek;
    public ComboBox<String> wtorek;
    public ComboBox<String> sroda;
    public ComboBox<String> czwartek;
    public ComboBox<String> piatek;
    public ComboBox<String> sobota;
    public ComboBox<String> niedziela;
    public Button dalej;

    private int pierwszyDzien;
    private int obecnyDzien;
    private int ostatniDzien;

    private Label[] tablicaDni;
    private ComboBox<String>[] nazwy;
    private ObservableList<String> przepisyPrezentacja = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ustawDaty();
            inicjujTabliceCombo();
            ustawPolaCombo();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    public void akcja_dalej(ActionEvent actionEvent) {
        //TODO: walidacja
        //TODO: krok 2
    }

    public void akcja_zakoncz(ActionEvent actionEvent) {
        wrocDoPoprzedniejFormatki();
    }

    public void akcja_dodaj(ActionEvent actionEvent) {
        otworzNowaFormatke(new DodajPrzepisKontroler());
    }

    private void ustawDaty(){
        Calendar calendar = Calendar.getInstance();
        pierwszyDzien = calendar.getFirstDayOfWeek();
        obecnyDzien = calendar.get(Calendar.DAY_OF_WEEK);
        ostatniDzien = pierwszyDzien + 6;

        tablicaDni = new Label[ostatniDzien + 1];

        tablicaDni[pierwszyDzien] = pon;
        tablicaDni[pierwszyDzien + 1] = wt;
        tablicaDni[pierwszyDzien + 2] = sr;
        tablicaDni[pierwszyDzien + 3] = czw;
        tablicaDni[pierwszyDzien + 4] = pt;
        tablicaDni[pierwszyDzien + 5] = sb;
        tablicaDni[pierwszyDzien + 6] = nd;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sformatowanaData = simpleDateFormat.format(calendar.getTime());

        if(obecnyDzien == 1){ //kiedy jest niedziela, Calendar.DAY_OF_WEEK == 1
            obecnyDzien = ostatniDzien;
        }
        tablicaDni[obecnyDzien].setText(sformatowanaData); //obecny dzień

        //jeśli obecny dzień to nie niedziela lub poniedziałek
        if(obecnyDzien != ostatniDzien || obecnyDzien != pierwszyDzien){
            for(int i = obecnyDzien + 1; i <= ostatniDzien; i++){
                calendar.add(Calendar.DATE, 1);
                tablicaDni[i].setText(simpleDateFormat.format(calendar.getTime()));//dni tygodnia do końca
            }

            calendar = Calendar.getInstance();

            for(int j = obecnyDzien - 1; j >= pierwszyDzien; j--){
                calendar.add(Calendar.DATE, -1);
                tablicaDni[j].setText(simpleDateFormat.format(calendar.getTime()));//dni tygodnia do początku
            }
        }

        //jeśli obecny dzień to niedziela
        if(obecnyDzien == ostatniDzien){
            calendar = Calendar.getInstance();
            for(int k = obecnyDzien - 1; k >= pierwszyDzien; k--){
                calendar.add(Calendar.DATE, -1);
                tablicaDni[k].setText(simpleDateFormat.format(calendar.getTime()));//dni tygodnia do początku
            }
        }

        //jeśli obecny dzień to poniedziałek
        if(obecnyDzien == pierwszyDzien){
            calendar = Calendar.getInstance();
            for(int l = obecnyDzien + 1; l <= ostatniDzien; l++){
                calendar.add(Calendar.DATE, 1);
                tablicaDni[l].setText(simpleDateFormat.format(calendar.getTime()));//dni tygodnia do końca
            }
        }

    }

    private void ustawPolaCombo(){

        inicjujJedzenieDAO();
        if(jedzenieDAO == null) return;

        try {
            for(String nazwaPrzepisu: jedzenieDAO.pobierzListePrzepisow()){
                przepisyPrezentacja.add(nazwaPrzepisu);
            }
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        for(ComboBox<String> c: nazwy){
            c.setItems(przepisyPrezentacja);
        }
    }

    private void inicjujTabliceCombo(){
        nazwy = new ComboBox[7];
        nazwy[0] = poniedzialek;
        nazwy[1] = wtorek;
        nazwy[2] = sroda;
        nazwy[3] = czwartek;
        nazwy[4] = piatek;
        nazwy[5] = sobota;
        nazwy[6] = niedziela;
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)pon.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/proces/Krok1.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }

    @Override
    protected void wrocDoPoprzedniejFormatki(){
        otworzNowaFormatke(new JedzenieGlownyKontroler());
    }
}
