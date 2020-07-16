package util;

import java.util.HashMap;

public class EntityManager <T> {

    private HashMap<Integer, T> entityMap;

    public EntityManager() {
        entityMap = new HashMap<>();
    }

    public void persist(T entity) {
        if(!contains(entity)) {
            entityMap.put(entity.hashCode(), entity);
        }
    }

    public T find() {
        return null; //TODO: logic
    }

    public void remove(T entity) {
        entityMap.remove(entity.hashCode());
    }

    public boolean contains(T entity) {
        return entityMap.containsValue(entity);
    }

    public void update() {

    }

}
