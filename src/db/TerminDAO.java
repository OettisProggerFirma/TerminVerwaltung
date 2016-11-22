package db;

import Logs.denisLog;
import datenmodell.Termin;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        try (PreparedStatement pstm = DBVerbindung.preparedStatement(sql)) {
            pstm.setObject(1, t.getStart());
            pstm.setObject(2, t.getEnde());
            pstm.setString(3, t.getThema());
            pstm.setString(4, t.getOrt());

            pstm.executeUpdate();

        } catch (SQLException e) {
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
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Termine: " + e.getMessage() + " : " + e.getSQLState());
        }

        return ergebnis;

    }
}
