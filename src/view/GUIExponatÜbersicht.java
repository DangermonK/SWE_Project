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

    JPanel uebersichtFrame;
    JPanel toppanel;
    public void processGUIEvent(GUIEvent guiEvent) {

    }

    public GUIExponatÜbersicht(){

        uebersichtFrame = new JPanel();
        uebersichtFrame.setLayout(new GridLayout(2,1));



        toppanel = new JPanel();
        toppanel.setLayout(new GridBagLayout());
        GridBagConstraints top = new GridBagConstraints();
        top.weightx=1.0;
        top.weighty=1.0;
        top.gridx=0;
        top.gridy =0;

        top.fill= GridBagConstraints.HORIZONTAL;

        JPanel leftPanel = new JPanel(new GridLayout(1,1));
        JPanel rightPanel = new JPanel();

        //Border blackline = BorderFactory.createEtchedBorder();
        Border leftBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createEtchedBorder());
        rightPanel.setBorder(leftBorder);
        leftPanel.setBorder(leftBorder);

        ImageElement[] loadedImageElements = null;
        try{
            loadedImageElements = ImageLoader.loadImageElements("C:\\users\\master\\desktop" );
        }
        catch( Exception e ){
            e.printStackTrace();
        }

        SlideshowComponent slideshow =   SlideshowComponent.builder("SSC").imageElements(loadedImageElements).smallImageSize(new Dimension(40, 40)).build();

        leftPanel.add(slideshow);
        toppanel.add(leftPanel,top);



        ButtonElement[] btns = new ButtonElement[]{
                ButtonElement.builder("addPic").buttonText("Bild hinzufügen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("removePic").buttonText("Bild entfernen").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("save").buttonText("speichern").type(ButtonElement.Type.BUTTON).build(),
                ButtonElement.builder("cancel").buttonText("abbrechen").type(ButtonElement.Type.BUTTON).build(),
        };

        ButtonComponent buttonComp = ButtonComponent.builder("BC").buttonElements(btns)
                .position(ButtonComponent.Position.EAST)

                //.stretchButtons()
                //.title("test")

                .buttonColors(new Color[]{Color.cyan, null, Color.red, Color.yellow, null})
                .buttonTextColors(new Color[]{null, Color.cyan, null, null, Color.magenta})
                //.buttonFonts(new Font[]{null, this.testFont1, this.testFont2, this.testFont3, this.testFont3})
                //.buttonSize(new Dimension(100, 40))
                .build();
        //buttonComp.addObserver(this);


        top.weightx=0.0;
        top.gridx=1;
        top.gridy =0;
        top.anchor = GridBagConstraints.NORTH;
        top.fill = GridBagConstraints.VERTICAL;


        //top.insets = new Insets(0,5,0,5);

        //Border blackline = BorderFactory.createLineBorder(Color.BLACK);
        //buttonComp.setBorder(blackline);
        rightPanel.add(buttonComp);
        toppanel.add(rightPanel,top);
        uebersichtFrame.add((toppanel));

        GUIExponatSuchComponent suchGUI = new GUIExponatSuchComponent();
        uebersichtFrame.add(suchGUI.getPane());

        uebersichtFrame.setSize(400,400);
        uebersichtFrame.setVisible(true);
    }

    public JPanel getContentPane(){
        return uebersichtFrame;
    }
}
