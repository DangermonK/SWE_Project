package view;

import com.sun.corba.se.impl.ior.GenericIdentifiable;
import com.sun.org.apache.xpath.internal.operations.Bool;
import de.dhbwka.swe.utils.event.EventCommand;
import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.EventListener;
import java.util.Map;

public class GUIExponatUebersicht extends ObservableComponent implements IGUIEventListener {

    private JPanel uebersichtPanel;
    private JPanel topPanel;
    private GUIExponatSuchComponent suchGUI;

    private SlideshowComponent slideshow;
    private JPanel leftPanel;

    private ButtonComponent buttonComp;

    public GUIExponatUebersicht(String[] bildPfade, String[] suchAttribute){

        uebersichtPanel = new JPanel();
        uebersichtPanel.setLayout(new GridLayout(2,1));

        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints top = new GridBagConstraints();
        top.weightx=1.0;
        top.weighty=1.0;
        top.gridx=0;
        top.gridy =0;
        top.fill= GridBagConstraints.HORIZONTAL;

        leftPanel = new JPanel(new GridLayout(1,1));
        JPanel rightPanel = new JPanel();

        Border panelBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createEtchedBorder());
        rightPanel.setBorder(panelBorder);
        leftPanel.setBorder(panelBorder);

        slideshow = SlideshowComponent.builder("SSC").smallImageSize(new Dimension(40, 40)).build();
        setUebersichtBilder(bildPfade);

        leftPanel.add(slideshow);
        topPanel.add(leftPanel,top);

        ButtonElement[] buttons = new ButtonElement[]{
                ButtonElement.builder("anlegen").buttonText("anlegen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("bearbeiten").buttonText("bearbeiten").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("loeschen").buttonText("löschen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("details").buttonText("mehr Details").type(ButtonElement.Type.BUTTON).build(),
        };


        buttonComp = ButtonComponent.builder("BC").buttonElements(buttons)
                .position(ButtonComponent.Position.EAST)
                .buttonColors(new Color[]{Color.cyan, null, Color.red, Color.yellow, null})
                .buttonTextColors(new Color[]{null, Color.cyan, null, null, Color.magenta})
                .build();

        buttonComp.addObserver(this);

        rightPanel.add(buttonComp);


        top.weightx=0.0;
        top.gridx=1;
        top.gridy =0;
        top.anchor = GridBagConstraints.NORTH;
        top.fill = GridBagConstraints.VERTICAL;

        topPanel.add(rightPanel,top);
        uebersichtPanel.add((topPanel));

        suchGUI = new GUIExponatSuchComponent(suchAttribute);
        uebersichtPanel.add(suchGUI.getPane());

        //uebersichtPanel.setSize(400,400);
        //uebersichtPanel.setVisible(true);
    }

    public void setUebersichtBilder(String[] bildPfade) {
        if (bildPfade.length == 0 || bildPfade[0].isEmpty()) {
            bildPfade = new String[]{"src/assets/images/keineBilder.jpg"};
        }

        ImageElement[] loadedImageElements = null;
        try{
            loadedImageElements = ImageLoader.loadImageElements(bildPfade);

        }
        catch( Exception e ){
            e.printStackTrace();
        }

        slideshow.setImageElements(loadedImageElements);

    }

    public String getTableSelection() {
        return suchGUI.getSelectionIndex();
    }

    public JPanel getPane(){
        return uebersichtPanel;
    }

    public void setSuchComponentErgebnisse(Object[][] tabellenDaten){
        suchGUI.setTabellenErgebnisse(tabellenDaten);
    }

    public void addTabellenElement(Object[] data) {
        suchGUI.addRow(data);
    }

    public void setGUIEventListener(IGUIEventListener listener) {
        this.addObserver(listener);
        suchGUI.setGUIListener(listener);
    }

    public void initBearbeitenGUI(Map<String, Object> data) {

        new GUIExponatBearbeiten((String[])data.get("bildPfade"),
                (String[])data.get("exponattypen"),
                (String[])data.get("kategorie"),
                (String[])data.get("material"),
                (String)data.get("name"),
                String.valueOf(data.get("erstellungsjahr")),
                String.valueOf(data.get("schaetzwert")),
                Boolean.parseBoolean(data.get("schowInWeb").toString()),
                (String)data.get("beschreibung"),
                this).setInvNr((String)data.get("invNr"));

    }

    @Override
    public void processGUIEvent(GUIEvent guiEvent) {
        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement)guiEvent.getData();
            switch (button.getID()) {
                case "anlegen":
                    buttonComp.enableButtons(false);
                    GUIExponatBearbeiten bearbeiten = new GUIExponatBearbeiten(this);
                    break;
                case "bearbeiten":
                    if(suchGUI.getSelectionIndex() != null) {
                        buttonComp.enableButtons(false);
                        fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "load edit", null));
                    }
                    break;
                case "loeschen":
                    if(suchGUI.getSelectionIndex() != null) {
                        int option = JOptionPane.showConfirmDialog(this, "löschen?", "Exponat löschen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if(option == 0) {
                            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "delete element", null));
                            suchGUI.removeSelectedRow();
                        }
                    }
                    break;
                case "details":
                    if(suchGUI.getSelectionIndex() != null) {
                        fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "open details", null));
                    }
                    break;
            }
        } else if(guiEvent.getCmdText().equals("closed frame")){
            buttonComp.enableButtons(true);
        } else if(guiEvent.getCmdText().equals("safe data")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "safe data", guiEvent.getData()));
        }
        if(guiEvent.getCmdText().equals("raum gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "raum gui", guiEvent.getData()));
        }
        if(guiEvent.getCmdText().equals("foerderung gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "foerderung gui", guiEvent.getData()));
        }
        if(guiEvent.getCmdText().equals("besitzer gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "besitzer gui", guiEvent.getData()));
        }
        if(guiEvent.getCmdText().equals("historie gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "historie gui", guiEvent.getData()));
        }
        if(guiEvent.getCmdText().equals("kuenstler gui")) {
            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "kuenstler gui", guiEvent.getData()));
        }
    }

    public void updateTabellenElement(Object[] data) {
        int index = suchGUI.getIndexOf(data[0].toString());
        if(index != -1) {
            suchGUI.removeRowAt(index);
            suchGUI.insertRow(data, index);
        }
    }
}
