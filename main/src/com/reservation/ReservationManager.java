package com.reservation;


import java.util.List;
import java.util.Queue;

public interface ReservationManager<R> {

     void queueReservations(Queue<?> reservationQueue, R table, List<?> booked, R currentReservation);
     void updateTableCount(List<?> booked, List<?> dislikes);
}
