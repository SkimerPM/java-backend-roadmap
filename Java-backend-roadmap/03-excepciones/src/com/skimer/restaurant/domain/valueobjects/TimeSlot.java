package com.skimer.restaurant.domain.valueobjects;

import com.skimer.restaurant.domain.exceptions.InvalidTimeSlotException;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimeSlot(LocalDateTime start, LocalDateTime end) {
    private static final Duration MIN_DURATION = Duration.ofMinutes(30);
    public TimeSlot{
        if (start == null) {
            throw new InvalidTimeSlotException("Start time cannot be null");
        }
        if (end == null) {
            throw new InvalidTimeSlotException("End time cannot be null");
        }
        if(!end.isAfter(start)){
            throw new InvalidTimeSlotException("End time must be after start time");
        }
        Duration duration = Duration.between(start, end);
        if(duration.compareTo(MIN_DURATION) < 0){
            throw new InvalidTimeSlotException("Time slot duration must be at least " + MIN_DURATION.toMinutes() + " minutes");
        }
    }
    public boolean overlaps(TimeSlot other){
        return this.start.isBefore(other.end()) && other.start.isBefore(this.end);
    }
}
