package view;

import de.dhbwka.swe.utils.gui.*;
import de.dhbwka.swe.utils.model.IListElement;
import de.dhbwka.swe.utils.model.ImageElement;
import de.dhbwka.swe.utils.model.Person;
import de.dhbwka.swe.utils.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import de.dhbwka.swe.utils.gui.TextComponent;
import java.util.ArrayList;

public class GUIExponatDetails extends ObservableComponent {


    public GUIExponatDetails() {

        JFrame detailFrame = new JFrame("");
        detailFrame.setLayout(new GridLayout(3,1));
        //GridBagConstraints f = new GridBagConstraints();



        //JPanel imagePanel = new JPanel();

        ImageElement[] loadedImageElements = null;
        try{
            loadedImageElements = ImageLoader.loadImageElements("C:\\users\\master\\desktop" );
        }
        catch( Exception e ){
            e.printStackTrace();
        }


        SlideshowComponent slideshow =   SlideshowComponent.builder("SSC").imageElements(loadedImageElements).smallImageSize(new Dimension(40, 40)).build();

       /* f.gridx = 0;
        f.gridy = 0;
        f.weighty = 0.0;
        f.weightx =1.0;
        f.fill = GridBagConstraints.BOTH;
        f.anchor = GridBagConstraints.NORTH;*/
        detailFrame.add(slideshow);


        //detailFrame.add(imagePanel);

        //JPanel attributePanel = new JPanel(new GridLayout(1,2,50,100));

        JPanel attributePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;


        String[] comboboxDataFutter = new String[]{"Gras", "Moehren", "Fleisch", "Mäuse"};
        String[] comboboxDataKatzen = new String[]{"Maunz", "Tommy", "Mieze"};
        AttributeElement[] attElemsLeft = new AttributeElement[]{
                AttributeElement.builder("Inv-Nr.")
                        .labelName("Inv-Nr.")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10)
                        .value("ebbes")
                        .build(),

                AttributeElement.builder("Schaetzwert")
                        .labelName("Schätzwert €")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),
                //AttributeElement.builder("Futter").labelName("Futter").value(comboboxDataFutter[0]).actionType(AttributeElement.ActionType.COMBOBOX).build()};

                AttributeElement.builder("Material")
                        .labelName("Material")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),

                /*AttributeElement.builder("Beschreibung")
                        .labelName("Beschreibung")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),*/
                };
        AttributeComponent attComp = null;

        AttributeElement[] attElemsRight = new AttributeElement[]{
                AttributeElement.builder("Name")
                        .labelName("Name")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),

                AttributeElement.builder("Erstellungsjahr")
                        .labelName("Erstellungsjahr")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),
                //AttributeElement.builder("Futter").labelName("Futter").value(comboboxDataFutter[0]).actionType(AttributeElement.ActionType.COMBOBOX).build()};

                AttributeElement.builder("Raum")
                        .labelName("Raum")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),

                AttributeElement.builder("Kategorie")
                        .labelName("Kategorie")
                        .labelSize(new Dimension(100,5))
                        .actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).value("ebbes").build(),

               /* AttributeElement.builder("isInMuseum")
                        .labelName("Ist im Museum")
                        .actionType(AttributeElement.ActionType.BUTTON)
                        .labelSize(new Dimension(100,5))
                        //.actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).build(),

                AttributeElement.builder("ShowInWeb")
                        .labelName("Im Web anzeigen")
                        .actionType(AttributeElement.ActionType.BUTTON)
                        .labelSize(new Dimension(100,5))
                        //.actionType(AttributeElement.ActionType.NONE).modificationType(AttributeElement.ModificationType.DIRECT)
                        .mandatory(false).maxLength(10).build(),*/
        };
        AttributeComponent attComp2 = null;

        for (AttributeElement ae: attElemsLeft) {
            ((JTextField)((AttributeElement) ae).getComponent(1)).setEditable(false);
        }
        for (AttributeElement ae: attElemsRight) {
            //((JTextField)((AttributeElement) ae).getComponent(1)).setEditable(false);
        }

        try {
            attComp = AttributeComponent.builder("leftAttributes").attributeElements(attElemsLeft).build();
            attComp2 = AttributeComponent.builder("rightAttributes").attributeElements(attElemsRight).build();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        attComp.setEnabled(true);

        attComp2.setEnabled(true);

        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 0;
        c.insets = new Insets(0,5,0,0);
        attributePanel.add(attComp,c);


        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 0;
        c.insets = new Insets(0,50,0,0);
        attributePanel.add(attComp2,c);

        TextComponent tc = TextComponent.builder("textFeld").title("Beschreibung").build();
        c.gridx = 0;
        c.gridy = 1;
        c.insets =new Insets(0,5,0,0);
        attributePanel.add(tc,c);

        JPanel checkBoxes = new JPanel(new GridLayout(2,1));
        JCheckBox cbInMuseum = new JCheckBox("Ist im Museum:");
        cbInMuseum.setHorizontalTextPosition(SwingConstants.LEFT);
        cbInMuseum.setEnabled(false);
        JCheckBox cbInWeb = new JCheckBox("Im Web anzeigen:");
        cbInWeb.setEnabled(false);

        cbInWeb.setHorizontalTextPosition(SwingConstants.LEFT);
        checkBoxes.add(cbInMuseum);
        checkBoxes.add(cbInWeb);

        c.gridx =1;
        c.gridy =1;
        c.insets = new Insets(0,50,0,0);
        attributePanel.add(checkBoxes,c);





       /* f.gridx = 0;
        f.gridy = 1;
        f.weightx =1.0;
        f.weighty =1.0;
        f.fill = GridBagConstraints.BOTH;*/
        detailFrame.add(attributePanel);


        ArrayList<IListElement> historyElements = new ArrayList<IListElement>();
        historyElements.add((new Person("Willi", "test")));

        ArrayList<IListElement> foerderungElements = new ArrayList<IListElement>();
        foerderungElements.add((new Person("cro", "test")));

        ArrayList<IListElement> besitzerElements = new ArrayList<IListElement>();
        foerderungElements.add((new Person("cro", "test")));

        ArrayList<IListElement> exponattypElements = new ArrayList<IListElement>();
        foerderungElements.add((new Person("cro", "test")));

        ArrayList<IListElement> kuenstlerElements = new ArrayList<IListElement>();
        foerderungElements.add((new Person("cro", "test")));



        SimpleListComponent historyListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .title("History")
                        .build();
        historyListComp.setListElements( historyElements );
        historyListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //historyListComp.addObserver( ... );

        SimpleListComponent foerderungListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .title("Förderung")
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .build();
        foerderungListComp.setListElements( historyElements );
        foerderungListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //foerderungListComp.addObserver( ... );

        SimpleListComponent besitzerListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .title("Besitzer")
                        .build();
        besitzerListComp.setListElements( historyElements );
        besitzerListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //besitzerListComp.addObserver( ... );

        SimpleListComponent exponattypListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .title("Exponattyp")
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .build();
        exponattypListComp.setListElements( historyElements );
        exponattypListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //exponattypListComp.addObserver( ... );

        SimpleListComponent kuenstlerListComp =
                SimpleListComponent.builder("historyList" )
                        .font( new Font( "SansSerif",Font.ITALIC,10) )
                        .title("Künstler")
                        .selectionMode( ListSelectionModel.SINGLE_SELECTION )
                        .build();
        kuenstlerListComp.setListElements( historyElements );
        kuenstlerListComp.setCellRenderer( new ListComponentCellRenderer() ); //optional
        //kuenstlerListComp.addObserver( ... );

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));
        listPanel.add(historyListComp);
        listPanel.add(foerderungListComp);
        listPanel.add(besitzerListComp);
        listPanel.add(exponattypListComp);
        listPanel.add(kuenstlerListComp);

        /*f.gridx = 0;
        f.gridy = 3;
        f.weighty = 1.0;
        f.weightx =1.0;
        f.fill = GridBagConstraints.BOTH;*/
        detailFrame.add(listPanel);



        detailFrame.setSize(400,400);
        detailFrame.setVisible(true);

    }

}
