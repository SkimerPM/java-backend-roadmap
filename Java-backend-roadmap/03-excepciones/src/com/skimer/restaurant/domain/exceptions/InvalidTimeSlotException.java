package com.skimer.restaurant.domain.exceptions;

public class InvalidTimeSlotException extends RuntimeException{
    public InvalidTimeSlotException(String message){
        super(message);
    }
}
