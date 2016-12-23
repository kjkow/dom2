package github.kjkow.implementacja;



import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Kamil.Kowalczyk on 2016-12-21.
 */
public class BazowyDAO {
    //TODO: dto?
    protected Connection polaczenie;
    protected Statement statement;
    protected ResultSet wynikKwerendy;

    private String host = "";
    private String db_user = "";
    private String db_password = "";

    public BazowyDAO() throws IOException {
        ustawDanePolaczenia();
    }

    private void ustawDanePolaczenia() throws IOException {
        Properties prop = new Properties();
        prop.load(getClass().getClassLoader().getResourceAsStream("github/kjkow/implementacja/bazodanowe.properties"));

        host = prop.getProperty("host");
        db_user = prop.getProperty("dbuser");
        db_password = prop.getProperty("dbpassword");
    }

    protected void otworzPolaczenie() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        polaczenie = DriverManager.getConnection("jdbc:mysql://" + host + "?characterEncoding=UTF-8", db_user, db_password);
        statement = polaczenie.createStatement();
    }

    protected void zamknijPolczenie() throws SQLException {
        polaczenie.close();
    }
}
