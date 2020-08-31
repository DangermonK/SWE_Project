package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityManager<T> {

    private HashMap<Object, T> entityMap;

    // Basis EntityManager kann mit verschiedenen Typen initialisiert werden
    public EntityManager() {
        entityMap = new HashMap<Object, T>();
    }

    public void persist(Object key, T entity) {
        if (!contains(entity)) {
            entityMap.put(key, entity);
        }
    }

    public List<T> getAsList() {
        return new ArrayList<>(entityMap.values());
    }

    public T find(Object key) {
        if (contains(entityMap.get(key))) {
            return entityMap.get(key);
        }
        return null;
    }

    public void remove(Object key) {
        if (contains(entityMap.get(key))) {
            entityMap.remove(key);
        }
    }

    public boolean contains(T entity) {
        return entityMap.containsValue(entity);
    }

}
