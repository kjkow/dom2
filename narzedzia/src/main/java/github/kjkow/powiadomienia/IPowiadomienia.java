package github.kjkow.powiadomienia;

/**
 * Created by Kamil.Kowalczyk on 2016-12-22.
 */
public interface IPowiadomienia {

    void wyswietlOknoBledu(String tresc);

    /**
     * @param tresc
     * @return true = OK, false = Cancel
     */
    boolean wyswietlOknoPotwierdzenia(String tresc);

    void wyswietlOknoInformacji(String tresc);
}
