package github.kjkow.kontrolery;


import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.bazowe.formatka.EnumOknoAplikacji;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class KontrolerEkranGlowny extends BazowyKontroler implements Initializable {

    @FXML public Label polaczenie;
    @FXML public Button sprzatanie;
    @FXML public Button jedzenie;
    @FXML public Button konfiguracja;
    @FXML public Button log;
    @FXML public Label opis_pogoda;
    @FXML public Label uroczystosci;
    //TODO: przejscie na automat do excela

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        op = new OknaPowiadomien();
//        scena = new Scena();
//        plik = new Plik();
        //inicjujUroczystosci();
//        try {
//            new KonektorBazyDanych("test połączenia");//testowanie połączenia z bazą danych pomocniczym konstruktorem
//        } catch (SQLException | ClassNotFoundException e) {
//            op.PokazOknoInformacji("", "Nie udało się połączyć z bazą danych");
//            polaczenie.setVisible(true);
//            sprzatanie.setDisable(true);
//            jedzenie.setDisable(true);
//            uroczystosci.setText("błąd połączenia z bazą danych");
//        }
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
        zarzadcaFormatek.wyswietlNowaFormatke(EnumOknoAplikacji.SPRZATANIE, zwrocSceneFormatki());
    }

    private void inicjujUroczystosci(){
//        StringBuilder etykieta = new StringBuilder();
//        etykieta.append("Najbliższe urodziny/imieniny:\n");
//
//        ArrayList<Uroczystosc> uroczystosciLista = model.pobierzModelUroczystosci().pobierzListe();
//        for(Uroczystosc u: uroczystosciLista){
//            etykieta.append(u.pobierzOsobe());
//            etykieta.append(" ma ");
//            etykieta.append(u.pobierzRodzajUroczystosci().toString().toLowerCase());
//            etykieta.append(" ");
//            String data = u.pobierzDateUroczystosci().toString();
//            etykieta.append(data.substring(5, 10));
//            etykieta.append("\n");
//        }
//        uroczystosci.setText(etykieta.toString());
//        if(uroczystosciLista.isEmpty()){
//            uroczystosci.setText("W najbliższym czasie nikt\nnie obchodzi urodzin ani imienin");
//        }
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)sprzatanie.getScene().getWindow();
    }
}
