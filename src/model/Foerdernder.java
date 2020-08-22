package model;

public class Foerdernder extends JuristischePerson {

    private Foerderung[] foerderungArray;

    public Foerdernder(String persNr, String name, String email, String adresse, String telefon) {
        super(persNr, name, email, adresse, telefon);
    }

    public Exponat[] getExponate() {
        return null; //Todo: add logic
    }

    public Foerderung[] getFoerderungArray() {
        return foerderungArray;
    }

    public void setFoerderungArray(Foerderung[] foerderungArray) {
        this.foerderungArray = foerderungArray;
    }
}
