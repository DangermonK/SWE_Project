package util;

import datentypen.ErweiterbareListe;

import java.util.HashMap;
import java.util.List;

public class Property {

    private static Property instance;
    private HashMap<ErweiterbareListe, List<String>> erweiterbareListeListMap;

    private Property() {
        erweiterbareListeListMap = new HashMap<ErweiterbareListe, List<String>>();
    }

    public static Property getInstance() {
        if(instance == null) {
            instance = new Property();
        }

        return instance;
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
        erweiterbareListeListMap.get(key).add(value);
    }

}
