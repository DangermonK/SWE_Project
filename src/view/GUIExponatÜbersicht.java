package view;

import com.sun.corba.se.impl.ior.GenericIdentifiable;
import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.gui.ButtonComponent;
import de.dhbwka.swe.utils.gui.ButtonElement;
import de.dhbwka.swe.utils.gui.SlideshowComponent;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUIExponatÜbersicht implements IGUIEventListener {

    JPanel uebersichtPanel;
    JPanel topPanel;
    GUIExponatSuchComponent suchGUI;


    public GUIExponatÜbersicht(String[] bildPfade, String[] suchAttribute){

        uebersichtPanel = new JPanel();
        uebersichtPanel.setLayout(new GridLayout(2,1));

        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints top = new GridBagConstraints();
        top.weightx=1.0;
        top.weighty=1.0;
        top.gridx=0;
        top.gridy =0;
        top.fill= GridBagConstraints.HORIZONTAL;

        JPanel leftPanel = new JPanel(new GridLayout(1,1));
        JPanel rightPanel = new JPanel();

        Border panelBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createEtchedBorder());
        rightPanel.setBorder(panelBorder);
        leftPanel.setBorder(panelBorder);


        ImageElement[] loadedImageElements = null;
        try{
            loadedImageElements = ImageLoader.loadImageElements(bildPfade);

        }
        catch( Exception e ){
            e.printStackTrace();
        }

        SlideshowComponent slideshow =   SlideshowComponent.builder("SSC").imageElements(loadedImageElements)
                .smallImageSize(new Dimension(40, 40)).build();

        leftPanel.add(slideshow);
        topPanel.add(leftPanel,top);

        ButtonElement[] buttons = new ButtonElement[]{
                ButtonElement.builder("anlegen").buttonText("anlegen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("bearbeiten").buttonText("bearbeiten").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("loeschen").buttonText("löschen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("details").buttonText("mehr Details").type(ButtonElement.Type.BUTTON).build(),
        };

        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(buttons)
                .position(ButtonComponent.Position.EAST)
                .buttonColors(new Color[]{Color.cyan, null, Color.red, Color.yellow, null})
                .buttonTextColors(new Color[]{null, Color.cyan, null, null, Color.magenta})
                .build();
        //buttonComp.addObserver(this);

        rightPanel.add(buttonComp);

        top.weightx=0.0;
        top.gridx=1;
        top.gridy =0;
        top.anchor = GridBagConstraints.NORTH;
        top.fill = GridBagConstraints.VERTICAL;

        topPanel.add(rightPanel,top);
        uebersichtPanel.add((topPanel));

        suchGUI = new GUIExponatSuchComponent(suchAttribute);
        uebersichtPanel.add(suchGUI.getPane());

        //uebersichtPanel.setSize(400,400);
        //uebersichtPanel.setVisible(true);
    }

    public JPanel getPane(){
        return uebersichtPanel;
    }

    public void setSuchComponentErgebnisse(Object[][] tabellenDaten){
        suchGUI.setTabellenErgebnisse(tabellenDaten);
    }

    public void processGUIEvent(GUIEvent guiEvent) {

    }
}
