package github.kjkow;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2016-12-07.
 */
public class Plik {

    public static HSSFWorkbook wczytajArkuszExcela(String sciezkaDoPliku) throws IOException {
        File plikArkusza = new File(sciezkaDoPliku);
        return new HSSFWorkbook(new FileInputStream(plikArkusza));
    }

    public static void zapiszArkuszExcela(HSSFWorkbook arkusz, String sciezkaDoPliku) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File(sciezkaDoPliku));
        arkusz.write(outputStream);
        outputStream.close();
    }

    public static void kopiujPlik(String sciezkaDoPlikuZrodlowego, String sciezkaDoPlikuDocelowego) throws IOException {
        File plikZrodlowy = new File(sciezkaDoPlikuZrodlowego);
        File plikDocelowy = new File(sciezkaDoPlikuDocelowego);

        FileChannel inputChannel = new FileInputStream(plikZrodlowy).getChannel();
        FileChannel outputChannel = new FileOutputStream(plikDocelowy).getChannel();
        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        inputChannel.close();
        outputChannel.close();
    }

    public static ArrayList<String> czytajZPliku(String sciezkaDoPliku) throws IOException {
        ArrayList<String> liniePliku = new ArrayList<>();
        BufferedReader bufor = new BufferedReader(new FileReader(sciezkaDoPliku));
        String liniaZPliku;
        do{
            liniaZPliku = bufor.readLine();
            if(liniaZPliku != null){
                liniePliku.add(liniaZPliku);
            }
        } while (liniaZPliku != null);

        return liniePliku;
    }

    public static void zapiszDoPliku(String sciezkaDoPliku, ArrayList<String> linieDoZapisania) throws IOException {
        FileWriter zapisywaczPliku =  new FileWriter(sciezkaDoPliku, true);
        BufferedWriter buforZapisu = new BufferedWriter(zapisywaczPliku);
        PrintWriter zapisywacz = new PrintWriter(buforZapisu);

        for(String linia: linieDoZapisania){
            zapisywacz.println(linia);
        }

        zapisywacz.close();
    }
}
