package com.skimer.restaurant.exceptions;

public class TableNotFoundException extends RuntimeException{
    public TableNotFoundException(String tableId){
        super("Mesa no encontrada: " + tableId);
    }
}
