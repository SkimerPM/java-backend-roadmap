package com.skimer.restaurant.exceptions;

public class TableAlreadyBookedException extends RuntimeException {
    public TableAlreadyBookedException(String tableId) {
        super("Table already has a reservation for the given time slot: " + tableId);
    }
}
