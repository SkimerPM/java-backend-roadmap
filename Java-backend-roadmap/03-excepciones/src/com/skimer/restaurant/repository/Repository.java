package com.skimer.restaurant.repository;

import java.util.List;
import java.util.Optional;

public interface Repository <T, ID>{
    //Guardar
    void save(ID id, T entity);
    //Buscar
    Optional<T> findById(ID id);
    //Traer todos
    List<T> findAll();
    //eliminiar
    void deleteById(ID id);
}
