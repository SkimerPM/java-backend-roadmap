package com.skimer.restaurant;

import com.skimer.restaurant.domain.entities.Reservation;
import com.skimer.restaurant.domain.valueobjects.Email;
import com.skimer.restaurant.domain.valueobjects.TimeSlot;
import com.skimer.restaurant.exceptions.TableAlreadyBookedException;
import com.skimer.restaurant.exceptions.TableNotFoundException;
import com.skimer.restaurant.service.ReservationService;
import com.skimer.restaurant.service.TableService;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        TableService tableService = new TableService();
        ReservationService reservationService = new ReservationService(tableService);

        System.out.println("---- Luxury Restaurant Reservations Demo ----");
        System.out.println();

        // 1) Crear mesas
        var tableVip   = tableService.createTable(4, "VIP Room");
        var tableHall  = tableService.createTable(6, "Main Hall");
        var tableTerra = tableService.createTable(2, "Terrace");

        System.out.println("Mesas creadas:");
        tableService.getAll().forEach(t ->
                System.out.println("- " + t.getId() + " | place=" + t.getPlace() + " | capacity=" + t.getSupportNumber())
        );
        System.out.println();

        // Fecha base para las reservas
        var date = LocalDateTime.of(2026, 2, 10, 0, 0);

        // 2) Reservas válidas
        try {
            // Gloria, mesa VIP, 2 personas, 20:00–21:30
            TimeSlot gloriaSlot = new TimeSlot(
                    date.withHour(20).withMinute(0),
                    date.withHour(21).withMinute(30)
            );
            Reservation gloriaRes = reservationService.createReservation(
                    tableVip.getId(),
                    "Gloria",
                    new Email("gloria@example.com"),
                    2,
                    gloriaSlot
            );
            System.out.println("Reserva creada para Gloria: " + gloriaRes.getId());

            // Fabricio, Main Hall, 4 personas, 19:00–20:30
            TimeSlot fabricioSlot = new TimeSlot(
                    date.withHour(19).withMinute(0),
                    date.withHour(20).withMinute(30)
            );
            Reservation fabricioRes = reservationService.createReservation(
                    tableHall.getId(),
                    "Fabricio",
                    new Email("fabricio@example.com"),
                    4,
                    fabricioSlot
            );
            System.out.println("Reserva creada para Fabricio: " + fabricioRes.getId());

            // Félix, Terrace, 2 personas, 18:00–19:00
            TimeSlot felixSlot = new TimeSlot(
                    date.withHour(18).withMinute(0),
                    date.withHour(19).withMinute(0)
            );
            Reservation felixRes = reservationService.createReservation(
                    tableTerra.getId(),
                    "Felix",
                    new Email("felix@example.com"),
                    2,
                    felixSlot
            );
            System.out.println("Reserva creada para Felix: " + felixRes.getId());

        } catch (Exception e) {
            System.out.println("Error inesperado creando reservas válidas: " + e.getMessage());
        }

        System.out.println();

        // 3) Reserva con grupo que excede la capacidad (José en mesa Terrace con 4 personas)
        try {
            TimeSlot joseSlot = new TimeSlot(
                    date.withHour(19).withMinute(0),
                    date.withHour(20).withMinute(0)
            );
            reservationService.createReservation(
                    tableTerra.getId(),
                    "Jose",
                    new Email("jose@example.com"),
                    4, // la mesa terrace soporta 2, esto debe fallar
                    joseSlot
            );
            System.out.println("ERROR: esta línea no debería imprimirse (capacidad excedida).");
        } catch (IllegalArgumentException e) {
            System.out.println("Esperado (capacidad excedida para Jose): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Otro error creando reserva de Jose: " + e.getMessage());
        }

        System.out.println();

        // 4) Reserva que se solapa en la misma mesa VIP (Antuanet en mismo rango que Gloria)
        try {
            // Gloria tenía 20:00–21:30; probamos 20:30–21:00 (mismo día, mismo tableId)
            TimeSlot antuanetSlot = new TimeSlot(
                    date.withHour(20).withMinute(30),
                    date.withHour(21).withMinute(0)
            );
            reservationService.createReservation(
                    tableVip.getId(),
                    "Antuanet",
                    new Email("antuanet@example.com"),
                    2,
                    antuanetSlot
            );
            System.out.println("ERROR: esta línea no debería imprimirse (mesa ya reservada).");
        } catch (TableAlreadyBookedException e) {
            System.out.println("Esperado (mesa VIP ya reservada para Antuanet): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Otro error creando reserva de Antuanet: " + e.getMessage());
        }

        System.out.println();

        // 5) Intentar reservar una mesa inexistente
        try {
            TimeSlot fakeSlot = new TimeSlot(
                    date.withHour(22).withMinute(0),
                    date.withHour(23).withMinute(0)
            );
            reservationService.createReservation(
                    "NON_EXISTENT_TABLE",
                    "Gloria",
                    new Email("gloria@example.com"),
                    2,
                    fakeSlot
            );
            System.out.println("ERROR: esta línea no debería imprimirse (mesa no encontrada).");
        } catch (TableNotFoundException e) {
            System.out.println("Esperado (mesa no encontrada): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Otro error creando reserva con mesa inexistente: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Reservas actuales en el sistema:");
        reservationService.getAll().forEach(r ->
                System.out.println("- " + r.getCustomerName()
                        + " | table=" + r.getTableId()
                        + " | partySize=" + r.getPartySize()
                        + " | timeSlot=" + r.getTimeSlot())
        );
    }
}
