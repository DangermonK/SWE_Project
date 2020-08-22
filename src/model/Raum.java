package model;

import java.util.List;

public class Raum {

    private int nummer;
    private int groesse;
    private int exponatszahl;
    private String kategorie;
    private String beschreibung;

    private Exponat[] exponatArray;

    private List<Bild> bildArray;

    @Override
    public int hashCode() {
        return nummer;
    }

    public Raum(int nummer, int groesse, int exponatszahl) {

        this.nummer = nummer;
        this.groesse = groesse;
        this.exponatszahl = exponatszahl;

    }

    public List<Bild> getBildArray() {
        return bildArray;
    }

    public void setBildArray(List<Bild> bildArray) {
        this.bildArray = bildArray;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public int getExponatszahl() {
        return exponatszahl;
    }

    public void setExponatszahl(int exponatszahl) {
        this.exponatszahl = exponatszahl;
    }

    public int getGroesse() {
        return groesse;
    }

    public void setGroesse(int groesse) {
        this.groesse = groesse;
    }
}
