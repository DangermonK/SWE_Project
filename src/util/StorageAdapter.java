package util;

import datentypen.Dateiformat;
import de.dhbwka.swe.utils.util.CSVReader;

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
                break;
            case JSON:

                break;
        }

        return data;
    }

    public void exportData(String path) {}

    public void savedata() {}
    public void loadalldata() {}
    public void loadProperties() {}
    public void saveProperties() {}

}
