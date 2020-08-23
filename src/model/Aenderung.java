package model;

import java.util.Date;

public class Aenderung {

    private Angestellter angestellter;

    private Date aenderungsDatum;

    private Historie historie;

    public Aenderung(Historie historie, Date aenderungsDatumm, Angestellter angestellter) {
        this.angestellter = angestellter;
        this.historie = historie;
        this.aenderungsDatum = aenderungsDatumm;
    }

    public Aenderung(Date aenderungsDatumm) {
        this.aenderungsDatum = aenderungsDatumm;
    }

    public Angestellter getAngestellter() {
        return angestellter;
    }

    public void setAngestellter(Angestellter angestellter) {
        this.angestellter = angestellter;
    }

    public Date getAenderungsDatum() {
        return aenderungsDatum;
    }

    public void setAenderungsDatum(Date aenderungsDatum) {
        this.aenderungsDatum = aenderungsDatum;
    }

    public Historie getHistorie() {
        return historie;
    }

    public void setHistorie(Historie historie) {
        this.historie = historie;
    }
}
