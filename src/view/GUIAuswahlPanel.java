package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;

import javax.swing.*;
import java.awt.*;

public class GUIAuswahlPanel extends ObservableComponent implements IGUIEventListener {

    private AttributeElement[] attElems;
    private JFrame auswahlFrame;
    private String elementName;

    public GUIAuswahlPanel(Object[] listeneintraege, String elementName, IGUIEventListener listener, String currentElement){
        this.addObserver(listener);
        this.elementName = elementName;

        auswahlFrame = new JFrame();
        auswahlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        auswahlFrame.setLayout(new GridLayout(1,2));
        JPanel raumPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        String comboBoxDefaultValue = String.valueOf(listeneintraege[0]);


        if(!currentElement.isEmpty()) {
            for (int i = 0; i < listeneintraege.length; i++) {
                if (listeneintraege[i].toString().equals(currentElement)) {
                    comboBoxDefaultValue = String.valueOf(listeneintraege[i]);
                    break;
                }
            }
        }
        System.out.println(comboBoxDefaultValue);

        attElems = new AttributeElement[]{

                AttributeElement.builder(elementName)
                        .labelName(elementName)
                        .labelSize(new Dimension(50,5))
                        .actionElementSize(new Dimension(300,5))
                        .value(comboBoxDefaultValue)
                        .data(listeneintraege)
                        .mandatory(true)
                        .modificationType(AttributeElement.ModificationType.INTERACTIVE)
                        .actionType(AttributeElement.ActionType.COMBOBOX).build()

        };

        AttributeComponent attComp = null;



        try {
            attComp = AttributeComponent.builder("combobox").attributeElements(attElems).actionElementSizes(new Dimension(100,20)).build();

        } catch (Exception var7) {
            var7.printStackTrace();
        }
        //attComp.setPreferredSize(new Dimension(300,100));
        attComp.setEnabled(true);
        raumPanel.add(attComp);

        ButtonElement[] btns = new ButtonElement[]{
                ButtonElement.builder("auswählen").buttonText(elementName+" auswählen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("abbrechen").buttonText("Abbrechen").type(ButtonElement.Type.BUTTON).build(),
        };

        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(btns)
                .position(ButtonComponent.Position.EAST)
                .stretchButtons()
                .build();

        buttonPanel.add(buttonComp);
        buttonComp.addObserver(this);


        auswahlFrame.add(raumPanel);
        auswahlFrame.add(buttonPanel);

        auswahlFrame.setSize(350,150);
        auswahlFrame.setResizable(false);
        auswahlFrame.setVisible(true);


    }

    @Override
    public void processGUIEvent(GUIEvent guiEvent) {
        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement) guiEvent.getData();
            switch (button.getID()) {
                case "auswählen":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> (elementName+" ausgewaehlt"), attElems[0].getValue()));
                    auswahlFrame.dispose();
                    break;

                case "abbrechen":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(),() -> elementName+" closed",null));
                    auswahlFrame.dispose();
            }
        }
    }
}
