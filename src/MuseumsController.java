import datentypen.Classtype;
import de.dhbwka.swe.utils.gui.SlideshowComponent;
import de.dhbwka.swe.utils.gui.TextComponent;
import model.Exponat;
import util.ElementFactory;
import util.EntityAdapter;
import util.StorageAdapter;

import javax.swing.*;

public class MuseumsController {

    private EntityAdapter entityAdapter;

    public MuseumsController() {
        entityAdapter = new EntityAdapter();

    }

    public static void main(String[] args) {

        MuseumsController controller = new MuseumsController();

        JFrame frame = new JFrame("test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controller.entityAdapter.addElement(Classtype.EXPONAT, new StorageAdapter().loadTestData());

        TextComponent text = TextComponent.builder("text").initialText(((Exponat)controller.entityAdapter.getElement(Classtype.EXPONAT, "E1034")).getName()).title("Testbox").build();

        frame.add(text);

        frame.setVisible(true);
        frame.pack();

    }

}
