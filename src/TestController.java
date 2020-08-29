import de.dhbwka.swe.utils.app.SimpleListComponentApp;
import de.dhbwka.swe.utils.app.SlideShowComponentApp;
import de.dhbwka.swe.utils.model.IListElement;
import de.dhbwka.swe.utils.model.Person;
import sun.applet.Main;
import view.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestController {
    public static void main(String[] args) throws IOException {

        /*Object[][] tabellenDaten = new Object[][]{
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
        new MainGUI(bildPfade, new String[]{"test1","test2"}, tabellenDaten);*/

        /*Map<String,String> attribute = new HashMap<String,String>();
        attribute.put("Inv-Nr.", "221");

        ArrayList<IListElement> historyElements = new ArrayList<IListElement>();
        historyElements.add((new Person("Willi", "test")));


        GUIExponatDetails gdetail = new GUIExponatDetails(new String[]{"C:\\_Workspace_\\IdeaProjects\\SWE_Project\\src\\assets\\images\\Schrei1.jpg"},
                attribute, "bla",true,false);
        gdetail.setHistorie(historyElements);*/

        String[] bildPfade = new String[]{""};
        String[] comboboxDataFutter = new String[]{"Gras", "Moehren", "Fleisch", "Mäuse"};
        new GUIExponatBearbeiten(bildPfade,comboboxDataFutter,comboboxDataFutter,comboboxDataFutter,"bla",
                "20","8 euro", true, "beschreibungdddddd", null);
    }
}
