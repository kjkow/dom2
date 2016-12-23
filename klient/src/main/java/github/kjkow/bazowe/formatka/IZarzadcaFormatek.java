package github.kjkow.bazowe.formatka;

import github.kjkow.bazowe.BazowyKontroler;
import javafx.stage.Stage;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public interface IZarzadcaFormatek {

    /**
     *
     * @param pKontroler kontroler formatki na ktora chcemy przejsc
     * @param scena scena obecnej formatki
     */
    void wyswietlNowaFormatke(BazowyKontroler pKontroler, Stage scena);
}
