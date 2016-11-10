package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by hmueller on 20.09.2016.
 */
public class DBVerbindung {

    private static Connection CON;

    public static boolean verbindungAufbauen(String url, String user, String pass){
        try {
            DBVerbindung.CON = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.err.println("Fehler beim Herstellen der Datenbankverbindung: " + e.getMessage() + " : " + e.getSQLState());
            System.exit(-1);
        }

        return DBVerbindung.verbindungSteht();
    }

    public static void schliessen() {
        if (DBVerbindung.CON != null && DBVerbindung.verbindungSteht()) {
            try {
                DBVerbindung.CON.close();
            } catch (SQLException e) {
                System.err.println("Fehler beim Schliessen der Datenbankverbindung: " + e.getMessage() + " : " + e.getSQLState());
            }
        }
    }

    public static boolean verbindungSteht() {
        boolean steht = false;
        if (DBVerbindung.CON != null) {
            try {
                steht = CON.isValid(0);
            } catch (SQLException e) {
                System.err.println("Fehler beim Überprüfen der Datenbankverbindung: " + e.getMessage() + " : " + e.getSQLState());
            }
        }
        return steht;
    }

    public static PreparedStatement preparedStatement( String sql ) throws SQLException {
        return DBVerbindung.CON.prepareStatement(sql);
    }

}
