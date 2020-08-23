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
                "E2103;Der Schrei;Menschliche Figur mit angstvollem Blick;Bild;1930;4000.0;Öl, Pastel auf Pappe;true;Van Gogh,30.11.1913,12.6.1940,Französisch;Schrei.Bilder/Vangogh,Vinvent.Bilder/Vangogh;Expressionismus.blabla;"+
                "23.2.2010-23.3.2010-400.0,1.8.2018-1.9.2018-300.0:12.5.2020-30.6.2020:17.4.2020-5000.0:30.9.2020-200.0:23.2.2010:23.8.2020";
        return exponatData;


    }

}
