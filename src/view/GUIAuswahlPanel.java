package view;

import de.dhbwka.swe.utils.gui.AttributeComponent;
import de.dhbwka.swe.utils.gui.AttributeElement;
import de.dhbwka.swe.utils.gui.ButtonComponent;
import de.dhbwka.swe.utils.gui.ButtonElement;

import javax.swing.*;
import java.awt.*;

public class GUIAuswahlPanel {


    public GUIAuswahlPanel(Object[] listeneintraege, String elementName){


        JFrame raumFrame = new JFrame();
        raumFrame.setLayout(new GridLayout(1,2));
        JPanel raumPanel = new JPanel();
        JPanel buttonPanel = new JPanel();


        AttributeElement[] attElems = new AttributeElement[]{

                AttributeElement.builder(elementName)
                        .labelName(elementName)
                        .labelSize(new Dimension(50,5))
                        .actionElementSize(new Dimension(300,5))
                        .value(String.valueOf(listeneintraege[0]))
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


        raumFrame.add(raumPanel);
        raumFrame.add(buttonPanel);

        raumFrame.setSize(350,150);
        raumFrame.setResizable(false);
        raumFrame.setVisible(true);


    }
}
