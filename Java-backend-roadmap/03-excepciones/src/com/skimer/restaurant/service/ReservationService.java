package com.skimer.restaurant.service;

import com.skimer.restaurant.domain.entities.Reservation;
import com.skimer.restaurant.domain.entities.Table;
import com.skimer.restaurant.domain.valueobjects.Email;
import com.skimer.restaurant.domain.valueobjects.TimeSlot;
import com.skimer.restaurant.exceptions.TableAlreadyBookedException;
import com.skimer.restaurant.exceptions.TableNotFoundException;
import com.skimer.restaurant.repository.InMemoryReservationRepository;
import com.skimer.restaurant.repository.ReservationRepository;
import com.skimer.restaurant.utils.Validations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ReservationService {
    private final ReservationRepository reservationRepo = new InMemoryReservationRepository();
    private final TableService tableService;

    public ReservationService(TableService tableService) {
        this.tableService = tableService;
    }
    // Creamos una reserva
    public Reservation createReservation(
            String tableId,
            String customerName,
            Email customerEmail,
            int partySize,
            TimeSlot timeSlot
    ){
        //buscar tabla
        Table table = tableService.getById(tableId)
                .orElseThrow(() -> new TableNotFoundException(tableId));

        //validar partySize
        Validations.checkCapacity(partySize, table.getSupportNumber(), "Group size exceeds number of people admitted to the selected table");
        //validar slot
        validateTimeSlotAvailable(tableId, timeSlot);

        Reservation reservation = new Reservation(
                tableId,
                customerName,
                customerEmail,
                partySize,
                timeSlot
        );

        reservationRepo.save(reservation.getId(), reservation);
        return reservation;
    }
    //buscamos por id
    public Optional<Reservation> getById(String id){
        Objects.requireNonNull(id, "Reservation ID cannot be null.");
        return reservationRepo.findById(id);
    }
    //listar
    public List<Reservation> getAll(){
        return reservationRepo.findAll();
    }
    //eliminar
    public void deleteById(String id){
        Objects.requireNonNull(id, "Reservation ID cannot be null.");
        reservationRepo.deleteById(id);
    }

    private void validateTimeSlotAvailable(String tableId, TimeSlot newTimeSlot) {
        List<Reservation> sameTableReservations = reservationRepo.findByTableId(tableId);

        boolean overlaps = sameTableReservations.stream()
                .filter(r -> r.getTimeSlot().start().toLocalDate()
                        .equals(newTimeSlot.start().toLocalDate()))
                .anyMatch(r -> r.getTimeSlot().overlaps(newTimeSlot));

        if (overlaps) {
            throw new TableAlreadyBookedException(tableId);
        }
    }

}
