package model;

import java.util.Arrays;
import java.util.Objects;

public abstract class Person {

    private static int persZaehler = 0;

    private String name;
    private int persNr;

    private Bild[] bildArray;

    @Override
    public int hashCode() {
        return persNr;
    }
}
