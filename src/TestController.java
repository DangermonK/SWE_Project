import de.dhbwka.swe.utils.app.SlideShowComponentApp;
import sun.applet.Main;
import view.*;

import java.io.IOException;

public class TestController {
    public static void main(String[] args) throws IOException {

        Object[][] tabellenDaten = new Object[][]{
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false), "test"},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true), "test"},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false), "test"},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true), "test"},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false), "test"}};

        String[] bildPfade = new String[]{"C:\\_Workspace_\\IdeaProjects\\SWE_Project\\src\\assets\\images\\Schrei1.jpg"};
        new MainGUI(bildPfade, new String[]{"test1","test2"}, tabellenDaten);
    }

}
