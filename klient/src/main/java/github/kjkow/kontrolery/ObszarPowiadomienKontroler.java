package github.kjkow.kontrolery;

import github.kjkow.Uroczystosc;
import github.kjkow.bazowe.BazowyKontroler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by kamil on 2017-02-06.
 */
public class ObszarPowiadomienKontroler extends BazowyKontroler implements Initializable {
    public Label uroczystosci;

    private void inicjujUroczystosci(){
        uroczystosci.setText("W najbliższym czasie nie ma żadnych uroczystości");

        StringBuilder etykieta = new StringBuilder();
        etykieta.append("Najbliższe uroczystości:\n\n");

        ArrayList<Uroczystosc> uroczystosciLista;

        kontekstZwracanyUroczystoscDAO = uroczystoscDAO.pobierzNajblizszeUroczystosci();
        if(!kontekstZwracanyUroczystoscDAO.isCzyBrakBledow()){
            obsluzBlad(kontekstZwracanyUroczystoscDAO.getLog(), kontekstZwracanyUroczystoscDAO.getBlad());
            return;
        }
        uroczystosciLista = kontekstZwracanyUroczystoscDAO.getListaUroczystosci();

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
