package github.kjkow.bazowe;

import github.kjkow.implementacja.konfiguracja.KonfiguracjaDAO;
import github.kjkow.implementacja.konfiguracja.KonfiguracjaDAOImpl;
import github.kjkow.implementacja.konfiguracja.KontekstZwracanyKonfiguracjaDAO;
import javafx.scene.layout.BorderPane;

/**
 * Created by kamil on 2017-02-06.
 */
public class KontekstAplikacji {
    private static BorderPane korzenFormatek = new BorderPane();

    public static BorderPane pobierzKorzenFormatek(){
        return korzenFormatek;
    }

    public static String pobierzSciezkeDziennikaAplikacji(){
        KonfiguracjaDAO konfiguracjaDAO = new KonfiguracjaDAOImpl();
        KontekstZwracanyKonfiguracjaDAO kontekst = konfiguracjaDAO.pobierzSciezkeDziennikaAplikacji();
        if(!kontekst.isCzyBrakBledow()){
            ObslugaBledu.obsluzBlad(kontekst.getLog(), kontekst.getBlad());
        }
        return kontekst.getSciezkaKonfiguracji();
    }
}
