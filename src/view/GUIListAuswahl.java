package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.model.IListElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIListAuswahl extends ObservableComponent implements IGUIEventListener {

    private String elementName;
    private SimpleListComponent listComp;
    private  JFrame listAuswahl;
    private java.util.List<IListElement> selectedVals = new ArrayList<>();



    public GUIListAuswahl(ArrayList<IListElement> listElements,String elementName, ArrayList<IListElement> currentElements, IGUIEventListener listener){
        this.addObserver(listener);
        this.elementName = elementName;

         listAuswahl = new JFrame();
        //listAuswahl.setLayout(new BoxLayout(listAuswahl.getContentPane(), BoxLayout.X_AXIS));
        //listAuswahl.setLayout(n);
        //listAuswahl.setLayout(new GridLayout(1,2));

       // JPanel listPanel = new JPanel();
       // JPanel buttonPanel = new JPanel();


        ButtonElement[] btns = new ButtonElement[]{
                ButtonElement.builder("auswählen").buttonText(elementName+" auswählen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("abbrechen").buttonText("Abbrechen").type(ButtonElement.Type.BUTTON).build(),
        };

        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(btns)
                .position(ButtonComponent.Position.EAST)
                .stretchButtons()
                .build();

        buttonComp.addObserver(this);
        //buttonPanel.add(buttonComp);



         listComp =
                SimpleListComponent.builder("chooselist" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .selectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
                        .title(elementName)
                        .build();

         List<Integer> indexe = new ArrayList<>();

         for(int i=0; i<listElements.size();i++){
             for(int c = 0; c<currentElements.size();c++){
                 if(listElements.get(i).getListText().equals(currentElements.get(c).getListText())){
                     indexe.add(i);
                     System.out.println(i);
                 }
             }

         }

        int[] array = indexe.stream().mapToInt(i->i).toArray();



        //historyListComp.setListElements( historyElements );
        listComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        listComp.setListElements(listElements);

        listComp.addObserver(this);
        listComp.selectElements(array);
        selectedVals.add(listComp.getSelectedElement());


        listAuswahl.add(listComp, BorderLayout.CENTER);
        listAuswahl.add(buttonComp, BorderLayout.EAST);

        listAuswahl.setSize(600,300);
        listAuswahl.setVisible(true);


    }

    @Override
    public void processGUIEvent(GUIEvent guiEvent) {

        if(SimpleListComponent.Commands.ELEMENT_SELECTED.equals(guiEvent.getCmd())){
            selectedVals = (List)guiEvent.getData();
        }
        else if(SimpleListComponent.Commands.MULTIPLE_ELEMENTS_SELECTED.equals(guiEvent.getCmd())){
            selectedVals = (List)guiEvent.getData();

        }
        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement) guiEvent.getData();
            switch (button.getID()) {
                case "auswählen":
                    if(selectedVals == null){
                        JOptionPane.showMessageDialog(this,
                                "Keine Förderung ausgewählt",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> (elementName+" ausgewaehlt"), selectedVals));
                    listAuswahl.dispose();
                    break;
                case "abbrechen":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(),() -> elementName+" closed",null));
                    listAuswahl.dispose();
            }


            }
        }

    }

