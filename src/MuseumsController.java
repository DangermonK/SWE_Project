import datentypen.Classtype;
import de.dhbwka.swe.utils.gui.TextComponent;
import model.Exponat;
import util.EntityAdapter;
import util.Statics;
import util.StorageAdapter;
import javax.swing.*;

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

        controller.entityAdapter.addElement(Classtype.EXPONAT, new StorageAdapter().loadTestData());

        Exponat exp = (Exponat)controller.entityAdapter.getElement(Classtype.EXPONAT, "E1034");

        TextComponent text = TextComponent.builder("text").initialText(Statics.dateFormat.format(exp.getKuenstler().getGeburtsdatum())).title("Testbox").build();

        frame.add(text);

        frame.setVisible(true);
        frame.pack();

    }

}
