package db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.*;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Created by hmueller on 20.09.2016.
 */

//todo db-verbindung

public class DBVerbindung {

    // Beginn der Loggingvorarbeit
    // Logger und Handler initialisieren
    private static FileHandler datei;
    private static ConsoleHandler konsole;
    private static Logger dateiLog = Logger.getLogger("DB_Logger_file");
    private static Logger konsolenLog = Logger.getLogger("DB_Logger_con");

    public DBVerbindung() {


        // FileHandler deklarieren/verknüpfen
        try {
            datei = new FileHandler(String.format("logging_%s.log", LocalDate.now().format(BASIC_ISO_DATE)));
            datei.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
            konsolenLog.log(Level.SEVERE, String.format("%s konnte nicht geschrieben werden!", e.getClass().getName()));
        }
        // Konsolenhandler deklarieren
        konsole = new ConsoleHandler();

        // Die Logger den Handlern hinzugfügen
        dateiLog.addHandler(datei);
        konsolenLog.addHandler(konsole);
        // Ende der Loggingvorarbeit

    }

    private static Connection CON;

    public static boolean verbindungAufbauen(String url, String user, String pass) {
        try {
            DBVerbindung.CON = DriverManager.getConnection(url, user, pass);
            konsolenLog.log(Level.INFO, "Die Verbindung wurde hergestellt.");
        } catch (SQLException e) {
            dateiLog.log(Level.SEVERE, String.format("Die Datenbankverbindung konnte nicht hergestellt werden. Message: %s, SQL-Status: %s", e.getMessage(), e.getSQLState()));
            //System.err.println("Fehler beim Herstellen der Datenbankverbindung: " + e.getMessage() + " : " + e.getSQLState());
            System.exit(-1);
        }

        return DBVerbindung.verbindungSteht();
    }

    public static void schliessen() {
        if (DBVerbindung.CON != null && DBVerbindung.verbindungSteht()) {
            try {
                DBVerbindung.CON.close();
                konsolenLog.log(Level.INFO, "Die Verbindung wurde geschlossen.");
            } catch (SQLException e) {
                dateiLog.log(Level.SEVERE, String.format("Die Datenbankverbindung konnte nicht geschlossen werden. Message: %s, SQL-Status: %s", e.getMessage(), e.getSQLState()));
                //System.err.println("Fehler beim Schliessen der Datenbankverbindung: " + e.getMessage() + " : " + e.getSQLState());
            }
        }
    }

    public static boolean verbindungSteht() {
        boolean steht = false;
        if (DBVerbindung.CON != null) {
            try {
                steht = CON.isValid(0);
            } catch (SQLException e) {
                dateiLog.log(Level.SEVERE, String.format("Die Datenbankverbindung konnte nicht überprüft werden. Message: %s, SQL-Status: %s", e.getMessage(), e.getSQLState()));
                //System.err.println("Fehler beim Überprüfen der Datenbankverbindung: " + e.getMessage() + " : " + e.getSQLState());
            }
        }
        return steht;
    }

    public static PreparedStatement preparedStatement(String sql) throws SQLException {
        return DBVerbindung.CON.prepareStatement(sql);
    }

}
