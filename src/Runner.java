import com.sun.istack.internal.FragmentContentHandler;
import db.DBVerbindung;
import gui.FrameHolder;

/**
 * Created by hmueller on 20.09.2016.
 */
public class Runner {

    public static void main(String[] args) {

        DBVerbindung.verbindungAufbauen("jdbc:postgresql://localhost/termine", "postgres", "password");

        new FrameHolder();
    }
}
