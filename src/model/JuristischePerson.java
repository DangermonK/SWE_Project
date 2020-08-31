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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
