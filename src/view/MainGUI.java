package view;

import javax.swing.*;

public class MainGUI {

    public MainGUI(){
        JFrame mainFrame = new JFrame();

        //JPanel rootPanel = new JPanel();
        //rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));

        GUIExponatÜbersicht test = new GUIExponatÜbersicht();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Exponat", test.getContentPane());


        mainFrame.add(tabbedPane);

        mainFrame.setSize(400,400);
        mainFrame.setVisible(true);
    }
}
