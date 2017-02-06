package github.kjkow.kontrolery;

import github.kjkow.Uroczystosc;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.ObslugaBledu;
import github.kjkow.implementacja.uroczystosc.UroczystoscDAO;
import github.kjkow.implementacja.uroczystosc.UroczystoscDAOImpl;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by kamil on 2017-02-06.
 */
public class ObszarPowiadomienKontroler extends BazowyKontroler implements Initializable {
    public Label uroczystosci;
    private UroczystoscDAO uroczystoscDAO;

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
            etykieta.append(u.getDataUroczystosci().toString());
            etykieta.append("  ");
            etykieta.append(u.getOsoba());
            etykieta.append(" ma ");
            etykieta.append(u.getRodzajUroczystosci());
            etykieta.append("\n");
        }
        uroczystosci.setText(etykieta.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicjujUroczystosci();
    }
}
