package github.kjkow.bazowe;

/**
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public final class PrzechowywaczDanych {

    private static Object obiekt;
    private static BazowyKontroler formatkaPowrotu;

    public static void zapiszObiekt(Object pObiekt){
        obiekt = pObiekt;
    }

    public static Object pobierzObiekt(){
        return obiekt;
    }

    public static void zapamietajWyjscie(BazowyKontroler pKontroler){
        formatkaPowrotu = pKontroler;
    }

    public static BazowyKontroler pobierzWyjscie(){
        return formatkaPowrotu;
    }
}
