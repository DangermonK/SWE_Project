package view;


import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import javax.swing.*;
import java.awt.*;

public class GUIExponatSuchComponent implements IGUIEventListener {

    JPanel suchPanel;

    public GUIExponatSuchComponent(){
        suchPanel = new JPanel();
        suchPanel.setLayout(new BoxLayout(suchPanel, BoxLayout.Y_AXIS));

        suchPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createEtchedBorder()));

        JPanel suchBar = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        JPanel tablePane = new JPanel(new BorderLayout());

        JTextField suchFeld = new JTextField("Suchattribut");

        suchFeld.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),(BorderFactory.createEmptyBorder(3,5,3,5))));
        JButton suchButton = new JButton("suchen");

        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
        JComboBox suchBox = new JComboBox(petStrings);

        suchBar.add(suchFeld);
        suchBar.add(suchBox);
        suchBar.add(suchButton);

        suchPanel.add(suchBar);

        String[] columnNames = {"Inv-Nr.",
                "Name",
                "Raum",
                "Künstler",
                "Kategorie",
                "Änd.-Datum"};

        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false),"test"},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true),"test"},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false),"test"},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true),"test"},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false),"test"}
        };

        //Deaktiviere Zelllenbearbeitung
        JTable resultTable = new JTable(data,columnNames) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(resultTable);

        tablePane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        tablePane.add(scrollPane);
        suchPanel.add(tablePane);

        suchPanel.setSize(400,400);
        suchPanel.setVisible(true);
    }

    public JPanel getPane(){
        return  suchPanel;
    }

    public void processGUIEvent(GUIEvent guiEvent) {

    }
}
