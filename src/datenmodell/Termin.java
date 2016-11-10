package datenmodell;

import java.time.LocalDateTime;

/**
 * Created by hmueller on 20.09.2016.
 */
public class Termin {

    private final LocalDateTime start;
    private final LocalDateTime ende;
    private final String thema;
    private final String ort;


    public Termin(LocalDateTime start, LocalDateTime ende, String thema, String ort) {
        if( !start.isBefore(ende) ){
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
