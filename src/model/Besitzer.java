package model;

public class Besitzer extends JuristischePerson {

    private Exponat[] exponatArray;

    public Besitzer(String persNr, String name, String email, String adresse, String telefon) {
        super(persNr, name, email, adresse, telefon);

    }

    public Exponat[] getExponatArray() {
        return exponatArray;
    }

    public void setExponatArray(Exponat[] exponatArray) {
        this.exponatArray = exponatArray;
    }
}
