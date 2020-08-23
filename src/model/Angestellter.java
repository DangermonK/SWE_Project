package model;

import java.util.List;

public class Angestellter extends Person {

    private Rolle rolle;

    private String domaenenName;

    private List<Anlage> anlageArray;
    private List<Aenderung> aenderungsarray;

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

    public List<Anlage> getAnlageArray() {
        return anlageArray;
    }

    public void setAnlageArray(List<Anlage> anlageArray) {
        this.anlageArray = anlageArray;
    }

    public List<Aenderung> getAenderungsarray() {
        return aenderungsarray;
    }

    public void setAenderungsarray(List<Aenderung> aenderungsarray) {
        this.aenderungsarray = aenderungsarray;
    }
}
