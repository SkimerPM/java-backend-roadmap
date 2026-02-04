package org.example.generics;

import java.util.*;

public class SkmRepository <T, ID> {
    private final Map<ID, T> itemsById = new HashMap<>();

    public void save(ID id, T entity){
        if (id == null || entity == null){
            throw new IllegalArgumentException("Id or Entity cannot be null.");
        }
        if (itemsById.containsKey(id)){
            throw new IllegalArgumentException("Object with id " + id + " already exists");
        }
        itemsById.put(id,entity);
    }
    public Optional<T> findById(ID id){
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return Optional.ofNullable(itemsById.get(id));
    }
    public List<T> findAll(){
        return new ArrayList<>(itemsById.values());
    }
    public void deleteById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        itemsById.remove(id);
    }
}
