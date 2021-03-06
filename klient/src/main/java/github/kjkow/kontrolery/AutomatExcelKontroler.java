package github.kjkow.kontrolery;


import github.kjkow.DziennikAplikacji;
import github.kjkow.automaty.excel.AutomatDoExcela;
import github.kjkow.automaty.excel.IAutomatDoExcela;
import github.kjkow.bazowe.BazowyKontroler;
import github.kjkow.KontekstZwracany;
import github.kjkow.bazowe.KontekstAplikacji;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kamil.Kowalczyk on 2016-12-12.
 */
public class AutomatExcelKontroler extends BazowyKontroler implements Initializable{

    public Label sciezka;
    public TextArea log;

    private IAutomatDoExcela automat;
    private KontekstZwracany pKontekst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        automat = new AutomatDoExcela();
    }

    /**
     * Button
     * @param actionEvent
     */
    public void akcjaPowrot(ActionEvent actionEvent) {

    }

    /**
     * Button
     * @param actionEvent
     */
    public void zmienSciezke(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File wybranyPlik = fileChooser.showOpenDialog(null);

        if(wybranyPlik != null){
            sciezka.setText(wybranyPlik.getAbsolutePath());
        }
    }

    /**
     * Button
     * @param actionEvent
     */
    public void utworzNowyArkusz(ActionEvent actionEvent) {
        if(!czyPustaSciezka()) {
            pKontekst = automat.utworzArkuszNaNowyRok(sciezka.getText());
            log.setText(pKontekst.getLog());
            try {
                DziennikAplikacji.zapiszInformacje(KontekstAplikacji.pobierzSciezkeDziennikaAplikacji(), "Wykonano migrator tworzenia nowego arkusza." + "\n" + pKontekst.getLog());
            } catch (IOException e) {
                zarzadcaFormatek.wyswietlOknoBledu(KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            }
        }
    }

    /**
     * Button
     * @param actionEvent
     */
    public void migrujZakresy(ActionEvent actionEvent) {
        try{
            puscMigratorZakresow();
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    private void puscMigratorZakresow(){
        if(!czyPustaSciezka()){
            pKontekst = automat.migrujZakresy(sciezka.getText());
            log.setText(pKontekst.getLog());
            try {
                DziennikAplikacji.zapiszInformacje(KontekstAplikacji.pobierzSciezkeDziennikaAplikacji(), "Wykonano migrator przesuwania zakresów." + "\n" + pKontekst.getLog());
            } catch (IOException e) {
                zarzadcaFormatek.wyswietlOknoBledu(KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
            }
        }
    }

    private boolean czyPustaSciezka(){
        return sciezka.getText().compareTo("") == 0;
    }
}
