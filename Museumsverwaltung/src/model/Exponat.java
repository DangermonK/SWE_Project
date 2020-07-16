package model;

import java.util.List;

public class Exponat {

    private static int invZaehler = 0;

    private String name;
    private int inventarnummer;
    private String kategorie;

    private int erstellungsJahr;
    private double schaetzWert;

    private String beschreibung;
    private String material;
    private boolean inWeb;

    private Kuenstler kuenstler;
    private Exponattyp[] expTypArray;
    private Besitzer[] besitzerArray;

    private Historie historie;
    private Raum raum;

    private ExponatsFoerderung[] foerderungArray;
    private Bild[] bildArray;

    public Exponat() {

    }

    public Foerdernder[] getFoerdernde() {
        return new Foerdernder[2];
    }

    public boolean isInMuseum() {
        return true; //TODO: implement logic
    }

    @Override
    public int hashCode() {
        return inventarnummer;
    }

}
