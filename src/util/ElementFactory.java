package util;

import model.*;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class ElementFactory {

    private EntityAdapter adapter;
    private static ElementFactory instance;

    private ElementFactory(EntityAdapter adapter) {
        this.adapter = adapter;
    }

    public static ElementFactory getInstance(EntityAdapter adapter) {
        if(instance == null) {
            instance = new ElementFactory(adapter);
        }
        return instance;
    }

    public Exponat createExponat(String[] args) {

        Exponat exp = new Exponat(args[0], args[1]);
        exp.setBeschreibung(args[2]);
        exp.setKategorie(args[3]);
        exp.setErstellungsJahr(Integer.valueOf(args[4]));
        exp.setSchaetzWert(Double.valueOf(args[5]));
        exp.setMaterial(args[6]);
        exp.setInWeb(Boolean.parseBoolean(args[7]));

        // TODO: Check if regexes can be used like this.
        String bildArgArr[] = args[8].split(",");
        List<Bild> bildArr = new LinkedList<Bild>();
        for (String bild : bildArgArr) {
            String bildArgs[] = bild.split(".");
            bildArr.add(createBild(bildArgs));
        }
        exp.setBildArray(bildArr);

        String exponatTypArgArr[] = args[9].split(",");
        List<Exponattyp> exponatTypArr = new LinkedList<Exponattyp>();
        for (String exponatTyp : exponatTypArgArr) {
            String exponatTypArg[] = exponatTyp.split(".");
            exponatTypArr.add(createExponattyp(exponatTypArg));
        }
        exp.setExpTypArray(exponatTypArr);

        // TODO: implement Besitzer and Foerderungs array

        return exp;

    }

    public Angestellter createAngestellter(String[] args) {

        Angestellter angestellter = new Angestellter(args[0], args[1], args[2]);
        angestellter.setRolle(Rolle.valueOf(args[3]));

        // TODO: Check if regexes can be used like this.
        String bildArgArr[] = args[4].split(",");
        List<Bild> bildArr = new LinkedList<Bild>();
        for (String bild : bildArgArr) {
            String bildArgs[] = bild.split(".");
            bildArr.add(createBild(bildArgs));
        }
        angestellter.setBildArray(bildArr);

        // TODO: implement Anlage and Aenderung array

        return angestellter;

    }

    public Besitzer createBesitzer(String[] args) {

        Besitzer besitzer = new Besitzer(args[0], args[1], args[2], args[3], args[4]);

        // TODO: Check if regexes can be used like this.
        String bildArgArr[] = args[5].split(",");
        List<Bild> bildArr = new LinkedList<Bild>();
        for (String bild : bildArgArr) {
            String bildArgs[] = bild.split(".");
            bildArr.add(createBild(bildArgs));
        }
        besitzer.setBildArray(bildArr);

        // TODO: implement Exponat array

        return besitzer;

    }

    public Foerdernder createFoerdernder(String[] args) {

        Foerdernder foerdernder = new Foerdernder(args[0], args[1], args[2], args[3], args[4]);

        // TODO: Check if regexes can be used like this.
        String bildArgArr[] = args[5].split(",");
        List<Bild> bildArr = new LinkedList<Bild>();
        for (String bild : bildArgArr) {
            String bildArgs[] = bild.split(".");
            bildArr.add(createBild(bildArgs));
        }
        foerdernder.setBildArray(bildArr);

        // TODO: implement Exponat array

        return foerdernder;

    }

    public Raum createRaum(String[] args) {

        Raum raum = new Raum(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));

        // TODO: Check if regexes can be used like this.
        String bildArgArr[] = args[4].split(",");
        List<Bild> bildArr = new LinkedList<Bild>();
        for (String bild : bildArgArr) {
            String bildArgs[] = bild.split(".");
            bildArr.add(createBild(bildArgs));
        }
        raum.setBildArray(bildArr);

        // TODO: implement Exponat array

        return raum;

    }

    public Bild createBild(String[] args) {
        return new Bild(args[0], args[1]);
    }

    public Historie createHistorie(String[] args) {

        return null;

    }

    public Verleih createVerleih(String[] args) {

        return null;

    }

    public Ausleihe createAusleihe(String[] args) {

        return null;

    }

    public Kauf createKauf(String[] args) {

        return null;

    }

    public Verkauf createVerkauf(String[] args) {

        return null;

    }

    public Anlage createAnlage(String[] args) {

        return null;

    }

    public Aenderung createAenderung(String[] args) {

        return null;

    }

    public Exponattyp createExponattyp(String[] args) {
        return new Exponattyp(args[0], args[1]);
    }

    public ExponatsFoerderung createExponatFoerderung(String[] args) {

        return null;

    }

    public MuseumsFoerderung createMuseumsFoerderung(String[] args) {

        return null;

    }

    public Kuenstler createKuenstler(String[] args) {

        return new Kuenstler(args[0], Date.valueOf(args[1]), Date.valueOf(args[2]), args[3]);

    }

}
