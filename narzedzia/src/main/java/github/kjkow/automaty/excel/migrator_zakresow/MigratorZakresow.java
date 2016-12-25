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

    private HSSFSheet wydatkiZestawienie;
    private int miesiac;
    private HSSFWorkbook arkusz;
    private KontekstZwracany pKontekst;

    /**
     * przy zwykłym migrowaniu zakresów podaj arkusz
     * przy zmianie roku podaj "stary" arkusz
     * @param Arkusz
     */
    public MigratorZakresow(HSSFWorkbook Arkusz){
        arkusz = Arkusz;
        pKontekst = new KontekstZwracany();
    }

    public KontekstZwracany migrujZakresy(int obecnyMiesiac) {
        inicjujKarteArkusza();
        miesiac = obecnyMiesiac;
        zmienWartosci();

        return pKontekst;
    }

    public KontekstZwracany migrujPrzyZmianieRoku(){
        inicjujKarteArkusza();
        if(!pKontekst.isCzyBrakBledow()) return pKontekst;

        miesiac = 13; //dodatkowa opcja która przestawi zakresy na 'do grudnia'
        pKontekst.dodajDoLogu("Rozpocznij zmianę zakresów dla miesiąca " + miesiac);
        zmienWartosci();

        return pKontekst;
    }


    private void inicjujKarteArkusza(){
        if(arkusz.getSheet("Wydatki zestawienie") != null){
            wydatkiZestawienie = arkusz.getSheet("Wydatki zestawienie");
        }else{
            pKontekst.dodajDoLogu("Nie udało się wczytać arkusza 'Wydatki zestawienie'.");
            pKontekst.setCzyBrakBledow(false);
        }
    }

    private void zmienWartosci(){
        switch (miesiac){
            case 1: //w styczniu wykorzystujemy to do wygenerowania nowego arkusza. Jest nietypowy, trzeba nadpisywać metody
                zmienWartosciKategorii("M", "B");
                if(!pKontekst.isCzyBrakBledow()) break;
                zmienSredniaZOst6Mies("H" + "99", "R" + "99", "M" + "99", "W" + "99");
                if(!pKontekst.isCzyBrakBledow()) break;
                zmienOpis("do stycznia");
                try{
                    wydatkiZestawienie.getRow(105).getCell(1).setCellFormula("AVERAGE(B105:B105)"); //oszczedności
                }catch (Exception e){
                    pKontekst.dodajDoLogu("Wystąpil blad podczas recznego nadpisywania wartosci\n" + e);
                    pKontekst.setCzyBrakBledow(false);
                }
                if(!pKontekst.isCzyBrakBledow()) break;
                zmienSrednieWydatkiNieregularne("M100", "B100");
                break;
            case 2:
                zmienWszystko("do stycznia", "B", "B", "B", "B");
                if(!pKontekst.isCzyBrakBledow()) break;
                try {
                    wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(S99:W99,B99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                }catch (Exception e){
                    pKontekst.dodajDoLogu("Wystąpil blad podczas recznego nadpisywania wartosci\n" + e);
                    pKontekst.setCzyBrakBledow(false);
                }
                break;
            case 3:
                zmienWszystko("do lutego", "B", "C", "B", "B");
                if(!pKontekst.isCzyBrakBledow()) break;
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
                    pKontekst.dodajDoLogu("Wystąpil blad podczas recznego nadpisywania wartosci\n" + e);
                    pKontekst.setCzyBrakBledow(false);
                }
                break;
            case 4:
                zmienWszystko("do marca", "C", "D", "B", "B");
                if(!pKontekst.isCzyBrakBledow()) break;
                try{
                    wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(U99:W99,B99:D99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                    break;
                }catch (Exception e){
                    pKontekst.dodajDoLogu("Wystąpil blad przy laczeniu liczb z zeszlego roku i obecnego\n" + e);
                    pKontekst.setCzyBrakBledow(false);
                }
                break;
            case 5:
                zmienWszystko("do kwietnia", "D", "E", "B", "B");
                if(!pKontekst.isCzyBrakBledow()) break;
                try{
                    wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(V99:W99,B99:E99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                    break;
                }catch (Exception e){
                    pKontekst.dodajDoLogu("Wystąpil blad przy laczeniu liczb z zeszlego roku i obecnego\n" + e);
                    pKontekst.setCzyBrakBledow(false);
                }

            case 6:
                zmienWszystko("do maja", "E", "F", "B", "B");
                if(!pKontekst.isCzyBrakBledow()) break;
                try{
                    wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(W99,B99:F99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                    break;
                }catch (Exception e){
                    pKontekst.dodajDoLogu("Wystąpil blad przy laczeniu liczb z zeszlego roku i obecnego\n" + e);
                    pKontekst.setCzyBrakBledow(false);
                }

            case 7:
                zmienWszystko("do czerwca", "F", "G", "B", "B");
                if(!pKontekst.isCzyBrakBledow()) break;
                try{
                    wydatkiZestawienie.getRow(99).getCell(14).setCellFormula("AVERAGE(B99:G99)"); //polaczenie liczb ze starego roku i obecnego   (średnia z ost 6m)
                    break;
                }catch (Exception e){
                    pKontekst.dodajDoLogu("Wystąpil blad przy laczeniu liczb z zeszlego roku i obecnego\n" + e);
                    pKontekst.setCzyBrakBledow(false);
                }

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
        if(pKontekst.isCzyBrakBledow()){
            zmienSredniaZOst6Mies(staraZmniejszania + "99", nowaZmniejszania + "99", staraZwiekszania + "99", nowaZwiekszania + "99"); //punkt 4
        }
        if(pKontekst.isCzyBrakBledow()){
            zmienSrednieWydatkiNieregularne(staraZwiekszania + "100", nowaZwiekszania + "100"); //punkt 5
        }
        if(pKontekst.isCzyBrakBledow()){
            zmienZakresOszczednosci(staraZwiekszania + "105", nowaZwiekszania + "105"); //punkt 6
        }
    }

    private void zmienZakresOszczednosci(String stara, String nowa){
        try {
            wydatkiZestawienie.getRow(105).getCell(1).setCellFormula(wydatkiZestawienie.getRow(105).getCell(1).getCellFormula().replaceAll(stara, nowa));
        }catch (Exception e){
            pKontekst.dodajDoLogu("Wystąpil blad podczas zmiany zakresow oszczednosci\n" + e);
            pKontekst.setCzyBrakBledow(false);
        }
    }

    private void zmienSredniaZOst6Mies(String staraPierwsza, String nowaPierwsza, String staraDruga, String nowaDruga){
        try {
            wydatkiZestawienie.getRow(99).getCell(14).setCellFormula(wydatkiZestawienie.getRow(99).getCell(14).getCellFormula().replace(staraPierwsza, nowaPierwsza));
            wydatkiZestawienie.getRow(99).getCell(14).setCellFormula(wydatkiZestawienie.getRow(99).getCell(14).getCellFormula().replace(staraDruga, nowaDruga));
        } catch (Exception e){
            pKontekst.dodajDoLogu("Wystpil blad przy zmienie sredniej z ostatnich szesciu miesiecy\n" + e);
            pKontekst.setCzyBrakBledow(false);
        }
    }

    private void zmienSrednieWydatkiNieregularne(String stara, String nowa){
        try {
            wydatkiZestawienie.getRow(99).getCell(16).setCellFormula(wydatkiZestawienie.getRow(99).getCell(16).getCellFormula().replaceAll(stara, nowa));
        }catch (Exception e){
            pKontekst.dodajDoLogu("Wystpil blad przy zmienie srednich wydatkow nieregularnych\n" + e);
            pKontekst.setCzyBrakBledow(false);
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
                pKontekst.dodajDoLogu("Wystpil blad przy zmianie wartosci kategorii\n" + e);
                pKontekst.setCzyBrakBledow(false);
            }
        }
    }

    private void zmienOpis(String opis){
        if(wydatkiZestawienie.getRow(0) != null && wydatkiZestawienie.getRow(0).getCell(13) != null){
            wydatkiZestawienie.getRow(0).getCell(13).setCellValue(opis);
        }
    }
}
