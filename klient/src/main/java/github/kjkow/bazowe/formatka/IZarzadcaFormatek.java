package github.kjkow.bazowe.formatka;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public interface IZarzadcaFormatek {

    void wyswietlNowaFormatke(String sciezkaDoFormatki);

    void wyswietlOknoBledu(String tresc);

    boolean wyswietlOknoPotwierdzenia(String tresc);

    void wyswietlOknoInformacji(String tresc);
}
