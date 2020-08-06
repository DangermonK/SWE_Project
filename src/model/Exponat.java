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

    public Exponat(String name) {

        this.inventarnummer = invZaehler;
        invZaehler++;

        this.name = name;

    }

    public Foerdernder[] getFoerdernde() {
        return null; //TODO: implement Logic
    }

    public boolean isInMuseum() {
        return true; //TODO: implement logic
    }

    @Override
    public int hashCode() {
        return inventarnummer;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public int getErstellungsJahr() {
        return erstellungsJahr;
    }

    public void setErstellungsJahr(int erstellungsJahr) {
        this.erstellungsJahr = erstellungsJahr;
    }

    public double getSchaetzWert() {
        return schaetzWert;
    }

    public void setSchaetzWert(double schaetzWert) {
        this.schaetzWert = schaetzWert;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isInWeb() {
        return inWeb;
    }

    public void setInWeb(boolean inWeb) {
        this.inWeb = inWeb;
    }

    public Kuenstler getKuenstler() {
        return kuenstler;
    }

    public void setKuenstler(Kuenstler kuenstler) {
        this.kuenstler = kuenstler;
    }

    public Historie getHistorie() {
        return historie;
    }

    public void setHistorie(Historie historie) {
        this.historie = historie;
    }

    public Raum getRaum() {
        return raum;
    }

    public void setRaum(Raum raum) {
        this.raum = raum;
    }

}
