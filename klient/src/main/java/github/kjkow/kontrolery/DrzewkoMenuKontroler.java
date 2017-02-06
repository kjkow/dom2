package github.kjkow.kontrolery;

import github.kjkow.bazowe.BazowyKontroler;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2017-02-06.
 */
public class DrzewkoMenuKontroler extends BazowyKontroler implements Initializable{
    public TreeView<String> menu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> fake = new TreeItem<>("fake");

        TreeItem<String> sprzatanie = new TreeItem<>("Sprzątanie");
        TreeItem<String> jedzenie = new TreeItem<>("Jedzenie");
        TreeItem<String> log = new TreeItem<>("Dziennik aplikacji");

        menu.setRoot(fake);
        fake.getChildren().addAll(sprzatanie, jedzenie, log);
        menu.setShowRoot(false);

        sprzatanie.getChildren().add(new TreeItem<>("Ekran główny"));
        sprzatanie.getChildren().add(new TreeItem<>("Zarządzanie"));

        jedzenie.getChildren().add(new TreeItem<>("Przepisy"));
        jedzenie.getChildren().add(new TreeItem<>("Proces"));
    }

    public void obslugaKliknieciaElementuDrzewka(Event event) {
         //todo: przeniesc to moze do jakiegos zewnetrzengo mechanizmu

        String nazwaWybranegoElementu = String.valueOf(menu.getSelectionModel().getSelectedItem());
        TreeItem wybranyElement = menu.getSelectionModel().getSelectedItem();

        if(nazwaWybranegoElementu.contains("Ekran główny") && wybranyElement.getParent().getValue().toString().contains("Sprzątanie")){
            otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/SprzatanieEkranGlowny.fxml");
        }

        if(nazwaWybranegoElementu.contains("Zarządzanie") && wybranyElement.getParent().getValue().toString().contains("Sprzątanie")){
            otworzNowaFormatke("github/kjkow/kontrolery/sprzatanie/Czynnosci.fxml");
        }

        if(nazwaWybranegoElementu.contains("Przepisy") && wybranyElement.getParent().getValue().toString().contains("Jedzenie")){
            otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/Przepisy.fxml");
        }

        if(nazwaWybranegoElementu.contains("Proces") && wybranyElement.getParent().getValue().toString().contains("Jedzenie")){
            otworzNowaFormatke("github/kjkow/kontrolery/jedzenie/proces/Krok1.fxml");
        }

        if(nazwaWybranegoElementu.contains("Dziennik aplikacji")){
            otworzNowaFormatke("github/kjkow/kontrolery/Log.fxml");
        }

    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return null;
    }

    @Override
    protected void ustawZrodloFormatki() {

    }

    @Override
    protected void zapametajPowrot() {

    }
}
