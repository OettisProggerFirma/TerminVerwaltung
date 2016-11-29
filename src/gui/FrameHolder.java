package gui;


import Logs.denisLog;
import db.DBVerbindung;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Denis on 20.11.2016.
 */
public class FrameHolder {

    private final JFrame frame;
    private static Logger dateiLog = Logger.getLogger(denisLog.class.getName());
    private static Logger konsolenLog = Logger.getLogger(denisLog.class.getName());
    private static denisLog meine = new denisLog();

    public FrameHolder() {

        dateiLog.addHandler(meine.getDatei());
        konsolenLog.addHandler(meine.getKonsole());
        this.frame = new JFrame("Termine in der Datenbank");

        this.frame.add(new TerminEditor());

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DBVerbindung.schliessen();
            }
        });

        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        DBVerbindung.schliessen();
                        konsolenLog.log(Level.INFO, "SQL-Verbindung ist geschlossen");
                    }
                });
    }
}
