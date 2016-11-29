import Logs.denisLog;
import db.DBVerbindung;
import gui.FrameHolder;

/**
 * Created by Denis on 20.11.2016.
 */
public class Runner {

    public static void main(String[] args) {

        DBVerbindung.verbindungAufbauen("jdbc:postgresql://localhost/termine", "postgres", "password");
        denisLog logTest = new denisLog();
//        logTest.testFormat();
        new FrameHolder();
    }
}
