package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.ObservableComponent;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

public class MainGUI extends ObservableComponent {

    private GUIExponatUebersicht uebersicht;

    private JFrame mainFrame;

    public MainGUI(String[] bildPfade, String[] suchAttribute, Object[][] tabellenDaten){
        mainFrame = new JFrame("Museumsverwaltung");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fireGUIEvent(new GUIEvent(e.getSource(), () -> "close Program", null));
            }
        });

        uebersicht = new GUIExponatUebersicht(bildPfade,suchAttribute);
        uebersicht.setSuchComponentErgebnisse(tabellenDaten);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Exponat", uebersicht.getPane());

        mainFrame.add(tabbedPane);
        mainFrame.setSize(400,400);
        mainFrame.setVisible(true);
    }

    public void initBearbeitenGUI(Map<String, Object> data) {
        uebersicht.initBearbeitenGUI(data);
    }

    public void addElement(Object[] data) {
        uebersicht.addTabellenElement(data);
    }

    public void updateElement(Object[] data) {
        uebersicht.updateTabellenElement(data);
    }

    public void setGUIListener(IGUIEventListener listener) {
        this.addObserver(listener);
        uebersicht.setGUIEventListener(listener);
    }

    public void setUebersichtBilder(String[] bildPfade) {
        uebersicht.setUebersichtBilder(bildPfade);
    }

    public String getTableSelection() {
        return uebersicht.getTableSelection();
    }

}
