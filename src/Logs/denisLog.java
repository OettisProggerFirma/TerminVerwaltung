package Logs;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Created by Denis on 20.11.2016.
 */
public class denisLog {
    private static FileHandler datei;
    private static ConsoleHandler konsole;
    private static Logger dateiLog = Logger.getLogger(denisLog.class.getName());
    private static Logger konsolenLog = Logger.getLogger(denisLog.class.getName());

    /**
     * Created by Denis on 20.11.2016.
     */
    public denisLog() {

        try {
            datei = new FileHandler(String.format("logging_%s.log", LocalDate.now().format(BASIC_ISO_DATE)));
            datei.setFormatter(new SimpleFormatter());
        } catch (IOException e) {
            e.printStackTrace();
            konsolenLog.log(Level.SEVERE, String.format("Das %s konnte nicht geschrieben werden!", e.getClass().getName()));
        }

        konsole = new ConsoleHandler();

        dateiLog.addHandler(datei);
        konsolenLog.addHandler(konsole);
    }

    public FileHandler getDatei() {
        return datei;
    }

    public ConsoleHandler getKonsole() {
        return konsole;
    }


    //
//    public static void testFormat() {
//        String eins = "ist";
//        String zwei = "ein";
//        String drei = "Test";
//
//        String tester = String.format("Dies %s %s %s!!!", eins, zwei, drei);
//        dateiLog.log(Level.INFO, tester);
//        konsolenLog.log(Level.INFO, tester);
//    }
}


