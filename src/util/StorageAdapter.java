package util;

import datentypen.Classtype;
import datentypen.Dateiformat;
import de.dhbwka.swe.utils.util.CSVReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorageAdapter {

    public List<String[]> importData(String path, Dateiformat format) {

        List<String[]> data = new ArrayList<>();
        switch(format) {
            case CSV:
                CSVReader reader = new CSVReader(path);
                try {
                    data = reader.readData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                data.removeIf(element -> element.length == 0 || element[0].matches("KOMMENTAR"));

                data = sort(data);
                break;
            case JSON:


                break;
        }

        return data;
    }

    private List<String[]> sort(List<String[]> data) {

        List<String[]> sorted = new ArrayList<>();

        data.forEach(element -> {
            if(element[0].equals(Classtype.RAUM.toString())) {
                sorted.add(element);
            }
        });

        data.forEach(element -> {
            if(element[0].equals(Classtype.ANGESTELLTER.toString())) {
                sorted.add(element);
            }
        });

        data.forEach(element -> {
            if(element[0].equals(Classtype.BESITZER.toString())) {
                sorted.add(element);
            }
        });

        data.forEach(element -> {
            if(element[0].equals(Classtype.EXPONAT.toString())) {
                sorted.add(element);
            }
        });

        data.forEach(element -> {
            if(element[0].equals(Classtype.FOERDERNDER.toString())) {
                sorted.add(element);
            }
        });

        return sorted;

    }

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

    public void savedata() {

    }

    public void loadalldata() {}
    public void loadProperties() {}
    public void saveProperties() {}

}
