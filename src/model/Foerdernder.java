package model;

import java.util.ArrayList;
import java.util.List;

public class Foerdernder extends JuristischePerson {

    private List<Foerderung> foerderungList;

    public Foerdernder(String persNr, String name, String email, String adresse, String telefon) {
        super(persNr, name, email, adresse, telefon);

        foerderungList = new ArrayList<>();

    }

    public List<Exponat> getExponate() {
        List<Exponat> exponatList = new ArrayList<>();
        foerderungList.forEach(foerderung -> {
            if(foerderung instanceof ExponatsFoerderung) {
                exponatList.add(((ExponatsFoerderung) foerderung).getExponat());
            }
        });
        return exponatList;
    }

    public void addFoerderung(Foerderung foerderung) {
        foerderungList.add(foerderung);
    }

    public List<Foerderung> getFoerderungList() {
        return foerderungList;
    }

    public void setFoerderungList(List<Foerderung> foerderungList) {
        this.foerderungList = foerderungList;
    }
}
