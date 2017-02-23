package github.kjkow;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class Czynnosc {
    private StringProperty nazwaCzynnosci;
    private ObjectProperty<LocalDate>  dataOstatniegoSprzatania;
    private StringProperty dniCzestotliwosci;
    private ObjectProperty<LocalDate> dataNastepnegoSprzatania ;

    public Czynnosc(){
        nazwaCzynnosci = new SimpleStringProperty();
        dataNastepnegoSprzatania = new SimpleObjectProperty<>();
        dataOstatniegoSprzatania = new SimpleObjectProperty<>();
        dniCzestotliwosci = new SimpleStringProperty();
    }

    public String getNazwaCzynnosci() {
        return nazwaCzynnosci.get();
    }

    public StringProperty nazwaCzynnosciProperty() {
        return nazwaCzynnosci;
    }

    public void setNazwaCzynnosci(String nazwaCzynnosci) {
        this.nazwaCzynnosci.set(nazwaCzynnosci);
    }

    public LocalDate getDataOstatniegoSprzatania() {
        return dataOstatniegoSprzatania.get();
    }

    public ObjectProperty<LocalDate> dataOstatniegoSprzataniaProperty() {
        return dataOstatniegoSprzatania;
    }

    public void setDataOstatniegoSprzatania(LocalDate dataOstatniegoSprzatania) {
        this.dataOstatniegoSprzatania.set(dataOstatniegoSprzatania);
    }

    public String getDniCzestotliwosci() {
        return dniCzestotliwosci.get();
    }

    public StringProperty dniCzestotliwosciProperty() {
        return dniCzestotliwosci;
    }

    public void setDniCzestotliwosci(String dniCzestotliwosci) {
        this.dniCzestotliwosci.set(dniCzestotliwosci);
    }

    public LocalDate getDataNastepnegoSprzatania() {
        return dataNastepnegoSprzatania.get();
    }

    public ObjectProperty<LocalDate> dataNastepnegoSprzataniaProperty() {
        return dataNastepnegoSprzatania;
    }

    public void setDataNastepnegoSprzatania(LocalDate dataNastepnegoSprzatania) {
        this.dataNastepnegoSprzatania.set(dataNastepnegoSprzatania);
    }
}
