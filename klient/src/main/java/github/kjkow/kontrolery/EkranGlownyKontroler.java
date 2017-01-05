package github.kjkow.kontrolery;


import github.kjkow.Uroczystosc;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.ObslugaBledu;
import github.kjkow.bazowe.PrzechowywaczDanych;
import github.kjkow.implementacja.uroczystosc.UroczystoscDAO;
import github.kjkow.implementacja.uroczystosc.UroczystoscDAOImpl;
import github.kjkow.kontrolery.jedzenie.JedzenieGlownyKontroler;
import github.kjkow.kontrolery.sprzatanie.SprzatanieEkranGlownyKontroler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class EkranGlownyKontroler extends BazowyKontroler implements Initializable {
//TODO: zmiana co roku dat urodzin i imienin
    @FXML public Label uroczystosci;//TODO: Zamienic na powiadomienia i dodac info o lokatach

    private UroczystoscDAO uroczystoscDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicjujUroczystosci();
    }

    /**
     * Button
     * @param actionEvent
     */
    public void obslugaLog(ActionEvent actionEvent) {
        otworzNowaFormatke(new LogKontroler());
    }

    /**
     * Button
     * @param actionEvent
     */
    public void obslugaKonfiguracja(ActionEvent actionEvent) {
        //plik.otworzZewnetrzny("E:\\programy\\dom\\zrodla\\konfiguracja.txt");
        //TODO: formatka i kontroler
    }

    /**
     * Button
     * @param actionEvent
     */
    public void obslugaJedzenie(ActionEvent actionEvent) {
        otworzNowaFormatke(new JedzenieGlownyKontroler());
    }

    /**
     * Button
     * @param actionEvent
     */
    public void obslugaSprzatanie(ActionEvent actionEvent) {
        otworzNowaFormatke(new SprzatanieEkranGlownyKontroler());
    }

    /**
     * Button
     * @param actionEvent
     */
    public void obslugaNarzedziaExcel(ActionEvent actionEvent) {
        otworzNowaFormatke(new AutomatExcelKontroler());
    }

    private void inicjujUroczystosci(){
        uroczystosci.setText("W najbliższym czasie nie ma żadnych uroczystości");

        StringBuilder etykieta = new StringBuilder();
        etykieta.append("Najbliższe uroczystości:\n\n");

        try {
            uroczystoscDAO = new UroczystoscDAOImpl();
        } catch (IOException e) {
            ObslugaBledu.obsluzBlad(KOMUNIKAT_BLEDU_KONSTRUKTORA_DAO, e);
            return;
        }

        ArrayList<Uroczystosc> uroczystosciLista;
        try {
            uroczystosciLista = uroczystoscDAO.pobierzNajblizszeUroczystosci();
        } catch (SQLException e) {
            ObslugaBledu.obsluzBlad(KOMUNIKAT_BLEDU_SQL, e);
            return;
        } catch (ClassNotFoundException e) {
            ObslugaBledu.obsluzBlad(KOMUNIKAT_BLEDU_KONEKTORA_JDBC, e);
            return;
        }

        if(uroczystosciLista.isEmpty()){
            return;
        }

        for(Uroczystosc u: uroczystosciLista){
            etykieta.append(u.getOsoba());
            etykieta.append(" ma ");
            etykieta.append(u.getRodzajUroczystosci());
            etykieta.append(" ");
            etykieta.append(u.getDataUroczystosci().toString().substring(5, 10));
            etykieta.append("\n");
        }
        uroczystosci.setText(etykieta.toString());
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)uroczystosci.getScene().getWindow();
    }

    @Override
    protected void ustawZrodloFormatki() {
        zrodloFormatki = getClass().getClassLoader().getResource("github/kjkow/kontrolery/StartProgramu.fxml");
    }

    @Override
    protected void zapametajPowrot() {
        PrzechowywaczDanych.zapamietajWyjscie(this);
    }
}
