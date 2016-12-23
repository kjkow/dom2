package github.kjkow.automaty.excel.migrator_arkusza_nowy_rok;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2016-11-28.
 *
 * przepisuje wartosci potrzebne do wyliczania srednich wydatkow stalych w pierwszych szesciu miesiacach roku
 * czyli sum wydatkow z poprzedniego roku od czerwca do grudnia
 */
public class PrzepisywaczWartosci extends MigratorArkuszaNowyRok {

    private HSSFSheet wydatkiZestawienie;
    private HSSFSheet stareWydatkiZestawienie;

    public PrzepisywaczWartosci(HSSFWorkbook staryArkusz, HSSFWorkbook nowyArkusz){
        super(staryArkusz, nowyArkusz);
    }

    public void przepiszWartosci(){
        wydatkiZestawienie = nowyArkusz.getSheet("Wydatki zestawienie");
        stareWydatkiZestawienie = staryArkusz.getSheet("Wydatki zestawienie");
        migrujWartosci();
    }

    private void migrujWartosci(){
        HSSFRow staryWiersz = stareWydatkiZestawienie.getRow(98);
        HSSFRow nowyWiersz = wydatkiZestawienie.getRow(98);
        //logger.info("Przenoszenie sum kosztow stalych ze starego do nowego arkusza.");
        //pobierz wartosci ze starego arkusza
        ArrayList<Double> stareWartosci = new ArrayList<Double>();
        for(int i = 7; i < 13; i++){
            stareWartosci.add(staryWiersz.getCell(i).getNumericCellValue());
        }

        //wrzuc te wartosci do nowego arkusza
        int j = 17;
        for(Double warosc: stareWartosci){
            if(nowyWiersz.getCell(j) != null){
                nowyWiersz.getCell(j).setCellValue(warosc);
            }else{
                nowyWiersz.createCell(j).setCellValue(warosc);
            }
            j++;
        }
    }
}
