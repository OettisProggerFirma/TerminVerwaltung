import com.sun.istack.internal.FragmentContentHandler;
import db.DBVerbindung;
import gui.FrameHolder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.*;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Created by hmueller on 20.09.2016.
 */

public class Runner {

    //todo insert statements
    private final static String url = "jdbc:postgresql://localhost/termine";
    private final static String user = "postgres";
    private final static String password = "password";

    public static void main(String[] args) {

        DBVerbindung.verbindungAufbauen(url, user, password);

        new FrameHolder();
    }
}
