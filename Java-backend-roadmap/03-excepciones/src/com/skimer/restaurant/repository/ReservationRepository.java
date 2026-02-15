package com.skimer.restaurant.repository;

import com.skimer.restaurant.domain.entities.Reservation;

import java.util.List;

public interface ReservationRepository extends Repository<Reservation, String>{

    List<Reservation> findByTableId(String tableId);
}
