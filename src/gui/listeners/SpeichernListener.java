package gui.listeners;

import datenmodell.Termin;
import db.TerminDAO;
import gui.TerminEditor;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Created by hmueller on 20.09.2016.
 */

public class SpeichernListener implements ActionListener {

    private final TerminEditor terminEditor;
    // copy start
    private static FileHandler datei;
    private static Logger dateiLog = Logger.getLogger("DAO_Logger_file");
    // copy ende

    public SpeichernListener(TerminEditor terminEditor) {
        this.terminEditor = terminEditor;
        // copy start
        try {
            datei = new FileHandler(String.format("logging_%s.log", LocalDate.now().format(BASIC_ISO_DATE)));
            datei.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(String.format("Das %s konnte nicht geschrieben werden!", e.getClass().getName()));
        }
        dateiLog.addHandler(datei);
        // copy ende

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Termin t = terminEditor.holeTermin();
            TerminDAO.speichern(t);
        } catch (RuntimeException e1) {
            e1.printStackTrace();
            dateiLog.log(Level.SEVERE, String.format("Runtimeexception vom Listener beim Speichern der Termine %s", e1.getMessage()));
        }

    }
}
