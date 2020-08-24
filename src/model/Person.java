package model;


import java.util.List;

public abstract class Person {

    private String name;
    private String persNr;

    private List<Bild> bildList;

    public Person(String persNr, String name) {
        this.name = name;

        this.persNr = persNr;

    }

    public String getPersNr() {
        return this.persNr;
    }

    public String getName() {
        return name;
    }

    public List<Bild> getBildList() {
        return bildList;
    }

    public void setBildList(List<Bild> bildList) {
        this.bildList = bildList;
    }
}
