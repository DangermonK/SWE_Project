package util;

import datentypen.Classtype;
import datentypen.Dateiformat;
import datentypen.ErweiterbareListe;
import de.dhbwka.swe.utils.util.CSVReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class StorageAdapter {

    /*
        verwaltet die Schnittstelle zwischen Datensatz und Programm
     */
    public StorageAdapter() {

    }

    // Importiert entweder json oder csv Daten
    public List<String[]> importData(String path, Dateiformat format) {

        List<String[]> data = new ArrayList<>();
        switch (format) {
            case CSV:
                CSVReader csvReader = new CSVReader(path);
                try {
                    data = csvReader.readData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data.removeIf(element -> element.length == 0 || element[0].matches("KOMMENTAR"));

                break;
            case JSON:
                JSONParser parser = new JSONParser();

                try (FileReader reader = new FileReader(path)) {
                    //Read JSON file
                    JSONObject obj = (JSONObject) parser.parse(reader);

                    JSONArray exponatArr = (JSONArray) obj.get("exponate");
                    for (Object exponat : exponatArr) {
                        data.add(getExponatData((JSONObject) exponat));
                    }

                    JSONArray raumArr = (JSONArray) obj.get("raeume");
                    for (Object raum : raumArr) {
                        data.add(getRaumData((JSONObject) raum));
                    }

                    JSONArray besitzerArr = (JSONArray) obj.get("besitzer");
                    for (Object besitzer : besitzerArr) {
                        data.add(getBesitzerData((JSONObject) besitzer));
                    }

                    JSONArray angestellterArr = (JSONArray) obj.get("angestellte");
                    for (Object angestellter : angestellterArr) {
                        data.add(getAngestellterData((JSONObject) angestellter));
                    }

                    JSONArray foerdernderArr = (JSONArray) obj.get("foerdernde");
                    for (Object foerdernder : foerdernderArr) {
                        data.add(getFoerdernderData((JSONObject) foerdernder));
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
        }
        data = sort(data);
        return data;
    }

    // übersetzt den json datensatz für die element factory
    private String[] getFoerdernderData(JSONObject object) {
        String[] attributes = new String[9];
        attributes[0] = Classtype.FOERDERNDER.toString();
        attributes[1] = (String) object.get("persNr");
        attributes[2] = (String) object.get("name");
        attributes[3] = (String) object.get("email");
        attributes[4] = (String) object.get("adresse");
        attributes[5] = (String) object.get("telefon");
        attributes[6] = "";
        JSONArray bilder = (JSONArray) object.get("bilder");
        bilder.forEach(bild -> attributes[6] += ((JSONObject) bild).get("titel") + "," + ((JSONObject) bild).get("pfad") + "-");

        attributes[7] = "";
        JSONArray expFoerArr = (JSONArray) object.get("exponatsFoerderung");
        expFoerArr.forEach(foerderung -> attributes[7] += ((JSONObject) foerderung).get("foerderungsart") + "-" + ((JSONObject) foerderung).get("foerderungsmittel")
                + "-" + ((JSONObject) foerderung).get("exponat") + ",");
        attributes[8] = "";
        JSONArray musFoerArr = (JSONArray) object.get("museumsFoerderung");
        musFoerArr.forEach(foerderung -> attributes[8] += ((JSONObject) foerderung).get("foerderungsart") + "-" + ((JSONObject) foerderung).get("foerderungsmittel") + ",");

        return attributes;

    }

    // übersetzt den json datensatz für die element factory
    private String[] getBesitzerData(JSONObject object) {

        String[] attributes = new String[7];
        attributes[0] = Classtype.BESITZER.toString();
        attributes[1] = (String) object.get("persNr");
        attributes[2] = (String) object.get("name");
        attributes[3] = (String) object.get("email");
        attributes[4] = (String) object.get("adresse");
        attributes[5] = (String) object.get("telefon");
        attributes[6] = "";
        JSONArray bilder = (JSONArray) object.get("bilder");
        bilder.forEach(bild -> attributes[6] += ((JSONObject) bild).get("titel") + "," + ((JSONObject) bild).get("pfad") + "-");

        return attributes;
    }

    // übersetzt den json datensatz für die element factory
    private String[] getAngestellterData(JSONObject object) {
        String[] attributes = new String[6];
        attributes[0] = Classtype.ANGESTELLTER.toString();
        attributes[1] = (String) object.get("persNr");
        attributes[2] = (String) object.get("name");
        attributes[3] = (String) object.get("domaenenname");
        attributes[4] = (String) object.get("rolle");
        attributes[5] = "";
        JSONArray bilder = (JSONArray) object.get("bilder");
        bilder.forEach(bild -> attributes[5] += ((JSONObject) bild).get("titel") + "," + ((JSONObject) bild).get("pfad") + "-");

        return attributes;
    }

    // übersetzt den json datensatz für die element factory
    private String[] getRaumData(JSONObject object) {
        String[] attributes = new String[7];
        attributes[0] = Classtype.RAUM.toString();
        attributes[1] = String.valueOf(object.get("nummer"));
        attributes[2] = String.valueOf(object.get("groesse"));
        attributes[3] = String.valueOf(object.get("anzahl"));
        attributes[4] = (String) object.get("beschreibung");
        attributes[5] = (String) object.get("kategorie");
        attributes[6] = "";
        JSONArray bilder = (JSONArray) object.get("bilder");
        bilder.forEach(bild -> attributes[6] += ((JSONObject) bild).get("titel") + "," + ((JSONObject) bild).get("pfad") + "-");
        return attributes;
    }

    // übersetzt den json datensatz für die element factory
    private String[] getExponatData(JSONObject object) {

        String[] attributes = new String[15];
        attributes[0] = Classtype.EXPONAT.toString();
        attributes[1] = (String) object.get("invNr");
        attributes[2] = (String) object.get("name");
        attributes[3] = (String) object.get("beschreibung");
        attributes[4] = (String) object.get("kategorie");
        attributes[5] = String.valueOf(object.get("erstellungsjahr"));
        attributes[6] = String.valueOf(object.get("schaetzwert"));
        attributes[7] = (String) object.get("material");
        attributes[8] = String.valueOf(object.get("in web"));

        JSONObject kuenstler = (JSONObject) object.get("kuenstler");
        attributes[9] = "";
        if (kuenstler != null)
            attributes[9] = kuenstler.get("name") + "," + kuenstler.get("geburt") + "," + kuenstler.get("tod") + "," + kuenstler.get("nationalitaet");

        // Todo: check all necessary elements for null pointers
        attributes[10] = "";
        JSONArray bilder = (JSONArray) object.get("bilder");
        if (bilder != null)
            bilder.forEach(bild -> attributes[10] += ((JSONObject) bild).get("titel") + "," + ((JSONObject) bild).get("pfad") + "-");

        attributes[11] = "";
        JSONArray exptypArr = (JSONArray) object.get("exponattypen");
        exptypArr.forEach(exptyp -> attributes[11] += ((JSONObject) exptyp).get("bezeichnung") + "," + ((JSONObject) exptyp).get("beschreibung") + ":");

        attributes[12] = "";
        JSONObject historie = (JSONObject) object.get("historie");
        JSONArray verleihArr = (JSONArray) historie.get("verleih");
        verleihArr.forEach(verleih -> attributes[12] += ((JSONObject) verleih).get("eingangsdatum") + "-" + ((JSONObject) verleih).get("ausgangsdatum") + "-" + ((JSONObject) verleih).get("wert") +
                ",");
        attributes[12] += ":";

        JSONArray ausleiheArr = (JSONArray) historie.get("ausleihe");
        ausleiheArr.forEach(ausleihe -> attributes[12] += ((JSONObject) ausleihe).get("eingangsdatum") + "-" + ((JSONObject) ausleihe).get("ausgangsdatum") + ",");
        attributes[12] += ":";

        JSONArray kaufArr = (JSONArray) historie.get("kauf");
        kaufArr.forEach(kauf -> attributes[12] += ((JSONObject) kauf).get("erwerbsdatum") + "-" + ((JSONObject) kauf).get("kaufwert") + ",");
        attributes[12] += ":";

        JSONArray verkaufArr = (JSONArray) historie.get("verkauf");
        verkaufArr.forEach(verkauf -> attributes[12] += ((JSONObject) verkauf).get("verkaufsdatum") + "-" + ((JSONObject) verkauf).get("verkaufswert") + ",");
        attributes[12] += ":";

        JSONObject anlage = (JSONObject) historie.get("anlage");
        attributes[12] += anlage.get("anlagedatum") + "-" + anlage.get("angestellter") + ":";

        JSONArray aenderungArr = (JSONArray) historie.get("aenderung");
        aenderungArr.forEach(aenderung -> attributes[12] += ((JSONObject) aenderung).get("aenderungsdatum") + "-" + ((JSONObject) aenderung).get("angestellter") + ",");

        attributes[13] = "";
        JSONArray besitzer = (JSONArray) object.get("besitzer");
        besitzer.forEach(b -> attributes[13] += b + ",");

        attributes[14] = String.valueOf(object.get("raum"));

        return attributes;
    }

    // Sortiert einen Datensatz so, dass Referenzen einfach aufgelöst werden können
    private List<String[]> sort(List<String[]> data) {

        List<String[]> sorted = new ArrayList<>();

        data.forEach(element -> {
            if (element[0].equals(Classtype.RAUM.toString())) {
                sorted.add(element);
            }
        });

        data.forEach(element -> {
            if (element[0].equals(Classtype.ANGESTELLTER.toString())) {
                sorted.add(element);
            }
        });

        data.forEach(element -> {
            if (element[0].equals(Classtype.BESITZER.toString())) {
                sorted.add(element);
            }
        });

        data.forEach(element -> {
            if (element[0].equals(Classtype.EXPONAT.toString())) {
                sorted.add(element);
            }
        });

        data.forEach(element -> {
            if (element[0].equals(Classtype.FOERDERNDER.toString())) {
                sorted.add(element);
            }
        });

        return sorted;

    }

    // Exportiert einen json Datensatz
    public void exportData(JSONObject data, String path) {

        try {
            FileWriter file = new FileWriter(path);
            file.write(data.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void savedata(JSONObject data) {
        exportData(data,"src/assets/database/data.json");
    }

    public List<String[]> loadalldata() {
        return importData("src/assets/database/data.json", Dateiformat.JSON);
    }

    // lädt die properties datei und speichert sie im Programm
    public void loadProperties() {
        File propertiesFile = new File("./src/assets/database/auswahllisten.properties");
        Properties properties = new Properties();

        if (propertiesFile.exists()) {
            BufferedInputStream bis;
            try {
                bis = new BufferedInputStream(new FileInputStream(propertiesFile));
                properties.load(bis);
                bis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Property.getInstance().putProperties(ErweiterbareListe.KATEGORIE, properties.getProperty("kategorie").split(","));
        String[] arr = properties.getProperty("exponattyp").split(",");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].replace(":", ",");
        }
        Property.getInstance().putProperties(ErweiterbareListe.EXPONATTYP, arr);
        Property.getInstance().putProperties(ErweiterbareListe.MATERIAL, properties.getProperty("material").split(","));

    }

    // speichert die Properties aus dem Programm in der properties datei
    public void saveProperties() {
        List<String> kategorieList = Arrays.asList(Property.getInstance().getProperty(ErweiterbareListe.KATEGORIE));
        List<String> materialList = Arrays.asList(Property.getInstance().getProperty(ErweiterbareListe.MATERIAL));
        List<String> exponattypList = Arrays.asList(Property.getInstance().getProperty(ErweiterbareListe.EXPONATTYP));

        final String[] propertiesArr = {"", "", ""};
        kategorieList.forEach(k -> propertiesArr[0] += k + ",");
        materialList.forEach(m -> propertiesArr[1] += m + ",");
        exponattypList.forEach(e -> propertiesArr[2] += e.replace(',', ':') + ",");

        Properties properties = new Properties();
        properties.setProperty("kategorie", propertiesArr[0]);
        properties.setProperty("material", propertiesArr[1]);
        properties.setProperty("exponattyp", propertiesArr[2]);

        try {
            properties.store(new FileOutputStream("./src/assets/database/auswahllisten.properties"), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
