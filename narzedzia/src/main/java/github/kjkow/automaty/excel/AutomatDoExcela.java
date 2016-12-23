package github.kjkow.automaty.excel;

import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.CzyscicielKomorek;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.MigratorNazwArkuszy;
import github.kjkow.automaty.excel.migrator_arkusza_nowy_rok.PrzepisywaczWartosci;
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

    //private Logger logger = Logger.getLogger(AutomatDoExcela.class);
    private static final int obecnyMiesiac = Calendar.getInstance().get(Calendar.MONTH) + 1;

    @Override
    public KontekstZwracany migrujZakresy(String sciezkaDoArkusza) {
        KontekstZwracany kontekstAutomatu = new KontekstZwracany();
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
            kontekstAutomatu.dodajDoLogu("Rozpocznij migracj� zakresow.");
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

    //TODO: przerobic na kontekst zwracany
    @Override
    public void utworzArkuszNaNowyRok(String sciezkaDoArkusza) {
        //logger.info("Rozpocznij tworzenie arkusza na nowy rok");
        if(Calendar.getInstance().get(Calendar.MONTH) != 0){ //robimy to tylko w styczniu
            //logger.error("Ten automat puszczamy tylko w styczniu.");
            return;
        }

        String sciezkaDoArkuszaNaNowyRok = zmienRokWSciezceDoArkusza(sciezkaDoArkusza);
        HSSFWorkbook staryArkusz;
        HSSFWorkbook nowyArkusz;
        IPlik plikUtils = new Plik();

        //wczytaj pliki
        try {
            //logger.info("Wczytaj plik arkusza.");
            staryArkusz = plikUtils.wczytajArkuszExcela(sciezkaDoArkusza);
            plikUtils.kopiujPlik(sciezkaDoArkusza, sciezkaDoArkuszaNaNowyRok);
            nowyArkusz = plikUtils.wczytajArkuszExcela(sciezkaDoArkuszaNaNowyRok);
        } catch (IOException e) {
            //logger.error("Blad przy wczytywaniu arkusza.", e);
            return;
        }

        //pusc migratory
        if(staryArkusz != null && nowyArkusz != null) {
            //logger.info("Inicjalizuj migratory");
            MigratorNazwArkuszy nazwArkuszy = new MigratorNazwArkuszy(nowyArkusz);
            CzyscicielKomorek czyscicielKomorek = new CzyscicielKomorek(nowyArkusz);
            PrzepisywaczWartosci przepisywaczWartosci = new PrzepisywaczWartosci(staryArkusz, nowyArkusz);
            MigratorZakresow migratorZakresow = new MigratorZakresow(nowyArkusz);
            MigratorZakresow migratorNaNowyRok = new MigratorZakresow(staryArkusz);

            //logger.info("Wykonaj migratory.");
            migratorNaNowyRok.migrujPrzyZmianieRoku();
            nazwArkuszy.modyfikujNazwyArkuszy();
            czyscicielKomorek.wyczyscPola();
            przepisywaczWartosci.przepiszWartosci();
            migratorZakresow.migrujZakresy(obecnyMiesiac);
        }

        try {
            //zapisz nowy nowyArkusz
            plikUtils.zapiszArkuszExcela(nowyArkusz, sciezkaDoArkuszaNaNowyRok);
            //puszczenie migratorZakresow.migrujPrzyZmianieRoku() zmieni zakresy na starym arkuszu, zapisujemy go w postaci "do grudnia"
            plikUtils.zapiszArkuszExcela(staryArkusz, sciezkaDoArkusza);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String zmienRokWSciezceDoArkusza(String sciezkaDoArkusza){
        String obcietaSciezka = sciezkaDoArkusza.substring(0, sciezkaDoArkusza.length() - 8);
        int nowyRok = Calendar.getInstance().get(Calendar.YEAR) + 1;
        return obcietaSciezka + String.valueOf(nowyRok) + ".xls";
    }
}
