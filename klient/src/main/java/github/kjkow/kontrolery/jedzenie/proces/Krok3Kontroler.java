package github.kjkow.kontrolery.jedzenie.proces;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.bazowe.ZarzadcaZwenetrznychPlikow;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Utworzył Kamil Kowalczyk dnia 2017-01-05.
 */
public class Krok3Kontroler extends BazowyKontroler implements Initializable{

    public TextArea skladniki;

    private String sciezkaDoExcela;

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
        kontekstZwracanyKonfiguracjaDAO = konfiguracjaDAO.pobierzSciezkeDoExcelaZProduktami();
        if(!kontekstZwracanyKonfiguracjaDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyKonfiguracjaDAO.getLog(), kontekstZwracanyKonfiguracjaDAO.getBlad());
            return;
        }
        sciezkaDoExcela = kontekstZwracanyKonfiguracjaDAO.getSciezkaKonfiguracji();
    }

    /**
     * Button dalej
     * @param actionEvent
     */
    public void dalej(ActionEvent actionEvent) {
        try {
            przejdzDoZakonczeniaProcesu();
            System.exit(0);
        } catch (Exception e) {
            obsluzBlad("Nie udało się otworzyć excela z produktami.", e);
        }
    }

    private void przejdzDoZakonczeniaProcesu() throws MalformedURLException {
        ZarzadcaZwenetrznychPlikow zarzadcaZwenetrznychPlikow = new ZarzadcaZwenetrznychPlikow();
        zarzadcaZwenetrznychPlikow.otworzZewnetrzne(sciezkaDoExcela);
    }
}
