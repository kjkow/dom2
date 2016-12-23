package github.kjkow.pliki;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;

/**
 * Created by Kamil.Kowalczyk on 2016-12-07.
 */
public interface IPlik {

    HSSFWorkbook wczytajArkuszExcela(String sciezkaDoPliku) throws IOException;

    void zapiszArkuszExcela(HSSFWorkbook arkusz, String sciezkaDoPliku) throws IOException;

    void kopiujPlik(String sciezkaDoPlikuZrodlowego, String sciezkaDoPlikuDocelowego) throws IOException;
}
