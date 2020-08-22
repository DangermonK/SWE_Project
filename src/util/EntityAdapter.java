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

    public Object getElement(Classtype type, String key) {
        switch (type) {
            case EXPONAT:
                return exponatEntityManager.find(key);
            case RAUM:
                return raumEntityManager.find(key);
            case ANGESTELLTER:
            case BESITZER:
            case FOERDERNDER:
                return personEntityManager.find(key);
            default:
                return null;
        }
    }

    public void removeElement(Classtype type, String key) {
        switch (type) {
            case EXPONAT:
                exponatEntityManager.remove(key);
            case RAUM:
                raumEntityManager.remove(key);
            case ANGESTELLTER:
            case BESITZER:
            case FOERDERNDER:
                personEntityManager.remove(key);
        }
    }

    public void changeElement(Classtype type, String key, String data) {}

    public void addElement(Classtype type, String data) {
        String argList[] = data.split(";");
        switch (type) {
            case RAUM:
                ElementFactory.getInstance(this).createRaum(argList);
                break;
            case EXPONAT:
                ElementFactory.getInstance(this).createExponat(argList);
                break;
            case ANGESTELLTER:
                ElementFactory.getInstance(this).createAngestellter(argList);
                break;
            case BESITZER:
                ElementFactory.getInstance(this).createBesitzer(argList);
                break;
            case FOERDERNDER:
                ElementFactory.getInstance(this).createFoerdernder(argList);
                break;
        }
    }

    public void createAll(String data) {}

}
