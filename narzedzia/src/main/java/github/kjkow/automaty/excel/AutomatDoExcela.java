package github.kjkow.automaty.excel;

import github.kjkow.KontekstZwracany;
import github.kjkow.Plik;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorArkuszaNowyRok;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorCzyszczeniaKomorek;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorNazwArkuszy;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorPrzepisywaniaWartosci;
import github.kjkow.automaty.excel.migrator_zakresow.MigratorZakresow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Kamil.Kowalczyk on 2016-12-07.
 */
public class AutomatDoExcela implements IAutomatDoExcela {

    private static final int obecnyMiesiac = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private KontekstZwracany kontekstAutomatu;
    private int nowyRok = Calendar.getInstance().get(Calendar.YEAR) + 1;

    @Override
    public KontekstZwracany migrujZakresy(String sciezkaDoArkusza) {
        kontekstAutomatu = new KontekstZwracany();
        HSSFWorkbook arkusz;

        //wczytaj plik
        try {
            arkusz = Plik.wczytajArkuszExcela(sciezkaDoArkusza);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Nie udało się wczytać pliku " + sciezkaDoArkusza + "\n" + e);
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        //pusc migrator
        if(arkusz != null && obecnyMiesiac != 1) {
            kontekstAutomatu.dodajDoLogu("Rozpoczęto migrację zakresów.");
            MigratorZakresow migrator = new MigratorZakresow(arkusz);

            KontekstZwracany kontekstMigratoraZakresow = migrator.migrujZakresy(obecnyMiesiac);
            kontekstAutomatu.setCzyBrakBledow(kontekstMigratoraZakresow.isCzyBrakBledow());
            kontekstAutomatu.dodajDoLogu(kontekstMigratoraZakresow.getLog());

            if(!kontekstAutomatu.isCzyBrakBledow()) return kontekstAutomatu;

        }else if(Calendar.getInstance().get(Calendar.MONTH) != 1){
            kontekstAutomatu.dodajDoLogu("Tego migratora nie można puszczać w styczniu.");
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }


        //zapisz plik
        try {
            Plik.zapiszArkuszExcela(arkusz, sciezkaDoArkusza);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Nie udało sie zapisać pliku " + sciezkaDoArkusza + "\n" + e);
            return kontekstAutomatu;
        }

        kontekstAutomatu.dodajDoLogu("Migracja zakończona pomyślnie.");
        return kontekstAutomatu;
    }

    @Override
    public KontekstZwracany utworzArkuszNaNowyRok(String sciezkaDoArkusza) {
        kontekstAutomatu = new KontekstZwracany();
        HSSFWorkbook staryArkusz;
        HSSFWorkbook nowyArkusz;

        if(obecnyMiesiac != 12 && obecnyMiesiac != 1){
            kontekstAutomatu.dodajDoLogu("Ten migrator można puszczać tylko w grudniu lub styczniu.");
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        if(obecnyMiesiac == 1){//jeśli puścimy to na początku stycznia to dla migratorow nowym rokiem bedzie obecny rok
            nowyRok = Calendar.getInstance().get(Calendar.YEAR);
        }


        //wczytaj stary arkusz
        try {
            staryArkusz = Plik.wczytajArkuszExcela(sciezkaDoArkusza);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Błąd przy wczytywaniu arkusza.\n" + e);
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        MigratorZakresow migratorNaNowyRok = new MigratorZakresow(staryArkusz);
        KontekstZwracany kontekstZmianyRoku = migratorNaNowyRok.migrujPrzyZmianieRoku();
        przepakujDoKontekstuAutomatu(kontekstZmianyRoku);
        if(!kontekstZmianyRoku.isCzyBrakBledow()) return kontekstAutomatu;
        //zapisz stary arkusz
        try {
            Plik.zapiszArkuszExcela(staryArkusz, sciezkaDoArkusza);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Błąd przy zapisywaniu arkusza.\n" + e);
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        String sciezkaDoArkuszaNaNowyRok = zmienRokWSciezceDoArkusza(sciezkaDoArkusza);

        //skopiuj stary na nowy i wczytaj nowy
        try {
            Plik.kopiujPlik(sciezkaDoArkusza, sciezkaDoArkuszaNaNowyRok);
            nowyArkusz = Plik.wczytajArkuszExcela(sciezkaDoArkuszaNaNowyRok);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Błąd przy wczytywaniu arkusza.\n" + e);
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        //pusc migratory na nowym arkuszu
        if(staryArkusz != null && nowyArkusz != null) {
            MigratorArkuszaNowyRok migratorNazwArkuszy = new MigratorNazwArkuszy(nowyArkusz, nowyRok, nowyRok-1);
            MigratorArkuszaNowyRok migratorCzyszczeniaKomorek = new MigratorCzyszczeniaKomorek(nowyArkusz, nowyRok);
            MigratorArkuszaNowyRok migratorPrzepisywaniaWartosci = new MigratorPrzepisywaniaWartosci(staryArkusz, nowyArkusz);
            MigratorZakresow migratorZakresow = new MigratorZakresow(nowyArkusz);

            KontekstZwracany kontekstNazwArkuszy = migratorNazwArkuszy.migruj();
            przepakujDoKontekstuAutomatu(kontekstNazwArkuszy);
            if(!kontekstNazwArkuszy.isCzyBrakBledow()) return kontekstAutomatu;

            KontekstZwracany kontekstCzyszczeniaKomorek = migratorCzyszczeniaKomorek.migruj();
            przepakujDoKontekstuAutomatu(kontekstCzyszczeniaKomorek);
            if(!kontekstCzyszczeniaKomorek.isCzyBrakBledow()) return kontekstAutomatu;

            KontekstZwracany kontekstPrzepisywaniaWartosci = migratorPrzepisywaniaWartosci.migruj();
            przepakujDoKontekstuAutomatu(kontekstPrzepisywaniaWartosci);
            if(!kontekstPrzepisywaniaWartosci.isCzyBrakBledow()) return kontekstAutomatu;

            KontekstZwracany kontekstZakresow = migratorZakresow.migrujZakresy(1);
            przepakujDoKontekstuAutomatu(kontekstZakresow);
            if(!kontekstZakresow.isCzyBrakBledow()) return kontekstAutomatu;

        }else{
            kontekstAutomatu.dodajDoLogu("Nie udało się utowrzyć arkusza.");
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        //zapisz nowy nowyArkusz
        try {
            Plik.zapiszArkuszExcela(nowyArkusz, sciezkaDoArkuszaNaNowyRok);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Błąd przy zapisywaniu arkusza.\n" + e);
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        kontekstAutomatu.dodajDoLogu("Migracja zakończona pomyślnie.");
        return kontekstAutomatu;
    }

    private String zmienRokWSciezceDoArkusza(String sciezkaDoArkusza){
        String obcietaSciezka = sciezkaDoArkusza.substring(0, sciezkaDoArkusza.length() - 8);
        return obcietaSciezka + String.valueOf(nowyRok) + ".xls";
    }

    private void przepakujDoKontekstuAutomatu(KontekstZwracany pKontekst){
        kontekstAutomatu.dodajDoLogu(pKontekst.getLog());
        kontekstAutomatu.setCzyBrakBledow(pKontekst.isCzyBrakBledow());
    }
}
