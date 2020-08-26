import datentypen.Classtype;
import datentypen.Dateiformat;
import de.dhbwka.swe.utils.gui.TextComponent;
import model.Exponat;
import model.Historie;
import model.Kuenstler;
import util.EntityAdapter;
import util.Statics;
import util.StorageAdapter;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MuseumsController {

    private EntityAdapter entityAdapter;

    private StorageAdapter storageAdapter;

    public MuseumsController() {
        entityAdapter = new EntityAdapter();
        storageAdapter = new StorageAdapter();
    }

    public static void main(String[] args) {

        MuseumsController controller = new MuseumsController();

        List<String[]> data = controller.storageAdapter.importData("src/assets/database/TestData.csv", Dateiformat.CSV);
        controller.entityAdapter.createAll(data);

        JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        Exponat exp = (Exponat)controller.entityAdapter.getElement(Classtype.EXPONAT, data.get(0)[0]);

        String exp1Info = TestAusgabe(exp.getInventarnummer(), exp.getName(), exp.getBeschreibung(), exp.getKategorie(), exp.getErstellungsJahr(),
                exp.getSchaetzWert(), exp.getMaterial(), exp.isInWeb(), exp.getKuenstler(), exp.getHistorie());

        TextComponent text = TextComponent.builder("text").editable(false).notEditableColor(Color.BLACK).initialText(exp1Info).title("Testbox").build();

        frame.add(text);

        frame.setVisible(true);

    }

    private static String TestAusgabe(String invNr, String name, String beschreibung, String kategorie, int erstellungsjahr, double schaetzwert, String material,
                                      boolean webanzeige, Kuenstler k, Historie h) {
        return "Inventarnummer: " + invNr + "\nName: " + name + "\nbeschreibung: " + beschreibung + "\nkategorie: " + kategorie + "\nErstellungsjahr: " + erstellungsjahr +
                "\nSchätzwert: " + schaetzwert + "\nMaterial: " + material + "\nWird im web angezeigt: " + (webanzeige ? "Ja" : "Nein") + "\nKünstler: " + k.getName() +
                "\n* " + Statics.dateFormat.format(k.getGeburtsdatum()) + "  † " + Statics.dateFormat.format(k.getTodesdatum()) + "\n" +
                "Historie: Das exponat wurde am " + Statics.dateFormat.format(h.getAnlage().getAnlageDatum()) + " angelegt. Gekauft wurde es am " +
                Statics.dateFormat.format(h.getKaufList().get(0).getErwerbsDatum()) + " für " + h.getKaufList().get(0).getKaufwert() + " €.";
    }

}
