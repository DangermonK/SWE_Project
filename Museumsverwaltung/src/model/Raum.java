package model;

public class Raum {

    private int nummer;
    private int groesse;
    private int exponatszahl;
    private String kategorie;
    private String beschreibung;

    private Exponat[] exponatArray;

    private Bild[] bildArray;

    @Override
    public int hashCode() {
        return nummer;
    }

}
