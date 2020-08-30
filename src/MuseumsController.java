import datentypen.Classtype;
import datentypen.Dateiformat;
import datentypen.ErweiterbareListe;
import datentypen.SuchkriteriumExponat;
import de.dhbwka.swe.utils.event.GUIEvent;
import de.dhbwka.swe.utils.event.IGUIEventListener;
import de.dhbwka.swe.utils.model.IListElement;
import model.*;
import util.EntityAdapter;
import util.Property;
import util.Statics;
import util.StorageAdapter;
import view.*;

import java.lang.reflect.Array;
import java.util.*;

public class MuseumsController implements IGUIEventListener {

    private EntityAdapter entityAdapter;

    private StorageAdapter storageAdapter;

    private MainGUI view;

    public MuseumsController() {
        entityAdapter = new EntityAdapter();
        storageAdapter = new StorageAdapter();

        storageAdapter.loadProperties();
        // todo: delete
        boolean json = false;
        if(!json) {
            List<String[]> data = storageAdapter.importData("src/assets/database/TestData.csv", Dateiformat.CSV);
            entityAdapter.createAll(data);
        } else {
            load();
        }

        Object[][] tData = getTabellenData();
        view = new MainGUI(new String[] {"src/assets/images/keineBilder.jpg"}, new String[]{
                SuchkriteriumExponat.RAUM.toString(),
                SuchkriteriumExponat.NAME.toString(),
                SuchkriteriumExponat.KUENSTLERNAME.toString(),
                SuchkriteriumExponat.KATEGORIE.toString(),
                SuchkriteriumExponat.INVENTARNUMMER.toString(),
                SuchkriteriumExponat.AENDERUNGSDATUM.toString()
        }, tData);
        view.setGUIListener(this);

    }

    private void load() {
        List<String[]> data = storageAdapter.importData("src/assets/database/data.json", Dateiformat.JSON);
        entityAdapter.createAll(data);
    }

    private void safe() {
        storageAdapter.exportData(entityAdapter.getAllData(), "src/assets/database/data.json");
        storageAdapter.saveProperties();
    }

    public Object[][] getTabellenData() {
        Object[][] tabellenArr = new Object[entityAdapter.getExponatList().size()][6];
        for (int i = 0; i < tabellenArr.length; i++) {
            tabellenArr[i][0] = entityAdapter.getExponatList().get(i).getInventarnummer();
            tabellenArr[i][1] = entityAdapter.getExponatList().get(i).getName();
            tabellenArr[i][2] = entityAdapter.getExponatList().get(i).getRaum().getNummer();
            tabellenArr[i][3] = entityAdapter.getExponatList().get(i).getKuenstler().getName();
            tabellenArr[i][4] = entityAdapter.getExponatList().get(i).getKategorie();
            Aenderung date = entityAdapter.getExponatList().get(i).getHistorie().getLetzteAenderung();
            tabellenArr[i][5] = (date != null ? Statics.dateFormat.format(date.getAenderungsDatum()) : null);
        }
        return tabellenArr;
    }

    public Object[] getExponatTabellenData(String index) {
        Object[] data = new Object[6];
        Exponat exponat = (Exponat) entityAdapter.getElement(Classtype.EXPONAT, index);
        data[0] = exponat.getInventarnummer();
        data[1] = exponat.getName();
        data[2] = exponat.getRaum().getNummer();
        data[3] = exponat.getKuenstler().getName();
        data[4] = exponat.getKategorie();
        Aenderung date = exponat.getHistorie().getLetzteAenderung();
        data[5] = (date != null ? Statics.dateFormat.format(date.getAenderungsDatum()) : null);
        return data;
    }

    public static void main(String[] args) {

        MuseumsController controller = new MuseumsController();

    }

    private String[] getImagePaths(String index) {
        Exponat exponat = (Exponat) entityAdapter.getElement(Classtype.EXPONAT, index);
        String[] pathsArr = new String[exponat.getBildList().size()];
        for (int i = 0; i < pathsArr.length; i++) {
            pathsArr[i] = exponat.getBildList().get(i).getPfad();
        }
        return pathsArr;
    }

    private void createDetailAnsicht() {
        Exponat exponat = (Exponat) entityAdapter.getElement(Classtype.EXPONAT, view.getTableSelection());

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("Name", exponat.getName());
        attributes.put("Inv-Nr.", exponat.getInventarnummer());
        attributes.put("Material", exponat.getMaterial());
        attributes.put("Schaetzwert", exponat.getSchaetzWert());
        attributes.put("Erstellungsjahr", exponat.getErstellungsJahr());
        attributes.put("Raum", exponat.getRaum().getNummer());
        attributes.put("Kategorie", exponat.getKategorie());
        attributes.put("beschreibung", exponat.getBeschreibung());
        attributes.put("isInWeb", exponat.isInWeb());
        attributes.put("isInMuseum", exponat.isInMuseum());

        GUIExponatDetails details = new GUIExponatDetails(getImagePaths(exponat.getInventarnummer()), attributes);
        ArrayList<IListElement> kuenstler = new ArrayList<>();
        kuenstler.add(new ListElement(exponat.getKuenstler()));
        details.setKuenstler(kuenstler);

        ArrayList<IListElement> besitzer = new ArrayList<>();
        exponat.getBesitzerList().forEach(b-> besitzer.add(new ListElement(b)));
        details.setBesitzer(besitzer);

        ArrayList<IListElement> foerderungen = new ArrayList<>();
        exponat.getFoerderungList().forEach(f-> foerderungen.add(new ListElement(f,f.hashCode())));
        details.setFoerderungen(foerderungen);

        ArrayList<IListElement> exponattypen = new ArrayList<>();
        exponat.getExpTypList().forEach(e-> exponattypen.add(new ListElement(e)));
        details.setExponattyp(exponattypen);

        ArrayList<IListElement> historie = new ArrayList<>();
        historie.add(new ListElement(exponat.getHistorie().getAnlage()));
        exponat.getHistorie().getAenderungList().forEach(aenderung -> historie.add(new ListElement(aenderung)));
        exponat.getHistorie().getKaufList().forEach(kauf -> historie.add(new ListElement(kauf)));
        exponat.getHistorie().getVerkaufList().forEach(verkauf -> historie.add(new ListElement(verkauf)));
        exponat.getHistorie().getVerleihList().forEach(verleih -> historie.add(new ListElement(verleih)));
        exponat.getHistorie().getAusleiheList().forEach(ausleihe -> historie.add(new ListElement(ausleihe)));
        details.setHistorie(historie);
    }

    private String findNextInventarnummer() {
        int counter = 1000;
        String invNummer = "E"+counter;
        while(entityAdapter.getElement(Classtype.EXPONAT, invNummer) != null) {
            counter++;
            invNummer = "E"+counter;
        }
        return invNummer;
    }

    @Override
    public void processGUIEvent(GUIEvent guiEvent) {
        switch (guiEvent.getCmdText()) {
            case "open details":
                createDetailAnsicht();
                break;
            case "load edit":
                Exponat exponat = (Exponat) entityAdapter.getElement(Classtype.EXPONAT, view.getTableSelection());

                Map<String, Object> map = new HashMap<>();
                map.put("invNr", exponat.getInventarnummer());
                map.put("bildPfade", getImagePaths(exponat.getInventarnummer()));

                String exponattyp = exponat.getExpTypList().get(0).getBezeichnung() + "," + exponat.getExpTypList().get(0).getBeschreibung();
                Property.getInstance().swap(ErweiterbareListe.EXPONATTYP, 0, Property.getInstance().getProperty(ErweiterbareListe.EXPONATTYP).indexOf(exponattyp));
                map.put("exponattypen", Property.getInstance().getProperty(ErweiterbareListe.EXPONATTYP).toArray());

                Property.getInstance().swap(ErweiterbareListe.KATEGORIE, 0, Property.getInstance().getProperty(ErweiterbareListe.KATEGORIE).indexOf(exponat.getKategorie()));
                map.put("kategorie", Property.getInstance().getProperty(ErweiterbareListe.KATEGORIE).toArray());
                Property.getInstance().swap(ErweiterbareListe.MATERIAL, 0, Property.getInstance().getProperty(ErweiterbareListe.MATERIAL).indexOf(exponat.getMaterial()));
                map.put("material", Property.getInstance().getProperty(ErweiterbareListe.MATERIAL).toArray());
                map.put("name", exponat.getName());
                map.put("erstellungsjahr", exponat.getErstellungsJahr());
                map.put("schaetzwert", exponat.getSchaetzWert());
                map.put("schowInWeb", String.valueOf(exponat.isInWeb()));
                map.put("beschreibung", exponat.getBeschreibung());
                map.put("raum", exponat.getRaum().getNummer());
                map.put("besitzer", exponat.getBesitzerList());
                map.put("kuenstler", exponat.getKuenstler());

                view.initBearbeitenGUI(map);
                break;
            case "selected element":
                view.setUebersichtBilder(getImagePaths(view.getTableSelection()));
                break;
            case "delete element":
                entityAdapter.removeElement(Classtype.EXPONAT, view.getTableSelection());
                view.setUebersichtBilder(new String[] {"src/assets/images/keineBilder.jpg"});
                break;
            case "close Program":
                safe();
                System.exit(0);
                break;
            case "safe data":
                String[] data = (String[]) guiEvent.getData();
                if(data[0] == null) {
                    data[0] = findNextInventarnummer();
                    entityAdapter.addElement(Classtype.EXPONAT, data);
                    view.addElement(getExponatTabellenData(data[0]));
                } else {
                    entityAdapter.addElement(Classtype.EXPONAT, data);
                    view.updateElement(getExponatTabellenData(data[0]));
                }
                break;
            case "raum gui":

                List<Raum> raumlist =  entityAdapter.getRaumList();
                String[] raeume = new String[raumlist.size()];
                int index =0;
                for (Raum r: raumlist){
                    raeume[index] = String.valueOf(r.getNummer());
                    index++;
                }

                String currentElement;
                if(guiEvent.getData()==null){
                     currentElement = "";
                }
                else{
                    currentElement = guiEvent.getData().toString();
                }


                //System.out.println(currentElement);
                view.initAuswahlPanel(raeume, "Raum", currentElement);
                //new GUIAuswahlPanel(raeume, "Raum");
                break;
            case "historie gui":
                System.out.println("historie ist geil");
                break;
            case "foerderung gui":
                System.out.println("foerderung ist geil");

                Exponat ex = (Exponat) entityAdapter.getElement(Classtype.EXPONAT, guiEvent.getData()) ;

                //Exponat ex = (Exponat) entityAdapter.getElement(Classtype.EXPONAT, "E2131");
                //System.out.println(ex.getFoerderungList().get(0).g);

                List<Person> personList = entityAdapter.getPersonList();
                personList.removeIf(p -> p instanceof Besitzer || p instanceof Angestellter);

                ArrayList<IListElement> foerderungElements =  new ArrayList<>();
                for (Person p: personList ) {
                    Foerdernder foerdernder =  (Foerdernder) p;
                    List<Foerderung> foerderungen =  new ArrayList<>(foerdernder.getFoerderungList()) ;
                    foerderungen.removeIf(m -> m instanceof MuseumsFoerderung);
                    for (Foerderung fd: foerderungen) {
                        foerderungElements.add(new ListElement(fd, fd.hashCode()));
                    }
                }
                List<Foerderung> currentSelected = new ArrayList<>(ex.getFoerderungList());
                currentSelected.removeIf(m->m instanceof MuseumsFoerderung);
                ArrayList<IListElement> currentFoerderungElements =  new ArrayList<>();
                for (Foerderung fd: currentSelected) {
                    currentFoerderungElements.add(new ListElement(fd, fd.hashCode()));
                }



                view.initListAuswahlPanel(foerderungElements, "Förderungen", currentFoerderungElements);
                break;
            case "besitzer gui":
                List<Person> besitzerList = entityAdapter.getPersonList();
                besitzerList.removeIf(p -> p instanceof Foerdernder || p instanceof Angestellter);



                String[] besitzer = new String[besitzerList.size()];
                index =0;
                for (Person b: besitzerList){
                    besitzer[index] = String.valueOf(b.getName());
                    index++;
                }

                String currentBesitzer;
                if(guiEvent.getData()==null){
                    currentBesitzer = "";
                }
                else{
                    currentBesitzer = guiEvent.getData().toString();
                }
                view.initAuswahlPanel(besitzer, "Besitzer", currentBesitzer);
                break;
            case "kuenstler gui":
                System.out.println("kuenstler ist geil");
                break;
        }
    }
}
