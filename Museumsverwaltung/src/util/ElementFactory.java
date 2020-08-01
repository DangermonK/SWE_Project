package util;

import model.Exponat;

import java.util.HashMap;

public class ElementFactory {

    private static ElementFactory instance;

    private ElementFactory() {

    }

    public static ElementFactory getInstance() {
        if(instance == null) {
            instance = new ElementFactory();
        }
        return instance;
    }

    public Exponat createExponat(HashMap<String, Object> data) {

        Exponat exp = new Exponat((String)data.get("name"));

        if(data.containsKey("beschreibung"))
            exp.setBeschreibung((String)data.get("beschreibung"));

        if(data.containsKey("erstellungsjahr"))
            exp.setErstellungsJahr(Integer.valueOf((String)data.get("erstellungsjahr")));

        return exp;

    }

}
