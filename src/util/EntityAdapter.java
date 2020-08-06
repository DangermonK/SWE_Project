package util;

import datentypen.Classtype;
import model.Exponat;
import model.Person;
import model.Raum;

public class EntityAdapter {

    private EntityManager<Person> personEntityManager;
    private EntityManager<Exponat> exponatEntityManager;
    private EntityManager<Raum> raumEntityManager;

    public EntityAdapter() {
        personEntityManager = new EntityManager<Person>();
        exponatEntityManager = new EntityManager<Exponat>();
        raumEntityManager = new EntityManager<Raum>();
    }

    public void getAllData() {}
    public void getElement(Classtype type, int hash) {}
    public void removeElement(Classtype type, int hash) {}
    public void changeElement(Classtype type, int hash, String data) {}

    public void addElement(Classtype type, String data) {
        switch (type) {
            case RAUM:
                break;
            case EXPONAT:
                break;
            case ANGESTELLTER:
                break;
            case BESITZER:
                break;
            case FOERDERNDER:
                break;
        }
    }

    public void createAll(String data) {}

}
