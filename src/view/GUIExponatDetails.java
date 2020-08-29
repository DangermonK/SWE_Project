package view;

import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.model.IListElement;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.util.ImageLoader;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import de.dhbwka.swe.utils.gui.TextComponent;
import java.util.ArrayList;
import java.util.Map;

public class GUIExponatDetails {

    SimpleListComponent kuenstlerListComp;
    SimpleListComponent besitzerListComp;
    SimpleListComponent foerderungListComp;
    SimpleListComponent exponattypListComp;
    SimpleListComponent historyListComp;

    private SlideshowComponent slideshow;

    public GUIExponatDetails(String []bildPfade , Map<String,Object> attribute) {

        JFrame detailFrame = new JFrame("Detailansicht " + attribute.get("Name"));
        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailFrame.setLayout(new GridBagLayout());
        GridBagConstraints main = new GridBagConstraints();

        main.gridx=0;
        main.weightx=1;
        main.weighty=0.2;
        main.gridwidth=1;
        main.gridy = 0;
        main.gridheight =1;
        main.fill = GridBagConstraints.HORIZONTAL;


        //JPanel slidePanel = new JPanel(new GridLayout(1,1));
        JPanel slidePanel = new JPanel();
        slidePanel.setLayout(new BorderLayout());

        slideshow =   SlideshowComponent.builder("SSC").smallImageSize(new Dimension(40, 40)).build();
        setBilder(bildPfade);
        slideshow.setPreferredSize(new Dimension(500,250));

        slidePanel.add(slideshow, BorderLayout.PAGE_START);
        Border panelBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),BorderFactory.createEtchedBorder());
        slidePanel.setBorder(panelBorder);
        detailFrame.add(slidePanel,main);

        main.fill = GridBagConstraints.HORIZONTAL;

        JPanel attributePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx=0;
        c.weightx=0.5;
        c.gridwidth=1;
        c.gridy = 0;
        c.gridheight =1;
        c.fill = GridBagConstraints.HORIZONTAL;


        AttributeElement[] attElemsLeft = new AttributeElement[]{
                AttributeElement.builder("Inv-Nr.")
                        .labelName("Inv-Nr.")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10)
                        .value((String) attribute.get("Inv-Nr."))
                        .build(),

                AttributeElement.builder("Schaetzwert")
                        .labelName("Schätzwert €")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value(String.valueOf(attribute.get("Schaetzwert"))).build(),

                AttributeElement.builder("Material")
                        .labelName("Material")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value((String) attribute.get("Material")).build(),
                };

        AttributeComponent attComp = null;

        AttributeElement[] attElemsRight = new AttributeElement[]{
                AttributeElement.builder("Name")
                        .labelName("Name")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value((String) attribute.get("Name")).build(),

                AttributeElement.builder("Erstellungsjahr")
                        .labelName("Erstellungsjahr")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value(String.valueOf(attribute.get("Erstellungsjahr"))).build(),

                AttributeElement.builder("Raum")
                        .labelName("Raum")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value(String.valueOf(attribute.get("Raum"))).build(),

                AttributeElement.builder("Kategorie")
                        .labelName("Kategorie")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value((String) attribute.get("Kategorie")).build(),
        };
        AttributeComponent attComp2 = null;

        //Bearbeitung der Attributfelder deaktivieren
        for (AttributeElement ae: attElemsLeft) {
            ((JTextField)((AttributeElement) ae).getComponent(1)).setEditable(false);
        }

        for (AttributeElement ae: attElemsRight) {
            ((JTextField)((AttributeElement) ae).getComponent(1)).setEditable(false);
        }

        try {
            attComp = AttributeComponent.builder("leftAttributes").attributeElements(attElemsLeft).build();
            attComp2 = AttributeComponent.builder("rightAttributes").attributeElements(attElemsRight).build();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        attComp.setEnabled(true);

        attComp2.setEnabled(true);


        //AttributComponent links hinzufügen
        c.insets = new Insets(0,0,0,30);
        attributePanel.add(attComp,c);

        //AttributComponent rechts hinzufügen
        c.gridx=1;
        c.weightx=0.5;
        c.insets = new Insets(0,0,0,0);
        attributePanel.add(attComp2,c);

        TextComponent tc = TextComponent.builder("textFeld").editable(false).title("Beschreibung").initialText((String) attribute.get("beschreibung")).build();
        c.gridx=0;
        c.gridy=1;
        c.insets = new Insets(0,0,0,30);
        attributePanel.add(tc,c);


        JPanel checkBoxes = new JPanel(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        d.gridx=0;
        d.weightx=0.5;
        d.gridwidth=1;
        d.gridy = 0;
        d.gridheight =1;
        d.fill = GridBagConstraints.HORIZONTAL;
        d.insets = new Insets(0,0,0,0);

        JLabel labelInMuseum = new JLabel("Ist im Museum:");
        JCheckBox cbInMuseum = new JCheckBox();
        cbInMuseum.setHorizontalTextPosition(SwingConstants.LEFT);
        cbInMuseum.setEnabled(false);
        cbInMuseum.setSelected((Boolean) attribute.get("isInMuseum"));
        JLabel labelInWeb = new JLabel("Im Web anzeigen:");
        JCheckBox cbInWeb = new JCheckBox();
        cbInWeb.setEnabled(false);
        cbInWeb.setSelected((Boolean) attribute.get("isInWeb"));
        cbInWeb.setHorizontalTextPosition(SwingConstants.LEFT);
        checkBoxes.add(labelInMuseum,d);

        d.gridx=1;
        d.insets = new Insets(0,0,0,0);
        checkBoxes.add(cbInMuseum,d);

        d.insets = new Insets(0,0,0,0);
        d.gridx=0;
        d.gridy=1;
        d.weightx=0.5;
        checkBoxes.add(labelInWeb,d);

        d.insets = new Insets(0,0,0,0);
        d.gridx=1;
        d.gridy=1;
        checkBoxes.add(cbInWeb,d);

        cbInMuseum.setHorizontalAlignment(SwingConstants.RIGHT);
        cbInWeb.setHorizontalAlignment(SwingConstants.RIGHT);


        c.insets = new Insets(0,0,0,0);
        c.gridx=1;
        attributePanel.add(checkBoxes,c);

        attributePanel.setBorder(panelBorder);
        main.weighty=0.3;
        main.gridy = 1;
       // main.fill = GridBagConstraints.BOTH;
        detailFrame.add(attributePanel,main);

        //ListComponents initalisieruen
         historyListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .title("History")
                        .build();
        //historyListComp.setListElements( historyElements );
        historyListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional

        //historyListComp.addObserver( ... );

         foerderungListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .title("Förderung")
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .build();

       // foerderungListComp.setListElements( historyElements );
        foerderungListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //foerderungListComp.addObserver( ... );

        besitzerListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .title("Besitzer")
                        .build();
        //besitzerListComp.setListElements( historyElements );
        //besitzerListComp.set
        besitzerListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //besitzerListComp.addObserver( ... );

         exponattypListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .title("Exponattyp")
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .build();
        //exponattypListComp.setListElements( historyElements );
        exponattypListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //exponattypListComp.addObserver( ... );

         kuenstlerListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .title("Künstler")
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .build();
       // kuenstlerListComp.setListElements( historyElements );
        kuenstlerListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //kuenstlerListComp.addObserver( ... );

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));
        listPanel.add(historyListComp);
        listPanel.add(foerderungListComp);
        listPanel.add(besitzerListComp);
        listPanel.add(exponattypListComp);
        listPanel.add(kuenstlerListComp);

        listPanel.setBorder(panelBorder);
        main.gridy = 2;
        main.weighty=0.45;
       // main.fill = GridBagConstraints.HORIZONTAL;
        detailFrame.add(listPanel,main);

        detailFrame.setSize(500,700);
        detailFrame.setVisible(true);
    }

    public void setBilder(String[] bildPfade) {
        if (bildPfade.length == 0 || bildPfade[0].isEmpty()) {
            bildPfade = new String[]{"src/assets/images/keineBilder.jpg"};
        }

        ImageElement[] loadedImageElements = null;
        try {
            loadedImageElements = ImageLoader.loadImageElements(bildPfade);
            loadedImageElements[0].setName("test");
        } catch (Exception e) {
            e.printStackTrace();
        }

        slideshow.setImageElements(loadedImageElements);

    }

    public void setKuenstler(ArrayList<IListElement> kuenstler){
        kuenstlerListComp.setListElements(kuenstler);
    }

    public void setFoerderungen(ArrayList<IListElement> foerderungen){
        foerderungListComp.setListElements(foerderungen);
    }

    public void setHistorie(ArrayList<IListElement> historie){
        historyListComp.setListElements(historie);
    }

    public void setExponattyp(ArrayList<IListElement> exponattyp){
        exponattypListComp.setListElements(exponattyp);
    }

    public void setBesitzer(ArrayList<IListElement> besitzer){
        besitzerListComp.setListElements(besitzer);
    }

}
