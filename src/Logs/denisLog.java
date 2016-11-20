package Logs;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Denis on 20.11.2016.
 */
public class denisLog {
    private static FileHandler textFile;
    private static SimpleFormatter textFormatter;
    private static Logger logger;


    public denisLog() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        try {
            textFile = new FileHandler("logging.properties");
        } catch (IOException e) {
            e.printStackTrace();
            logger.getLogger("Die Log-Datei existiert nicht");
        }
    }

    public static void loggingVerbindungAufgebaut() {

    }

    public static String testFormat() {
        String eins = "ist";
        String zwei = "ein";
        String drei = "Test";

        return String.format("Dies %s %s %s!!!", eins, zwei, drei);
    }
}


