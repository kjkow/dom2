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
    int przyszlyRok;
    int obecnyRok;
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
