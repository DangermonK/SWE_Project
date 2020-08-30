package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.model.IListElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUIListAuswahl extends ObservableComponent implements IGUIEventListener {

    private String elementName;
    private SimpleListComponent listComp;
    private  JFrame listAuswahl;


    public GUIListAuswahl(ArrayList<IListElement> listElements,String elementName, ArrayList<IListElement> currentElements, IGUIEventListener listener){
        this.addObserver(listener);
        this.elementName = elementName;

         listAuswahl = new JFrame();
        //listAuswahl.setLayout(new BoxLayout(listAuswahl.getContentPane(), BoxLayout.X_AXIS));
        //listAuswahl.setLayout(n);
        //listAuswahl.setLayout(new GridLayout(1,2));

        JPanel listPanel = new JPanel();
        JPanel buttonPanel = new JPanel();


        ButtonElement[] btns = new ButtonElement[]{
                ButtonElement.builder("auswählen").buttonText(elementName+" auswählen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("abbrechen").buttonText("Abbrechen").type(ButtonElement.Type.BUTTON).build(),
        };

        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(btns)
                .position(ButtonComponent.Position.EAST)
                .stretchButtons()
                .build();

        buttonComp.addObserver(this);
        buttonPanel.add(buttonComp);



         listComp =
                SimpleListComponent.builder("chooselist" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .selectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION )
                        .title(elementName)
                        .build();
        //historyListComp.setListElements( historyElements );
        listComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        listComp.setListElements(listElements);
        listComp.addObserver(this);
        listPanel.add(listComp);

        listAuswahl.add(listPanel, BorderLayout.CENTER);
        listAuswahl.add(buttonPanel, BorderLayout.EAST);

        listAuswahl.setSize(600,300);
        listAuswahl.setVisible(true);


    }

    @Override
    public void processGUIEvent(GUIEvent guiEvent) {
        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement) guiEvent.getData();
            switch (button.getID()) {
                case "auswählen":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> (elementName+" ausgewaehlt"), listComp.getSelectedElement()));
                    listAuswahl.dispose();
                    break;
            }
        }
    }
}
