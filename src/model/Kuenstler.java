package model;

import java.util.Date;

public class Kuenstler {

    private String name;
    private Date geburtsdatum;
    private Date todesdatum;
    private String nationalitaet;

    public Kuenstler(String name, Date geburtsdatum, Date todesdatum, String nationalitaet) {
        this.name = name;
        this.geburtsdatum = geburtsdatum;
        this.todesdatum = todesdatum;
        this.nationalitaet = nationalitaet;
    }

    public String getLebensspanne() {

        return null;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public Date getTodesdatum() {
        return todesdatum;
    }

    public void setTodesdatum(Date todesdatum) {
        this.todesdatum = todesdatum;
    }

    public String getNationalitaet() {
        return nationalitaet;
    }

    public void setNationalitaet(String nationalitaet) {
        this.nationalitaet = nationalitaet;
    }
}
