package github.kjkow.bazowe;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Utworzył Kamil Kowalczyk dnia 2017-01-05.
 */
public final class ZarzadcaZwenetrznychPlikow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void otworzZewnetrzne(String sciezkaDoPliku) throws MalformedURLException {
        File plik = new File(sciezkaDoPliku);
        getHostServices().showDocument(plik.toURI().toURL().toExternalForm());
    }
}
