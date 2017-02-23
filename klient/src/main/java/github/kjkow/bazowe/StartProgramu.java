package github.kjkow.bazowe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartProgramu extends Application {



    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox drzewko;
        AnchorPane ekranFormatek;
        HBox powiadomienia;

        //wczytaj pliki formatek
        try {
            drzewko = FXMLLoader.load(getClass().getClassLoader().getResource("github/kjkow/kontrolery/DrzewkoMenu.fxml"));
            ekranFormatek = FXMLLoader.load(getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/SprzatanieEkranGlowny.fxml"));
            powiadomienia = FXMLLoader.load(getClass().getClassLoader().getResource("github/kjkow/kontrolery/ObszarPowiadomien.fxml"));
        }catch (NullPointerException e){
            ObslugaBledu.obsluzBlad("Brak plików dla formatek", e);
            return;
        }

        //ustaw elementy na oknie głównym aplikacji
        KontekstAplikacji.pobierzKorzenFormatek().setLeft(drzewko);
        KontekstAplikacji.pobierzKorzenFormatek().setCenter(ekranFormatek);
        KontekstAplikacji.pobierzKorzenFormatek().setBottom(powiadomienia);

        //pokaż scenę
        Scene scena = new Scene(KontekstAplikacji.pobierzKorzenFormatek());
        primaryStage.setScene(scena);
        primaryStage.show();
    }
}
