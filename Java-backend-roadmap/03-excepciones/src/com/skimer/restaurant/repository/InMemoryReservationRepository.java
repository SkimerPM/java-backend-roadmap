package com.skimer.restaurant.repository;

import com.skimer.restaurant.domain.entities.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryReservationRepository extends InMemoryRepository<Reservation, String> implements ReservationRepository{

    @Override
    public List<Reservation> findByTableId(String tableId) {
        return findAll().stream()
                .filter(r -> r.getTableId().equals(tableId))
                .toList();
    }
}
