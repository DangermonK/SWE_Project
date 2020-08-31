package view;

import datentypen.ErweiterbareListe;
import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.model.IListElement;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import model.Besitzer;
import model.Kuenstler;
import util.Property;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

//GUI für die Überischt, die im Tabbed Pane auf der MainGUI dargstellt wird.
public class GUIExponatUebersicht extends ObservableComponent implements IGUIEventListener {

    private JPanel uebersichtPanel;
    private JPanel topPanel;
    private GUIExponatSuchComponent suchGUI;
    private GUIExponatBearbeiten bearbeitenGUI;
    private SlideshowComponent slideshow;
    private JPanel leftPanel;
    private ButtonComponent buttonComp;

    public GUIExponatUebersicht(String[] bildPfade, String[] suchAttribute) {

        uebersichtPanel = new JPanel();
        uebersichtPanel.setLayout(new GridLayout(2, 1));

        //Panel für Slideshow und ButtonComp (leftpanel + rightpanel)
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints top = new GridBagConstraints();

        //Layout Einstellungen für SlideshowPanel
        top.weightx = 1.0;
        top.weighty = 1.0;
        top.gridx = 0;
        top.gridy = 0;
        top.fill = GridBagConstraints.HORIZONTAL;

        //Panel für Slideshow
        leftPanel = new JPanel(new GridLayout(1, 1));
        //Panel für ButtonComp
        JPanel rightPanel = new JPanel();

        Border panelBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createEtchedBorder());
        rightPanel.setBorder(panelBorder);
        leftPanel.setBorder(panelBorder);

        slideshow = SlideshowComponent.builder("SSC").smallImageSize(new Dimension(40, 40)).build();
        //Erzeuge ImageElements und füge sie zur Slideshow hinzu
        setUebersichtBilder(bildPfade);

        leftPanel.add(slideshow);
        topPanel.add(leftPanel, top);

        ButtonElement[] buttons = new ButtonElement[]{
                ButtonElement.builder("anlegen").buttonText("anlegen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("bearbeiten").buttonText("bearbeiten").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("loeschen").buttonText("löschen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("details").buttonText("mehr Details").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("leer").buttonText(" ").type(ButtonElement.Type.BUTTON).backgroundColor(Color.WHITE).enableAtStartup(false).build(),
                ButtonElement.builder("speichern").buttonText("speichern").type(ButtonElement.Type.BUTTON).build(),
        };

        buttonComp = ButtonComponent.builder("BC").buttonElements(buttons)
                .position(ButtonComponent.Position.EAST)
                .build();

        buttonComp.addObserver(this);

        rightPanel.add(buttonComp);

        //Layout einstellungen für buttonPanel
        top.weightx = 0.0;
        top.gridx = 1;
        top.gridy = 0;
        top.anchor = GridBagConstraints.NORTH;
        top.fill = GridBagConstraints.VERTICAL;

        topPanel.add(rightPanel, top);

        uebersichtPanel.add((topPanel));

        //Erstelle SuchComponennt und füge sie hinzu
        suchGUI = new GUIExponatSuchComponent(suchAttribute);
        uebersichtPanel.add(suchGUI.getPane());


    }

    //Erzeuge ImageElements und für sie zur Slideshow hinzu
    public void setUebersichtBilder(String[] bildPfade) {
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


    //Methoden, um auf die SuchComponent zuzugreifen
    public String getTableSelection() {
        return suchGUI.getSelectionIndex();
    }

    public JPanel getPane() {
        return uebersichtPanel;
    }

    public void setSuchComponentErgebnisse(Object[][] tabellenDaten) {
        suchGUI.setTabellenErgebnisse(tabellenDaten);
    }

    public void addTabellenElement(Object[] data) {
        suchGUI.addRow(data);
    }

    public void setGUIEventListener(IGUIEventListener listener) {
        this.addObserver(listener);
        suchGUI.setGUIListener(listener);
    }

    //Methode zum Erzeugen der Bearbeiten GUI, die Daten werden als Map mitgegeben.
    public void initBearbeitenGUI(Map<String, Object> data) {

        bearbeitenGUI = new GUIExponatBearbeiten("Exponat: "+ data.get("name")+ " bearbeiten",
                (String[]) data.get("bildPfade"),
                (String[]) data.get("exponattypen"),
                (String[]) data.get("kategorie"),
                (String[]) data.get("material"),
                (String) data.get("name"),
                String.valueOf(data.get("erstellungsjahr")),
                String.valueOf(data.get("schaetzwert")),
                Boolean.parseBoolean(data.get("schowInWeb").toString()),
                (String) data.get("beschreibung"),
                this);
        bearbeitenGUI.setCurrentRaum(String.valueOf(data.get("raum")));
        //bearbeitenGUI.setBesitzerList((java.util.List<Besitzer>) data.get("besitzer"), true);
        bearbeitenGUI.setInvNr((String) data.get("invNr"));
        Kuenstler k = ((Kuenstler) data.get("kuenstler"));
        bearbeitenGUI.setCurrentKuenstler(k);
        bearbeitenGUI.setBesitzerList((java.util.List<Besitzer>) data.get("besitzer"));
    }

    //Methode zum Erzeugen der Anlege GUI
    public void initAnlegenGUI(Map<String, Object> data) {
        bearbeitenGUI = new GUIExponatBearbeiten(this,
                Property.getInstance().getProperty(ErweiterbareListe.EXPONATTYP),
                Property.getInstance().getProperty(ErweiterbareListe.KATEGORIE),
                Property.getInstance().getProperty(ErweiterbareListe.MATERIAL));
        //bearbeitenGUI.setBesitzerList((java.util.List<Besitzer>) data.get("besitzer"), false);
    }

    //Methode zum Erzeugen eines AuswahlPanels, wird vom Controller aus mit den entsprechenden Daten aufgerufen
    public void initAuswahlPanel(Object[] auswahlDaten, String elementname, String currentElement) {
        bearbeitenGUI.initAuswahlPanel(auswahlDaten, elementname, currentElement);

    }

    //Methode zum Erzeugen eines ListAuswahlPanels, wird vom Controller aus mit den entsprechenden Daten aufgerufen
    public void initListAuswahlPanel(ArrayList<IListElement> listElements, String elementname, ArrayList<IListElement> currentElement) {
        bearbeitenGUI.initListAuswahlPanel(listElements, elementname, currentElement);
    }


    //Events die von untergeordneten GUI Komponenten kommen (Bearbeiten, Anlegen..) und an den Controller weitergereicht werden
    @Override
    public void processGUIEvent(GUIEvent guiEvent) {
        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement) guiEvent.getData();
            switch (button.getID()) {
                case "anlegen":
                    buttonComp.enableButtons(false);
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "load anlegen", null));
                    break;
                case "bearbeiten":
                    if (suchGUI.getSelectionIndex() != null) {
                        buttonComp.enableButtons(false);
                        fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "load edit", null));
                    }
                    break;
                case "loeschen":
                    if (suchGUI.getSelectionIndex() != null) {
                        int option = JOptionPane.showConfirmDialog(this, "löschen?", "Exponat löschen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (option == 0) {
                            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "delete element", null));
                            suchGUI.removeSelectedRow();
                        }
                    }
                    break;
                case "speichern":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "save programm", null));
                    break;
                case "details":
                    if (suchGUI.getSelectionIndex() != null) {
                        fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "open details", null));
                    }
                    break;
            }
        }
        if (guiEvent.getCmdText().equals("closed frame")) {
            buttonComp.enableButtons(true);
        }
        if (guiEvent.getCmdText().equals("safe data")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "safe data", guiEvent.getData()));
        }
        if (guiEvent.getCmdText().equals("raum gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "raum gui", guiEvent.getData()));
        }
        if (guiEvent.getCmdText().equals("foerderung gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "foerderung gui", guiEvent.getData()));
        }
        if (guiEvent.getCmdText().equals("besitzer gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "besitzer gui", guiEvent.getData()));
        }
        if (guiEvent.getCmdText().equals("historie gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "historie gui", guiEvent.getData()));
        }
        if (guiEvent.getCmdText().equals("kuenstler gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "kuenstler gui", guiEvent.getData()));
        }
    }

    public void updateTabellenElement(Object[] data) {
        int index = suchGUI.getIndexOf(data[0].toString());
        if (index != -1) {
            suchGUI.removeRowAt(index);
            suchGUI.insertRow(data, index);
        }
    }
}
