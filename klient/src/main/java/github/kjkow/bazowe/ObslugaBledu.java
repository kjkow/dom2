package github.kjkow.bazowe;

import github.kjkow.DziennikAplikacji;
import github.kjkow.bazowe.formatka.IZarzadcaFormatek;
import github.kjkow.bazowe.formatka.ZarzadcaFormatek;

/**
 * Created by Kamil.Kowalczyk on 2016-12-23.
 */
public final class ObslugaBledu {

    private static IZarzadcaFormatek zarzadcaFormatek = new ZarzadcaFormatek();

    public static void obsluzBlad(String tresc, Exception e){

        try {
            DziennikAplikacji.zapiszBlad(KontekstAplikacji.pobierzSciezkeDziennikaAplikacji(), tresc, e);
        } catch (Exception e1) {
            tresc += "\nBłąd aplikacji: \n" + e.getLocalizedMessage() +
                    "\nDodatkowo wystąpił problem z zapisaniem błędu do dziennika. Błąd zapisu do dziennika: \n" +
                    e1.getLocalizedMessage();
        }
        zarzadcaFormatek.wyswietlOknoBledu(tresc);
    }
}
