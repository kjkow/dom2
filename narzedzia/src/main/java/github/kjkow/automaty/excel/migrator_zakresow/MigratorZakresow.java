package github.kjkow.automaty.excel.migrator_zakresow;

import github.kjkow.kontekst.KontekstZwracany;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Utworzył Kamil Kowalczyk dnia 2016-11-27.
 *
 *  zmienia wartości komórek w arkuszu "Wydatki zestawienie":
 *
 1. zmienić wartość: "do miesiąca" z poprzedniego na obecny miesiąc
 2. zmienić zakres średniej każdego wiersza
 3. zmienić zakres sumy każdego wiersza
 4. zmienić zakres średniej z ostatnich sześciu miesięcy
 5. zwiększyć zakres sumy wydatki nieregularne
 6. zwiekszyć zakres średniej "oszczedzamy średnio co miesiąc"
 */
public class MigratorZakresow  {

    private StringBuilder logMigratora;
    private HSSFSheet wydatkiZestawienie;
    private int miesiac;
    private HSSFWorkbook arkusz;
    private boolean czyBrakBledow;
    private static String NOWY_WIERSZ = "\n";

    /**
     * przy zwykłym migrowaniu zakresów podaj arkusz
     * przy zmianie roku podaj "stary" arkusz
     * @param Arkusz
     */
    public MigratorZakresow(HSSFWorkbook Arkusz){
        arkusz = Arkusz;
        logMigratora = new StringBuilder();
    }

    public KontekstZwracany migrujZakresy(int obecnyMiesiac) {
        logMigratora.append(NOWY_WIERSZ);
        inicjujKarteArkusza();
        miesiac = obecnyMiesiac;
        zmienWartosci();

        KontekstZwracany kontekst = new KontekstZwracany();
        kontekst.setCzyBrakBledow(czyBrakBledow);
        kontekst.dodajDoLogu(logMigratora.toString());
        return kontekst;
    }

    public void migrujPrzyZmianieRoku(){
        inicjujKarteArkusza();
        miesiac = 13; //dodatkowa opcja która przestawi zakresy na 'do grudnia'
        logMigratora.append("Rozpocznij migrowanie zakresów z listopada na gudzień");
        logMigratora.append(NOWY_WIERSZ);
        zmienWartosci();
    }

    private void inicjujKarteArkusza(){
        wydatkiZestawienie = arkusz.getSheet("Wydatki zestawienie");
    }

    private void zmienWartosci(){
        switch (miesiac){
            case 1: //w styczniu wykorzystujemy to do wygenerowania nowego arkusza. Jest nietypowy, trzeba nadpisywać metody
                zmienWartosciKategorii("M", "B");
                zmienSredniaZOst6Mies("H" + "99", "R" + "99", "M" + "99", "W" + "99");
                zmienOpis("do stycznia");
                try{
                    wydatkiZestawienie.getRow(105).getCell(1).setCellFormula("AVERAGE(B105:B105)"); //oszczedności
                }catch (Exception e){
                    logMigratora.append("Wystąpil blad podczas recznego nadpisywania wartosci");
                    logMigratora.append(NOWY_WIERSZ);
                    logMigratora.append(e);
                    logMigratora.append(NOWY_WIERSZ);
                    czyBrakBledow = false;
                }

                zmienSrednieWydatkiNieregularne("M100", "B100");
                break;
            case 2:
                zmienWszystko("do stycznia", "B", "B", "B", "B");
                try {
                    wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(S99:W99,B99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                }catch (Exception e){
                    logMigratora.append("Wystąpil blad podczas recznego nadpisywania wartosci");
                    logMigratora.append(NOWY_WIERSZ);
                    logMigratora.append(e);
                    logMigratora.append(NOWY_WIERSZ);
                    czyBrakBledow = false;
                }
                break;
            case 3:
                zmienWszystko("do lutego", "B", "C", "B", "B");
                try {
                    for (int i = 2; i < 96; i++) {//wpisanie z palca żeby poźniej działały replacy
                        HSSFRow wiersz = wydatkiZestawienie.getRow(i);
                        for (int j = 13; j < 15; j++) {
                            String formula = "AVERAGE(B" + (i + 1) + ":C" + (i + 1) + ")";
                            wiersz.getCell(j).setCellFormula(formula);
                        }
                    }
                    wydatkiZestawienie.getRow(105).getCell(1).setCellFormula("AVERAGE(B105:C105)"); //oszczedności
                    wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(T99:W99,B99:C99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                    wydatkiZestawienie.getRow(99).getCell(16).setCellFormula("AVERAGE(B100:C100)");
                }catch (Exception e){
                    logMigratora.append("Wystąpil blad podczas recznego nadpisywania wartosci");
                    logMigratora.append(NOWY_WIERSZ);
                    logMigratora.append(e);
                    logMigratora.append(NOWY_WIERSZ);
                    czyBrakBledow = false;
                }
                break;
            case 4:
                zmienWszystko("do marca", "C", "D", "B", "B");
                wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(U99:W99,B99:D99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                break;
            case 5:
                zmienWszystko("do kwietnia", "D", "E", "B", "B");
                wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(V99:W99,B99:E99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                break;
            case 6:
                zmienWszystko("do maja", "E", "F", "B", "B");
                wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(W99,B99:F99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                break;
            case 7:
                zmienWszystko("do czerwca", "F", "G", "B", "B");
                wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(B99:G99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                break;
            case 8:
                zmienWszystko("do lipca", "G", "H", "B", "C");
                break;
            case 9:
                zmienWszystko("do sierpnia", "H", "I", "C", "D");
                break;
            case 10:
                zmienWszystko("do września", "I", "J", "D", "E");
                break;
            case 11:
                zmienWszystko("do października", "J", "K", "E", "F");
                break;
            case 12:
                zmienWszystko("do listopada", "K", "L", "F", "G");
                break;
            case 13:
                zmienWszystko("do grudnia", "L", "M", "G", "H");
                break;
        }
    }

    private void zmienWszystko(String opis, String staraZwiekszania, String nowaZwiekszania, String staraZmniejszania, String nowaZmniejszania){
        zmienOpis(opis); //punkt 1

        zmienWartosciKategorii(staraZwiekszania, nowaZwiekszania); //punkt 2 i 3
        if(czyBrakBledow){
            zmienSredniaZOst6Mies(staraZmniejszania + "99", nowaZmniejszania + "99", staraZwiekszania + "99", nowaZwiekszania + "99"); //punkt 4
        }
        if(czyBrakBledow){
            zmienSrednieWydatkiNieregularne(staraZwiekszania + "100", nowaZwiekszania + "100"); //punkt 5
        }
        if(czyBrakBledow){
            zmienZakresOszczednosci(staraZwiekszania + "105", nowaZwiekszania + "105"); //punkt 6
        }
    }

    private void zmienZakresOszczednosci(String stara, String nowa){
        try {
            wydatkiZestawienie.getRow(105).getCell(1).setCellFormula(wydatkiZestawienie.getRow(105).getCell(1).getCellFormula().replaceAll(stara, nowa));
        }catch (Exception e){
            logMigratora.append("Wystąpil blad podczas zmiany zakresow oszczednosci");
            logMigratora.append(NOWY_WIERSZ);
            logMigratora.append(e);
            logMigratora.append(NOWY_WIERSZ);
            czyBrakBledow = false;
        }
    }

    private void zmienSredniaZOst6Mies(String staraPierwsza, String nowaPierwsza, String staraDruga, String nowaDruga){
        try {
            wydatkiZestawienie.getRow(99).getCell(14).setCellFormula(wydatkiZestawienie.getRow(99).getCell(14).getCellFormula().replace(staraPierwsza, nowaPierwsza));
            wydatkiZestawienie.getRow(99).getCell(14).setCellFormula(wydatkiZestawienie.getRow(99).getCell(14).getCellFormula().replace(staraDruga, nowaDruga));
        } catch (Exception e){
            logMigratora.append("Wystpil blad przy zmienie sredniej z ostatnich szesciu miesiecy");
            logMigratora.append(NOWY_WIERSZ);
            logMigratora.append(e);
            logMigratora.append(NOWY_WIERSZ);
            czyBrakBledow = false;
        }
    }

    private void zmienSrednieWydatkiNieregularne(String stara, String nowa){
        try {
            wydatkiZestawienie.getRow(99).getCell(16).setCellFormula(wydatkiZestawienie.getRow(99).getCell(16).getCellFormula().replaceAll(stara, nowa));
        }catch (Exception e){
            logMigratora.append("Wystpil blad przy zmienie srednich wydatkow nieregularnych");
            logMigratora.append(NOWY_WIERSZ);
            logMigratora.append(e);
            logMigratora.append(NOWY_WIERSZ);
            czyBrakBledow = false;
        }
    }

    private void zmienWartosciKategorii(String staraLiteraKolumny, String nowaLiteraKolumny){
        for(int i= 2; i < 96; i++){
            HSSFRow wiersz = wydatkiZestawienie.getRow(i);
            String stara = staraLiteraKolumny + String.valueOf(i+1);
            String nowa = nowaLiteraKolumny + String.valueOf(i+1);
            try {
                for (int j = 13; j < 15; j++) {
                    wiersz.getCell(j).setCellFormula(wiersz.getCell(j).getCellFormula().replaceAll(stara, nowa));
                }
            }catch (Exception e){
                logMigratora.append("Wystpil blad przy zmianie wartosci kategorii");
                logMigratora.append(NOWY_WIERSZ);
                logMigratora.append(e);
                logMigratora.append(NOWY_WIERSZ);
                czyBrakBledow = false;
            }
        }
    }

    private void zmienOpis(String opis){
        if(wydatkiZestawienie.getRow(0) != null && wydatkiZestawienie.getRow(0).getCell(13) != null){
            wydatkiZestawienie.getRow(0).getCell(13).setCellValue(opis);
        }
    }
}
