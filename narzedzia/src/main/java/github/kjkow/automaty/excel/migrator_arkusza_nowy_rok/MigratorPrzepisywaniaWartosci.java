package github.kjkow.automaty.excel.migrator_arkusza_nowy_rok;

import github.kjkow.kontekst.KontekstZwracany;
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
public class MigratorPrzepisywaniaWartosci extends MigratorArkuszaNowyRok {

    private HSSFSheet wydatkiZestawienie;
    private HSSFSheet stareWydatkiZestawienie;

    public MigratorPrzepisywaniaWartosci(HSSFWorkbook staryArkusz, HSSFWorkbook nowyArkusz){
        super(staryArkusz, nowyArkusz);
        pKontekst = new KontekstZwracany();
    }

    @Override
    public KontekstZwracany migruj() {
        przepiszWartosci();
        return pKontekst;
    }

    public void przepiszWartosci(){
        //inicjuj strony arkusza
        try {
            wydatkiZestawienie = nowyArkusz.getSheet("Wydatki zestawienie");
            stareWydatkiZestawienie = staryArkusz.getSheet("Wydatki zestawienie");
        }catch (NullPointerException e){
            pKontekst.dodajDoLogu("Blad przy inicjowaniu strony arkusza 'Wydatki zestawienie'\n" + e);
            pKontekst.setCzyBrakBledow(false);
            return;
        }

        migrujWartosci();
    }

    private void migrujWartosci(){
        HSSFRow staryWiersz;
        HSSFRow nowyWiersz;

        try{
            staryWiersz = stareWydatkiZestawienie.getRow(98);
            nowyWiersz = wydatkiZestawienie.getRow(98);
        }catch (NullPointerException e){
            pKontekst.dodajDoLogu("Blad przy inicjalizacji wiersza.\n" + e);
            pKontekst.setCzyBrakBledow(false);
            return;
        }


        //pobierz wartosci ze starego arkusza
        ArrayList<Double> stareWartosci = new ArrayList<>();
        for(int i = 7; i < 13; i++){
            try {
                stareWartosci.add(staryWiersz.getCell(i).getNumericCellValue());
            }catch (Exception e){
                pKontekst.dodajDoLogu("Blad przy pobieraniu wartosci ze starego arkusza.\n" + e);
                pKontekst.setCzyBrakBledow(false);
                return;
            }
        }

        //wrzuc te wartosci do nowego arkusza
        int j = 17;
        for(Double warosc: stareWartosci){
            try {
                if (nowyWiersz.getCell(j) != null) {
                    nowyWiersz.getCell(j).setCellValue(warosc);
                } else {
                    nowyWiersz.createCell(j).setCellValue(warosc);
                }
                j++;
            }catch (Exception e){
                pKontekst.dodajDoLogu("Blad przy wpisywaniu wartosci do nowego arkusza.\n" + e);
                pKontekst.setCzyBrakBledow(false);
                return;
            }
        }
    }
}
