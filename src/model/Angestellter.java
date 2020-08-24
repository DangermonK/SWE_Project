package model;

import java.util.List;

public class Angestellter extends Person {

    private Rolle rolle;

    private String domaenenName;

    private List<Anlage> anlageList;
    private List<Aenderung> aenderungsList;

    public Angestellter(String persNr, String name, String domaenenName) {
        super(persNr, name);
        this.domaenenName = domaenenName;

    }

    public Rolle getRolle() {
        return rolle;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    public String getDomaenenName() {
        return domaenenName;
    }

    public void setDomaenenName(String domaenenName) {
        this.domaenenName = domaenenName;
    }

    public List<Anlage> getAnlageList() {
        return anlageList;
    }

    public void setAnlageList(List<Anlage> anlageList) {
        this.anlageList = anlageList;
    }

    public List<Aenderung> getAenderungsList() {
        return aenderungsList;
    }

    public void setAenderungsList(List<Aenderung> aenderungsList) {
        this.aenderungsList = aenderungsList;
    }
}
