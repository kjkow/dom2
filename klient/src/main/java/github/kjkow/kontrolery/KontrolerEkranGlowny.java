package github.kjkow.kontrolery;


import github.kjkow.Uroczystosc;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.ObslugaBledu;
import github.kjkow.implementacja.uroczystosc.UroczystoscDAO;
import github.kjkow.implementacja.uroczystosc.UroczystoscDAOImpl;
import github.kjkow.kontrolery.sprzatanie.KontrolerSprzatanieEkranGlowny;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class KontrolerEkranGlowny extends BazowyKontroler implements Initializable {

    @FXML public Label uroczystosci;//TODO: Zamienic na powiadomienia i dodac info o lokatach

    private UroczystoscDAO uroczystoscDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicjujUroczystosci();
    }

    public void obslugaLog(ActionEvent actionEvent) {
        //scena.wyswietlNowaScene(sprzatanie, EnumOknaAplikacji.LOG);
    }

    public void obslugaKonfiguracja(ActionEvent actionEvent) {
        //plik.otworzZewnetrzny("E:\\programy\\dom\\zrodla\\konfiguracja.txt");
    }

    public void obslugaJedzenie(ActionEvent actionEvent) {
        //scena.wyswietlNowaScene(sprzatanie, EnumOknaAplikacji.JEDZENIE);

    }

    public void obslugaSprzatanie(ActionEvent actionEvent) {
        zarzadcaFormatek.wyswietlNowaFormatke(new KontrolerSprzatanieEkranGlowny(), zwrocSceneFormatki());
    }

    public void obslugaNarzedziaExcel(ActionEvent actionEvent) {
        otworzNowaFormatke(new KontolerAutomatExcel());//TODO: przerobic wszystkie przejscia formatkowe w ten sposob
    }

    private void inicjujUroczystosci(){
        uroczystosci.setText("W najbliższym czasie nikt nie obchodzi urodzin ani imienin");

        StringBuilder etykieta = new StringBuilder();
        etykieta.append("Najbliższe urodziny/imieniny:\n");

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
}
