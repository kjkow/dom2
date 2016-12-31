package github.kjkow.automaty.excel.migrator_arkusza_nowy_rok;

import github.kjkow.kontekst.KontekstZwracany;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by uzytkownik on 26.11.16.
 * zmienia nazwy arkuszy w excelu - podmienia stary na nowy przyszlyRok
 */
public class MigratorNazwArkuszy extends MigratorArkuszaNowyRok {

    public MigratorNazwArkuszy(HSSFWorkbook Arkusz, int pNowyRok, int pObecnyRok){
        super(Arkusz);
        pKontekst = new KontekstZwracany();
        przyszlyRok = pNowyRok;
        obecnyRok = pObecnyRok;
    }

    @Override
    public KontekstZwracany migruj() {
        zmienNazwyArkuszy();
        return pKontekst;
    }

    private void zmienNazwyArkuszy(){
        try {
            for (int i = 0; i < nowyArkusz.getNumberOfSheets(); i++) {
                nowyArkusz.setSheetName(i, ustalNowaNazwe(nowyArkusz.getSheetName(i)));
            }
        } catch (Exception e){
            pKontekst.dodajDoLogu("Blad podczas ustawiania nowych nazw arkuszy.\n" + e);
        }
    }

    private String ustalNowaNazwe(String staraNazwa){
        return staraNazwa.replaceAll(String.valueOf(obecnyRok), String.valueOf(przyszlyRok));
    }

}
