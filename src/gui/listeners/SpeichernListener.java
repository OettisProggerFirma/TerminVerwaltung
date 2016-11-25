package gui.listeners;

import Logs.denisLog;
import datenmodell.Termin;
import db.TerminDAO;
import gui.TerminEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
//todo: Schöne Strings einsetzen

/**
 * Created by Denis on 20.11.2016.
 */
public class SpeichernListener implements ActionListener {
    //    gehört zum logging
    private static Logger dateiLog = Logger.getLogger(denisLog.class.getName());
    private static Logger konsolenLog = Logger.getLogger(denisLog.class.getName());
    private static denisLog meine = new denisLog();
//    gehört zum logging


    private final TerminEditor terminEditor;


    public SpeichernListener(TerminEditor terminEditor) {
        //    gehört zum logging

        dateiLog.addHandler(meine.getDatei());
        konsolenLog.addHandler(meine.getKonsole());
        //    gehört zum logging
        this.terminEditor = terminEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Termin t = terminEditor.holeTermin();
            TerminDAO.speichern(t);
        } catch (RuntimeException e1) {
            //todo SEVERE
            e1.printStackTrace();
        }

    }
}
