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

import java.util.*;

public class MuseumsController implements IGUIEventListener {

    private EntityAdapter entityAdapter;
    private StorageAdapter storageAdapter;
    private MainGUI view;
    private Angestellter user;

    /*
        lädt die Daten in das Model und Startet die GUI
        stellt die Schnittstelle zwischen GUI und Model dar
     */
    public MuseumsController(String customPath, String propertiesPath) {
        entityAdapter = new EntityAdapter();
        storageAdapter = new StorageAdapter();

        storageAdapter.loadProperties(propertiesPath);

        if(customPath != null) {
            entityAdapter.createAll(storageAdapter.importData(customPath, Dateiformat.CSV));
        } else {
            load();
        }

        user = (Angestellter) entityAdapter.getElement(Classtype.ANGESTELLTER, "P200");

        Object[][] tData = getTabellenData();
        view = new MainGUI(new String[]{"src/assets/images/keineBilder.jpg"}, new String[]{
                SuchkriteriumExponat.RAUM.toString(),
                SuchkriteriumExponat.NAME.toString(),
                SuchkriteriumExponat.KUENSTLERNAME.toString(),
                SuchkriteriumExponat.KATEGORIE.toString(),
                SuchkriteriumExponat.INVENTARNUMMER.toString(),
                SuchkriteriumExponat.AENDERUNGSDATUM.toString()
        }, tData);
        view.setGUIListener(this);

    }

    // lädt den JSON Datensatz automatisch in das Programm
    private void load() {
        entityAdapter.createAll(storageAdapter.loadalldata());
    }

    // speichert den JSON Datensatz automatisch
    private void safe() {
        storageAdapter.savedata(entityAdapter.getAllData());
        storageAdapter.saveProperties();
    }

    // Lädt alle relevanten Tabellendaten in der richtigen Reihenfolge
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

    // Lädt die Daten für eine einzelne Zeile anhand des index
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

        MuseumsController controller = new MuseumsController(args.length > 0 ? args[0] : null, args.length > 1 ? args[1] : null);

    }

    // lädt die Bildpfade eines Exponats
    private String[] getImagePaths(String index) {
        Exponat exponat = (Exponat) entityAdapter.getElement(Classtype.EXPONAT, index);
        String[] pathsArr = new String[exponat.getBildList().size()];
        for (int i = 0; i < pathsArr.length; i++) {
            pathsArr[i] = exponat.getBildList().get(i).getPfad();
        }
        return pathsArr;
    }

    // Erstellt die Detailansicht mit den relevanten Daten
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
        exponat.getBesitzerList().forEach(b -> besitzer.add(new ListElement(b)));
        details.setBesitzer(besitzer);

        ArrayList<IListElement> foerderungen = new ArrayList<>();
        exponat.getFoerderungList().forEach(f -> foerderungen.add(new ListElement(f, f.hashCode())));
        details.setFoerderungen(foerderungen);

        ArrayList<IListElement> exponattypen = new ArrayList<>();
        exponat.getExpTypList().forEach(e -> exponattypen.add(new ListElement(e)));
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

    // sucht eine freie Inventarnummer
    private String findNextInventarnummer() {
        int counter = 1000;
        String invNummer = "E" + counter;
        while (entityAdapter.getElement(Classtype.EXPONAT, invNummer) != null) {
            counter++;
            invNummer = "E" + counter;
        }
        return invNummer;
    }

    // verarbeitet alle GUI Events die Zugriff auf das Modell benötigen
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
                Property.getInstance().swap(ErweiterbareListe.EXPONATTYP, 0, Property.getInstance().indexOf(ErweiterbareListe.EXPONATTYP, exponattyp));
                map.put("exponattypen", Property.getInstance().getProperty(ErweiterbareListe.EXPONATTYP));

                Property.getInstance().swap(ErweiterbareListe.KATEGORIE, 0, Property.getInstance().indexOf(ErweiterbareListe.KATEGORIE, exponat.getKategorie()));
                map.put("kategorie", Property.getInstance().getProperty(ErweiterbareListe.KATEGORIE));
                Property.getInstance().swap(ErweiterbareListe.MATERIAL, 0, Property.getInstance().indexOf(ErweiterbareListe.MATERIAL, exponat.getMaterial()));
                map.put("material", Property.getInstance().getProperty(ErweiterbareListe.MATERIAL));
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

            case "load anlegen":

                Map<String, Object> anlegenmap = new HashMap<>();

                anlegenmap.put("exponattyp", Property.getInstance().getProperty(ErweiterbareListe.EXPONATTYP));
                anlegenmap.put("kategorie", Property.getInstance().getProperty(ErweiterbareListe.KATEGORIE));
                anlegenmap.put("material", Property.getInstance().getProperty(ErweiterbareListe.MATERIAL));


                List<Person> allBesitzer = entityAdapter.getPersonList();
                allBesitzer.removeIf(p -> p instanceof Foerdernder || p instanceof Angestellter);
                anlegenmap.put("besitzer", allBesitzer);


                view.initAnlegenGUI(anlegenmap);

                break;
            case "selected element":
                view.setUebersichtBilder(getImagePaths(view.getTableSelection()));
                break;
            case "delete element":
                entityAdapter.removeElement(Classtype.EXPONAT, view.getTableSelection());
                view.setUebersichtBilder(new String[]{"src/assets/images/keineBilder.jpg"});
                view.setUpdate(false);
                break;
            case "close Program save":
                safe();
                System.exit(0);
                break;

            case "close Program":
                System.exit(0);
                break;
            case "save programm":
                safe();
                view.setUpdate(true);
                break;
            case "safe data":
                String[] data = (String[]) guiEvent.getData();
                String anlage = Statics.dateFormat.format(new Date()) + "-" + user.getPersNr();
                if (data[0] == null) {
                    data[0] = findNextInventarnummer();
                    data[11] = "null:null:null:null:" + anlage + ":" + anlage;
                    entityAdapter.addElement(Classtype.EXPONAT, data);
                    view.addElement(getExponatTabellenData(data[0]));
                } else {
                    Historie h = ((Exponat) entityAdapter.getElement(Classtype.EXPONAT, data[0])).getHistorie();
                    data[11] = "null:null:null:null:" + Statics.dateFormat.format(h.getAnlage().getAnlageDatum()) + "-" + h.getAnlage().getAngestellter().getPersNr() + ":";
                    data[11] += anlage;
                    h.getAenderungList().forEach(aenderung -> {
                        data[11] += "," + Statics.dateFormat.format(aenderung.getAenderungsDatum()) + "-" + aenderung.getAngestellter().getPersNr();
                    });
                    entityAdapter.addElement(Classtype.EXPONAT, data);
                    view.updateElement(getExponatTabellenData(data[0]));
                }
                if (data[14] != null) {
                    List<String> hashList = Arrays.asList(data[14].split(","));
                    List<Person> pList = entityAdapter.getPersonList();
                    pList.removeIf(p -> p instanceof Angestellter || p instanceof Besitzer);

                    List<ExponatsFoerderung> expFoeList = new ArrayList<>();

                    for (Person p : pList) {
                        List<Foerderung> foerderungList = ((Foerdernder) p).getFoerderungList();
                        for (Foerderung f : foerderungList) {
                            if (f instanceof ExponatsFoerderung && hashList.contains(String.valueOf(f.hashCode()))) {
                                ((ExponatsFoerderung) f).setExponat((Exponat) entityAdapter.getElement(Classtype.EXPONAT, data[0]));
                                expFoeList.add((ExponatsFoerderung) f);
                            }
                        }
                    }

                    ((Exponat) entityAdapter.getElement(Classtype.EXPONAT, data[0])).setFoerderungList(expFoeList);
                }
                view.setUpdate(false);
                break;
            case "raum gui":

                List<Raum> raumlist = entityAdapter.getRaumList();
                String[] raeume = new String[raumlist.size()];
                int index = 0;
                for (Raum r : raumlist) {
                    raeume[index] = String.valueOf(r.getNummer());
                    index++;
                }

                String currentElement;
                if (guiEvent.getData() == null) {
                    currentElement = "";
                } else {
                    currentElement = guiEvent.getData().toString();
                }

                view.initAuswahlPanel(raeume, "Raum", currentElement);
                break;
            case "historie gui":
                System.out.println("historie ist geil");
                break;
            case "foerderung gui":
                System.out.println("foerderung ist geil");

                if (guiEvent.getData() != null) {

                }

                Exponat ex = (Exponat) entityAdapter.getElement(Classtype.EXPONAT, guiEvent.getData());

                List<Person> personList = entityAdapter.getPersonList();
                personList.removeIf(p -> p instanceof Besitzer || p instanceof Angestellter);

                ArrayList<IListElement> foerderungElements = new ArrayList<>();
                for (Person p : personList) {
                    Foerdernder foerdernder = (Foerdernder) p;
                    List<Foerderung> foerderungen = new ArrayList<>(foerdernder.getFoerderungList());
                    foerderungen.removeIf(m -> m instanceof MuseumsFoerderung);
                    for (Foerderung fd : foerderungen) {
                        foerderungElements.add(new ListElement(fd, fd.hashCode()));
                    }
                }

                ArrayList<IListElement> currentFoerderungElements = new ArrayList<>();
                if (ex != null) {

                    List<Foerderung> currentSelected = new ArrayList<>(ex.getFoerderungList());
                    currentSelected.removeIf(m -> m instanceof MuseumsFoerderung);

                    for (Foerderung fd : currentSelected) {
                        currentFoerderungElements.add(new ListElement(fd, fd.hashCode()));
                    }
                }


                view.initListAuswahlPanel(foerderungElements, "Förderungen", currentFoerderungElements);
                break;
            case "besitzer gui":
                List<Person> besitzerList = entityAdapter.getPersonList();
                besitzerList.removeIf(p -> p instanceof Foerdernder || p instanceof Angestellter);


                String[] besitzer = new String[besitzerList.size()];
                index = 0;
                for (Person b : besitzerList) {
                    besitzer[index] = String.valueOf(b.getName());
                    index++;
                }

                String currentBesitzer;
                if (guiEvent.getData() == null) {
                    currentBesitzer = "";
                } else {
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
