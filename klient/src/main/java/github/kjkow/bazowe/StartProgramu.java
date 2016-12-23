package github.kjkow.bazowe;

import github.kjkow.bazowe.formatka.EnumOknoAplikacji;
import github.kjkow.bazowe.formatka.IZarzadcaFormatek;
import github.kjkow.bazowe.formatka.ZarzadcaFormatek;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartProgramu extends Application {

    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try{
            IZarzadcaFormatek zarzadcaFormatek = new ZarzadcaFormatek();
            zarzadcaFormatek.wyswietlNowaFormatke(EnumOknoAplikacji.GLOWNE, primaryStage);
            primaryStage.show();
        }catch (Exception e){
            //TODO: obsluzyc
            System.out.println("Błąd przy otwieraniu programu");
            e.printStackTrace();
        }
    }
}
