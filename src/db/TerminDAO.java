package db;

import datenmodell.Termin;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Created by hmueller on 20.09.2016.
 */

//todo: Stsatements werden nicht geloggt

public class TerminDAO {
    // Beginn der Loggingvorarbeit
    // Logger und Handler initialisieren
    private static FileHandler datei;
    private static ConsoleHandler konsole;
    private static Logger dateiLog = Logger.getLogger("DAO_Logger_file");
    private static Logger konsolenLog = Logger.getLogger("DAO_Logger_con");

    private TerminDAO() {

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

    public static void speichern(Termin t) {
        String sql = "INSERT INTO termin(start, ende, thema, ort) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstm = DBVerbindung.preparedStatement(sql)) {
            pstm.setObject(1, t.getStart());
            pstm.setObject(2, t.getEnde());
            pstm.setString(3, t.getThema());
            pstm.setString(4, t.getOrt());

            pstm.executeUpdate();

        } catch (SQLException e) {
            dateiLog.log(Level.SEVERE, String.format("Fehler beim Speichern des Termins. Message: %s, SQL-Status: %s", e.getMessage(), e.getSQLState()));
            //    System.err.println("Fehler beim Speichern des Termins: " + e.getMessage() + " : " + e.getSQLState() + " : " + t.toString());
        }
        //todo:

        konsolenLog.log(Level.FINE, String.format("Folgendes Termin in der Datenbank gespeichert: Start: %s, Ende: %s, Ort: %s, Thema: %s", t.getStart(), t.getEnde(), t.getOrt(), t.getThema()));

    }

    public static List<Termin> alleLaden() {
        String sql = "SELECT start,ende,thema,ort FROM termin;";

        List<Termin> ergebnis = new ArrayList<>();

        try (PreparedStatement pstm = DBVerbindung.preparedStatement(sql)) {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                LocalDateTime start = rs.getObject(1, LocalDateTime.class);
                LocalDateTime ende = rs.getObject(2, LocalDateTime.class);
                String thema = rs.getString(3);
                String ort = rs.getString(4);
                ergebnis.add(new Termin(start, ende, thema, ort));
                konsolenLog.log(Level.FINE, String.format("Folgendes Statement ausgeführt: %s", pstm.toString()));

            }
        } catch (SQLException e) {
            dateiLog.log(Level.SEVERE, String.format("Fehler beim Laden der Termine. Message: %s, SQL-Status: %s", e.getMessage(), e.getSQLState()));
//            System.err.println("Fehler beim Laden der Termine: " + e.getMessage() + " : " + e.getSQLState());
        }

        return ergebnis;

    }
}
