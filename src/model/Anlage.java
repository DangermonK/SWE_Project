package model;

import java.util.Date;

public class Anlage {

    private Angestellter angestellter;

    private Date anlageDatum;

    private Historie historie;

    public Anlage(Historie historie, Date anlageDatum, Angestellter angestellter) {
        this.historie = historie;
        this.anlageDatum = anlageDatum;
        this.angestellter = angestellter;
    }

    public Anlage(Date anlageDatum) {
        this.anlageDatum = anlageDatum;
    }

    public Angestellter getAngestellter() {
        return angestellter;
    }

    public void setAngestellter(Angestellter angestellter) {
        this.angestellter = angestellter;
    }

    public Date getAnlageDatum() {
        return anlageDatum;
    }

    public void setAnlageDatum(Date anlageDatum) {
        this.anlageDatum = anlageDatum;
    }

    public Historie getHistorie() {
        return historie;
    }

    public void setHistorie(Historie historie) {
        this.historie = historie;
    }
}
