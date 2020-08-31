package model;

public class ExponatsFoerderung extends Foerderung {

    private Exponat exponat;

    public ExponatsFoerderung(String foerderungsart, double foerderungsmittel) {
        super(foerderungsart, foerderungsmittel);
    }

    public Exponat getExponat() {
        return exponat;
    }

    public void setExponat(Exponat exponat) {
        this.exponat = exponat;
    }
}
