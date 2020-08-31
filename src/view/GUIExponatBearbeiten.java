package view;

import datentypen.ErweiterbareListe;
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
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

//GUI um ein Exponat zu bearbeiten oder neu anzulegen
public class GUIExponatBearbeiten extends ObservableComponent implements IGUIEventListener {

    private JCheckBox inWebBox;
    private AttributeElement[] attElemsLeft;
    private JFrame bearbeitenFrame;
    private SlideshowComponent slideshow;
    private TextComponent tc;
    private AttributeComponent attComp = null;

    private int currentImageIndex = 0;
    private String currentRaumnummer;
    private String currentBesitzerNrs;
    private String invNr;
    private List<String> imagePaths = new ArrayList<>();
    private Map<String, String> besitzerMap;
    private List<ListElement> foerderungen;
    private List<ListElement> besitzer = new ArrayList<>();
    private String currentFoerderungen;
    private String currentKuenstler = "";
    private Kuenstler kuenstler;

    private JButton raumButton;
    private JButton kuenstlerButton;
    private JButton foerderungenButton;
    private JButton besitzerButton;
    private JButton historieButton;

    //Konstruktur der aufgerufen wird, wenn ein Exponat neu angelegt werden soll. Der Konstruktur ruft dann den
    //vollständigen Konstruktur auf und übergibt leere Werte bei den Feldern die beim Anlegen leer sind
    // (z.B. bildpfade, textfelder etc.)
    public GUIExponatBearbeiten(IGUIEventListener listener, String[] comboboxDataExponattyp, String[] comboboxDataKategorie,
                                String[] comboboxDataMaterial) {
        this("Exponat anlegen",new String[0], comboboxDataExponattyp, comboboxDataKategorie, comboboxDataMaterial, null, null,
                null, false, null, listener);
    }

    //Konstruktur der beim erzeugen der Bearbeiten Ansicht aufgerufen wird.
    // Hier werden die aktuelle Daten des Exponats für die Komponenten übergeben.
    public GUIExponatBearbeiten(String title, String[] bildPfade, String[] comboboxDataExponattyp, String[] comboboxDataKategorie,
                                String[] comboboxDataMaterial, String name, String erstellungsjahr,
                                String schaetzwert, Boolean showWeb, String beschreibung, IGUIEventListener listener) {

        //Füge übergeordnete GUI (Überischt) als Oberserver hinzu
        this.addObserver(listener);

        //Speichere Bildpfade in Liste
        imagePaths.addAll(Arrays.asList(bildPfade));

        bearbeitenFrame = new JFrame(title);
        bearbeitenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bearbeitenFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                //Schicke Event an Übersicht GUI, um die Buttons wieder zu aktivieren.
                fireGUIEvent(new GUIEvent(e.getSource(), () -> "closed frame", null));
            }
        });

        bearbeitenFrame.setLayout(new GridLayout(3, 1, 20, 20));

        //Border für die Panels
        Border panelBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7)
                , BorderFactory.createEtchedBorder());

        //Panel für die Slideshow und Buttons
        JPanel toppanel = new JPanel();

        toppanel.setLayout(new GridBagLayout());
        GridBagConstraints top = new GridBagConstraints();
        //Layout Optionen Slideshow Component
        top.weightx = 0.6;
        top.weighty = 1.0;
        top.gridx = 0;
        top.gridy = 0;
        top.fill = GridBagConstraints.HORIZONTAL;

        slideshow = SlideshowComponent.builder("SSC").smallImageSize(new Dimension(40, 40)).build();
       //Erzeuge ImageElements und füge sie der Slideshow hinzu
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

        //Layout Optionen Button Component
        top.weightx = 0.0;
        top.gridx = 1;
        top.gridy = 0;
        top.anchor = GridBagConstraints.NORTH;
        top.fill = GridBagConstraints.VERTICAL;

        buttonComp.setBorder(panelBorder);
        toppanel.add(buttonComp, top);

        //Panel für die Attribute
        JPanel attributePanel = new JPanel(new GridBagLayout());
        attributePanel.setBorder(panelBorder);
        GridBagConstraints attLayout = new GridBagConstraints();


        /*attLayout.weightx = 1;
        attLayout.weighty = 0.35;
        attLayout.gridx = 0;
        attLayout.gridy = 0;
        attLayout.fill = GridBagConstraints.HORIZONTAL;*/

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



        //Layout Einstellungen für die AttributeComponent
        attLayout.gridx = 0;
        attLayout.gridwidth = 1;
        attLayout.gridy = 0;
        attLayout.weighty = 0.5;
        attLayout.weightx=1;
        attLayout.fill = GridBagConstraints.HORIZONTAL;
        attLayout.insets = new Insets(0, 5, 0, 0);
        attributePanel.add(attComp, attLayout);

        //Panel für Label und Buttons mit denen neue GUIs zur Auswahl und Hinzufügen von Infos (z.B. Raum) geöffnet
        //werden können
        JPanel choosePanel = new JPanel(new GridLayout(6, 2));

        //Action Listener für JButtons
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getSource() == raumButton) {
                    //Sende Event an ÜbersichtGUI, mit aktuellem gesetztem Raum.
                    //Diese leitet dann weiter an den Controller, der das AuswahlPanel mit allen verfügbaren Räumen erzeugt.
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "raum gui", currentRaumnummer));
                    raumButton.setEnabled(false);
                }

                if (e.getSource() == kuenstlerButton) {
                    //Rufe Künstler GUI auf und gebe aktuellen gesetzten Künstler mit. Künstler können nicht ausgewählt werden,
                    //sondern müssen immer von Hand eingetragen werden. Deswegen muss hier nicht über den Controller gegagen werden.
                    kuenstlerButton.setEnabled(false);
                    initKuenstlerGUI(kuenstler);
                }

                if (e.getSource() == foerderungenButton) {
                    //Sende Event an ÜbersichtGUI, mit aktueller Exponats invNR.
                    //Diese leitet dann weiter an den Controller, der das ListAuswahlGUI mit allen verfügbaren Förderungen
                    // aufruft und die aktuell gesetzten Förderungen anhand der invNR ermittelt und vorselektiert.
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "foerderung gui", invNr));
                    foerderungenButton.setEnabled(false);
                }

                if (e.getSource() == besitzerButton) {
                    //Sende Event an ÜbersichtGUI.
                    //Diese leitet dann weiter an den Controller, der das AuswahlPanel mit allen Besitzern erzeugt.
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "besitzer gui", invNr));
                    besitzerButton.setEnabled(false);
                }

                //Historie bearbeiten ist nicht Implementiert. Deswegen erscheint beim Drücken des Buttons eine Meldung.
                //ABER: Anlage und Bearbeitungsdatum werden automatisch in der Historie des Exponats gespeichert.
                if (e.getSource() == historieButton) {
                    JOptionPane.showMessageDialog(bearbeitenFrame,
                            "Historie bearbeiten ist noch nicht möglich.\nDas Anlage- und Änderungsdatum wird jedoch automatisch gesetzt und in der Historie abgespeichert.",
                            "Bald verfügbar",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };



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

        foerderungenButton = new JButton("auswählen");
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

        //Layout Einstellungen für JLabel Button Panel
        attLayout.gridx = 1;
        attLayout.gridwidth = 1;
        attLayout.gridy = 0;
        attLayout.insets = new Insets(0, 50, 0, 0);
        attLayout.weighty = 0.5;

        attributePanel.add(choosePanel, attLayout);

        //JPanel für Beschreibung
        JPanel textFieldPanel = new JPanel();
        tc = TextComponent.builder("textFeld").title("Beschreibung").initialText(beschreibung).build();

        //Border für TextComponent
        Border tb = BorderFactory.createTitledBorder("Beschreibung");
        Border tcBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7), tb);
        tc.setBorder(tcBorder);

        textFieldPanel.add(tc);

        bearbeitenFrame.add(toppanel);
        bearbeitenFrame.add(attributePanel);
        bearbeitenFrame.add(tc);
        bearbeitenFrame.setSize(800, 700);
        bearbeitenFrame.setVisible(true);
    }


    //Methode um ein AuswahlGUI zu initialisieren. Übergibt den Namen des Elements, die Daten der ComboBox und das
    //aktuelle ausgewählte Element, was dann vorselektiert wird.
    public void initAuswahlPanel(Object[] auswahlDaten, String Elementname, String currentElement) {

        new GUIAuswahlPanel(auswahlDaten, Elementname, this, currentElement);
    }

    //Methode um eine ListAuswahlGUI zu inistialiseren. Die verfügbaren Listenelemente sowie die aktuell gewählten
    // Elemente werden übergeben damit diese in der SimpleListComponent vorselektiert werden können
    public void initListAuswahlPanel(ArrayList<IListElement> listElements, String elementname, ArrayList<IListElement> currentElement) {
        new GUIListAuswahl(listElements, elementname, currentElement, this);
    }

    //Erstellt die BildElemente und fügt Sie zur SlideshowComponent hinzu
    public void setBilder(String[] bildPfade) {
        if (bildPfade.length == 0 || bildPfade[0].isEmpty()) {
            bildPfade = new String[]{"src/assets/images/keineBilder.jpg"};
        }

        ImageElement[] loadedImageElements = null;
        try {
            loadedImageElements = ImageLoader.loadImageElements(bildPfade);
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

    //Methode die den Wert eines AttributeElements anhand dessen ID zurückgibt.
    public String getAttElementValue(String id) {

        for (int i = 0; i < attElemsLeft.length; i++) {
            if (attElemsLeft[i].getID().equals(id)) {
                return attElemsLeft[i].getValue();
            }
        }
        return "";
    }

    public void setCurrentRaum(String raum) {
        currentRaumnummer = raum;
    }

    //Methode um den Künstler zu setzen.
    public void setCurrentKuenstler(Kuenstler kuenstler) {
        this.kuenstler = kuenstler;
        //Richtiges Datumsformat
        Format formatter = new SimpleDateFormat("dd.MM.YYYY");
        String todesdatum = "null";
        //Todesdatum kann null sein
        if (kuenstler.getTodesdatum() != null) {
            //formatiere Todesdatum wenn vorhanden
            todesdatum = ((SimpleDateFormat) formatter).format(kuenstler.getTodesdatum());
        }
        //Baue String zusammen, um diesen an den Controller zu geben für das Speichern
        currentKuenstler = kuenstler.getName() + "," + ((SimpleDateFormat) formatter).format(kuenstler.getGeburtsdatum())
                + "," + todesdatum
                + "," + kuenstler.getNationalitaet();
    }

    //Methode um GUI zum Künstler hinzufügen zu öffnen.
    public void initKuenstlerGUI(Kuenstler kuenstler) {
        new GUIKuenstler(this, kuenstler);
    }


    public void setBesitzerList(List<Besitzer> besitzerList) {

        for (int i =0 ;i < besitzerList.size();i++) {
            besitzer.add(new ListElement(besitzerList.get(i), besitzerList.get(i).getPersNr()));
            if(i==0){
                currentBesitzerNrs = besitzer.get(i).getBesitzerPersNr();
            }
            currentBesitzerNrs = currentBesitzerNrs+ ","+besitzer.get(i).getBesitzerPersNr();

        }

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
                //Speicher button: Hier werden die ganzen Daten aus den Elementen geholt und in einem String Array
                //als Datensatz aufgebaut. Dann per Event an die Übersicht und von dort an den Controller geschickt.
                //Dieser speichert dann den Datensatz ab.
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

                    //Prüfe nicht ausgefüllte Pflichtfelder und gebe ggf. Meldung aus
                    String[] nichtEingetragen = attComp.validateMandatoryAttributeValues();
                    if ((nichtEingetragen.length > 0)) {
                        JOptionPane.showMessageDialog(this,
                                "Attribute nicht eingetragen",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);

                    } else if (currentRaumnummer == null) {

                        JOptionPane.showMessageDialog(this,
                                "Raum nicht ausgewählt",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);

                    } else if (currentKuenstler.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                "Kuenstler nicht hinzgefügt",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (besitzer.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                "Besitzer nicht ausgewählt",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Wenn alle Pflichtfelder gesetzt: Baue Datensatz zusammen
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
                                "", currentBesitzerNrs,
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

        if (guiEvent.getCmdText().equals("Raum closed")) {
            raumButton.setEnabled(true);
        }
        if (guiEvent.getCmdText().equals("Besitzer closed")) {
            besitzerButton.setEnabled(true);
        }
        if (guiEvent.getCmdText().equals("Historie closed")) {
            historieButton.setEnabled(true);
        }
        if (guiEvent.getCmdText().equals("Künstler closed")) {
            kuenstlerButton.setEnabled(true);
        }
        //Wenn raum ausgewählt wurde. Aktiviere Button wieder und speichere
        //ausgewählten Raum;
        if (guiEvent.getCmdText().equals("Raum ausgewaehlt")) {
            raumButton.setEnabled(true);
            currentRaumnummer = guiEvent.getData().toString();
        }
        if (guiEvent.getCmdText().equals("Besitzer ausgewaehlt")) {
            besitzerButton.setEnabled(true);
            //currentBesitzer = guiEvent.getData().toString();
            besitzer = (List<ListElement>) guiEvent.getData();
            int index = 0;
            for (ListElement il : besitzer) {
                if (index == 0) {
                    currentBesitzerNrs = il.getBesitzerPersNr();
                    index++;
                    continue;
                }
                currentBesitzerNrs = currentBesitzerNrs + "," + il.getBesitzerPersNr();
            }
        }

        //Wenn Förderungen ausgewählt wurden. Hole den Hash der gewählten Förderungen und setze diese als String zusammen.
        //Dem Controller werden die Förderungen als Hashs übergeben. Dieser baut dann anhand der Hashs die Verknüpfungen
        //zwischen Förderer - Förderung - Exponat auf.
        if (guiEvent.getCmdText().equals("Förderungen ausgewaehlt")) {
            foerderungenButton.setEnabled(true);
            foerderungen = (List<ListElement>) guiEvent.getData();
            int index = 0;
            for (ListElement il : foerderungen) {
                if (index == 0) {
                    currentFoerderungen = String.valueOf(il.getFoerderungElementHash());
                    index++;
                    continue;
                }
                currentFoerderungen = currentFoerderungen + "," + il.getFoerderungElementHash();
            }

        }
        if (guiEvent.getCmdText().equals("keineFörderung")) {
            currentFoerderungen = null;
            foerderungenButton.setEnabled(true);
        }
        if (guiEvent.getCmdText().equals("Förderungen closed")) {
            foerderungenButton.setEnabled(true);
        }

        //Wenn ein Künstler hinzugefügt wurde. Baue den String zur Übergabe fürs Speichern. Außerdem speichere den
        //hinzugefügten Künstler in einem temporären Künstler objekt, falls er vor dem Speichern des Exponats
        // nochmal überarbeitet werden sollte

        if (guiEvent.getCmdText().equals("künstler hinzugefügt")) {
            kuenstlerButton.setEnabled(true);
            String[] kuenstlerdata = (String[]) guiEvent.getData();

            String name = "";
            Date geburtsdatum = null;
            Date todesdatum;
            String nationalität = "";

            //Formatiere String in Datum
            try {
                name = kuenstlerdata[0];
                nationalität = kuenstlerdata[3];
                geburtsdatum = Statics.dateFormat.parse(kuenstlerdata[1]);
                todesdatum = Statics.dateFormat.parse(kuenstlerdata[2]);

            } catch (ParseException e) {
                todesdatum = null;
            }
            //temporäres Kuenstler Objekt
            kuenstler = new Kuenstler(name, geburtsdatum, todesdatum, nationalität);

            //Baue String für die Speicherung
            String newKuenstler = "";
            for (int i = 0; i < kuenstlerdata.length; i++) {

                newKuenstler = newKuenstler + kuenstlerdata[i];
                if (i < kuenstlerdata.length - 1) {
                    newKuenstler = newKuenstler + ",";
                }
            }
            currentKuenstler = newKuenstler;
        }
    }
}
