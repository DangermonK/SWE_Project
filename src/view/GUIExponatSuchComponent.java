package view;


import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.ObservableComponent;
import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;


//GUI Komponente, die die Suchbar und die Tabelle mit den Exponaten enthält. Wird auf der GUIExponatÜbersicht eingebungen
public class GUIExponatSuchComponent extends ObservableComponent implements ListSelectionListener, IGUIEventListener {

    JPanel suchPanel;
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
        JComboBox suchBox = new JComboBox(suchAttribute);

        suchBar.add(suchFeld);
        suchBar.add(suchBox);
        suchBar.add(suchButton);

        suchPanel.add(suchBar);

        //Deaktiviere Zelllenbearbeitung der Tabelle
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

        ergebnisTable = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        ergebnisTable.getTableHeader().setReorderingAllowed(false);
        setTabellenListener(this);

        //Tabelle in Scrollpane, um Scrollable zu machen
        JScrollPane scrollPane = new JScrollPane(ergebnisTable);

        tabellenPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tabellenPane.add(scrollPane);
        suchPanel.add(tabellenPane);
    }

    public void setGUIListener(IGUIEventListener listener) {
        this.addObserver(listener);
    }

    public JPanel getPane() {
        return suchPanel;
    }

    //Methode um Tabellendaten zu aktualisieren
    public void setTabellenErgebnisse(Object[][] tabellenDaten) {

        DefaultTableModel model = (DefaultTableModel) ergebnisTable.getModel();

        for (int i = 0; i < tabellenDaten.length; i++) {
            model.addRow(tabellenDaten[i]);
        }
        ergebnisTable.setModel(model);
    }

    //Methode um Daten an bestimmter Zeilenindex hinzuzufügen
    public void insertRow(Object[] data, int row) {
        ((DefaultTableModel) ergebnisTable.getModel()).insertRow(row, data);
        selectRow(row);
    }

    public void addRow(Object[] data) {
        ((DefaultTableModel) ergebnisTable.getModel()).addRow(data);
    }

    public void removeSelectedRow() {
        removeRowAt(ergebnisTable.getSelectedRow());
    }

    public void removeRowAt(int index) {
        ((DefaultTableModel) ergebnisTable.getModel()).removeRow(index);
    }

    //Listener für Zeilenauswahl hinzufügen
    public void setTabellenListener(ListSelectionListener listener) {
        ListSelectionModel model = ergebnisTable.getSelectionModel();
        model.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        model.addListSelectionListener(listener);
        ergebnisTable.setSelectionModel(model);
    }

    public void selectRow(int row) {
        ergebnisTable.getSelectionModel().setLeadSelectionIndex(row);
    }

    //Index von Exponat anhand InvNr holen
    public int getIndexOf(String invNr) {
        for (int i = 0; i < ergebnisTable.getRowCount(); i++) {
            if (ergebnisTable.getValueAt(i, 0).toString().equals(invNr)) {
                return i;
            }
        }
        return -1;
    }

    //Hole InvNr einer bestimmten Zeile
    public String getSelectionIndex() {
        if (ergebnisTable.getSelectedRow() <= -1)
            return null;

        return ergebnisTable.getValueAt(ergebnisTable.getSelectedRow(), ergebnisTable.getColumn("Inv-Nr.").getModelIndex()).toString();
    }

    public void processGUIEvent(GUIEvent guiEvent) {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (getSelectionIndex() != null) {
            //event an controller, um Bilder der Slideshow für neues selektiertes Element zu ändern.
            fireGUIEvent(new GUIEvent(e.getSource(), () -> "selected element", null));
        }
    }
}
