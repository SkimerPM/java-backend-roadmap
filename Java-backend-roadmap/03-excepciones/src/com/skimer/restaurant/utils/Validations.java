package com.skimer.restaurant.utils;

import java.util.Objects;

public final class Validations {
    // Para no permitir instancia de la misma.
    private Validations() {
        throw new AssertionError("No instances for you");
    }

    public static String requireNonBlank(String value, String fieldName){
        Objects.requireNonNull(value, fieldName + " cannot be null.");
        String cleaned = value.strip();
        if (cleaned.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return cleaned;
    }

    public static void checkCapacity(int requestNumber, int maxNumber , String message ){
        if(requestNumber > maxNumber){
            throw new IllegalArgumentException(message);
        }
    }

}
