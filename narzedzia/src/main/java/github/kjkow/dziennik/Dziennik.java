package github.kjkow.dziennik;

import github.kjkow.pliki.IPlik;
import github.kjkow.pliki.Plik;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kamil.Kowalczyk on 2016-12-22.
 */
public class Dziennik implements IDziennik {

    private IPlik plik;
    private ArrayList<String> liniePliku;
    private final String SCIEZKA_DZIENNIKA_APLIKACJI = "dziennik.log";
    private DateFormat formatDaty;
    private Date data;

    public Dziennik(){
        plik = new Plik();
        liniePliku = new ArrayList<>();
        formatDaty = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data = new Date();
    }

    @Override
    public void zapiszInformacje(String tresc) throws IOException {
        liniePliku.add(formatDaty.format(data) + " " + tresc);
        plik.zapiszDoPliku(SCIEZKA_DZIENNIKA_APLIKACJI, liniePliku);
    }

    @Override
    public void zapiszBlad(String tresc, Exception e) throws IOException {

        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        liniePliku.add(formatDaty.format(data) + " " + tresc);
        liniePliku.add(errors.toString());
        plik.zapiszDoPliku(SCIEZKA_DZIENNIKA_APLIKACJI, liniePliku);
    }

    @Override
    public String zwrocDziennik() throws IOException {
        liniePliku = plik.czytajZPliku(SCIEZKA_DZIENNIKA_APLIKACJI);
        String dziennik = "";
        for(String linia: liniePliku){
            dziennik += linia + "\n";
        }
        return dziennik;
    }
}
