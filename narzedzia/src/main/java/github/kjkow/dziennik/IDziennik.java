package github.kjkow.dziennik;

import java.io.IOException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-22.
 */
public interface IDziennik {

    void zapiszInformacje(String tresc) throws IOException;

    void zapiszBlad(String tresc, Exception e) throws IOException;

    /**
     *
     * @return caly dostepny log aplikacji
     */
    String zwrocDziennik() throws IOException;
}
