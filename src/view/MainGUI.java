package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.ObservableComponent;
import de.dhbwka.swe.utils.model.IListElement;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;;
import java.util.ArrayList;
import java.util.Map;

public class MainGUI extends ObservableComponent {

    private GUIExponatUebersicht uebersicht;
    private JFrame mainFrame;
    //Flag das zur Speichern-Abfrage beim Schließen benötigt wird, um ungespeicherte Zustände zu erkennen.
    private boolean update = true;

    public MainGUI(String[] bildPfade, String[] suchAttribute, Object[][] tabellenDaten) {
        mainFrame = new JFrame("Museumsverwaltung");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Listener für die Speicherabfrage beim Fenster schließen
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!update) {
                    int option = JOptionPane.showConfirmDialog(null, "Vor dem Schließen speichern?",
                            "Programm schließen", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (option == 0) {
                        fireGUIEvent(new GUIEvent(e.getSource(), () -> "close Program save", null));

                    }
                    if (option == 1) {
                        fireGUIEvent(new GUIEvent(e.getSource(), () -> "close Program", null));
                    }
                } else {
                    fireGUIEvent(new GUIEvent(e.getSource(), () -> "close Program", null));
                }
            }
        });

        //Erstelle Übersicht GUI
        uebersicht = new GUIExponatUebersicht(bildPfade, suchAttribute);
        //Setze Tabelleneinträge der Exonatsübersicht
        uebersicht.setSuchComponentErgebnisse(tabellenDaten);

        //Exponats Tab, Übersicht GUI in Tab anzeigen
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Exponat", uebersicht.getPane());

        mainFrame.add(tabbedPane);
        mainFrame.setSize(600, 500);
        mainFrame.setVisible(true);
    }

    //Methode zum initalisieren der Bearbeiten GUI, dabei werden die Daten als Map übergeben
    //Wird an Übersicht GUI weitergereicht
    public void initBearbeitenGUI(Map<String, Object> data) {
        uebersicht.initBearbeitenGUI(data);
    }

    //Methode zum initalisieren der Anlegen GUI, dabei werden die Daten als Map übergeben
    //Wird an Übersicht GUI weitergereicht
    public void initAnlegenGUI(Map<String, Object> data) {
        uebersicht.initAnlegenGUI(data);
    }

    //Setze das Flag ob ein Update stattfand, also ob gespeichert werden muss
    public void setUpdate(boolean update) {
        this.update = update;
    }

    //Methode zum Erzeugen eines AuswahlPanels, wird vom Controller aus mit den entsprechenden Daten aufgerufen
    //Wird an Übersicht GUI weitergereicht
    public void initAuswahlPanel(Object[] auswahlDaten, String elementname, String currentElement) {
        uebersicht.initAuswahlPanel(auswahlDaten, elementname, currentElement);
    }

    //Methode zum Erzeugen eines ListAuswahlPanels, wird vom Controller aus mit den entsprechenden Daten aufgerufen
    //Wird an Übersicht GUI weitergereicht
    public void initListAuswahlPanel(ArrayList<IListElement> listElements, String elementname, ArrayList<IListElement> currentElement) {
        uebersicht.initListAuswahlPanel(listElements, elementname, currentElement);
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
