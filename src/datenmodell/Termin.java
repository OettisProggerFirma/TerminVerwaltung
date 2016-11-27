package datenmodell;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.*;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Created by hmueller on 20.09.2016.
 */


public class Termin {

    private final LocalDateTime start;
    private final LocalDateTime ende;
    private final String thema;
    private final String ort;
    private static FileHandler datei;
    private static Logger dateiLog = Logger.getLogger("DAO_Logger_file");


    public Termin(LocalDateTime start, LocalDateTime ende, String thema, String ort) {
        try {
            datei = new FileHandler(String.format("logging_%s.log", LocalDate.now().format(BASIC_ISO_DATE)));
            datei.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(String.format("%s konnte nicht geschrieben werden!", e.getClass().getName()));
        }
        dateiLog.addHandler(datei);
        if (!start.isBefore(ende)) {
            dateiLog.log(Level.SEVERE, String.format("Der Start muss vor dem Ende liegen!"));

            throw new IllegalArgumentException("Der Start muss vor dem Ende liegen!");
        }
        this.start = start;
        this.ende = ende;
        this.thema = thema;
        this.ort = ort;
    }


    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnde() {
        return ende;
    }

    public String getThema() {
        return thema;
    }

    public String getOrt() {
        return ort;
    }

}
