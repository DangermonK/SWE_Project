package view;

import de.dhbwka.swe.utils.event.EventCommand;
import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.gui.TextComponent;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUIExponatBearbeiten extends ObservableComponent implements IGUIEventListener {


    JCheckBox inWebBox;
    AttributeElement[] attElemsLeft;

    private JFrame bearbeitenFrame;

    private int currentImageIndex = 0;

    private List<String> imagePaths = new ArrayList<>();

    private SlideshowComponent slideshow;

    private TextComponent tc;

    private JButton raumButton;
    private JButton kuenstlerButton;
    private JButton foerderungenButton;
    private JButton besitzerButton;
    private JButton historieButton;

    public GUIExponatBearbeiten(IGUIEventListener listener) {
        this(new String[]{""}, new String[]{""}, new String[]{""}, new String[]{""}, null, null, null, false, null, listener);

    }

    public GUIExponatBearbeiten(String[] bildPfade, String[] comboboxDataExponattyp, String[] comboboxDataKategorie, String[] comboboxDataMaterial,
                                String name, String erstellungsjahr, String schaetzwert, Boolean showWeb, String beschreibung, IGUIEventListener listener) {
        this.addObserver(listener);

        bearbeitenFrame = new JFrame("Bearbeiten");
        bearbeitenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bearbeitenFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fireGUIEvent(new GUIEvent(e.getSource(), () -> "closed frame", null));
            }
        });

        bearbeitenFrame.setLayout(new GridLayout(3, 1, 20, 20));

        Border panelBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7), BorderFactory.createEtchedBorder());

        JPanel toppanel = new JPanel();

        toppanel.setLayout(new GridBagLayout());
        GridBagConstraints top = new GridBagConstraints();
        top.weightx = 0.6;
        top.weighty = 1.0;
        top.gridx = 0;
        top.gridy = 0;
        top.fill = GridBagConstraints.HORIZONTAL;


        slideshow = SlideshowComponent.builder("SSC").smallImageSize(new Dimension(40, 40)).build();
        setBilder(bildPfade);

        slideshow.addObserver(this);

        slideshow.setBorder(panelBorder);
        toppanel.add(slideshow, top);

        ButtonElement[] btns = new ButtonElement[]{
                ButtonElement.builder("addPic").buttonText("Bild hinzufügen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("removePic").buttonText("Bild entfernen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("leer").buttonText(" ").type(ButtonElement.Type.BUTTON).backgroundColor(Color.WHITE).enableAtStartup(false).build(),
                ButtonElement.builder("save").buttonText("speichern").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("cancel").buttonText("abbrechen").type(ButtonElement.Type.BUTTON).build(),
        };

        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(btns)
                .position(ButtonComponent.Position.EAST)
                .stretchButtons()
                .build();
        buttonComp.addObserver(this);

        top.weightx = 0.0;
        top.gridx = 1;
        top.gridy = 0;
        top.anchor = GridBagConstraints.NORTH;
        top.fill = GridBagConstraints.VERTICAL;

        buttonComp.setBorder(panelBorder);
        toppanel.add(buttonComp, top);

        JPanel attributePanel = new JPanel(new GridBagLayout());
        // attributePanel.setBackground(Color.white);
        attributePanel.setBorder(panelBorder);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 0.35;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;


        attElemsLeft = new AttributeElement[]{
                AttributeElement.builder("Name")
                        .labelName("Name")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10)
                        .value(name)
                        .build(),

                AttributeElement.builder("Erstellungsjahr")
                        .labelName("Erstellungsjahr")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value(erstellungsjahr).build(),

                AttributeElement.builder("Schaetzwert")
                        .labelName("Schätzwert €")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value(schaetzwert).build(),
                //AttributeElement.builder("Futter").labelName("Futter").value(comboboxDataFutter[0]).actionType(AttributeElement.ActionType.COMBOBOX).build()};


                AttributeElement.builder("Kategorie")
                        .labelName("Kategorie")
                        .labelSize(new Dimension(100, 5))
                        .value(comboboxDataKategorie[0])
                        .data(comboboxDataKategorie)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build(),

                AttributeElement.builder("Material")
                        .labelName("Material")
                        .labelSize(new Dimension(100, 5))
                        .value(comboboxDataMaterial[0])
                        .data(comboboxDataMaterial)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build(),

                AttributeElement.builder("Exponattyp")
                        .labelName("Exponattyp")
                        .labelSize(new Dimension(100, 5))
                        .value(comboboxDataExponattyp[0])
                        .data(comboboxDataExponattyp)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build()
        };

        AttributeComponent attComp = null;


        try {
            attComp = AttributeComponent.builder("leftAttributes").attributeElements(attElemsLeft).build();

        } catch (Exception var7) {
            var7.printStackTrace();
        }
        attComp.setEnabled(true);


        //attElemsLeft[5].setLabelBGColor(Color.RED);


        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 0;
        c.weighty = 0.5;
        c.insets = new Insets(0, 5, 0, 0);
        attributePanel.add(attComp, c);

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == raumButton){
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "raum gui", null));
                    raumButton.setEnabled(false);

                }

                if (e.getSource() == kuenstlerButton){
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "kuenstler gui", null));
                    kuenstlerButton.setEnabled(false);

                }

                if (e.getSource() == foerderungenButton){
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "foerderung gui", null));
                    foerderungenButton.setEnabled(false);
                }
                if (e.getSource() == besitzerButton){
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "besitzer gui", null));
                    besitzerButton.setEnabled(false);
                }
                if (e.getSource() == historieButton){
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "historie gui", null));
                    historieButton.setEnabled(false);
                }
            }
        };

        JPanel choosePanel = new JPanel(new GridLayout(6, 2));

        JLabel raumLabel = new JLabel("Raum");
        JLabel kuenstlerLabel = new JLabel("Künstler");
        JLabel foerderungenLabel = new JLabel("Förderungen");
        JLabel besitzerLabel = new JLabel("Besitzer");
        JLabel historieLabel = new JLabel("Historie");
        JLabel inWebLabel = new JLabel("Im Web anzeigen");

         raumButton = new JButton("auswählen");
         raumButton.addActionListener(buttonListener);


         kuenstlerButton = new JButton("bearbeiten");
        kuenstlerButton.addActionListener(buttonListener);
         foerderungenButton = new JButton("bearbeiten");
        foerderungenButton.addActionListener(buttonListener);
        besitzerButton = new JButton("auswählen");
        besitzerButton.addActionListener(buttonListener);
        historieButton = new JButton("bearbeiten");
        historieButton.addActionListener(buttonListener);

        inWebBox = new JCheckBox();
        inWebBox.setSelected(showWeb);
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
        c.insets = new Insets(0, 50, 0, 0);
        c.weighty = 0.5;

        attributePanel.add(choosePanel, c);

        JPanel textFieldPanel = new JPanel();
        tc = TextComponent.builder("textFeld").title("Beschreibung").initialText(beschreibung).build();
        textFieldPanel.add(tc);


        bearbeitenFrame.add(toppanel);
        bearbeitenFrame.add(attributePanel);
        bearbeitenFrame.add(tc);
        bearbeitenFrame.setSize(500, 700);
        bearbeitenFrame.setVisible(true);


    }

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

    public Boolean getInWebBox() {
        return inWebBox.isSelected();
    }

    public String getAttElementValue(String id) {

        for (int i = 0; i < attElemsLeft.length; i++) {
            if (attElemsLeft[i].getID().equals(id)) {
                return attElemsLeft[i].getValue();
            }

        }
        return "";

    }

    public void processGUIEvent(GUIEvent guiEvent) {
        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement) guiEvent.getData();
            switch (button.getID()) {
                case "addPic":
                    JFileChooser fc = new JFileChooser();
                    fc.setMultiSelectionEnabled(true);

                    int returnVal = fc.showDialog(this, "hinzufügen");

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        for (File file : fc.getSelectedFiles()) {
                            String[] name = file.getName().split("\\.");
                            if (name[1].toLowerCase().equals("jpg") && !imagePaths.contains(file.getAbsolutePath()))
                                imagePaths.add(file.getAbsolutePath());
                        }
                        setBilder(imagePaths.toArray(new String[]{}));
                    }
                    break;
                case "removePic":
                    if (!imagePaths.isEmpty()) {
                        imagePaths.remove(currentImageIndex);
                    }
                    setBilder(imagePaths.toArray(new String[]{}));
                    break;
                case "save":
                    String imageString = "";
                    for (String path : imagePaths) {
                        imageString += "Bild," + path + "-";
                    }
                    String[] data = new String[]{
                            "E1000",
                            attElemsLeft[0].getValue(),
                            tc.getText(),
                            attElemsLeft[3].getValue(),
                            attElemsLeft[1].getValue(),
                            attElemsLeft[2].getValue(),
                            attElemsLeft[4].getValue(),
                            String.valueOf(getInWebBox()),
                            "So Jin,12.3.1975,null,Koreanisch",
                            imageString,
                            attElemsLeft[5].getValue(),
                            "null:null:null:null:23.2.2010-P100:23.2.2010-P100",
                            "P102",
                            "1"
                    };
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "safe data", data));
                    bearbeitenFrame.dispatchEvent(new WindowEvent(bearbeitenFrame, WindowEvent.WINDOW_CLOSING));
                    break;
                case "cancel":
                    bearbeitenFrame.dispatchEvent(new WindowEvent(bearbeitenFrame, WindowEvent.WINDOW_CLOSING));
                    break;
            }
        } else if (SlideshowComponent.Commands.IMAGE_SELECTED.equals(guiEvent.getCmd())) {
            ImageElement imageElement = (ImageElement) guiEvent.getData();
            currentImageIndex = imagePaths.indexOf(imageElement.getPath());
        }


    }


}