import datentypen.Classtype;
import de.dhbwka.swe.utils.gui.TextComponent;
import model.Exponat;
import model.Kuenstler;
import util.EntityAdapter;
import util.Statics;
import util.StorageAdapter;
import javax.swing.*;
import java.awt.*;

public class MuseumsController {

    private EntityAdapter entityAdapter;

    private StorageAdapter storageAdapter;

    public MuseumsController() {
        entityAdapter = new EntityAdapter();
        storageAdapter = new StorageAdapter();
    }

    public static void main(String[] args) {

        MuseumsController controller = new MuseumsController();

        JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        controller.entityAdapter.createAll(controller.storageAdapter.loadTestData());

        Exponat exp = (Exponat)controller.entityAdapter.getElement(Classtype.EXPONAT, "E0000");
        Exponat exp1 = (Exponat)controller.entityAdapter.getElement(Classtype.EXPONAT, "E1000");
        Exponat exp2 = (Exponat)controller.entityAdapter.getElement(Classtype.EXPONAT, "E1001");

        String exp1Info = TestAusgabe(exp1.getInventarnummer(), exp1.getName(), exp1.getBeschreibung(), exp1.getKategorie(), exp1.getErstellungsJahr(),
                exp1.getSchaetzWert(), exp1.getMaterial(), exp1.isInWeb(), exp1.getKuenstler());
        String exp2Info = TestAusgabe(exp2.getInventarnummer(), exp2.getName(), exp2.getBeschreibung(), exp2.getKategorie(), exp2.getErstellungsJahr(),
                exp2.getSchaetzWert(), exp2.getMaterial(), exp2.isInWeb(), exp2.getKuenstler());

        TextComponent text = TextComponent.builder("text").editable(false).notEditableColor(Color.BLACK).initialText(exp1Info + "\n" + exp2Info).title("Testbox").build();

        frame.add(text);

        frame.setVisible(true);

    }

    private static String TestAusgabe(String invNr, String name, String beschreibung, String kategorie, int erstellungsjahr, double schaetzwert, String material,
                                      boolean webanzeige, Kuenstler k) {
        return "Inventarnummer: " + invNr + "\nName: " + name + "\nbeschreibung: " + beschreibung + "\nkategorie: " + kategorie + "\nErstellungsjahr: " + erstellungsjahr +
                "\nSchätzwert: " + schaetzwert + "\nMaterial: " + material + "\nWird im web angezeigt: " + (webanzeige ? "Ja" : "Nein") + "\nKünstler: " + k.getName() +
                "\n* " + Statics.dateFormat.format(k.getGeburtsdatum()) + "  † " + Statics.dateFormat.format(k.getTodesdatum()) + "\n";
    }

}
