package github.kjkow.pliki;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2016-12-07.
 */
public interface IPlik {

    HSSFWorkbook wczytajArkuszExcela(String sciezkaDoPliku) throws IOException;

    void zapiszArkuszExcela(HSSFWorkbook arkusz, String sciezkaDoPliku) throws IOException;

    void kopiujPlik(String sciezkaDoPlikuZrodlowego, String sciezkaDoPlikuDocelowego) throws IOException;

    /**
     *
     * @param sciezkaDoPliku
     * @return linie tekstu z wczytanego pliku
     */
    ArrayList<String> czytajZPliku(String sciezkaDoPliku) throws IOException;
}
