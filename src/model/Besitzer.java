package model;

import java.util.ArrayList;
import java.util.List;

public class Besitzer extends JuristischePerson {

    private List<Exponat> exponatList;

    public Besitzer(String persNr, String name, String email, String adresse, String telefon) {
        super(persNr, name, email, adresse, telefon);

        exponatList = new ArrayList<>();

    }

    public void addExponat(Exponat exponat) {
        exponatList.add(exponat);
    }

    public List<Exponat> getExponatList() {
        return exponatList;
    }

    public void setExponatList(List<Exponat> exponatList) {
        this.exponatList = exponatList;
    }
}
