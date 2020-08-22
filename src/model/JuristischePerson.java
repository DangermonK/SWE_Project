package model;

public abstract class JuristischePerson extends Person {

    private String email;
    private String adresse;
    private String telefon;

    public JuristischePerson(String persNr, String name, String email, String adresse, String telefon) {
        super(persNr, name);

        this.email = email;
        this.adresse = adresse;
        this.telefon = telefon;
    }

}
