package model;

import java.util.ArrayList;
import java.util.List;

public class Exponat {

    private String name;
    private String inventarnummer;
    private List<Exponattyp> expTypList;
    private List<Besitzer> besitzerList;

    private Historie historie;
    private Raum raum;
    private String kategorie;

    private int erstellungsJahr;
    private double schaetzWert;

    private String beschreibung;
    private String material;
    private boolean inWeb;

    private Kuenstler kuenstler;

    private List<ExponatsFoerderung> foerderungList;
    private List<Bild> bildList;

    public Exponat(String inventarnummer, String name) {

        this.inventarnummer = inventarnummer;

        this.name = name;

        expTypList = new ArrayList<>();
        besitzerList = new ArrayList<>();
        foerderungList = new ArrayList<>();
        bildList = new ArrayList<>();

    }

    public List<Foerdernder> getFoerdernde() {
        List<Foerdernder> foerdernderList = new ArrayList<>();
        foerderungList.forEach(foerderung -> {
            foerdernderList.add(foerderung.getFoerdernder());
        });
        return foerdernderList;
    }

    public boolean isInMuseum() {
        return true; //TODO: implement logic
    }

    public String getInventarnummer() {
        return this.inventarnummer;
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

    public List<Bild> getBildList() {
        return bildList;
    }

    public void setBildList(List<Bild> bildList) {
        this.bildList = bildList;
    }

    public List<Exponattyp> getExpTypList() {
        return expTypList;
    }

    public void setExpTypList(List<Exponattyp> expTypList) {
        this.expTypList = expTypList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBesitzer(Besitzer besitzer) {
        besitzerList.add(besitzer);
    }

    public List<Besitzer> getBesitzerList() {
        return besitzerList;
    }

    public void setBesitzerList(List<Besitzer> besitzerList) {
        this.besitzerList = besitzerList;
    }

    public void addFoerderung(ExponatsFoerderung foerderung) {
        foerderungList.add(foerderung);
    }

    public List<ExponatsFoerderung> getFoerderungList() {
        return foerderungList;
    }

    public void setFoerderungList(List<ExponatsFoerderung> foerderungList) {
        this.foerderungList = foerderungList;
    }
}
