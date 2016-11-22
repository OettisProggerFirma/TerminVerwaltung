import Logs.denisLog;
import db.DBVerbindung;

/**
 * Created by Denis on 20.11.2016.
 */
public class Runner {

    public static void main(String[] args) {
        DBVerbindung verbinder;
        verbinder = new DBVerbindung();
        verbinder.verbindungAufbauen("jdbc:postgresql://localhost/termine", "postgres", "password");
        denisLog logTest = new denisLog();
//        logTest.testFormat();
        // new FrameHolder();


        verbinder.schliessen();
    }
}
