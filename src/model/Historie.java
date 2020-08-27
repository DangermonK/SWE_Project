package model;

import java.util.List;

public class Historie {

    private Anlage anlage;
    private List<Aenderung> aenderungList;
    private List<Verkauf> verkaufList;
    private List<Verleih> verleihList;
    private List<Kauf> kaufList;
    private List<Ausleihe> ausleiheList;

    private Exponat exponat;

    public Historie() {

    }

    public Aenderung getLetzteAenderung() {
        return aenderungList.get(0);
    }

    public Anlage getAnlage() {
        return anlage;
    }

    public void setAnlage(Anlage anlage) {
        this.anlage = anlage;
    }

    public List<Aenderung> getAenderungList() {
        return aenderungList;
    }

    public void setAenderungList(List<Aenderung> aenderungList) {
        this.aenderungList = aenderungList;
    }

    public List<Verkauf> getVerkaufList() {
        return verkaufList;
    }

    public void setVerkaufList(List<Verkauf> verkaufList) {
        this.verkaufList = verkaufList;
    }

    public List<Verleih> getVerleihList() {
        return verleihList;
    }

    public void setVerleihList(List<Verleih> verleihList) {
        this.verleihList = verleihList;
    }

    public List<Kauf> getKaufList() {
        return kaufList;
    }

    public void setKaufList(List<Kauf> kaufList) {
        this.kaufList = kaufList;
    }

    public List<Ausleihe> getAusleiheList() {
        return ausleiheList;
    }

    public void setAusleiheList(List<Ausleihe> ausleiheList) {
        this.ausleiheList = ausleiheList;
    }

    public Exponat getExponat() {
        return exponat;
    }

    public void setExponat(Exponat exponat) {
        this.exponat = exponat;
    }
}
