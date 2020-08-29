package model;

import util.Statics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getLebensspanne() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return (geburtsdatum != null ? format.format(geburtsdatum) : "N/A") + " - " + (todesdatum != null ? format.format(todesdatum) : "N/A");
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
