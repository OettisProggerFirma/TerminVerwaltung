package gui;

import db.DBVerbindung;
import db.TerminDAO;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by hmueller on 20.09.2016.
 */


public class FrameHolder {

    private final JFrame frame;


    public FrameHolder() {

        this.frame = new JFrame("Termine in der Datenbank");

        this.frame.add( new TerminEditor() );

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
    }
}
