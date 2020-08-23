package model;

import java.util.List;

public class Historie {

    private Anlage anlage;
    private List<Aenderung> aenderungArray;
    private List<Verkauf> verkaufArray;
    private List<Verleih> verleihArray;
    private List<Kauf> kaufArray;
    private List<Ausleihe> ausleiheArray;

    private Exponat exponat;

    public Historie() {

    }

    public Aenderung getLetzteAenderung() {
        return null; //TODO: logic
    }

    public Anlage getAnlage() {
        return anlage;
    }

    public void setAnlage(Anlage anlage) {
        this.anlage = anlage;
    }

    public List<Aenderung> getAenderungList() {
        return aenderungArray;
    }

    public void setAenderungList(List<Aenderung> aenderungArray) {
        this.aenderungArray = aenderungArray;
    }

    public List<Verkauf> getVerkaufList() {
        return verkaufArray;
    }

    public void setVerkaufList(List<Verkauf> verkaufArray) {
        this.verkaufArray = verkaufArray;
    }

    public List<Verleih> getVerleihList() {
        return verleihArray;
    }

    public void setVerleihList(List<Verleih> verleihArray) {
        this.verleihArray = verleihArray;
    }

    public List<Kauf> getKaufList() {
        return kaufArray;
    }

    public void setKaufList(List<Kauf> kaufArray) {
        this.kaufArray = kaufArray;
    }

    public List<Ausleihe> getAusleiheList() {
        return ausleiheArray;
    }

    public void setAusleiheList(List<Ausleihe> ausleiheArray) {
        this.ausleiheArray = ausleiheArray;
    }

    public Exponat getExponat() {
        return exponat;
    }

    public void setExponat(Exponat exponat) {
        this.exponat = exponat;
    }
}
