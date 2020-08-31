package view;

import com.sun.org.apache.xpath.internal.operations.Bool;
import datentypen.ErweiterbareListe;
import de.dhbwka.swe.utils.event.EventCommand;
import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.gui.TextComponent;
import de.dhbwka.swe.utils.model.IListElement;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import model.Besitzer;
import model.Kuenstler;
import util.Property;
import util.Statics;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class GUIExponatBearbeiten extends ObservableComponent implements IGUIEventListener {


    JCheckBox inWebBox;
    AttributeElement[] attElemsLeft;

    private JFrame bearbeitenFrame;

    private int currentImageIndex = 0;

    private String currentRaumnummer;
    private String currentBesitzer;

    private String invNr;
    private List<String> imagePaths = new ArrayList<>();

    private SlideshowComponent slideshow;

    private TextComponent tc;

    private JButton raumButton;
    private JButton kuenstlerButton;
    private JButton foerderungenButton;
    private JButton besitzerButton;
    private JButton historieButton;
    private Map<String,String> besitzerMap;
    private List<ListElement> foerderungen;
    private String currentFoerderungen;
    private String currentKuenstler ="";
    private Kuenstler kuenstler;
    private AttributeComponent attComp = null;

    public GUIExponatBearbeiten(IGUIEventListener listener, String[] comboboxDataExponattyp, String[] comboboxDataKategorie, String[] comboboxDataMaterial) {
        this(new String[0], comboboxDataExponattyp, comboboxDataKategorie, comboboxDataMaterial, null, null, null, false, null, listener);

    }

    public GUIExponatBearbeiten(String[] bildPfade, String[] comboboxDataExponattyp, String[] comboboxDataKategorie, String[] comboboxDataMaterial,
                                String name, String erstellungsjahr, String schaetzwert, Boolean showWeb, String beschreibung, IGUIEventListener listener) {
        this.addObserver(listener);

        imagePaths.addAll(Arrays.asList(bildPfade));

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
                        .mandatory(true)
                        .value(name)
                        .build(),

                AttributeElement.builder("Erstellungsjahr")
                        .labelName("Erstellungsjahr")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(true).allowedChars(AttributeElement.CHARSET_NUMBER).value(erstellungsjahr).build(),

                AttributeElement.builder("Schaetzwert")
                        .labelName("Schätzwert €")
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(true).allowedChars(AttributeElement.CHARSET_FLOAT).maxLength(10).value(schaetzwert).build(),
                //AttributeElement.builder("Futter").labelName("Futter").value(comboboxDataFutter[0]).actionType(AttributeElement.ActionType.COMBOBOX).build()};


                AttributeElement.builder("Kategorie")
                        .labelName("Kategorie")
                        .labelSize(new Dimension(100, 5))
                        .value(comboboxDataKategorie[0])
                        .data(comboboxDataKategorie)
                        .mandatory(true)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build(),

                AttributeElement.builder("Material")
                        .labelName("Material")
                        .labelSize(new Dimension(100, 5))
                        .value(comboboxDataMaterial[0])
                        .data(comboboxDataMaterial)
                        .mandatory(true)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build(),

                AttributeElement.builder("Exponattyp")
                        .labelName("Exponattyp")
                        .labelSize(new Dimension(100, 5))
                        .value(comboboxDataExponattyp[0])
                        .data(comboboxDataExponattyp)
                        .mandatory(true)
                        .actionType(AttributeElement.ActionType.EDITABLE_COMBOBOX).build()
        };


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
                    //System.out.println(currentRaumnummer);
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "raum gui", currentRaumnummer));
                    raumButton.setEnabled(false);

                }

                if (e.getSource() == kuenstlerButton){
                    //fireGUIEvent(new GUIEvent(e.getSource(), () -> "kuenstler gui", null));
                    kuenstlerButton.setEnabled(false);
                    initKuenstlerGUI(kuenstler);

                }

                if (e.getSource() == foerderungenButton){
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "foerderung gui", invNr));
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

    public void initAuswahlPanel(Object[] auswahlDaten, String Elementname, String currentElement){

        new GUIAuswahlPanel(auswahlDaten,Elementname, this, currentElement);

    }

    public void initListAuswahlPanel(ArrayList<IListElement> listElements, String elementname, ArrayList<IListElement>  currentElement){
        new GUIListAuswahl(listElements, elementname, currentElement, this);
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

    public void setInvNr(String invNr) {
        this.invNr = invNr;
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
                    String kategorie = attElemsLeft[3].getValue();
                    Property.getInstance().addPropertyValue(ErweiterbareListe.KATEGORIE, kategorie);
                    String material = attElemsLeft[4].getValue();
                    Property.getInstance().addPropertyValue(ErweiterbareListe.MATERIAL, material);
                    String exponattyp = attElemsLeft[5].getValue();
                    Property.getInstance().addPropertyValue(ErweiterbareListe.EXPONATTYP, exponattyp);

                    String[] nichtEingetragen = attComp.validateMandatoryAttributeValues();


                    if((nichtEingetragen.length>0)){
                        JOptionPane.showMessageDialog(this,
                                "Attribute nicht eingetragen",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);

                    }else if(currentRaumnummer == null){

                        JOptionPane.showMessageDialog(this,
                                    "Raum nicht ausgewählt",
                                    "Daten fehlen",
                                    JOptionPane.ERROR_MESSAGE);

                    }
                    else if (currentBesitzer == null){
                        JOptionPane.showMessageDialog(this,
                                "Besitzer nicht ausgewählt",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);

                    }else if (currentKuenstler.isEmpty()){
                        JOptionPane.showMessageDialog(this,
                                "Kuenstler nicht hinzgefügt",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        String[] data = new String[]{
                                invNr,
                                attElemsLeft[0].getValue(),
                                tc.getText(),
                                kategorie,
                                attElemsLeft[1].getValue(),
                                attElemsLeft[2].getValue(),
                                material,
                                String.valueOf(getInWebBox()),
                                currentKuenstler,
                                imageString,
                                exponattyp,
                                "null:null:null:null:23.2.2010-P100:23.2.2010-P100",besitzerMap.get(currentBesitzer),
                                currentRaumnummer, currentFoerderungen
                        };
                        fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "safe data", data));
                        bearbeitenFrame.dispatchEvent(new WindowEvent(bearbeitenFrame, WindowEvent.WINDOW_CLOSING));
                     }

                    break;
                case "cancel":
                    bearbeitenFrame.dispatchEvent(new WindowEvent(bearbeitenFrame, WindowEvent.WINDOW_CLOSING));
                    break;
            }
        } else if (SlideshowComponent.Commands.IMAGE_SELECTED.equals(guiEvent.getCmd())) {
            ImageElement imageElement = (ImageElement) guiEvent.getData();
            currentImageIndex = imagePaths.indexOf(imageElement.getPath());
        }
        if(guiEvent.getCmdText().equals("Raum ausgewaehlt")){
            raumButton.setEnabled(true);
            currentRaumnummer = guiEvent.getData().toString();
            System.out.println(currentRaumnummer);
        }
        if(guiEvent.getCmdText().equals("Raum closed")){
            raumButton.setEnabled(true);

        }
        if(guiEvent.getCmdText().equals("Historie closed")){
            historieButton.setEnabled(true);

        }
        if(guiEvent.getCmdText().equals("Künstler closed")){
            kuenstlerButton.setEnabled(true);

        }
        if(guiEvent.getCmdText().equals("Besitzer ausgewaehlt")){
            besitzerButton.setEnabled(true);
            currentBesitzer = guiEvent.getData().toString();
            //System.out.println(currentBesitzer);
        }
        if(guiEvent.getCmdText().equals("Besitzer closed")){
            besitzerButton.setEnabled(true);

        }
        if(guiEvent.getCmdText().equals("Förderungen ausgewaehlt")){
            foerderungenButton.setEnabled(true);
            foerderungen = (List<ListElement>) guiEvent.getData();
            int index = 0;
            for (ListElement il: foerderungen) {
                if(index==0){
                    currentFoerderungen= String.valueOf(il.getFoerderungElementHash());
                    index++;
                    continue;
                }
                currentFoerderungen = currentFoerderungen+ "," +il.getFoerderungElementHash();
            }
        }
        if(guiEvent.getCmdText().equals("keineFörderung")){
            currentFoerderungen = null;
            foerderungenButton.setEnabled(true);
        }

        if(guiEvent.getCmdText().equals("Förderungen closed")){
            foerderungenButton.setEnabled(true);

        }

        if(guiEvent.getCmdText().equals("künstler hinzugefügt")){
            kuenstlerButton.setEnabled(true);
            String[] kuenstlerdata = (String[]) guiEvent.getData();

            String name="";
            Date geburtsdatum= null ;
            Date todesdatum ;
            String nationalität="";

            try {
                 name = kuenstlerdata[0];
                 nationalität = kuenstlerdata[3];
                 geburtsdatum  = Statics.dateFormat.parse(kuenstlerdata[1]);
                 todesdatum = Statics.dateFormat.parse(kuenstlerdata[2]);

            } catch (ParseException e) {
                todesdatum = null;
            }
            kuenstler = new Kuenstler(name, geburtsdatum,todesdatum,nationalität);

            String newKuenstler="";
            for (int i = 0; i< kuenstlerdata.length; i++) {

                newKuenstler = newKuenstler + kuenstlerdata[i];
                if(i<kuenstlerdata.length-1){

                    newKuenstler = newKuenstler+",";
                }
            }
            currentKuenstler = newKuenstler;
            System.out.println(newKuenstler);
        }


    }


    public void setCurrentRaum(String raum) {
        currentRaumnummer = raum;

    }

    public void setCurrentKuenstler(Kuenstler kuenstler){
        this.kuenstler = kuenstler;
        Format formatter = new SimpleDateFormat("dd.MM.YYYY");
        String todesdatum ="null";
        if(kuenstler.getTodesdatum()!=null){
            todesdatum = ((SimpleDateFormat) formatter).format(kuenstler.getTodesdatum());
        }

        currentKuenstler = kuenstler.getName()+","+ ((SimpleDateFormat) formatter).format(kuenstler.getGeburtsdatum())+","
                + todesdatum
                +","+kuenstler.getNationalitaet();
    }

    public void initKuenstlerGUI(Kuenstler kuenstler){
        new GUIKuenstler(this, kuenstler);
    }




    public void setBesitzerList(List<Besitzer> besitzerList, Boolean bearbeiten) {
        //TODO : Exponat kann mehrere Besitzer haben?
        if(bearbeiten){
            currentBesitzer = besitzerList.get(0).getName();
        }
        this.besitzerMap = new HashMap<>();
        for (Besitzer b: besitzerList) {
            besitzerMap.put(b.getName(),b.getPersNr());
        }
    }
}
