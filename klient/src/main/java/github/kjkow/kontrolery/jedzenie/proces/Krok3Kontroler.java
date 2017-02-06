package github.kjkow.kontrolery.jedzenie.proces;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.bazowe.ZarzadcaZwenetrznychPlikow;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Utworzył Kamil Kowalczyk dnia 2017-01-05.
 */
public class Krok3Kontroler extends BazowyKontroler implements Initializable{

    public TextArea skladniki;
    public Label sciezka;

    private String sciezkaDoExcela;
    private final String OPIS_SCIEZKI = "Ścieżka do excela z produktami:";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            skladniki.setText((String) PrzechowywaczDanych.pobierzObiekt());
            pobierzSciezkeZBazy();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void pobierzSciezkeZBazy(){
        inicjujJedzenieDAO();
        if(jedzenieDAO == null) return;
        try {
            sciezkaDoExcela = jedzenieDAO.pobierzSciezkeDoExcelaZProduktami();
            sciezka.setText(OPIS_SCIEZKI + " " + sciezkaDoExcela);
        } catch (SQLException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
        } catch (ClassNotFoundException e) {
            obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
        }
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)skladniki.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        //zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/jedzenie/proces/Krok3.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }

    /**
     * Button dalej
     * @param actionEvent
     */
    public void dalej(ActionEvent actionEvent) {
        try {
            przejdzDoZakonczeniaProcesu();
            System.exit(0);
        } catch (MalformedURLException e) {
            obsluzBlad("Nie udało się otworzyć excela z produktami.", e);
        }
    }

    private void przejdzDoZakonczeniaProcesu() throws MalformedURLException {
        ZarzadcaZwenetrznychPlikow zarzadcaZwenetrznychPlikow = new ZarzadcaZwenetrznychPlikow();
        zarzadcaZwenetrznychPlikow.otworzZewnetrzne(sciezkaDoExcela);
    }

    /**
     * Button zakoncz
     * @param actionEvent
     */
    public void zakoncz(ActionEvent actionEvent) {
        //todo:zamkniecie formatki
    }

    /**
     * Button zmien sciezke
     * @param actionEvent
     */
    public void zmienSciezke(ActionEvent actionEvent) {
        try{
            zmienSciezkeDoExcela();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void zmienSciezkeDoExcela(){
        sciezkaDoExcela = "";
        //TODO: przeniesc do bazowki - wybieranie pliku i inne sciezki dac tez na baze, moze osobne dao na to
        FileChooser fileChooser = new FileChooser();
        File wybranyPlik = fileChooser.showOpenDialog(null);

        if(wybranyPlik != null){
            inicjujJedzenieDAO();
            if(jedzenieDAO == null) return;
            try {
                jedzenieDAO.zapiszSciezkeDoExcelaZProduktami(wybranyPlik.getAbsolutePath());
            } catch (SQLException e) {
                obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
                return;
            } catch (ClassNotFoundException e) {
                obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
                return;
            }
            sciezka.setText(OPIS_SCIEZKI + " " + wybranyPlik.getAbsolutePath());
            sciezkaDoExcela = wybranyPlik.getAbsolutePath();
        }
    }

}
