package util;

import java.util.HashMap;

public class EntityManager <T> {

    private HashMap<Object, T> entityMap;

    public EntityManager() {
        entityMap = new HashMap<Object, T>();
    }

    public void persist(Object key, T entity) {
        if(!contains(entity)) {
            entityMap.put(key, entity);
        }
    }

    public T find(Object key)  {
        if(contains(entityMap.get(key))) {
            return entityMap.get(key);
        }
        return null;
    }

    public void remove(Object key) {
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
