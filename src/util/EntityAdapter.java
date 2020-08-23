package util;

import datentypen.Classtype;
import model.*;

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
                Raum raum = ElementFactory.getInstance(this).createRaum(argList);
                raumEntityManager.persist(raum.getNummer(), raum);
                break;
            case EXPONAT:
                Exponat exponat = ElementFactory.getInstance(this).createExponat(argList);
                exponatEntityManager.persist(exponat.getInventarnummer(), exponat);
                break;
            case ANGESTELLTER:
                Angestellter angestellter = ElementFactory.getInstance(this).createAngestellter(argList);
                personEntityManager.persist(angestellter.getPersNr(), angestellter);
                break;
            case BESITZER:
                Besitzer besitzer = ElementFactory.getInstance(this).createBesitzer(argList);
                personEntityManager.persist(besitzer.getPersNr(), besitzer);
                break;
            case FOERDERNDER:
                Foerdernder foerdernder = ElementFactory.getInstance(this).createFoerdernder(argList);
                personEntityManager.persist(foerdernder.getPersNr(), foerdernder);
                break;
        }
    }

    public void createAll(String data) {
        String argList[] = data.split("\n");
        for(String arg: argList) {
            addElement(Classtype.EXPONAT, arg);
        }
    }

}
