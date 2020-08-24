package model;

public abstract class Foerderung {

    private String foerderungsart;
    private double foerderungsmittel;

    private Foerdernder foerdernder;

    public Foerderung(String foerderungsart, double foerderungsmittel) {
        this.foerderungsart = foerderungsart;
        this.foerderungsmittel = foerderungsmittel;
    }

    public String getFoerderungsart() {
        return foerderungsart;
    }

    public void setFoerderungsart(String foerderungsart) {
        this.foerderungsart = foerderungsart;
    }

    public double getFoerderungsmittel() {
        return foerderungsmittel;
    }

    public void setFoerderungsmittel(double foerderungsmittel) {
        this.foerderungsmittel = foerderungsmittel;
    }

    public Foerdernder getFoerdernder() {
        return foerdernder;
    }

    public void setFoerdernder(Foerdernder foerdernder) {
        this.foerdernder = foerdernder;
    }
}
