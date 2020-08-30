package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import model.Kuenstler;

import javax.swing.*;
import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;

public class GUIKuenstler extends ObservableComponent implements IGUIEventListener {

    private JFrame kuenstlerframe;
    private AttributeElement[] attElements;

    public GUIKuenstler(IGUIEventListener listener, Kuenstler kuenstler){
        this.addObserver(listener);

        kuenstlerframe = new JFrame();
        Format formatter = new SimpleDateFormat("dd.MM.YYYY");



        attElements = new AttributeElement[]{
                AttributeElement.builder("Name")
                        .labelName("Name")
                        .labelSize(new Dimension(100, 5))
                        .value(kuenstler.getName())
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(true)
                        .build(),

                AttributeElement.builder("Geburtsdatum")
                        .labelName("Geburtsdatum")
                        .labelSize(new Dimension(100, 5))
                        .value(formatter.format(kuenstler.getGeburtsdatum()))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).allowedChars(AttributeElement.CHARSET_DATE).build(),

                AttributeElement.builder("Todesdatum")
                        .labelName("Todesdatum")
                        .value(formatter.format(kuenstler.getTodesdatum()))
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).allowedChars(AttributeElement.CHARSET_DATE)
                        .build(),

                AttributeElement.builder("Nationalitaet")
                        .labelName("Nationalität")
                        .value(kuenstler.getNationalitaet())
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(true).build(),
        };

        AttributeComponent attComp = null;


        try {
            attComp = AttributeComponent.builder("attributes").attributeElements(attElements).build();

        } catch (Exception var7) {
            var7.printStackTrace();
        }
        attComp.setEnabled(true);




        ButtonElement[] btns = new ButtonElement[]{
                ButtonElement.builder("hinzufügen").buttonText("Künstler hinzufügen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("abbrechen").buttonText("Abbrechen").type(ButtonElement.Type.BUTTON).build(),
        };

        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(btns)
                .position(ButtonComponent.Position.EAST)
                .stretchButtons()
                .build();

        buttonComp.addObserver(this);
        kuenstlerframe.add(attComp,BorderLayout.CENTER);
        kuenstlerframe.add(buttonComp,BorderLayout.EAST);

        kuenstlerframe.setSize(400,400);
        kuenstlerframe.setVisible(true);

    }

    @Override
    public void processGUIEvent(GUIEvent guiEvent) {
        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement) guiEvent.getData();
            switch (button.getID()) {
                case "hinzufügen":
                    String[] data = new String[]{
                            attElements[0].getValue(),
                            attElements[1].getValue(),
                            attElements[2].getValue(),
                            attElements[3].getValue()
                    };
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(),() -> "künstler hinzugefügt", data));
                    kuenstlerframe.dispose();
                    break;
                case "abbrechen":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(),() -> "Künstler closed",null));
                    kuenstlerframe.dispose();
            }
        }
    }
}
