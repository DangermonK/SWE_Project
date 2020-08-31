package util;

import datentypen.ErweiterbareListe;

import java.util.*;
import java.util.List;

public class Property {

    private static Property instance;
    private HashMap<ErweiterbareListe, List<String>> erweiterbareListeListMap;

    private Property() {
        erweiterbareListeListMap = new HashMap<>();
    }

    public static Property getInstance() {
        if(instance == null) {
            instance = new Property();
        }

        return instance;
    }

    public void swap(ErweiterbareListe eList, int a, int b) {
        if(a >= 0 && b >= 0)
            Collections.swap(erweiterbareListeListMap.get(eList), a, b);
    }

    public List<String> getProperty(ErweiterbareListe eList) {
        return erweiterbareListeListMap.get(eList);
    }

    public HashMap<ErweiterbareListe, List<String>> getAllProperties() {
        return erweiterbareListeListMap;
    }

    public void putProperties(ErweiterbareListe key, List<String> value) {
        erweiterbareListeListMap.put(key, value);
    }

    public void addPropertyValue(ErweiterbareListe key, String value) {
        if(!erweiterbareListeListMap.get(key).contains(value)) {
            ArrayList<String> list = new ArrayList<>(erweiterbareListeListMap.get(key));
            list.add(value);
            erweiterbareListeListMap.put(key, list);
        }
    }

}
