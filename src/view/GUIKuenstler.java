package view;

import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.*;
import model.Kuenstler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//GUI für das Künstler hinzufügen
public class GUIKuenstler extends ObservableComponent implements IGUIEventListener {

    private JFrame kuenstlerframe;
    private AttributeElement[] attElements;
    private Format formatter;
    private AttributeComponent attComp = null;

    public GUIKuenstler(IGUIEventListener listener, Kuenstler kuenstler) {
        this.addObserver(listener);

        kuenstlerframe = new JFrame();
        kuenstlerframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                fireGUIEvent(new GUIEvent(e.getSource(), () -> "Künstler closed", null));
            }
        });
        kuenstlerframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Datumsformat spezifizieren
        formatter = new SimpleDateFormat("dd.MM.YYYY");

        String name = "";
        String geburtsdatum = "";
        String todesdatum = "";
        String nationalität = "";

        //Wenn schon ein Kuenstler eingetragen war, hole Daten, formatiere Sie, sodass sie in den Attribute Elements
        // angezeigt werden
        if (kuenstler != null) {
            name = kuenstler.getName();
            geburtsdatum = formatter.format(kuenstler.getGeburtsdatum());
            if (kuenstler.getTodesdatum() != null) {
                todesdatum = formatter.format(kuenstler.getTodesdatum());
            }
            nationalität = kuenstler.getNationalitaet();
        }

        attElements = new AttributeElement[]{
                AttributeElement.builder("Name")
                        .labelName("Name")
                        .labelSize(new Dimension(100, 5))
                        .value(name)
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(true)
                        .build(),

                AttributeElement.builder("Geburtsdatum")
                        .labelName("Geburtsdatum")
                        .labelSize(new Dimension(100, 5))
                        .value(geburtsdatum)
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(true).maxLength(10).allowedChars(AttributeElement.CHARSET_DATE).build(),

                AttributeElement.builder("Todesdatum")
                        .labelName("Todesdatum")
                        .value(todesdatum)
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).allowedChars(AttributeElement.CHARSET_DATE)
                        .build(),

                AttributeElement.builder("Nationalitaet")
                        .labelName("Nationalität")
                        .value(nationalität)
                        .labelSize(new Dimension(100, 5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(true).build(),
        };

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
        kuenstlerframe.add(attComp, BorderLayout.CENTER);
        kuenstlerframe.add(buttonComp, BorderLayout.EAST);

        Border b = BorderFactory.createEmptyBorder(7,7,7,7);
        attComp.setBorder(b);
        buttonComp.setBorder(b);

        kuenstlerframe.setSize(400, 150);
        kuenstlerframe.setVisible(true);
        kuenstlerframe.setResizable(false);

    }

    @Override
    public void processGUIEvent(GUIEvent guiEvent) {
        if (ButtonComponent.Commands.BUTTON_PRESSED.equals(guiEvent.getCmd())) {
            ButtonElement button = (ButtonElement) guiEvent.getData();
            switch (button.getID()) {

                //Prüfe ob alle Pflichtangaben getätigt wurden, hole Daten aus Attribute Elements,
                //prüfe Formatierung. Gebe Meldung aus, wenn etwas fehlt/fehlerhaft.
                //Wenn alles ok, schicke Daten an Bearbeiten GUI.
                case "hinzufügen":
                    String[] nichtEingetragen = attComp.validateMandatoryAttributeValues();
                    if (!(nichtEingetragen.length > 0)) {
                        String[] data = new String[]{
                                attElements[0].getValue(),
                                attElements[1].getValue(),
                                attElements[2].getValue(),
                                attElements[3].getValue()
                        };

                        try {
                            formatter.parseObject(data[1]);
                            if (!data[2].isEmpty()) {
                                formatter.parseObject(data[2]);
                            }
                            fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "künstler hinzugefügt", data));
                            kuenstlerframe.dispose();

                        } catch (ParseException e) {
                            JOptionPane.showMessageDialog(this,
                                    "Geburtsdatum falsches Format, korrigiere in: dd.mm.yyyy",
                                    "Format fehlerhaft",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Es wurden nicht alle benötigten Daten eingegeben",
                                "Daten fehlen",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case "abbrechen":
                    fireGUIEvent(new GUIEvent(guiEvent.getSource(), () -> "Künstler closed", null));
                    kuenstlerframe.dispose();
            }
        }
    }
}
