package github.kjkow.bazowe;


import github.kjkow.DziennikAplikacji;
import github.kjkow.automaty.excel.AutomatDoExcela;
import github.kjkow.automaty.excel.IAutomatDoExcela;
import github.kjkow.bazowe.formatka.IZarzadcaFormatek;
import github.kjkow.bazowe.formatka.ZarzadcaFormatek;
import github.kjkow.implementacja.jedzenie.JedzenieDAO;
import github.kjkow.implementacja.jedzenie.JedzenieDAOImpl;
import github.kjkow.implementacja.jedzenie.KontekstZwracanyJedzenieDAO;
import github.kjkow.implementacja.konfiguracja.KonfiguracjaDAO;
import github.kjkow.implementacja.konfiguracja.KonfiguracjaDAOImpl;
import github.kjkow.implementacja.konfiguracja.KontekstZwracanyKonfiguracjaDAO;
import github.kjkow.implementacja.sprzatanie.KontekstZwracanySprzatanieDAO;
import github.kjkow.implementacja.sprzatanie.SprzatanieDAO;
import github.kjkow.implementacja.sprzatanie.SprzatanieDAOImpl;
import github.kjkow.implementacja.uroczystosc.KontekstZwracanyUroczystoscDAO;
import github.kjkow.implementacja.uroczystosc.UroczystoscDAO;
import github.kjkow.implementacja.uroczystosc.UroczystoscDAOImpl;

import java.io.IOException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-13.
 */
public class BazowyKontroler {

    protected final IZarzadcaFormatek zarzadcaFormatek = new ZarzadcaFormatek();
    protected final IAutomatDoExcela automatDoExcela  = new AutomatDoExcela();

    protected static final String KOMUNIKAT_BLEDU_KONSTRUKTORA_DAO = "Wystąpił błąd podczas wczytywania konfiguracji bazy danych z pliku";
    protected static final String KOMUNIKAT_BLEDU_SQL = "Wystąpił błąd na bazie danych";
    protected static final String KOMUNIKAT_BLEDU_KONEKTORA_JDBC = "Wystąpił błąd sterownika bazy danych";
    protected static final String KOMUNIKAT_BLEDU_IO = "Wystąpił błąd podczas wczytywania pliku.";
    protected static final String KOMUNIKAT_AMBIWALENCJI_DZIENNIKA = "Nastąpił poprawny zapis na bazie, ale nie udało się zapisać informacji w dzienniku aplikacji.";
    protected static final String KOMUNIKAT_NIEOCZEKIWANY = "Wystąpił nieoczekiwany błąd aplikacji";

    protected final SprzatanieDAO sprzatanieDAO = new SprzatanieDAOImpl();
    protected final JedzenieDAO jedzenieDAO = new JedzenieDAOImpl();
    protected final KonfiguracjaDAO konfiguracjaDAO = new KonfiguracjaDAOImpl();
    protected final UroczystoscDAO uroczystoscDAO = new UroczystoscDAOImpl();

    protected KontekstZwracanyJedzenieDAO kontekstZwracanyJedzenieDAO;
    protected KontekstZwracanyKonfiguracjaDAO kontekstZwracanyKonfiguracjaDAO;
    protected KontekstZwracanySprzatanieDAO kontekstZwracanySprzatanieDAO;
    protected KontekstZwracanyUroczystoscDAO kontekstZwracanyUroczystoscDAO;

    protected void obsluzBlad(String trescKomunikatu, Exception e){
        ObslugaBledu.obsluzBlad(trescKomunikatu, e);
    }

    protected void otworzNowaFormatke(String sciezka){
        try {
            zarzadcaFormatek.wyswietlNowaFormatke(sciezka);
        }catch (Exception e){
            obsluzBlad(KOMUNIKAT_NIEOCZEKIWANY, e);
        }
    }

    protected void zapiszWykonanieWDzienniku(String tresc){
        try {
            DziennikAplikacji.zapiszInformacje(KontekstAplikacji.pobierzSciezkeDziennikaAplikacji(), tresc);
        } catch (IOException e) {
            zarzadcaFormatek.wyswietlOknoInformacji(KOMUNIKAT_AMBIWALENCJI_DZIENNIKA + "\n" +
                    KOMUNIKAT_BLEDU_IO + "\n" + e.getLocalizedMessage());
        }
    }
}
