package view;

import de.dhbwka.swe.utils.app.ComponentTest;
import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUIExponatSuchComponent implements IGUIEventListener {

    JPanel suchPanel;
    public void processGUIEvent(GUIEvent guiEvent) {

    }

    public GUIExponatSuchComponent(){
        suchPanel = new JPanel();
        suchPanel.setLayout(new BoxLayout(suchPanel, BoxLayout.Y_AXIS));
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createEtchedBorder()));
        //suchFrame.setLayout(new BoxLayout(, BoxLayout.Y_AXIS));
        JPanel suchBar = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        JPanel tablePane = new JPanel(new BorderLayout());

        JTextField suchFeld = new JTextField("Suchattribut");
        Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        //suchFeld.setBorder(blackline);
        //BorderFactory.createMatteBorder(5,5,5,5, Color.BLACK);
        suchFeld.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),(BorderFactory.createEmptyBorder(3,5,3,5))));
        JButton suchButton = new JButton("suchen");

        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
        JComboBox suchBox = new JComboBox(petStrings);

        suchBar.add(suchFeld);
        suchBar.add(suchBox);
        suchBar.add(suchButton);

        rootPanel.add(suchBar);

        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };

        JTable resultTable = new JTable(data,columnNames);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        tablePane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        tablePane.add(scrollPane);
        rootPanel.add(tablePane);
        suchPanel.add(rootPanel);
        suchPanel.setSize(400,400);
        suchPanel.setVisible(true);
    }

    public JPanel getPane(){
        return  suchPanel;
    }
}
