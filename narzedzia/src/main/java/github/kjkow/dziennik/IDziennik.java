package github.kjkow.dziennik;

/**
 * Created by Kamil.Kowalczyk on 2016-12-22.
 */
public interface IDziennik {

    void zapiszInformacje(String tresc);

    void zapiszBlad(String tresc, Exception e);

    /**
     *
     * @return caly dostepny log aplikacji
     */
    String zwrocDziennik();
}
