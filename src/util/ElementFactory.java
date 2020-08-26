package util;

import datentypen.Classtype;
import model.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

        Exponat exponat = new Exponat(args[0], args[1]);

        exponat.setBeschreibung(args[2]);
        exponat.setKategorie(args[3]);

        if(args[4].matches("[0-9]*"))
            exponat.setErstellungsJahr(Integer.valueOf(args[4]));
        if(args[5].matches("[0-9]+\\.[0-9]*"))
            exponat.setSchaetzWert(Double.valueOf(args[5]));

        exponat.setMaterial(args[6]);
        exponat.setInWeb(Boolean.parseBoolean(args[7]));
        exponat.setKuenstler(createKuenstler(args[8].split(",")));

        // TODO: Check if regexes can be used like this.
        exponat.setBildList(createBildList(args[9].split(",")));

        exponat.setExpTypList(createExponattypList(args[10].split(",")));

        Historie historie = createHistorie(args[11].split(":"));
        historie.setExponat(exponat);
        exponat.setHistorie(historie);

        // TODO: implement Foerderungs array solve historie references
        String[] besitzerArray = args[12].split(",");
        for(String ref : besitzerArray) {
            ((Besitzer)adapter.getElement(Classtype.BESITZER, ref)).addExponat(exponat);
            exponat.addBesitzer((Besitzer)adapter.getElement(Classtype.BESITZER, ref));
        }

        return exponat;

    }

    public Angestellter createAngestellter(String[] args) {

        Angestellter angestellter = new Angestellter(args[0], args[1], args[2]);
        angestellter.setRolle(Rolle.valueOf(args[3]));

        angestellter.setBildList(createBildList(args[4].split(",")));

        // TODO: implement Anlage and Aenderung array

        return angestellter;

    }

    public Besitzer createBesitzer(String[] args) {

        Besitzer besitzer = new Besitzer(args[0], args[1], args[2], args[3], args[4]);

        besitzer.setBildList(createBildList(args[5].split(",")));

        // TODO: implement Exponat array

        return besitzer;

    }

    public Foerdernder createFoerdernder(String[] args) {

        Foerdernder foerdernder = new Foerdernder(args[0], args[1], args[2], args[3], args[4]);

        foerdernder.setBildList(createBildList(args[5].split(",")));

        // TODO: implement Exponat array

        String[] exponatFoerderungArray = args[6].split(",");
        for(String exponatFoerderung : exponatFoerderungArray) {
            ExponatsFoerderung foerderung = createExponatFoerderung(exponatFoerderung.split("-"));
            foerderung.setFoerdernder(foerdernder);
            foerdernder.addFoerderung(foerderung);
        }

        String[] museumFoerderungArray = args[7].split(",");
        for(String museumFoerderung : museumFoerderungArray) {
            MuseumsFoerderung foerderung = createMuseumsFoerderung(museumFoerderung.split("-"));
            foerderung.setFoerdernder(foerdernder);
            foerdernder.addFoerderung(foerderung);
        }

        return foerdernder;

    }

    public Raum createRaum(String[] args) {

        Raum raum = new Raum(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));

        raum.setBeschreibung(args[3]);
        raum.setKategorie(args[4]);

        raum.setBildList(createBildList(args[5].split(",")));

        // TODO: implement Exponat array
        // This is a temporary test
        // ----------------------------
        String[] expRefArray = args[6].split(",");
        for(String ref: expRefArray) {
            ((Exponat)adapter.getElement(Classtype.EXPONAT, ref)).setRaum(raum);
            raum.addExponat((Exponat)adapter.getElement(Classtype.EXPONAT, ref));
        }
        // ----------------------------

        return raum;

    }

    private List<Bild> createBildList(String[] args) {
        // TODO: Check if regexes can be used like this.
        List<Bild> bildList = new ArrayList<>();
        for (String bild : args) {
            String[] bildArgs = bild.split("\\.");
            bildList.add(createBild(bildArgs));
        }
        return bildList;
    }

    public Bild createBild(String[] args) {
        return new Bild(args[0], args[1]);
    }

    public Historie createHistorie(String[] args) {

        Historie historie = new Historie();

        List<Verleih> verleihList = createVerleihList(args[0].split(","));
        verleihList.forEach((verleih) -> {verleih.setHistorie(historie);});
        historie.setVerleihList(verleihList);

        List<Ausleihe> ausleiheList = createAusleiheList(args[1].split(","));
        ausleiheList.forEach((ausleihe -> {ausleihe.setHistorie(historie);}));
        historie.setAusleiheList(ausleiheList);

        List<Kauf> kaufList = createKaufList(args[2].split(","));
        kaufList.forEach((kauf -> {kauf.setHistorie(historie);}));
        historie.setKaufList(kaufList);

        List<Verkauf> verkaufList = createVerkaufList(args[3].split(","));
        verkaufList.forEach((verkauf -> {verkauf.setHistorie(historie);}));
        historie.setVerkaufList(verkaufList);

        Anlage anlage = createAnlage(args[4].split("-"));
        anlage.setHistorie(historie);
        historie.setAnlage(anlage);

        List<Aenderung> aenderungList = createaenderungList(args[5].split(","));
        aenderungList.forEach((aenderung -> {aenderung.setHistorie(historie);}));
        historie.setAenderungList(aenderungList);

        return historie;

    }

    public List<Verleih> createVerleihList(String[] args) {
        List<Verleih> verleihList = new ArrayList<Verleih>();
        for (String verleih : args) {
            String[] verleihArgs = verleih.split("-");
            verleihList.add(createVerleih(verleihArgs));
        }
        return verleihList;
    }

    public Verleih createVerleih(String[] args) {

        try {
            Verleih verleih = new Verleih(Statics.dateFormat.parse(args[0]), Statics.dateFormat.parse(args[1]), Double.parseDouble(args[2]));
            return verleih;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Ausleihe> createAusleiheList(String[] args) {
        List<Ausleihe> ausleiheList = new ArrayList<Ausleihe>();
        for (String ausleihe : args) {
            String[] ausleiheArgs = ausleihe.split("-");
            ausleiheList.add(createAusleihe(ausleiheArgs));
        }
        return ausleiheList;
    }

    public Ausleihe createAusleihe(String[] args) {

        try {
            Ausleihe ausleihe = new Ausleihe(Statics.dateFormat.parse(args[0]), Statics.dateFormat.parse(args[1]));
            return ausleihe;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Kauf> createKaufList(String[] args) {
        List<Kauf> kaufList = new ArrayList<Kauf>();
        for (String kauf : args) {
            String[] kaufArgs = kauf.split("-");
            kaufList.add(createKauf(kaufArgs));
        }
        return kaufList;
    }

    public Kauf createKauf(String[] args) {

        try {
            Kauf kauf = new Kauf(Statics.dateFormat.parse(args[0]), Double.parseDouble(args[1]));
            return kauf;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Verkauf> createVerkaufList(String[] args) {
        List<Verkauf> verkaufList = new ArrayList<Verkauf>();
        for (String verkauf : args) {
            String[] verkaufArgs = verkauf.split("-");
            verkaufList.add(createVerkauf(verkaufArgs));
        }
        return verkaufList;
    }

    public Verkauf createVerkauf(String[] args) {

        try {
            Verkauf verkauf = new Verkauf(Statics.dateFormat.parse(args[0]), Double.parseDouble(args[1]));
            return verkauf;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Anlage createAnlage(String[] arg) {

        try {
            Anlage anlage = new Anlage(Statics.dateFormat.parse(arg[0]));
            ((Angestellter)adapter.getElement(Classtype.ANGESTELLTER, arg[1])).addAnlage(anlage);
            anlage.setAngestellter((Angestellter)adapter.getElement(Classtype.ANGESTELLTER, arg[1]));
            return anlage;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Aenderung> createaenderungList(String[] args) {
        List<Aenderung> aenderungList = new ArrayList<Aenderung>();
        for (String aenderung : args) {
            aenderungList.add(createAenderung(aenderung.split("-")));
        }
        return aenderungList;
    }

    public Aenderung createAenderung(String[] arg) {

        try {
            Aenderung aenderung = new Aenderung(Statics.dateFormat.parse(arg[0]), (Angestellter)adapter.getElement(Classtype.ANGESTELLTER, arg[1]));
            ((Angestellter)adapter.getElement(Classtype.ANGESTELLTER, arg[1])).addAenderung(aenderung);
            return aenderung;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    private List<Exponattyp> createExponattypList(String[] args) {
        List<Exponattyp> exponatTypArr = new ArrayList<Exponattyp>();
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

        ExponatsFoerderung foerderung = new ExponatsFoerderung(args[0], Double.parseDouble(args[1]));
        ((Exponat)adapter.getElement(Classtype.EXPONAT, args[2])).addFoerderung(foerderung);
        foerderung.setExponat((Exponat)adapter.getElement(Classtype.EXPONAT, args[2]));

        return foerderung;

    }

    public MuseumsFoerderung createMuseumsFoerderung(String[] args) {

        return new MuseumsFoerderung(args[0], Double.parseDouble(args[1]));

    }

    public Kuenstler createKuenstler(String[] args) {
        try {
            Date geburt = Statics.dateFormat.parse(args[1]);
            Date tod = Statics.dateFormat.parse(args[2]);
            return new Kuenstler(args[0], geburt, tod, args[3]);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

}
