package gui;

import datenmodell.Termin;
import db.TerminDAO;
import gui.listeners.SpeichernListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

/**
 * Created by hmueller on 20.09.2016.
 */
public class TerminEditor extends JPanel {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

    private final JTextField start;
    private final JTextField ende;
    private final JTextField ort;
    private final JTextArea thema;


    public TerminEditor() {

        this.setLayout(new BorderLayout(5, 5));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.start = new JTextField(FORMATTER.format(LocalDateTime.now()));
        this.ende = new JTextField(FORMATTER.format(LocalDateTime.now()));

        this.thema = new JTextArea(5, 40);
        this.ort = new JTextField(20);

        JPanel startPanel = new JPanel();
        startPanel.add(start);
        startPanel.setBorder(BorderFactory.createTitledBorder("Beginn"));

        JPanel endPanel = new JPanel();
        endPanel.add(ende);
        endPanel.setBorder(BorderFactory.createTitledBorder("Ende"));

        JPanel ortPanel = new JPanel();
        ortPanel.add(this.ort);
        ortPanel.setBorder(BorderFactory.createTitledBorder("Ort"));

        JPanel themaPanel = new JPanel();
        themaPanel.setBorder(BorderFactory.createTitledBorder("Thema"));
        themaPanel.add(thema );

        top.add(startPanel);
        top.add(endPanel);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(ortPanel);

        JButton leeren = new JButton("Leeren");
        leeren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leeren();
            }
        });

        bottom.add(leeren);

        JButton speichern = new JButton("Speichern");
        speichern.addActionListener(new SpeichernListener(this));

        bottom.add(speichern);

        this.add(top, BorderLayout.NORTH);
        this.add(themaPanel, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);
    }

    public void anzeigen(Termin t) {
        this.start.setText(FORMATTER.format(t.getStart()));
        this.ende.setText(FORMATTER.format(t.getEnde()));
        this.thema.setText(t.getThema());
        this.ort.setText(t.getOrt());
    }

    public void leeren() {
        this.start.setText("dd.mm.yy hh:mm");
        this.ende.setText("dd.mm.yy hh:mm");
        this.thema.setText("");
        this.ort.setText("");
    }

    public Termin holeTermin() {
        LocalDateTime startzeit = null;
        LocalDateTime endzeit = null;

        try {
            startzeit = LocalDateTime.parse(this.start.getText(), FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Ungültige Startzeit: dd.mm.yy hh:mm", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            throw e;
        }

        try {
            endzeit = LocalDateTime.parse(this.ende.getText(), FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Ungültige Endzeit: dd.mm.yy hh:mm", "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            throw e;
        }

        if (startzeit != null && endzeit != null) {
            return new Termin(startzeit, endzeit, this.thema.getText(), this.ort.getText());
        } else {
            throw new RuntimeException("Das sollte nicht passieren...");
        }
    }

}
