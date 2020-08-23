package util;

import datentypen.Dateiformat;

public class StorageAdapter {

    public String[] importData(String path, Dateiformat format) {
        return null;
    }

    public void exportData(String path) {}

    public void savedata() {}
    public void loadalldata() {}
    public void loadProperties() {}
    public void saveProperties() {}

    public String loadTestData() {

        String exponatData =
                "E0000;Name;Beschreibung;Kategorie;Erstellungsjahr;Schätzwert;Material;Webanzeige;Künstlername,1.1.1111,1.1.1111,Nationalität;Bildname.Bildpfad,Bildname.Bildpfad;bezeichner.beschreibung,bez.beschr\n"+
                "E1000;Test;Test beschreibung;Skulptur;1920;5010.5;Marmor;true;Max,2.3.1880,23.4.1945,Fransösisch;test1.a/b,test2.a/b;Epoche.beschreibung,bez.beschr\n"+
                "E1001;Test2;Test2 beschreibung;Bild;1978;100;Wax;true;Tim,20.9.1934,7.5.2014,Deutsch;test1.a/b,test2.a/b;Epoche.beschreibung,bez.beschr";

        return exponatData;


    }

}
