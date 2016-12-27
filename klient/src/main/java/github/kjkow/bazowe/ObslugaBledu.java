package github.kjkow.bazowe;

import github.kjkow.bazowe.formatka.IZarzadcaFormatek;
import github.kjkow.bazowe.formatka.ZarzadcaFormatek;
import github.kjkow.dziennik.Dziennik;
import github.kjkow.dziennik.IDziennik;

import java.io.IOException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-23.
 */
public class ObslugaBledu {

    private static IZarzadcaFormatek zarzadcaFormatek = new ZarzadcaFormatek();
    private static IDziennik dziennik = new Dziennik();

    public static void obsluzBlad(String tresc, Exception e){
        try {
            dziennik.zapiszBlad(tresc, e);
        } catch (IOException e1) {
            tresc += "\nBłąd aplikacji: \n" + e.getLocalizedMessage() +
                     "\nDodatkowo wystąpił problem z zapisaniem błędu do dziennika. Błąd zapisu do dziennika: \n" + e1.getLocalizedMessage();
        }
        zarzadcaFormatek.wyswietlOknoBledu(tresc);
    }
}
