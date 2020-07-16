package model;

import java.util.List;

public class Exponat {

    private static int invZaehler = 0;

    private String name;
    private String inventarnummer;
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

    public Exponat() {

    }

    public Foerdernder[] getFoerdernde() {
        return new Foerdernder[2];
    }

    public boolean isInMuseum() {
        return true; //TODO: implement logic
    }

}
