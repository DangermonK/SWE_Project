package view;


import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUIExponatSuchComponent implements IGUIEventListener {

    JPanel suchPanel;
    //String[] suchAttribute;
    Object[][] tabellenDaten;
    JTable ergebnisTable;

    public GUIExponatSuchComponent(String[] suchAttribute) {
        suchPanel = new JPanel();
        suchPanel.setLayout(new BoxLayout(suchPanel, BoxLayout.Y_AXIS));

        suchPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createEtchedBorder()));

        JPanel suchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel tabellenPane = new JPanel(new BorderLayout());

        JTextField suchFeld = new JTextField("Suchattribut");
        suchFeld.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), (BorderFactory.createEmptyBorder(3, 5, 3, 5))));
        JButton suchButton = new JButton("suchen");

        //String[] suchAttribute = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
        JComboBox suchBox = new JComboBox(suchAttribute);

        suchBar.add(suchFeld);
        suchBar.add(suchBox);
        suchBar.add(suchButton);

        suchPanel.add(suchBar);

       /* String[] spaltenNamen = {"Inv-Nr.",
                "Name",
                "Raum",
                "Künstler",
                "Kategorie",
                "Änd.-Datum"};*/

        /*tabellenDaten = new Object[][]{
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false), "test"},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true), "test"},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false), "test"},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true), "test"},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false), "test"}
        };*/

        //Deaktiviere Zelllenbearbeitung
        //JTable ergebnisTable = new JTable(tabellenDaten,spaltenNamen) ;
        DefaultTableModel model = new DefaultTableModel() {
            String[] spaltenNamen = {"Inv-Nr.",
                    "Name",
                    "Raum",
                    "Künstler",
                    "Kategorie",
                    "Änd.-Datum"};

            @Override
            public int getColumnCount() {
                return spaltenNamen.length;
            }

            @Override
            public String getColumnName(int index) {
                return spaltenNamen[index];
            }


        };


        ergebnisTable = new JTable(model){

        public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        JScrollPane scrollPane = new JScrollPane(ergebnisTable);

        tabellenPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        tabellenPane.add(scrollPane);
        suchPanel.add(tabellenPane);

        //suchPanel.setSize(400,400);
        //suchPanel.setVisible(true);
    }

    public JPanel getPane(){
        return  suchPanel;
    }

    public void setTabellenErgebnisse(Object[][] tabellenDaten){

        DefaultTableModel model = (DefaultTableModel) ergebnisTable.getModel();

        for(int i= 0; i< tabellenDaten.length;i++){
            model.addRow(tabellenDaten[i]);
        }

        ergebnisTable.setModel(model);

    }

    public void processGUIEvent(GUIEvent guiEvent) {

    }
}
