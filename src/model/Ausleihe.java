package model;

import java.util.Date;

public class Ausleihe {

    private Date ausleiheEingang;
    private Date ausleiheAusgang;

    private Historie historie;

    public Ausleihe(Historie historie, Date ausleiheEingang, Date ausleiheAusgang) {
        this.historie = historie;
        this.ausleiheEingang = ausleiheEingang;
        this.ausleiheAusgang = ausleiheAusgang;
    }

    public Ausleihe(Date ausleiheEingang, Date ausleiheAusgang) {
        this.ausleiheEingang = ausleiheEingang;
        this.ausleiheAusgang = ausleiheAusgang;
    }

    public Date getAusleiheEingang() {
        return ausleiheEingang;
    }

    public void setAusleiheEingang(Date ausleiheEingang) {
        this.ausleiheEingang = ausleiheEingang;
    }

    public Date getAusleiheAusgang() {
        return ausleiheAusgang;
    }

    public void setAusleiheAusgang(Date ausleiheAusgang) {
        this.ausleiheAusgang = ausleiheAusgang;
    }

    public Historie getHistorie() {
        return historie;
    }

    public void setHistorie(Historie historie) {
        this.historie = historie;
    }
}
