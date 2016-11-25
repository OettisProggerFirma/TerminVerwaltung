package db;

import Logs.denisLog;
import datenmodell.Termin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//todo: Schöne Strings einsetzen

/**
 * Created by Denis on 20.11.2016.
 */
public class TerminDAO {
    //    gehört zum logging
    private static Logger dateiLog = Logger.getLogger(denisLog.class.getName());
    private static Logger konsolenLog = Logger.getLogger(denisLog.class.getName());
    private static denisLog meine = new denisLog();
//    gehört zum logging

    private TerminDAO() {
        //    gehört zum logging

        dateiLog.addHandler(meine.getDatei());
        konsolenLog.addHandler(meine.getKonsole());
//    gehört zum logging

    }

    public static void speichern(Termin t) {
        String sql = "INSERT INTO termin(start, ende, thema, ort) VALUES (?, ?, ?, ?)";

        String start = t.getStart().toString();
        String ende = t.getEnde().toString();
        String thema = t.getThema();
        String ort = t.getOrt();


        try (PreparedStatement pstm = DBVerbindung.preparedStatement(sql)) {
            pstm.setObject(1, t.getStart());
            pstm.setObject(2, t.getEnde());
            pstm.setString(3, t.getThema());
            pstm.setString(4, t.getOrt());

            pstm.executeUpdate();
            //todo Vorlagen fürs Logging
            konsolenLog.log(Level.FINE, String.format("Abfrage mit folgenden Werten ausgeführt: \n Start: %s, Ende: %s, Thema: %s, Ort: %s", start, ende, thema, ort));
        } catch (SQLException e) {
            dateiLog.log(Level.SEVERE, String.format("SQL-Fehler: %s: %s", e.getMessage() + e.getSQLState()));
            System.err.println("Fehler beim Speichern des Termins: " + e.getMessage() + " : " + e.getSQLState() + " : " + t.toString());
        }
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
                konsolenLog.log(Level.FINE, String.format("Abfrage mit folgenden Werten ausgeführt: \n Start: %s, Ende: %s, Thema: %s, Ort: %s", start.toString(), ende.toString(), thema, ort));

            }

        } catch (SQLException e) {
            //todo SEVERE
            System.err.println("Fehler beim Laden der Termine: " + e.getMessage() + " : " + e.getSQLState());
        }

        return ergebnis;

    }
}
