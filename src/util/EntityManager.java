package util;

import java.util.HashMap;

public class EntityManager <T> {

    private HashMap<String, T> entityMap;

    public EntityManager() {
        entityMap = new HashMap<String, T>();
    }

    public void persist(String key, T entity) {
        if(!contains(entity)) {
            entityMap.put(key, entity);
        }
    }

    public T find(String key) {
        if(contains(entityMap.get(key))) {
            return entityMap.get(key);
        }
        return null;
    }

    public void remove(String key) {
        if(contains(entityMap.get(key))) {
            entityMap.remove(key);
        }
    }

    public boolean contains(T entity) {
        return entityMap.containsValue(entity);
    }

    public void update() {

    }

}
