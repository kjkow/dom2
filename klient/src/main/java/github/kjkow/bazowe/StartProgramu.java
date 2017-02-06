package github.kjkow.bazowe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartProgramu extends Application {

    private static BorderPane root = new BorderPane();

    public static BorderPane getRoot(){
        return root;
    }

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox drzewko = FXMLLoader.load(getClass().getClassLoader().getResource("github/kjkow/kontrolery/DrzewkoMenu.fxml"));
        AnchorPane ekranPodwladny = FXMLLoader.load(getClass().getClassLoader().getResource("github/kjkow/kontrolery/sprzatanie/SprzatanieEkranGlowny.fxml"));
        HBox powiadomienia = FXMLLoader.load(getClass().getClassLoader().getResource("github/kjkow/kontrolery/ObszarPowiadomien.fxml"));

        root.setLeft(drzewko);
        root.setCenter(ekranPodwladny);
        root.setBottom(powiadomienia);

        Scene scena = new Scene(root);
        primaryStage.setScene(scena);
        primaryStage.show();

        //todo:przeniesc caly bajzel do eleganckiej postaci
    }
}
