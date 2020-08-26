package util;

import datentypen.Classtype;
import model.*;

import java.lang.reflect.Array;
import java.util.List;

public class EntityAdapter {

    private EntityManager<Person> personEntityManager;
    private EntityManager<Exponat> exponatEntityManager;
    private EntityManager<Raum> raumEntityManager;

    public EntityAdapter() {
        personEntityManager = new EntityManager<>();
        exponatEntityManager = new EntityManager<>();
        raumEntityManager = new EntityManager<>();
    }

    public void getAllData() {}

    public Object getElement(Classtype type, Object key) {
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

    public void removeElement(Classtype type, Object key) {
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

    public void changeElement(Classtype type, Object key, String data) {}

    public void addElement(Classtype type, String[] data) {
        switch (type) {
            case RAUM:
                Raum raum = ElementFactory.getInstance(this).createRaum(data);
                raumEntityManager.persist(raum.getNummer(), raum);
                break;
            case EXPONAT:
                Exponat exponat = ElementFactory.getInstance(this).createExponat(data);
                exponatEntityManager.persist(exponat.getInventarnummer(), exponat);
                break;
            case ANGESTELLTER:
                Angestellter angestellter = ElementFactory.getInstance(this).createAngestellter(data);
                personEntityManager.persist(angestellter.getPersNr(), angestellter);
                break;
            case BESITZER:
                Besitzer besitzer = ElementFactory.getInstance(this).createBesitzer(data);
                personEntityManager.persist(besitzer.getPersNr(), besitzer);
                break;
            case FOERDERNDER:
                Foerdernder foerdernder = ElementFactory.getInstance(this).createFoerdernder(data);
                personEntityManager.persist(foerdernder.getPersNr(), foerdernder);
                break;
        }
    }

    public void createAll(List<String[]> data) {
        data.forEach(element -> {
            Classtype type = Classtype.valueOf(element[0]);
            String[] attribuutes = new String[element.length-1];
            System.arraycopy(element, 1, attribuutes, 0, attribuutes.length);
            addElement(type, attribuutes);
        });
    }

}
