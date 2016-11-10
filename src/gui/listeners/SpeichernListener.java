package gui.listeners;

import datenmodell.Termin;
import db.TerminDAO;
import gui.TerminEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hmueller on 20.09.2016.
 */
public class SpeichernListener implements ActionListener {

    private final TerminEditor terminEditor;


    public SpeichernListener(TerminEditor terminEditor ) {
        this.terminEditor = terminEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Termin t = terminEditor.holeTermin();
            TerminDAO.speichern(t);
        } catch (RuntimeException e1) {
            e1.printStackTrace();
        }

    }
}
