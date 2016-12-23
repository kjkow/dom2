package github.kjkow.bazowe;

import github.kjkow.bazowe.formatka.IZarzadcaFormatek;
import github.kjkow.bazowe.formatka.ZarzadcaFormatek;
import github.kjkow.dziennik.Dziennik;
import github.kjkow.dziennik.IDziennik;

/**
 * Created by Kamil.Kowalczyk on 2016-12-23.
 */
public class ObslugaBledu {

    private static IZarzadcaFormatek zarzadcaFormatek = new ZarzadcaFormatek();
    private static IDziennik dziennik = new Dziennik();

    public static void obsluzBlad(String tresc, Exception e){
        dziennik.zapiszBlad(tresc, e);
        zarzadcaFormatek.wyswietlOknoBledu(tresc);
    }
}
