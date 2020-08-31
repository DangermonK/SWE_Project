package model;

public class Bild {

    private String titel;
    private String pfad;

    public Bild(String titel, String pfad) {
        this.titel = titel;
        this.pfad = pfad;
    }


    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getPfad() {
        return pfad;
    }

    public void setPfad(String pfad) {
        this.pfad = pfad;
    }
}
