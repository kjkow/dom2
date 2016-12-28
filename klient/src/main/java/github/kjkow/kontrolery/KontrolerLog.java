package github.kjkow.kontrolery;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.PrzechowywaczDanych;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Created by Kamil.Kowalczyk on 2016-08-19.
 */
public class KontrolerLog extends BazowyKontroler implements Initializable {

    @FXML public TextArea obszar_logu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicjujOknoLogu();
    }

    private void inicjujOknoLogu(){
        try {
            Scanner s = new Scanner(new File("dziennik.log")).useDelimiter("\\s+");
            while (s.hasNext()) {
                if (s.hasNext()) {
                    obszar_logu.appendText(s.nextLine() + "\n");
                } else {
                    obszar_logu.appendText(s.nextLine() + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            obsluzBlad("Nie udało się wczytać logu aplikacji.", e);
            powrot();
        }
    }

    public void akcja_powrot(ActionEvent actionEvent) {
        powrot();
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)obszar_logu.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/Log.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }
}
