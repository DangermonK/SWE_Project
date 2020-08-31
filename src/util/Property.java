package util;

import datentypen.ErweiterbareListe;
import java.util.*;

public class Property {

    private static Property instance;
    private HashMap<ErweiterbareListe, String[]> erweiterbareListeListMap;

    /*
        Speichert die erweiterbaren listen so ab, dass einfach darauf zugegriffen werden kann
     */
    private Property() {
        erweiterbareListeListMap = new HashMap<>();
    }

    public static Property getInstance() {
        if (instance == null) {
            instance = new Property();
        }

        return instance;
    }

    // tauscht zwei elemente um eine Reihenfolge zu erstellen
    public void swap(ErweiterbareListe eList, int a, int b) {
        if (a >= 0 && b >= 0)
            Collections.swap(Arrays.asList(erweiterbareListeListMap.get(eList)), a, b);
    }

    public String[] getProperty(ErweiterbareListe eList) {
        return erweiterbareListeListMap.get(eList);
    }

    // sucht den Index für ein Element
    public int indexOf(ErweiterbareListe eList, String value) {
        String[] arr = getProperty(eList);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(value))
                return i;
        }
        return -1;
    }

    public HashMap<ErweiterbareListe, String[]> getAllProperties() {
        return erweiterbareListeListMap;
    }

    public void putProperties(ErweiterbareListe key, String[] value) {
        erweiterbareListeListMap.put(key, value);
    }

    public void addPropertyValue(ErweiterbareListe key, String value) {
        if (!Arrays.asList(erweiterbareListeListMap.get(key)).contains(value)) {
            String[] newArr = new String[erweiterbareListeListMap.get(key).length + 1];
            newArr[0] = value;
            for (int i = 0; i < erweiterbareListeListMap.get(key).length; i++) {
                newArr[i + 1] = getProperty(key)[i];
            }
            erweiterbareListeListMap.put(key, newArr);
        }
    }

}
