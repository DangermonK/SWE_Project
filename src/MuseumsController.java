import de.dhbwka.swe.utils.gui.SlideshowComponent;
import de.dhbwka.swe.utils.gui.TextComponent;
import util.ElementFactory;
import util.EntityAdapter;

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

        TextComponent text = TextComponent.builder("text").title("Testbox").build();

        frame.add(text);

        frame.setVisible(true);
        frame.pack();

    }

}
