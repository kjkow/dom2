package github.kjkow.automaty.excel.migrator_arkusza_nowy_rok;

import github.kjkow.kontekst.KontekstZwracany;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Calendar;

/**
 * Created by Kamil.Kowalczyk on 2016-12-09.
 */
public abstract class MigratorArkuszaNowyRok {

    HSSFWorkbook nowyArkusz;
    HSSFWorkbook staryArkusz;
    final static int przyszlyRok = Calendar.getInstance().get(Calendar.YEAR) + 1;
    final static int obecnyRok = Calendar.getInstance().get(Calendar.YEAR);
    KontekstZwracany pKontekst;

    MigratorArkuszaNowyRok(HSSFWorkbook aStaryArkusz, HSSFWorkbook aNowyArkusz){
        nowyArkusz = aNowyArkusz;
        staryArkusz = aStaryArkusz;
    }

    MigratorArkuszaNowyRok(HSSFWorkbook aNowyArkusz){
        nowyArkusz = aNowyArkusz;
    }

    /**
     * glowna metoda migratora
     * @return
     */
    public abstract KontekstZwracany migruj();
}
