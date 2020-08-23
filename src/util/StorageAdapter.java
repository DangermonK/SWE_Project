package util;

import datentypen.Dateiformat;

public class StorageAdapter {



    public String[] importData(String path, Dateiformat format) {
        return null;
    }

    public void exportData(String path) {

    }

    public void savedata() {}
    public void loadalldata() {}
    public void loadProperties() {}
    public void saveProperties() {}

    public String loadTestData() {

        String exponatData[] = {
                "E8593; ; ; ; ; ; ; ; . ; . ",
                "E1034;Der Schrei;Eine Menschliche Figur unter rotem Himmel, die ihre Hände gegen den Kopf presst, während sie Mund und Augen angstvoll aufreißt;Expressionismus;1910;200.0;Öl, Tempera und Pastell auf Pappe;false;Van Gogh,3.2.1879,23.5.1936,Französisch; . ; . ",
                "E2839;Martin-Luther;Portrait von Martin Luther;Expressionismus;1730;90000.0;Acryl;true; . ; . "
        };

        return exponatData[1];


    }

}
