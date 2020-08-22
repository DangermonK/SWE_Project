package model;

public class Angestellter extends Person {

    private Rolle rolle;

    private String domaenenName;

    private Anlage[] anlageArray;
    private Aenderung[] aenderungsarray;

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

    public Anlage[] getAnlageArray() {
        return anlageArray;
    }

    public void setAnlageArray(Anlage[] anlageArray) {
        this.anlageArray = anlageArray;
    }

    public Aenderung[] getAenderungsarray() {
        return aenderungsarray;
    }

    public void setAenderungsarray(Aenderung[] aenderungsarray) {
        this.aenderungsarray = aenderungsarray;
    }

    public String getDomaenenName() {
        return domaenenName;
    }

    public void setDomaenenName(String domaenenName) {
        this.domaenenName = domaenenName;
    }
}
