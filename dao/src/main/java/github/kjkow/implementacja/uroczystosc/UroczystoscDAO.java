package github.kjkow.implementacja.uroczystosc;

import github.kjkow.Uroczystosc;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Kamil.Kowalczyk on 2016-12-23.
 */
public interface UroczystoscDAO {
    ArrayList<Uroczystosc> pobierzNajblizszeUroczystosci() throws SQLException, ClassNotFoundException;
}
