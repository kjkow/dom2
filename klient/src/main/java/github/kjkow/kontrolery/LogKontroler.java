package github.kjkow.kontrolery;

import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.implementacja.konfiguracja.KonfiguracjaDAO;
import github.kjkow.implementacja.konfiguracja.KonfiguracjaDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Created by Kamil.Kowalczyk on 2016-08-19.
 */
public class LogKontroler extends BazowyKontroler implements Initializable {

    @FXML public TextArea obszar_logu;
    private String sciezkaDziennika = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicjujOknoLogu();
    }

    private void inicjujOknoLogu(){
        przypiszSciezkeDziennika();
        if(sciezkaDziennika.compareTo("")==0) return;

        try {
            Scanner s = new Scanner(new File(sciezkaDziennika)).useDelimiter("\\s+");
            while (s.hasNext()) {
                if (s.hasNext()) {
                    obszar_logu.appendText(s.nextLine() + "\n");
                } else {
                    obszar_logu.appendText(s.nextLine() + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            obsluzBlad("Nie udało się wczytać logu aplikacji.", e);
        }
    }

    private void przypiszSciezkeDziennika(){
        try {
            KonfiguracjaDAO konfiguracjaDAO = new KonfiguracjaDAOImpl();
            sciezkaDziennika = konfiguracjaDAO.pobierzSciezkeDziennikaAplikacji();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            obsluzBlad("Błąd podczas pobierania ścieżki do dziennika aplikacji z bazy danych.", e);
        }
    }
}
