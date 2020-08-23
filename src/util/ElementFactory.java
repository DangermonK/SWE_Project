package util;

import model.*;

import javax.swing.text.DateFormatter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        if(args.length > 2)
            exp.setBeschreibung(args[2]);
        if(args.length > 3)
            exp.setKategorie(args[3]);
        if(args.length > 4) {
            if(args[4].matches("[0-9]*"))
                exp.setErstellungsJahr(Integer.valueOf(args[4]));
        }
        if(args.length > 5) {
            if(args[5].matches("[0-9]+\\.[0-9]*"))
            exp.setSchaetzWert(Double.valueOf(args[5]));
        }
        if(args.length > 6)
            exp.setMaterial(args[6]);
        if(args.length > 7)
            exp.setInWeb(Boolean.parseBoolean(args[7]));
        if(args.length > 8)
            exp.setKuenstler(createKuenstler(args[8].split(",")));

        // TODO: Check if regexes can be used like this.
        if(args.length > 9) {
            exp.setBildArray(createBildArray(args[9].split(",")));
        }

        if(args.length > 10) {
            exp.setExpTypArray(createExponattypList(args[10].split(",")));
        }

        // TODO: implement Besitzer, Foerderungs, Raum, Historie array

        return exp;

    }

    public Angestellter createAngestellter(String[] args) {

        Angestellter angestellter = new Angestellter(args[0], args[1], args[2]);
        angestellter.setRolle(Rolle.valueOf(args[3]));

        angestellter.setBildArray(createBildArray(args[4].split(",")));

        // TODO: implement Anlage and Aenderung array

        return angestellter;

    }

    public Besitzer createBesitzer(String[] args) {

        Besitzer besitzer = new Besitzer(args[0], args[1], args[2], args[3], args[4]);

        besitzer.setBildArray(createBildArray(args[5].split(",")));

        // TODO: implement Exponat array

        return besitzer;

    }

    public Foerdernder createFoerdernder(String[] args) {

        Foerdernder foerdernder = new Foerdernder(args[0], args[1], args[2], args[3], args[4]);

        foerdernder.setBildArray(createBildArray(args[5].split(",")));

        // TODO: implement Exponat array

        return foerdernder;

    }

    public Raum createRaum(String[] args) {

        Raum raum = new Raum(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));

        raum.setBildArray(createBildArray(args[4].split(",")));

        // TODO: implement Exponat array

        return raum;

    }

    private List<Bild> createBildArray(String[] args) {
        // TODO: Check if regexes can be used like this.
        List<Bild> bildArr = new LinkedList<Bild>();
        for (String bild : args) {
            String[] bildArgs = bild.split("\\.");
            bildArr.add(createBild(bildArgs));
        }
        return bildArr;
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

    private List<Exponattyp> createExponattypList(String[] args) {
        List<Exponattyp> exponatTypArr = new LinkedList<Exponattyp>();
        for (String exponatTyp : args) {
            String[] exponatTypArg = exponatTyp.split("\\.");
            exponatTypArr.add(createExponattyp(exponatTypArg));
        }
        return exponatTypArr;
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
        try {
            return new Kuenstler(args[0], Statics.dateFormat.parse(args[1]), Statics.dateFormat.parse(args[2]), args[3]);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

}
