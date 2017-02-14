package github.kjkow.dziennik;

import github.kjkow.implementacja.konfiguracja.KonfiguracjaDAO;
import github.kjkow.implementacja.konfiguracja.KonfiguracjaDAOImpl;
import github.kjkow.pliki.IPlik;
import github.kjkow.pliki.Plik;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
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
    private  String sciezkaDziennika = "";
    private DateFormat formatDaty;
    private Date data;

    public Dziennik() throws SQLException, IOException, ClassNotFoundException {
        przypiszSciezkeDziennika();
        plik = new Plik();
        liniePliku = new ArrayList<>();
        formatDaty = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private void przypiszSciezkeDziennika() throws IOException, SQLException, ClassNotFoundException {
        KonfiguracjaDAO konfiguracjaDAO = new KonfiguracjaDAOImpl();
        sciezkaDziennika = konfiguracjaDAO.pobierzSciezkeDziennikaAplikacji();
    }

    @Override
    public void zapiszInformacje(String tresc) throws IOException {
        data = new Date();
        liniePliku.add(formatDaty.format(data) + " " + tresc);
        plik.zapiszDoPliku(sciezkaDziennika, liniePliku);
    }

    @Override
    public void zapiszBlad(String tresc, Exception e) throws IOException {
        data = new Date();
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        liniePliku.add(formatDaty.format(data) + " " + tresc);
        liniePliku.add(errors.toString());
        plik.zapiszDoPliku(sciezkaDziennika, liniePliku);
    }

    @Override
    public String zwrocDziennik() throws IOException {
        liniePliku = plik.czytajZPliku(sciezkaDziennika);
        String dziennik = "";
        for(String linia: liniePliku){
            dziennik += linia + "\n";
        }
        return dziennik;
    }
}
