package model;

import java.util.Date;

public class Kauf {

    private Date erwerbsDatum;
    private double kaufwert;

    private Historie historie;

    public Kauf(Historie historie, Date erwerbsDatum, double kaufwert) {
        this.historie = historie;
        this.erwerbsDatum = erwerbsDatum;
        this.kaufwert = kaufwert;
    }

    public Kauf(Date erwerbsDatum, double kaufwert) {
        this.erwerbsDatum = erwerbsDatum;
        this.kaufwert = kaufwert;
    }

    public Date getErwerbsDatum() {
        return erwerbsDatum;
    }

    public void setErwerbsDatum(Date erwerbsDatum) {
        this.erwerbsDatum = erwerbsDatum;
    }

    public double getKaufwert() {
        return kaufwert;
    }

    public void setKaufwert(double kaufwert) {
        this.kaufwert = kaufwert;
    }

    public Historie getHistorie() {
        return historie;
    }

    public void setHistorie(Historie historie) {
        this.historie = historie;
    }
}
