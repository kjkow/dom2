package github.kjkow.kontrolery;


import github.kjkow.automaty.excel.AutomatDoExcela;
import github.kjkow.automaty.excel.IAutomatDoExcela;
import github.kjkow.kontekst.KontekstZwracany;
import github.kjkow.bazowe.BazowyKontroler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-12.
 */
public class KontolerAutomatExcel extends BazowyKontroler implements Initializable{

    public Button migrator_zakresow;
    public Button tworzenie_nowego_arkusza;
    public Label sciezka;
    public Button zmien_sciezke;
    public TextArea log;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sciezka.setText("C:\\Users\\kamil.kowalczyk\\IdeaProjects\\automat_xls\\budzet_2016.xls");//TODO: 'odszyæ'
        sciezka.setUnderline(true);
    }

    public void zmienSciezke(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File wybranyPlik = fileChooser.showOpenDialog(null);

        if(wybranyPlik != null){
            sciezka.setText(wybranyPlik.getAbsolutePath());
        }
    }

    public void utworzNowyArkusz(ActionEvent actionEvent) {
        if(!czyPustaSciezka()) {
            IAutomatDoExcela automat = new AutomatDoExcela();
            automat.utworzArkuszNaNowyRok(sciezka.getText());
        }
    }


    public void migrujZakresy(ActionEvent actionEvent) {
        if(!czyPustaSciezka()){
            IAutomatDoExcela automat = new AutomatDoExcela();
            KontekstZwracany pKontekst = automat.migrujZakresy(sciezka.getText());
            log.setText(pKontekst.getLog());
        }
    }

    private boolean czyPustaSciezka(){
        return sciezka.getText().compareTo("") == 0;
    }

    @Override
    protected Stage zwrocSceneFormatki() {
        return (Stage)zmien_sciezke.getScene().getWindow();
    }
}
