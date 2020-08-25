package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.gui.TextComponent;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class GUIExponatBearbeiten implements IGUIEventListener {

    public GUIExponatBearbeiten(){
        JFrame bearbeitenFrame = new JFrame();
        bearbeitenFrame.setLayout(new GridLayout(3,1,20,20));

        JPanel toppanel = new JPanel();
        toppanel.setLayout(new GridBagLayout());
        GridBagConstraints top = new GridBagConstraints();
        top.weightx=0.6;
        top.weighty=1.0;
        top.gridx=0;
        top.gridy =0;

        top.fill= GridBagConstraints.HORIZONTAL;

        ImageElement[] loadedImageElements = null;
        try{
            loadedImageElements = ImageLoader.loadImageElements("C:\\users\\master\\desktop" );
        }
        catch( Exception e ){
            e.printStackTrace();
        }

        SlideshowComponent slideshow =   SlideshowComponent.builder("SSC").imageElements(loadedImageElements).smallImageSize(new Dimension(40, 40)).build();

        toppanel.add(slideshow,top);



        ButtonElement[] btns = new ButtonElement[]{
                ButtonElement.builder("addPic").buttonText("Bild hinzufügen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("removePic").buttonText("Bild entfernen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("save").buttonText("speichern").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("cancel").buttonText("abbrechen").type(ButtonElement.Type.BUTTON).build(),
        };

        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(btns)
                .position(ButtonComponent.Position.EAST)
                .stretchButtons()
                .title("test")

                .buttonColors(new Color[]{Color.cyan, null, Color.red, Color.yellow, null})
                .buttonTextColors(new Color[]{null, Color.cyan, null, null, Color.magenta})
                //.buttonFonts(new Font[]{null, this.testFont1, this.testFont2, this.testFont3, this.testFont3})
                //.buttonSize(new Dimension(100, 40))
                .build();
        //buttonComp.addObserver(this);

        top.weightx=0.4;
        top.gridx=1;
        top.gridy =0;


        toppanel.add(buttonComp,top);

        JPanel attributePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 0.35;
        c.gridx=0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;


        String[] comboboxDataFutter = new String[]{"Gras", "Moehren", "Fleisch", "Mäuse"};
        String[] comboboxDataKatzen = new String[]{"Maunz", "Tommy", "Mieze"};
        AttributeElement[] attElemsLeft = new AttributeElement[]{
                AttributeElement.builder("Name")
                        .labelName("Name")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10)
                        .value("ebbes")
                        .build(),

                AttributeElement.builder("Erstellungsjahr")
                        .labelName("Erstellungsjahr")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),

                AttributeElement.builder("Schaetzwert")
                        .labelName("Schätzwert €")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),
                //AttributeElement.builder("Futter").labelName("Futter").value(comboboxDataFutter[0]).actionType(AttributeElement.ActionType.COMBOBOX).build()};



                AttributeElement.builder("Kategorie")
                        .labelName("Kategorie").value(comboboxDataFutter[0])
                        .labelSize(new Dimension(100,5))
                        .value(comboboxDataFutter[0])
                        .data(comboboxDataFutter)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build(),

                AttributeElement.builder("Material")
                        .labelName("Material").value(comboboxDataFutter[0])
                        .labelSize(new Dimension(100,5))
                        .value(comboboxDataFutter[0])
                        .data(comboboxDataFutter)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build(),

                AttributeElement.builder("Exponattyp")
                        .labelName("Exponattyp").value(comboboxDataFutter[0])
                        .labelSize(new Dimension(100,5))
                        .value(comboboxDataFutter[0])
                        .data(comboboxDataFutter)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build()
        };

        AttributeComponent attComp = null;



        try {
            attComp = AttributeComponent.builder("leftAttributes").attributeElements(attElemsLeft).build();

        } catch (Exception var7) {
            var7.printStackTrace();
        }
        attComp.setEnabled(true);


        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 0;
        c.weighty=0.5;
        c.insets = new Insets(0,5,0,0);
        attributePanel.add(attComp,c);

        JPanel choosePanel = new JPanel(new GridLayout(6,2));

        JLabel raumLabel = new JLabel("Raum");
        JLabel kuenstlerLabel = new JLabel("Künstler");
        JLabel foerderungenLabel = new JLabel("Förderungen");
        JLabel besitzerLabel = new JLabel("Besitzer");
        JLabel historieLabel = new JLabel("Historie");
        JLabel inWebLabel = new JLabel("Im Web anzeigen");

        JButton raumButton = new JButton("auswählen");
        JButton kuenstlerButton = new JButton("bearbeiten");
        JButton foerderungenButton = new JButton("bearbeiten");
        JButton besitzerButton = new JButton("auswählen");
        JButton historieButton = new JButton("bearbeiten");

        JCheckBox inWebBox = new JCheckBox();
        inWebBox.setHorizontalAlignment(SwingConstants.RIGHT);

        choosePanel.add(raumLabel);
        choosePanel.add(raumButton);

        choosePanel.add(kuenstlerLabel);
        choosePanel.add(kuenstlerButton);

        choosePanel.add(foerderungenLabel);
        choosePanel.add(foerderungenButton);

        choosePanel.add(besitzerLabel);
        choosePanel.add(besitzerButton);

        choosePanel.add(historieLabel);
        choosePanel.add(historieButton);

        choosePanel.add(inWebLabel);
        choosePanel.add(inWebBox);


        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 0;
        c.insets = new Insets(0,50,0,0);
        c.weighty=0.5;

        attributePanel.add(choosePanel,c);

        JPanel textFieldPanel = new JPanel();
        TextComponent tc = TextComponent.builder("textFeld").title("Beschreibung").build();
        //tc.setAlignmentX(SwingConstants.CENTER);


        //attributePanel.add(tc);
        textFieldPanel.add(tc);

        c.weighty=0.35;
        bearbeitenFrame.add(toppanel,c);
        bearbeitenFrame.add(attributePanel,c);
        bearbeitenFrame.add(tc);

        bearbeitenFrame.setSize(400,400);
        bearbeitenFrame.setVisible(true);

    }

    public void processGUIEvent(GUIEvent guiEvent) {

    }
}
