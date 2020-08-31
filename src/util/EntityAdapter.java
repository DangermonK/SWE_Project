package util;

import datentypen.Classtype;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class EntityAdapter {

    private EntityManager<Person> personEntityManager;
    private EntityManager<Exponat> exponatEntityManager;
    private EntityManager<Raum> raumEntityManager;

    /*
        Verwaltet alle Model Objekte gegliedert in einer Map
     */
    public EntityAdapter() {
        personEntityManager = new EntityManager<>();
        exponatEntityManager = new EntityManager<>();
        raumEntityManager = new EntityManager<>();
    }

    public List<Exponat> getExponatList() {
        return exponatEntityManager.getAsList();
    }

    public List<Person> getPersonList() {
        return personEntityManager.getAsList();
    }

    public List<Raum> getRaumList() {

        return raumEntityManager.getAsList();
    }


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
                Exponat exponat = exponatEntityManager.find(key);

                exponat.getFoerderungList().forEach(exponatsFoerderung -> {
                    exponatsFoerderung.getFoerdernder().getFoerderungList().remove(exponatsFoerderung);
                });

                Anlage anlage = exponat.getHistorie().getAnlage();
                anlage.getAngestellter().getAnlageList().remove(anlage);

                exponat.getHistorie().getAenderungList().forEach(aenderung -> {
                    aenderung.getAngestellter().getAenderungsList().remove(aenderung);
                });

                exponat.getRaum().getExponatList().remove(exponat);

                exponat.getBesitzerList().forEach(besitzer -> {
                    besitzer.getExponatList().remove(exponat);
                });

                exponatEntityManager.remove(key);
            case RAUM:
                raumEntityManager.remove(key);
            case ANGESTELLTER:
            case BESITZER:
            case FOERDERNDER:
                personEntityManager.remove(key);
        }
    }

    // Erstellt ein einzelnes Element mit einem String Array und speichert es
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

    // Erstellt alle Daten mit einer Liste von String Arrays und speichert sie
    public void createAll(List<String[]> data) {
        data.forEach(element -> {
            Classtype type = Classtype.valueOf(element[0]);
            String[] attribuutes = new String[element.length - 1];
            System.arraycopy(element, 1, attribuutes, 0, attribuutes.length);
            addElement(type, attribuutes);
        });
    }

    // Gibt das Gesamte Model object als JSONObjekt zum speichern zurück
    public JSONObject getAllData() {
        JSONObject data = new JSONObject();
        data.put("raeume", getRaumData());
        data.put("angestellte", getAngestellteData());
        data.put("besitzer", getBesitzerData());
        data.put("exponate", getExponatData());
        data.put("foerdernde", getFoerderndeData());
        return data;
    }

    private JSONArray getRaumData() {
        JSONArray raumArr = new JSONArray();
        for (Raum raum : raumEntityManager.getAsList()) {
            JSONObject r = new JSONObject();
            r.put("nummer", raum.getNummer());
            r.put("groesse", raum.getGroesse());
            r.put("anzahl", raum.getExponatszahl());
            r.put("kategorie", raum.getKategorie());
            r.put("beschreibung", raum.getBeschreibung());
            r.put("bilder", createBildArray(raum.getBildList()));
            raumArr.add(r);
        }
        return raumArr;
    }

    private JSONArray getFoerderndeData() {
        JSONArray foerderndeArr = new JSONArray();
        for (Person person : personEntityManager.getAsList()) {
            if (person instanceof Foerdernder) {
                Foerdernder foe = (Foerdernder) person;
                JSONObject foerdernder = new JSONObject();
                foerdernder.put("persNr", foe.getPersNr());
                foerdernder.put("name", foe.getName());
                foerdernder.put("email", foe.getEmail());
                foerdernder.put("adresse", foe.getAdresse());
                foerdernder.put("telefon", foe.getTelefon());
                foerdernder.put("bilder", createBildArray(foe.getBildList()));

                JSONArray exponatFoerderungArr = new JSONArray();
                JSONArray museumFoerderungArr = new JSONArray();
                for (Foerderung foerderung : foe.getFoerderungList()) {
                    if (foerderung instanceof MuseumsFoerderung) {
                        JSONObject foer = new JSONObject();
                        foer.put("foerderungsart", foerderung.getFoerderungsart());
                        foer.put("foerderungsmittel", foerderung.getFoerderungsmittel());
                        museumFoerderungArr.add(foer);
                    } else if (foerderung instanceof ExponatsFoerderung) {
                        JSONObject foer = new JSONObject();
                        foer.put("foerderungsart", foerderung.getFoerderungsart());
                        foer.put("foerderungsmittel", foerderung.getFoerderungsmittel());
                        foer.put("exponat", ((ExponatsFoerderung) foerderung).getExponat().getInventarnummer());
                        exponatFoerderungArr.add(foer);

                    }
                }
                foerdernder.put("exponatsFoerderung", exponatFoerderungArr);
                foerdernder.put("museumsFoerderung", museumFoerderungArr);

                foerderndeArr.add(foerdernder);
            }
        }
        return foerderndeArr;
    }

    private JSONArray getBesitzerData() {
        JSONArray besitzerArr = new JSONArray();
        for (Person person : personEntityManager.getAsList()) {
            if (person instanceof Besitzer) {
                Besitzer bes = (Besitzer) person;
                JSONObject besitzer = new JSONObject();
                besitzer.put("persNr", bes.getPersNr());
                besitzer.put("name", bes.getName());
                besitzer.put("email", bes.getEmail());
                besitzer.put("adresse", bes.getAdresse());
                besitzer.put("telefon", bes.getTelefon());
                besitzer.put("bilder", createBildArray(bes.getBildList()));
                besitzerArr.add(besitzer);
            }
        }
        return besitzerArr;
    }

    private JSONArray getAngestellteData() {
        JSONArray angestellterArr = new JSONArray();
        for (Person person : personEntityManager.getAsList()) {
            if (person instanceof Angestellter) {
                Angestellter ang = (Angestellter) person;
                JSONObject angestellter = new JSONObject();
                angestellter.put("persNr", ang.getPersNr());
                angestellter.put("name", ang.getName());
                angestellter.put("domaenenname", ang.getDomaenenName());
                angestellter.put("rolle", ang.getRolle().toString());
                angestellter.put("bilder", createBildArray(ang.getBildList()));
                angestellterArr.add(angestellter);
            }
        }
        return angestellterArr;
    }

    private JSONArray createBildArray(List<Bild> bildList) {
        JSONArray bildArr = new JSONArray();
        for (Bild bild : bildList) {
            JSONObject b = new JSONObject();
            b.put("titel", bild.getTitel());
            b.put("pfad", bild.getPfad());
            bildArr.add(b);
        }
        return bildArr;
    }

    private JSONArray getExponatData() {
        JSONArray exponatArray = new JSONArray();
        for (Exponat exp : exponatEntityManager.getAsList()) {
            JSONObject exponat = new JSONObject();
            exponat.put("invNr", exp.getInventarnummer());
            exponat.put("name", exp.getName());
            exponat.put("beschreibung", exp.getBeschreibung());
            exponat.put("kategorie", exp.getKategorie());
            exponat.put("erstellungsjahr", exp.getErstellungsJahr());
            exponat.put("schaetzwert", exp.getSchaetzWert());
            exponat.put("material", exp.getMaterial());
            exponat.put("in web", exp.isInWeb());
            exponat.put("raum", exp.getRaum().getNummer());

            JSONObject kuenstler = new JSONObject();
            kuenstler.put("name", exp.getKuenstler().getName());
            kuenstler.put("geburt", (exp.getKuenstler().getGeburtsdatum() != null ? Statics.dateFormat.format(exp.getKuenstler().getGeburtsdatum()) : null));
            kuenstler.put("tod", (exp.getKuenstler().getTodesdatum() != null ? Statics.dateFormat.format(exp.getKuenstler().getTodesdatum()) : null));
            kuenstler.put("nationalitaet", exp.getKuenstler().getNationalitaet());
            exponat.put("kuenstler", kuenstler);

            exponat.put("bilder", createBildArray(exp.getBildList()));

            JSONArray exponattypen = new JSONArray();
            for (Exponattyp exponattyp : exp.getExpTypList()) {
                JSONObject expTyp = new JSONObject();
                expTyp.put("bezeichnung", exponattyp.getBezeichnung());
                expTyp.put("beschreibung", exponattyp.getBeschreibung());
                exponattypen.add(expTyp);
            }
            exponat.put("exponattypen", exponattypen);

            JSONObject historie = new JSONObject();

            JSONArray verleihArr = new JSONArray();
            for (Verleih verleih : exp.getHistorie().getVerleihList()) {
                JSONObject v = new JSONObject();
                v.put("eingangsdatum", Statics.dateFormat.format(verleih.getVerleihEingangsdatum()));
                v.put("ausgangsdatum", Statics.dateFormat.format(verleih.getVerleihAusgangsdatum()));
                v.put("wert", verleih.getLeihwert());
                verleihArr.add(v);
            }
            historie.put("verleih", verleihArr);

            JSONArray ausleihArr = new JSONArray();
            for (Ausleihe ausleihe : exp.getHistorie().getAusleiheList()) {
                JSONObject a = new JSONObject();
                a.put("eingangsdatum", Statics.dateFormat.format(ausleihe.getAusleiheEingang()));
                a.put("ausgangsdatum", Statics.dateFormat.format(ausleihe.getAusleiheAusgang()));
                ausleihArr.add(a);
            }
            historie.put("ausleihe", ausleihArr);

            JSONArray kaufArr = new JSONArray();
            for (Kauf kauf : exp.getHistorie().getKaufList()) {
                JSONObject k = new JSONObject();
                k.put("erwerbsdatum", Statics.dateFormat.format(kauf.getErwerbsDatum()));
                k.put("kaufwert", kauf.getKaufwert());
                kaufArr.add(k);
            }
            historie.put("kauf", kaufArr);

            JSONArray verkaufArr = new JSONArray();
            for (Verkauf verkauf : exp.getHistorie().getVerkaufList()) {
                JSONObject v = new JSONObject();
                v.put("verkaufsdatum", Statics.dateFormat.format(verkauf.getVerkaufsDatum()));
                v.put("verkaufswert", verkauf.getVerkaufswert());
                verkaufArr.add(v);
            }
            historie.put("verkauf", verkaufArr);

            JSONObject anlage = new JSONObject();
            Anlage a = exp.getHistorie().getAnlage();
            anlage.put("anlagedatum", (a != null ? Statics.dateFormat.format(a.getAnlageDatum()) : null));
            anlage.put("angestellter", (a != null ? a.getAngestellter().getPersNr() : null));
            historie.put("anlage", anlage);

            JSONArray aenderungArr = new JSONArray();
            for (Aenderung aenderung : exp.getHistorie().getAenderungList()) {
                JSONObject aObj = new JSONObject();
                aObj.put("aenderungsdatum", Statics.dateFormat.format(aenderung.getAenderungsDatum()));
                aObj.put("angestellter", aenderung.getAngestellter().getPersNr());
                aenderungArr.add(aObj);
            }
            historie.put("aenderung", aenderungArr);

            exponat.put("historie", historie);

            JSONArray besitzerArr = new JSONArray();
            for (Besitzer besitzer : exp.getBesitzerList()) {
                besitzerArr.add(besitzer.getPersNr());
            }
            exponat.put("besitzer", besitzerArr);

            exponatArray.add(exponat);
        }
        return exponatArray;
    }

}
