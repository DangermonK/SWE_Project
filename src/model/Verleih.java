package model;

import java.util.Date;

public class Verleih {

    private Date verleihEingangsdatum;
    private Date verleihAusgangsdatum;
    private double leihwert;

    private Historie historie;

    public Verleih(Historie historie, Date verleihEingangsdatum, Date verleihAusgangsdatum, double leihwert) {
        this.historie = historie;
        this.verleihEingangsdatum = verleihEingangsdatum;
        this.verleihAusgangsdatum = verleihAusgangsdatum;
        this.leihwert = leihwert;
    }

    public Verleih(Date verleihEingangsdatum, Date verleihAusgangsdatum, double leihwert) {
        this.verleihEingangsdatum = verleihEingangsdatum;
        this.verleihAusgangsdatum = verleihAusgangsdatum;
        this.leihwert = leihwert;
    }

    public Date getVerleihEingangsdatum() {
        return verleihEingangsdatum;
    }

    public void setVerleihEingangsdatum(Date verleihEingangsdatum) {
        this.verleihEingangsdatum = verleihEingangsdatum;
    }

    public Date getVerleihAusgangsdatum() {
        return verleihAusgangsdatum;
    }

    public void setVerleihAusgangsdatum(Date verleihAusgangsdatum) {
        this.verleihAusgangsdatum = verleihAusgangsdatum;
    }

    public double getLeihwert() {
        return leihwert;
    }

    public void setLeihwert(double leihwert) {
        this.leihwert = leihwert;
    }

    public Historie getHistorie() {
        return historie;
    }

    public void setHistorie(Historie historie) {
        this.historie = historie;
    }
}
