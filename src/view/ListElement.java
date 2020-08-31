package view;

import de.dhbwka.swe.utils.model.IListElement;
import model.*;
import util.Statics;

import java.util.HashMap;

//Eigene WrapperKlasse für das Interface IListElement. So mussten wir nicht nachträglich das Interface in
// unseren Modell-Klassen implementieren. Und können stattdessen diese Wrapperklasse verwenden
public class ListElement implements IListElement {

    private String listText;
    private int hash;
    private String besitzerPersNr;


    //Erstellt aus unseren ModelObjekten ListElemente und baut die Listentexte zusammen, die angezeigt werden sollen.
    public ListElement(Anlage anlage) {
        this.listText = "Anlagedatum: " + Statics.dateFormat.format(anlage.getAnlageDatum()) + ", Angelegt von: " + anlage.getAngestellter().getName();
    }

    public ListElement(Aenderung aenderung) {
        this.listText = "Änderungsdatum: " + Statics.dateFormat.format(aenderung.getAenderungsDatum()) + ", bearbeitet von: " + aenderung.getAngestellter().getName();
    }

    public ListElement(Kauf kauf) {
        this.listText = "Erwerbsdatum: " + Statics.dateFormat.format(kauf.getErwerbsDatum()) + ", Einkaufswert: " + kauf.getKaufwert() + "€";
    }

    public ListElement(Verkauf verkauf) {
        this.listText = "Verkaufsdatum: " + Statics.dateFormat.format(verkauf.getVerkaufsDatum()) + ", Verkaufswert: " + verkauf.getVerkaufswert() + "€";
    }

    public ListElement(Ausleihe ausleihe) {
        this.listText = "Ausleiheingangsdatum: " + Statics.dateFormat.format(ausleihe.getAusleiheEingang()) + ", Ausleihausgangsdatum: Datum: " + Statics.dateFormat.format(ausleihe.getAusleiheAusgang());
    }

    public ListElement(Verleih verleih) {
        this.listText = "Verleihausgangsdatum: " + Statics.dateFormat.format(verleih.getVerleihAusgangsdatum()) + ", Verleiheingangsdatum: Datum: " + Statics.dateFormat.format(verleih.getVerleihEingangsdatum());
    }

    public ListElement(Besitzer besitzer, String besitzerPersNr) {
        this.besitzerPersNr = besitzerPersNr;
        this.listText = "Name: " + besitzer.getName()
        +", E-Mail: "+besitzer.getEmail();
    }

    public ListElement(Kuenstler kuenstler) {
        this.listText = "Name: " + kuenstler.getName() + ", nationalität: " + kuenstler.getNationalitaet() +
                ", lebensspanne: " + kuenstler.getLebensspanne();
    }

    //speichert auch den Hash der Förderung der später zur Speicherung der Förderung benötigt wird
    public ListElement(Foerderung foerderung, int hash) {
        this.hash = hash;
        this.listText = "Name: " + foerderung.getFoerdernder().getName() + ", Art: " + foerderung.getFoerderungsart()
                + ", Mittel: " + foerderung.getFoerderungsmittel() + "€";
    }

    public ListElement(Exponattyp exponattyp) {
        this.listText = "Bezeichnung: " + exponattyp.getBezeichnung() + ", Beschreibung: " + exponattyp.getBeschreibung();
    }

    public int getFoerderungElementHash() {
        return hash;
    }

    public String getBesitzerPersNr() {
        return besitzerPersNr;
    }

    @Override
    public Class<?> getElementClass() {
        return null;
    }

    @Override
    public String getElementName() {
        return null;
    }

    @Override
    public HashMap<String, Object> getDetails() {
        return null;
    }

    @Override
    public String getListText() {
        return this.listText;
    }

    @Override
    public String[] getAttributeNames() {
        return new String[0];
    }


}
