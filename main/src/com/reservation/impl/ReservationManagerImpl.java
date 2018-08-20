package com.reservation.impl;


import com.reservation.ReservationManager;

import java.util.*;

class Reservation {
    String name;
    int size;
    Table table;

    public Reservation(String name, int size) {
        this.name=name;
        this.size=size;
    }

    public Reservation(String name, int size, Table table) {
        this.name=name;
        this.size=size;
        this.table=table;
    }
}

class Table {
    String tableName;
    int size;

    public Table(String tableName, int size) {
        this.tableName=tableName;
        this.size=size;
    }
}

public class ReservationManagerImpl implements ReservationManager {

    private Stack<Table> tables;  //stack for keeping track of all the tables

    public  ReservationManagerImpl(Stack<Table> stack) {
        this.tables =stack;
    }

    @Override
    /**
     * Recursive Algorithm that attempts to accomodate all of the reservations.
     * Algorithm applies a divide/conquer strategy with a greedy approach.  The reservations
     * are separated into 2 Queues i.e. one having no dislike constraints
     * and the other queue having dislike constraint.  Algorithm uses 2 sweeps to process each
     * queue respectively.
     *
     * Method Args:
     *  1. @param reservationQueue: This queue holds all the reservations that need to be seated
     *  2. @param table:  The table object per current line of execution
     *  3. @param booked: List of all reservations that have been processed thus far
     *  4. @param currentReservation:  Current reservation being processed
     */
    public void queueReservations(Queue reservationQueue, Object table, List booked, Object currentReservation) {

        while(reservationQueue.size() > 0) {  //greedily seat all parties
            Table currentTable = (Table) table;
            int tableSize = currentTable.size;
            Reservation reservation = null;
            reservation = (Reservation) reservationQueue.poll();

            if(currentReservation != null && !reservation.name.equals((Reservation) currentReservation)) {
                reservationQueue.add(reservation);
                reservation = (Reservation) currentReservation;
                reservationQueue.remove(currentReservation);
            }

            int partySize = reservation.size;
            int diff = tableSize-partySize;  //difference

            if(diff == 0) { //table can entirely fit the current reservation
                currentTable.size=diff;
                Reservation bookedReservation = new Reservation(reservation.name,partySize,currentTable);
                booked.add(bookedReservation);
                queueReservations(reservationQueue, tables.pop(),booked,null);
            }

            else if ((partySize < tableSize) && (diff > 0)) {
                Reservation bookedReservation = new Reservation(reservation.name,partySize,currentTable);
                booked.add(bookedReservation);
                currentTable.size = diff;
                if(tables.size() ==0)
                    break; //done
                queueReservations(reservationQueue,currentTable,booked,null);
            }

            else if (partySize > tableSize) {
                int reservationSize = booked.size();
                Reservation lastItem = (Reservation) booked.get(reservationSize-1);
                if(lastItem.name.equals(reservation.name)) {
                    break;  //done
                }

                if(tables.size() ==0)
                    break;
                currentTable = tables.pop();
                reservation.table=currentTable;

                reservationQueue.add(reservation);
                queueReservations(reservationQueue,currentTable,booked,reservation);
            }
        }
    }

    /**
     * helper to update table counts.  this supports greedy approach of accomodating reservations.
     * @param booked
     * @param dislikes
    */
    public void updateTableCount(List booked, List dislikes) {
        Stack<Table> temp = tables;
        if(temp.size() == 0)  //ran out of tables !
            throw new IllegalStateException("It is not possible to seat all parties at the same table.");
        temp.add(tables.pop());
        for(Object booking : booked) {
            Reservation reservation = (Reservation) booking;
            if(!dislikes.contains(reservation.name) && reservation.table.size > 0) {
                Table table = reservation.table;
                temp.push(table);
            }
        }
        tables = temp;
    }
}
