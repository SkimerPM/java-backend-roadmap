package com.skimer.restaurant.service;

import com.skimer.restaurant.domain.entities.Table;
import com.skimer.restaurant.repository.InMemoryTableRepository;
import com.skimer.restaurant.repository.TableRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TableService {
    private final TableRepository tableRepo = new InMemoryTableRepository();
    //crear una mesa
    public Table createTable(int supportNumber, String place) {
        Table newTable = new Table(supportNumber, place);
        tableRepo.save(newTable.getId(), newTable);
        return newTable;
    }
    //obtener por id
    public Optional<Table> getById(String id){
        Objects.requireNonNull(id, "Table id cannot be null.");
        return tableRepo.findById(id);
    }
    //obtener todas
    public List<Table> getAll(){
        return tableRepo.findAll();
    }
    //eliminar por id
    public void deleteById(String id){
        Objects.requireNonNull(id, "Table ID cannot be null.");
        tableRepo.deleteById(id);
    }
}
