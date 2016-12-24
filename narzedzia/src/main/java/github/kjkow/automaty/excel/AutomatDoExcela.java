package github.kjkow.automaty.excel;

import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorCzyszczeniaKomorek;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorArkuszaNowyRok;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorNazwArkuszy;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorPrzepisywaniaWartosci;
import github.kjkow.automaty.excel.migrator_zakresow.MigratorZakresow;
import github.kjkow.kontekst.KontekstZwracany;
import github.kjkow.pliki.IPlik;
import github.kjkow.pliki.Plik;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Kamil.Kowalczyk on 2016-12-07.
 */
public class AutomatDoExcela implements IAutomatDoExcela {

    private static final int obecnyMiesiac = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private KontekstZwracany kontekstAutomatu;

    @Override
    public KontekstZwracany migrujZakresy(String sciezkaDoArkusza) {
        kontekstAutomatu = new KontekstZwracany();
        HSSFWorkbook arkusz;
        IPlik plik = new Plik();

        //wczytaj plik
        try {
            arkusz = plik.wczytajArkuszExcela(sciezkaDoArkusza);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Nie udalo sie wczytac pliku " + sciezkaDoArkusza + "\n" + e);
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        //pusc migrator
        if(arkusz != null && obecnyMiesiac != 0) {
            kontekstAutomatu.dodajDoLogu("Rozpocznij migrację zakresow.");
            MigratorZakresow migrator = new MigratorZakresow(arkusz);

            KontekstZwracany kontekstMigratoraZakresow = migrator.migrujZakresy(obecnyMiesiac);
            kontekstAutomatu.setCzyBrakBledow(kontekstMigratoraZakresow.isCzyBrakBledow());
            kontekstAutomatu.dodajDoLogu(kontekstMigratoraZakresow.getLog());

            if(!kontekstAutomatu.isCzyBrakBledow()) return kontekstAutomatu;

        }else if(Calendar.getInstance().get(Calendar.MONTH) != 0){
            kontekstAutomatu.dodajDoLogu("Tego migratora nie mozna puszczac w styczniu.");
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }


        //zapisz plik
        try {
            plik.zapiszArkuszExcela(arkusz, sciezkaDoArkusza);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Nie udalo sie zapisac pliku " + sciezkaDoArkusza + "\n" + e);
            return kontekstAutomatu;
        }
        return kontekstAutomatu;
    }

    @Override
    public KontekstZwracany utworzArkuszNaNowyRok(String sciezkaDoArkusza) {
        kontekstAutomatu = new KontekstZwracany();

        if(Calendar.getInstance().get(Calendar.MONTH) != 0){ //robimy to tylko w styczniu
            //logger.error("Ten automat puszczamy tylko w styczniu.");
            kontekstAutomatu.dodajDoLogu("Ten automat puszczamy tylko w styczniu!");
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        String sciezkaDoArkuszaNaNowyRok = zmienRokWSciezceDoArkusza(sciezkaDoArkusza);
        HSSFWorkbook staryArkusz;
        HSSFWorkbook nowyArkusz;
        IPlik pPlik = new Plik();

        //wczytaj pliki
        try {
            staryArkusz = pPlik.wczytajArkuszExcela(sciezkaDoArkusza);
            pPlik.kopiujPlik(sciezkaDoArkusza, sciezkaDoArkuszaNaNowyRok);
            nowyArkusz = pPlik.wczytajArkuszExcela(sciezkaDoArkuszaNaNowyRok);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Bład przy wczytywaniu arkusza.\n" + e);
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        //pusc migratory
        if(staryArkusz != null && nowyArkusz != null) {
            MigratorArkuszaNowyRok migratorNazwArkuszy = new MigratorNazwArkuszy(nowyArkusz);
            MigratorArkuszaNowyRok migratorCzyszczeniaKomorek = new MigratorCzyszczeniaKomorek(nowyArkusz);
            MigratorArkuszaNowyRok migratorPrzepisywaniaWartosci = new MigratorPrzepisywaniaWartosci(staryArkusz, nowyArkusz);
            MigratorZakresow migratorZakresow = new MigratorZakresow(nowyArkusz);
            MigratorZakresow migratorNaNowyRok = new MigratorZakresow(staryArkusz);

            KontekstZwracany kontekstZmianyRoku = migratorNaNowyRok.migrujPrzyZmianieRoku();
            if(!kontekstZmianyRoku.isCzyBrakBledow()){
                przepakujDoKontekstuAutomatu(kontekstZmianyRoku);
                return kontekstAutomatu;
            }

            KontekstZwracany kontekstNazwArkuszy = migratorNazwArkuszy.migruj();
            if(!kontekstNazwArkuszy.isCzyBrakBledow()){
                przepakujDoKontekstuAutomatu(kontekstNazwArkuszy);
                return kontekstAutomatu;
            }

            KontekstZwracany kontekstCzyszczeniaKomorek = migratorCzyszczeniaKomorek.migruj();
            if(!kontekstCzyszczeniaKomorek.isCzyBrakBledow()){
                przepakujDoKontekstuAutomatu(kontekstCzyszczeniaKomorek);
                return kontekstAutomatu;
            }

            KontekstZwracany kontekstPrzepisywaniaWartosci = migratorPrzepisywaniaWartosci.migruj();
            if(!kontekstPrzepisywaniaWartosci.isCzyBrakBledow()){
                przepakujDoKontekstuAutomatu(kontekstPrzepisywaniaWartosci);
                return kontekstAutomatu;
            }

            KontekstZwracany kontekstZakresow = migratorZakresow.migrujZakresy(obecnyMiesiac);
            if(!kontekstZakresow.isCzyBrakBledow()){
                przepakujDoKontekstuAutomatu(kontekstZakresow);
                return kontekstAutomatu;
            }
        }else{
            kontekstAutomatu.dodajDoLogu("Nie udało się utowrzyć arkusza.");
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        //zapisz nowy nowyArkusz
        try {
            pPlik.zapiszArkuszExcela(nowyArkusz, sciezkaDoArkuszaNaNowyRok);
            //puszczenie migratorZakresow.migrujPrzyZmianieRoku() zmieni zakresy na starym arkuszu, zapisujemy go w postaci "do grudnia"
            pPlik.zapiszArkuszExcela(staryArkusz, sciezkaDoArkusza);
        } catch (IOException e) {
            kontekstAutomatu.dodajDoLogu("Błąd przy zapisywaniu arkusza.\n" + e);
            kontekstAutomatu.setCzyBrakBledow(false);
            return kontekstAutomatu;
        }

        kontekstAutomatu.dodajDoLogu("Migrator zakończony sukcesem.");
        return kontekstAutomatu;
    }

    private String zmienRokWSciezceDoArkusza(String sciezkaDoArkusza){
        String obcietaSciezka = sciezkaDoArkusza.substring(0, sciezkaDoArkusza.length() - 8);
        int nowyRok = Calendar.getInstance().get(Calendar.YEAR) + 1;
        return obcietaSciezka + String.valueOf(nowyRok) + ".xls";
    }

    private void przepakujDoKontekstuAutomatu(KontekstZwracany pKontekst){
        kontekstAutomatu.dodajDoLogu(pKontekst.getLog());
        kontekstAutomatu.setCzyBrakBledow(pKontekst.isCzyBrakBledow());
    }
}
