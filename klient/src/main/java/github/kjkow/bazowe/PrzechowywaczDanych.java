package github.kjkow.bazowe;

/**
 * Created by Kamil.Kowalczyk on 2016-12-27.
 */
public class PrzechowywaczDanych {

    private static Object obiekt;

    public static void zapiszObiekt(Object pObiekt){
        obiekt = pObiekt;
    }

    public static Object pobierzObiekt(){
        return obiekt;
    }
}
