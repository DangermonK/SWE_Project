package model;

import java.util.Date;

public class Verkauf {

    private Date verkaufsDatum;
    private double verkaufswert;

    private Historie historie;

    public Verkauf(Historie historie, Date verkaufsDatum, double verkaufswert) {
        this.historie = historie;
        this.verkaufsDatum = verkaufsDatum;
        this.verkaufswert = verkaufswert;
    }

    public Verkauf(Date verkaufsDatum, double verkaufswert) {
        this.verkaufsDatum = verkaufsDatum;
        this.verkaufswert = verkaufswert;
    }

    public Date getVerkaufsDatum() {
        return verkaufsDatum;
    }

    public void setVerkaufsDatum(Date verkaufsDatum) {
        this.verkaufsDatum = verkaufsDatum;
    }

    public double getVerkaufswert() {
        return verkaufswert;
    }

    public void setVerkaufswert(double verkaufswert) {
        this.verkaufswert = verkaufswert;
    }

    public Historie getHistorie() {
        return historie;
    }

    public void setHistorie(Historie historie) {
        this.historie = historie;
    }
}
