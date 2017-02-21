package github.kjkow.pliki;

import github.kjkow.Plik;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public class PlikTest {

    private IPlik plik;

    @Before
    public void inicjacja(){
        plik = new Plik();
    }

    @Test
    public void testCzytaniaZPliku(){
        ArrayList<String> linie = new ArrayList<>();

        try {
            linie = plik.czytajZPliku("narzedzia/src/test/github/kjkow/pliki/test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(linie);
    }

    @Test
    public void testZapiszDoPliku(){
        ArrayList<String> linie = new ArrayList<>();
        linie.add("test1");
        linie.add("test2");
        linie.add("test3");

        try {
            plik.zapiszDoPliku("narzedzia/src/test/github/kjkow/pliki/test.txt", linie);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
