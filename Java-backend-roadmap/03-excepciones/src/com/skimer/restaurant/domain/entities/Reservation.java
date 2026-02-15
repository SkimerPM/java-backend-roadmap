package com.skimer.restaurant.domain.entities;

import com.skimer.restaurant.domain.valueobjects.Email;
import com.skimer.restaurant.domain.valueobjects.TimeSlot;
import com.skimer.restaurant.utils.Validations;

import java.util.Objects;
import java.util.UUID;

public class Reservation {
    private final String id;
    private final String tableId;
    private final String customerName;
    private final Email customerEmail;
    private final int partySize;
    private final TimeSlot timeSlot;

    public Reservation(String tableId,
                       String customerName,
                       Email customerEmail,
                       int partySize,
                       TimeSlot timeSlot) {

        // validate and clean string parameters
        this.tableId = Validations.requireNonBlank(tableId, "tableId");
        this.customerName = Validations.requireNonBlank(customerName, "customerName");

        // Email record valida el valor interno; aquí evitamos referencia nula
        this.customerEmail = Objects.requireNonNull(customerEmail, "customerEmail cannot be null.");

        if (partySize <= 0) {
            throw new IllegalArgumentException("partySize must be greater than zero.");
        }
        this.partySize = partySize;

        this.timeSlot = Objects.requireNonNull(timeSlot, "timeSlot cannot be null.");

        // generar id al final, cuando ya pasó todas las validaciones
        this.id = UUID.randomUUID().toString() + "_" + this.tableId + "_" + this.customerEmail.value();
    }

    public String getId() {
        return id;
    }

    public String getTableId() {
        return tableId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Email getCustomerEmail() {
        return customerEmail;
    }

    public int getPartySize() {
        return partySize;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
