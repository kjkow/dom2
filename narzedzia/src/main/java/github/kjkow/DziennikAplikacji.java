package github.kjkow;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kamil.Kowalczyk on 2017-02-20.
 */
public class DziennikAplikacji {

    private static ArrayList<String> liniePliku = new ArrayList<>();;
    private static final DateFormat FORMAT_DATY = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public static void zapiszInformacje(String sciezkaDziennika, String tresc) throws IOException {
        Date data = new Date();
        liniePliku.add(FORMAT_DATY.format(data) + " " + tresc);
        Plik.zapiszDoPliku(sciezkaDziennika, liniePliku);
    }


    public static void zapiszBlad(String sciezkaDziennika,String tresc, Exception e) throws IOException {
        Date data = new Date();
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        liniePliku.add(FORMAT_DATY.format(data) + " " + tresc);
        liniePliku.add(errors.toString());
        Plik.zapiszDoPliku(sciezkaDziennika, liniePliku);
    }


    public static String zwrocDziennik(String sciezkaDziennika) throws IOException {
        liniePliku = Plik.czytajZPliku(sciezkaDziennika);
        String dziennik = "";
        for(String linia: liniePliku){
            dziennik += linia + "\n";
        }
        return dziennik;
    }
}
