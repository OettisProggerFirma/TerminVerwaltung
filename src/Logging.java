import java.io.IOException;
import java.util.logging.*;

/**
 * Created by Denis on 18.11.2016.
 */
public class Logging {

    private static FileHandler textFile;
    static private SimpleFormatter textFormatter;

    private static Logger logException;
    private static Logger logDBPara;
    private static Logger logDBVerbindung;
    private static Logger logAbfragenPara;

    public Logging() {
        logException = Logger.getLogger("Exceptionlogger");
        logDBPara = Logger.getLogger("DBParameterlogger");
        logDBVerbindung = Logger.getLogger("DBConnectionlogger");
        logAbfragenPara = Logger.getLogger("Abfragenlogger");

        logAbfragenPara.setLevel(Level.SEVERE);
        logDBPara.setLevel(Level.CONFIG);
        logDBVerbindung.setLevel(Level.INFO);
        logAbfragenPara.setLevel(Level.FINE);
    }

    public static void loggingConf() throws IOException {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        textFile = new FileHandler("logging.properties");
    }

    public static String testFormat() {

        String eins = "ist";
        String zwei = "ein";
        String drei = "Test";

        return String.format("Dies %s %s %s!!!", eins, zwei, drei);

    }


//      a) alle Exceptions mit dem Loglevel SEVERE protokolliert werden

//      b) die Datenbank-Parameter (URL, User, Passwort) mit dem Loglevel CONFIG einmalig vor dem Herstellen
//      der Verbindung protokolliert werden (vom DBVerbinder aus)

//      c ) das Herstellen und Schließen der DB-Verbindung mit dem Logevel INFO protokolliert werden

//      d) alle Parameter für Datenbankabfragen mit dem Loglevel FINE protokolliert werden

//      Sorgen Sie dafür, dass b-d auf der Konsole ausgegeben werden, und die Meldungen mit dem Leverl SEVERE in ein
//      Protokolldatei im Programmverzeichnis geschrieben werden.


}
