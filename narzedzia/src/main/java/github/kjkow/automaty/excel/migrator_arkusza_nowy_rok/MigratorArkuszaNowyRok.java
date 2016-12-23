package github.kjkow.automaty.excel.migrator_arkusza_nowy_rok;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Calendar;

/**
 * Created by Kamil.Kowalczyk on 2016-12-09.
 */
public class MigratorArkuszaNowyRok {

    //protected Logger logger = Logger.getLogger(MigratorArkuszaNowyRok.class);
    protected HSSFWorkbook nowyArkusz;
    protected HSSFWorkbook staryArkusz;
    protected final static int przyszlyRok = Calendar.getInstance().get(Calendar.YEAR) + 1;
    protected final static int obecnyRok = Calendar.getInstance().get(Calendar.YEAR);

    public MigratorArkuszaNowyRok(HSSFWorkbook aStaryArkusz, HSSFWorkbook aNowyArkusz){
        nowyArkusz = aNowyArkusz;
        staryArkusz = aStaryArkusz;
    }

    public MigratorArkuszaNowyRok(HSSFWorkbook aNowyArkusz){
        nowyArkusz = aNowyArkusz;
    }
}
