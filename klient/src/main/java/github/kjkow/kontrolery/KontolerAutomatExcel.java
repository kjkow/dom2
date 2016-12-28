package github.kjkow.kontrolery;


import github.kjkow.automaty.excel.AutomatDoExcela;
import github.kjkow.automaty.excel.IAutomatDoExcela;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.kontekst.KontekstZwracany;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-12.
 */
public class KontolerAutomatExcel extends BazowyKontroler implements Initializable{

    public Label sciezka;
    public TextArea log;

    private IAutomatDoExcela automat;
    private KontekstZwracany pKontekst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        automat = new AutomatDoExcela();
    }

    public void akcjaPowrot(ActionEvent actionEvent) {
        otworzNowaFormatke(new KontrolerEkranGlowny());
    }

    public void zmienSciezke(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File wybranyPlik = fileChooser.showOpenDialog(null);

        if(wybranyPlik != null){
            sciezka.setText(wybranyPlik.getAbsolutePath());
        }
    }

    public void utworzNowyArkusz(ActionEvent actionEvent) {
        if(!czyPustaSciezka()) {
            pKontekst = automat.utworzArkuszNaNowyRok(sciezka.getText());
            log.setText(pKontekst.getLog());
        }
    }


    public void migrujZakresy(ActionEvent actionEvent) {
        if(!czyPustaSciezka()){
            pKontekst = automat.migrujZakresy(sciezka.getText());
            log.setText(pKontekst.getLog());
        }
    }

    private boolean czyPustaSciezka(){
        return sciezka.getText().compareTo("") == 0;
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)log.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/AutomatExcel.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }
}
