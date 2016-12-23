package github.kjkow.automaty.excel.migrator_arkusza_nowy_rok;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by uzytkownik on 26.11.16.
 * czyści wszystkie pola w arkuszu które są dodawane z palca
 * puszczany już po zmianie nazw arkuszy (obecny przyszlyRok to nowy przyszlyRok)
 */
public class CzyscicielKomorek extends MigratorArkuszaNowyRok{

    public CzyscicielKomorek(HSSFWorkbook Arkusz){
        super(Arkusz);
    }

    public void wyczyscPola(){
        for(int i = 0; i < nowyArkusz.getNumberOfSheets(); i++){

            if(nowyArkusz.getSheetName(i).startsWith(String.valueOf(przyszlyRok))){
                //logger.debug("Czyszczenie " + nowyArkusz.getSheetName(i));
                //PRZYCHODY
                for(int j=48; j < 55; j++){
                    //planowane przychody
                    nowyArkusz.getSheetAt(i).getRow(j).getCell(2).setCellFormula(null);
                    nowyArkusz.getSheetAt(i).getRow(j).getCell(2).setCellValue(0);


                    //rzeczywiste przychody
                    nowyArkusz.getSheetAt(i).getRow(j).getCell(3).setCellFormula(null);
                    nowyArkusz.getSheetAt(i).getRow(j).getCell(3).setCellValue(0);
                }

                //WYDATKI
                wyczyscPolaWiersza(63,67,i);//jedzenie
                wyczyscPolaWiersza(70,79,i);//mieszkanie/dom
                wyczyscPolaWiersza(82,89,i);//transport
                wyczyscPolaWiersza(92,96,i);//telekomunikacja
                wyczyscPolaWiersza(99,102,i);//opieka zdrowotna
                wyczyscPolaWiersza(105,109,i);//ubranie
                wyczyscPolaWiersza(112,116,i);//higiena
                wyczyscPolaWiersza(119,124,i);//dzieci
                wyczyscPolaWiersza(127,134,i);//rozrywka
                wyczyscPolaWiersza(137,139,i);//zwierzeta
                wyczyscPolaWiersza(142,149,i);//inne wydatki
                wyczyscPolaWiersza(152,157,i);//spłada długów
                wyczyscPolaWiersza(160,167,i);//oszczędności
            }
        }
    }

    private void wyczyscPolaWiersza(int pierwszyWiersz, int ostatniWiersz, int idArkusza){
        for(int i= pierwszyWiersz - 1; i < ostatniWiersz; i++){
            HSSFRow wiersz = nowyArkusz.getSheetAt(idArkusza).getRow(i);

            //planowane wydatki
            if(wiersz.getCell(2) != null) {
                wiersz.getCell(2).setCellFormula(null);
                wiersz.getCell(2).setCellValue(0);
            }

            //komentarz
            if(wiersz.getCell(6) != null){
                wiersz.getCell(6).setCellValue("");
            }

            //dni
            for(int j = 8; j < 39; j++){
                if(wiersz.getCell(j) != null){
                    wiersz.getCell(j).setCellFormula(null);
                    wiersz.getCell(j).setCellValue("");
                }
            }
        }

    }
}
