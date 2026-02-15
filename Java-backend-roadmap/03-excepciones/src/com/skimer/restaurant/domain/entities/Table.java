package com.skimer.restaurant.domain.entities;



import com.skimer.restaurant.utils.Validations;

import java.util.UUID;

public class Table {
    private final String id;
    private final int supportNumber;
    private final String place;

    public Table(int supportNumber, String place){
        if(supportNumber <= 0){
            throw new IllegalArgumentException("Support number must be greater than zero.");
        }
        this.supportNumber = supportNumber;
        //limpieza y validación
        String cleanedPlace = Validations.requireNonBlank(place, "place");
        this.place = cleanedPlace;
        this.id = UUID.randomUUID().toString() + "_" + cleanedPlace + "_" + supportNumber;
    }

    public String getId() {
        return id;
    }

    public int getSupportNumber() {
        return supportNumber;
    }

    public String getPlace() {
        return place;
    }
}
