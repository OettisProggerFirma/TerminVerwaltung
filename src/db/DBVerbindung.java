package db;

import Logs.denisLog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 20.11.2016.
 */
public class DBVerbindung {

    //    gehört zum logging
    private static Logger dateiLog = Logger.getLogger(denisLog.class.getName());
    private static Logger konsolenLog = Logger.getLogger(denisLog.class.getName());
    private static denisLog meine = new denisLog();
//    gehört zum logging


    public DBVerbindung() {
        //    gehört zum logging

        dateiLog.addHandler(meine.getDatei());
        konsolenLog.addHandler(meine.getKonsole());
//    gehört zum logging

    }

    private static Connection CON;

    public static boolean verbindungAufbauen(String url, String user, String pass) {
        try {
            DBVerbindung.CON = DriverManager.getConnection(url, user, pass);
//            Herstellen loggen:
            dateiLog.log(Level.INFO, "SQL-Verbindung hat geklappt");
//            DB-Parameter loggen:
            dateiLog.log(Level.CONFIG, String.format("SQL-Verbindung mit folgenden Parametern: URL - %s, User - %s, Passwort - %s (sollte ja wohl bekannt sein)", url, user, pass));

        } catch (SQLException e) {
            dateiLog.log(Level.SEVERE, String.format("SQL-Fehler: %s: %s", e.getMessage() + e.getSQLState()));
            System.exit(-1);
        }

        return DBVerbindung.verbindungSteht();
    }

    public static void schliessen() {
        if (DBVerbindung.CON != null && DBVerbindung.verbindungSteht()) {
            try {
                DBVerbindung.CON.close();
                dateiLog.log(Level.INFO, "SQL-Verbindung ist geschlossen");
            } catch (SQLException e) {
                System.err.println("Fehler beim Schliessen der Datenbankverbindung: " + e.getMessage() + " : " + e.getSQLState());
                dateiLog.log(Level.SEVERE, String.format("SQL-Fehler: %s: %s", e.getMessage() + e.getSQLState()));
            }
        }
    }

    public static boolean verbindungSteht() {
        boolean steht = false;
        if (DBVerbindung.CON != null) {
            try {
                steht = CON.isValid(0);
            } catch (SQLException e) {
                dateiLog.log(Level.SEVERE, String.format("SQL-Fehler: %s: %s", e.getMessage() + e.getSQLState()));
            }
        }
        return steht;
    }

    public static PreparedStatement preparedStatement(String sql) throws SQLException {
        return DBVerbindung.CON.prepareStatement(sql);
    }

}
