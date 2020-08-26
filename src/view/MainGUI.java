package view;

import javax.swing.*;

public class MainGUI {

    public MainGUI(String[] bildPfade, String[] suchAttribute, Object[][] tabellenDaten){
        JFrame mainFrame = new JFrame();

        GUIExponatÜbersicht uebersicht = new GUIExponatÜbersicht(bildPfade,suchAttribute);
        uebersicht.setSuchComponentErgebnisse(tabellenDaten);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Exponat", uebersicht.getPane());

        mainFrame.add(tabbedPane);
        mainFrame.setSize(400,400);
        mainFrame.setVisible(true);
    }
}
