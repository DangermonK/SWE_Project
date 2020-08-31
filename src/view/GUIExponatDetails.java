package view;

import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.model.IListElement;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import de.dhbwka.swe.utils.gui.TextComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import java.util.ArrayList;
import java.util.Map;


//GUI für die Detailansicht
public class GUIExponatDetails {

    private SimpleListComponent kuenstlerListComp;
    private SimpleListComponent besitzerListComp;
    private SimpleListComponent foerderungListComp;
    private SimpleListComponent exponattypListComp;
    private SimpleListComponent historyListComp;

    private SlideshowComponent slideshow;

    //Attribute werden als vom Controller als Map übergeben
    public GUIExponatDetails(String[] bildPfade, Map<String, Object> attribute) {

        JFrame detailFrame = new JFrame("Detailansicht " + attribute.get("Name"));
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setLayout(new GridBagLayout());
        GridBagConstraints main = new GridBagConstraints();

        //Layout Einstellungen für DetailFrame
        main.gridx = 0;
        main.weightx = 1;
        main.weighty = 0.2;
        main.gridwidth = 1;
        main.gridy = 0;
        main.gridheight = 1;
        main.insets = new Insets(-20, 0, 0, 0);
        main.fill = GridBagConstraints.HORIZONTAL;

        //Panel für Slideshow
        JPanel slidePanel = new JPanel();
        slidePanel.setLayout(new BorderLayout());
        slideshow = SlideshowComponent.builder("SSC").smallImageSize(new Dimension(40, 40)).build();

        //Erstelle ImageElements und füge sie zur SlideshowComponent hinzu
        setBilder(bildPfade);

        slideshow.setPreferredSize(new Dimension(500, 250));
        slidePanel.add(slideshow, BorderLayout.PAGE_START);

        //Border für die Components
        Border panelBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createEtchedBorder());
        slidePanel.setBorder(panelBorder);

        detailFrame.add(slidePanel, main);


        JPanel attributePanel = new JPanel(new GridBagLayout());
        GridBagConstraints attributeLayout = new GridBagConstraints();

        //Layout Einstellungen für das Attribute Panel
        attributeLayout.gridx = 0;
        attributeLayout.weightx = 0.5;
        attributeLayout.gridwidth = 1;
        attributeLayout.gridy = 0;
        attributeLayout.gridheight = 1;
        attributeLayout.fill = GridBagConstraints.HORIZONTAL;

        //Attribute der linken Component
        AttributeElement[] attElemsLeft = new AttributeElement[]{
                AttributeElement.builder("Inv-Nr.")
                        .labelName("Inv-Nr.")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10)
                        .value((String) attribute.get("Inv-Nr."))
                        .build(),

                AttributeElement.builder("Schaetzwert")
                        .labelName("Schätzwert €")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value(String.valueOf(attribute.get("Schaetzwert"))).build(),

                AttributeElement.builder("Material")
                        .labelName("Material")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).value((String) attribute.get("Material")).build(),
        };

        AttributeComponent attComp = null;

        //Attribute der rechten Component
        AttributeElement[] attElemsRight = new AttributeElement[]{
                AttributeElement.builder("Name")
                        .labelName("Name")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).value((String) attribute.get("Name")).build(),

                AttributeElement.builder("Erstellungsjahr")
                        .labelName("Erstellungsjahr")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value(String.valueOf(attribute.get("Erstellungsjahr"))).build(),

                AttributeElement.builder("Raum")
                        .labelName("Raum")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value(String.valueOf(attribute.get("Raum"))).build(),

                AttributeElement.builder("Kategorie")
                        .labelName("Kategorie")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).value((String) attribute.get("Kategorie")).build(),
        };
        AttributeComponent attComp2 = null;

        //Bearbeitung der Attributfelder deaktivieren
        for (AttributeElement ae : attElemsLeft) {
            ((JTextField) ((AttributeElement) ae).getComponent(1)).setEditable(false);
        }

        for (AttributeElement ae : attElemsRight) {
            ((JTextField) ((AttributeElement) ae).getComponent(1)).setEditable(false);
        }

        try {
            attComp = AttributeComponent.builder("leftAttributes").attributeElements(attElemsLeft).build();
            attComp2 = AttributeComponent.builder("rightAttributes").attributeElements(attElemsRight).build();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        attComp.setEnabled(true);
        attComp2.setEnabled(true);


        //AttributComponent links hinzufügen
        attributeLayout.insets = new Insets(0, 0, 0, 30);
        attributePanel.add(attComp, attributeLayout);

        //AttributComponent rechts hinzufügen
        attributeLayout.gridx = 1;
        attributeLayout.weightx = 0.5;
        attributeLayout.insets = new Insets(0, 0, 0, 0);
        attributePanel.add(attComp2, attributeLayout);

        //TextComponent unter linke Attribute Component fügen
        TextComponent tc = TextComponent.builder("textFeld").editable(false).title("Beschreibung").initialText((String) attribute.get("beschreibung")).build();

        attributeLayout.gridx = 0;
        attributeLayout.gridy = 1;
        attributeLayout.insets = new Insets(0, 0, 0, 30);
        attributePanel.add(tc, attributeLayout);

        //Panel für Checkboxen
        JPanel checkBoxes = new JPanel(new GridBagLayout());
        GridBagConstraints checkboxLayout = new GridBagConstraints();
        checkboxLayout.gridx = 0;
        checkboxLayout.weightx = 0.5;
        checkboxLayout.gridwidth = 1;
        checkboxLayout.gridy = 0;
        checkboxLayout.gridheight = 1;
        checkboxLayout.fill = GridBagConstraints.HORIZONTAL;
        checkboxLayout.insets = new Insets(0, 0, 0, 0);

        JLabel labelInMuseum = new JLabel("Ist im Museum:");
        JCheckBox cbInMuseum = new JCheckBox();
        cbInMuseum.setHorizontalTextPosition(SwingConstants.LEFT);
        cbInMuseum.setEnabled(false);
        cbInMuseum.setSelected((Boolean) attribute.get("isInMuseum"));
        JLabel labelInWeb = new JLabel("Im Web anzeigen:");
        JCheckBox cbInWeb = new JCheckBox();
        cbInWeb.setEnabled(false);
        cbInWeb.setSelected((Boolean) attribute.get("isInWeb"));
        cbInWeb.setHorizontalTextPosition(SwingConstants.LEFT);
        checkBoxes.add(labelInMuseum, checkboxLayout);

        checkboxLayout.gridx = 1;
        checkboxLayout.insets = new Insets(0, 0, 0, 0);
        checkBoxes.add(cbInMuseum, checkboxLayout);

        checkboxLayout.insets = new Insets(0, 0, 0, 0);
        checkboxLayout.gridx = 0;
        checkboxLayout.gridy = 1;
        checkboxLayout.weightx = 0.5;
        checkBoxes.add(labelInWeb, checkboxLayout);

        checkboxLayout.insets = new Insets(0, 0, 0, 0);
        checkboxLayout.gridx = 1;
        checkboxLayout.gridy = 1;
        checkBoxes.add(cbInWeb, checkboxLayout);

        cbInMuseum.setHorizontalAlignment(SwingConstants.RIGHT);
        cbInWeb.setHorizontalAlignment(SwingConstants.RIGHT);

        //checkBox Panel unter rechte Attribute Component fügen
        attributeLayout.insets = new Insets(0, 0, 0, 0);
        attributeLayout.gridx = 1;
        attributePanel.add(checkBoxes, attributeLayout);
        attributePanel.setBorder(panelBorder);

        main.weighty = 0.3;
        main.gridy = 1;
        detailFrame.add(attributePanel, main);

        //ListComponents initalisieruen
        historyListComp =
                SimpleListComponent.builder("historyList")
                        .font(new Font("SansSerif", Font.ITALIC, 10))
                        .selectionMode(ListSelectionModel.SINGLE_SELECTION)
                        .title("History")
                        .build();
        historyListComp.setCellRenderer(new ListComponentCellRenderer());

        foerderungListComp =
                SimpleListComponent.builder("historyList")
                        .font(new Font("SansSerif", Font.ITALIC, 10))
                        .title("Förderung")
                        .selectionMode(ListSelectionModel.SINGLE_SELECTION)
                        .build();
        foerderungListComp.setCellRenderer(new ListComponentCellRenderer());

        besitzerListComp =
                SimpleListComponent.builder("historyList")
                        .font(new Font("SansSerif", Font.ITALIC, 10))
                        .selectionMode(ListSelectionModel.SINGLE_SELECTION)
                        .title("Besitzer")
                        .build();
        besitzerListComp.setCellRenderer(new ListComponentCellRenderer());

        exponattypListComp =
                SimpleListComponent.builder("historyList")
                        .font(new Font("SansSerif", Font.ITALIC, 10))
                        .title("Exponattyp")
                        .selectionMode(ListSelectionModel.SINGLE_SELECTION)
                        .build();
        exponattypListComp.setCellRenderer(new ListComponentCellRenderer());

        kuenstlerListComp =
                SimpleListComponent.builder("historyList")
                        .font(new Font("SansSerif", Font.ITALIC, 10))
                        .title("Künstler")
                        .selectionMode(ListSelectionModel.SINGLE_SELECTION)
                        .build();
        kuenstlerListComp.setCellRenderer(new ListComponentCellRenderer());

        //Panel für List Components
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        listPanel.add(historyListComp);
        listPanel.add(foerderungListComp);
        listPanel.add(besitzerListComp);
        listPanel.add(exponattypListComp);
        listPanel.add(kuenstlerListComp);

        listPanel.setBorder(panelBorder);

        main.gridy = 2;
        main.weighty = 0.5;
        main.insets = new Insets(-40, 0, 0, 0);
        detailFrame.add(listPanel, main);

        detailFrame.setSize(600, 750);
        detailFrame.setVisible(true);
    }

    //Methode um ImageElements zu erstellen und zur Slideshow hinzuzufügen
    public void setBilder(String[] bildPfade) {
        if (bildPfade.length == 0 || bildPfade[0].isEmpty()) {
            bildPfade = new String[]{"src/assets/images/keineBilder.jpg"};
        }

        ImageElement[] loadedImageElements = null;
        try {
            loadedImageElements = ImageLoader.loadImageElements(bildPfade);
            loadedImageElements[0].setName("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        slideshow.setImageElements(loadedImageElements);
    }

    //Methoden um die Inhalte der SimpleListComponents zu setzen
    public void setKuenstler(ArrayList<IListElement> kuenstler) {
        kuenstlerListComp.setListElements(kuenstler);
    }

    public void setFoerderungen(ArrayList<IListElement> foerderungen) {
        foerderungListComp.setListElements(foerderungen);
    }

    public void setHistorie(ArrayList<IListElement> historie) {
        historyListComp.setListElements(historie);
    }

    public void setExponattyp(ArrayList<IListElement> exponattyp) {
        exponattypListComp.setListElements(exponattyp);
    }

    public void setBesitzer(ArrayList<IListElement> besitzer) {
        besitzerListComp.setListElements(besitzer);
    }

}
