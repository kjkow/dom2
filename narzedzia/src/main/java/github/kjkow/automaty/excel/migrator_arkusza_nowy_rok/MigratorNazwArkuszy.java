package github.kjkow.automaty.excel.migrator_arkusza_nowy_rok;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by uzytkownik on 26.11.16.
 * zmienia nazwy arkuszy w excelu - podmienia stary na nowy przyszlyRok
 */
public class MigratorNazwArkuszy extends MigratorArkuszaNowyRok {

    public MigratorNazwArkuszy(HSSFWorkbook Arkusz){
        super(Arkusz);
    }

    public void modyfikujNazwyArkuszy(){
        zmienNazwyArkuszy();
    }

    private void zmienNazwyArkuszy(){
        for(int i = 0; i < nowyArkusz.getNumberOfSheets(); i++){
            nowyArkusz.setSheetName(i, ustalNowaNazwe(nowyArkusz.getSheetName(i)));
        }
    }

    private String ustalNowaNazwe(String staraNazwa){
        //logger.info("Zmiana roku w nazwach arkuszy z " + obecnyRok + " na " + przyszlyRok);
        return staraNazwa.replaceAll(String.valueOf(obecnyRok), String.valueOf(przyszlyRok));
    }

}
