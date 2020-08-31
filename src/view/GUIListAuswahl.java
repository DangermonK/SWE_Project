package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.model.IListElement;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

//GUI für Mehrfachauswahlen mit der SimpleListComponent
public class GUIListAuswahl extends ObservableComponent implements IGUIEventListener {

    private String elementName;
    private SimpleListComponent listComp;
    private JFrame listAuswahl;
    private java.util.List<IListElement> selectedVals = new ArrayList<>();

    public GUIListAuswahl(ArrayList<IListElement> listElements, String elementName, ArrayList<IListElement> currentElements,
                          IGUIEventListener listener) {
        this.addObserver(listener);
        this.elementName = elementName;

        listAuswahl = new JFrame();
        listAuswahl.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fireGUIEvent(new GUIEvent(e.getSource(), () -> elementName + " closed", null));
            }
        });
        listAuswahl.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Baue buttons. Bei Förderungen wird ein zusätzlicher Button: Keine Förderung auswählen benötigt,
        //da es auch möglich sein muss keine Förderung auszuwählen
        ButtonElement[] btns;
        if (elementName == "Förderungen") {
            btns = new ButtonElement[]{
                    ButtonElement.builder("auswählen").buttonText(elementName + " auswählen").type(ButtonElement.Type.BUTTON).build(),
                    ButtonElement.builder("keineauswahl").buttonText("Keine Förderung auswählen").type(ButtonElement.Type.BUTTON).build(),
                    ButtonElement.builder("abbrechen").buttonText("Abbrechen").type(ButtonElement.Type.BUTTON).build()
            };
        } else {
            btns = new ButtonElement[]{
                    ButtonElement.builder("auswählen").buttonText(elementName + " auswählen").type(ButtonElement.Type.BUTTON).build(),
                    ButtonElement.builder("abbrechen").buttonText("Abbrechen").type(ButtonElement.Type.BUTTON).build()
            };
        }
        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(btns)
                .position(ButtonComponent.Position.EAST)
                .stretchButtons()
                .build();

        buttonComp.addObserver(this);

        listComp =
                SimpleListComponent.builder("chooselist")
                        .font(new Font("SansSerif", Font.ITALIC, 10))
                        .selectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
                        .title(elementName)
                        .build();
        listComp.setCellRenderer(new ListComponentCellRenderer());
        listComp.setListElements(listElements);

        listComp.addObserver(this);

        List<Integer> indexe = new ArrayList<>();
        //Gehe alle ListElemente durch und vergleiche mit den zuvor ausgewählten. Speichere Indexe dieser ab.
        //Somit können die Indexe der ListComponent übergeben werden und diese kann die zuletzt ausgwählten Einträge
        //vorselektieren
        for (int i = 0; i < listElements.size(); i++) {
            for (int c = 0; c < currentElements.size(); c++) {
                if (listElements.get(i).getListText().equals(currentElements.get(c).getListText())) {
                    indexe.add(i);
                    System.out.println(i);
                }
            }
        }
        int[] indexArray = indexe.stream().mapToInt(i -> i).toArray();

        //Setze vorselektierte Elemente
        listComp.selectElements(indexArray);
        //Speichere selektierte Elemente in Liste
        selectedVals.add(listComp.getSelectedElement());

        listAuswahl.add(listComp, BorderLayout.CENTER);
        listAuswahl.add(buttonComp, BorderLayout.EAST);

        Border b = BorderFactory.createEmptyBorder(7,7,7,7);
        buttonComp.setBorder(b);
        listComp.setBorder(b);

        listAuswahl.setSize(500, 200);
        listAuswahl.setVisible(true);
    }

    @Override
    public void processGUIEvent(GUIEvent guiEvent) {

        //Speichere die in der Liste selektierte Elemente
        if (SimpleListComponent.Commands.ELEMENT_SELECTED.equals(guiEvent.getCmd())) {
            selectedVals = (List) guiEvent.getData();
        } else if (SimpleListComponent.Commands.MULTIPLE_ELEMENTS_SELECTED.equals(guiEvent.getCmd())) {
            selectedVals = (List) guiEvent.getData();
        }

        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement) guiEvent.getData();
            switch (button.getID()) {
                //Prüfe ob Elemente ausgewählt wurde und wenn ja, schicke Elemente an Bearbeiten GUI
                case "auswählen":
                    System.out.println(selectedVals);
                    if (selectedVals.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                ("Keine " +elementName +" ausgewählt"),
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> (elementName + " ausgewaehlt"), selectedVals));
                    listAuswahl.dispose();
                    break;
                case "abbrechen":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> elementName + " closed", null));
                    listAuswahl.dispose();
                    break;
                //wird benötigt, um keine Förderung auszuwählen.
                // also wenn das exponat bereits eine förderung hatte, kann diese wieder gelöscht werden.
                case "keineauswahl":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> elementName + " keineFörderung", null));
                    listAuswahl.dispose();
            }
        }
    }
}

