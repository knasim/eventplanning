package com.reservation.impl;

import com.reservation.ReservationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerImplTest {

    private List<Reservation> booked; //the goal (targeted list of reservations being sought i.e)
    private Stack<Table> tables;
    private Reservation[] reservations;
    private ReservationManager manager;
    private Queue<Reservation> reservation;
    private Queue<Reservation> reservationDislikes;
    private List<String> dislikes;


    @BeforeEach
    void setUp() {

        /**
         * Thornton, party of 3 
         * Garcia, party of 2 
         * Owens, party of 6 dislikes Thornton,
         * Smith, party of 1 dislikes Garcia 
         * Taylor, party of 5
         * Reese, party of 7
         */

        booked = new ArrayList<>();
        reservation = new LinkedList<>();
        reservationDislikes = new LinkedList<>();

        Reservation thornton = new Reservation("Thornton", 3);
        Reservation garcia = new Reservation("Garcia", 2);
        Reservation taylor = new Reservation("Taylor", 5);
        Reservation smith = new Reservation("Smith", 1);
        Reservation owens = new Reservation("Owens", 6);
        Reservation reese = new Reservation("Reese", 7);

        reservations = new Reservation[6];
        reservations[0] = thornton; reservations[1] = garcia; reservations[2] = taylor;
        reservations[3] = smith; reservations[4] = owens; reservations[5] = reese;

        dislikes = new ArrayList<>();
        dislikes.add("Garcia"); dislikes.add("Thornton");


        Table a = new Table("a",8);Table b = new Table("b",8);
        Table c = new Table("c",7);Table d = new Table("d",7);

        List<Table> tables = new ArrayList<>();
        tables.add(a);tables.add(b);tables.add(c);tables.add(d);

        this.tables = new Stack<>();
        this.tables.add(a);
        this.tables.add(b);
        this.tables.add(c);
        this.tables.add(d);
        manager = new ReservationManagerImpl(this.tables);

        reservation = new LinkedList<>();
        reservation.add(reese); //This assignment can be randomized
        reservation.add(owens);
        reservation.add(smith);
        reservation.add(taylor);

        reservationDislikes = new LinkedList<>();
        reservationDislikes.add(garcia); reservationDislikes.add(thornton);

    }

    @AfterEach
    void tearDown() {
        manager = null;
        booked = null;
        tables = null;
        reservations = null;
        dislikes = null;
        reservationDislikes = null;
    }

    @Test
    void queueReservations() {
        manager.queueReservations(reservation, tables.pop(),booked,null);
        manager.updateTableCount(booked,dislikes);
        manager.queueReservations(reservationDislikes, tables.pop(),booked,null);

        assertEquals(booked.size(), reservations.length);
        assertEquals(booked.get(5).name,reservations[0].name);
        assertEquals(booked.get(0).name,reservations[5].name);
        assertEquals(booked.get(1).name,reservations[4].name);
        assertEquals(booked.get(2).name,reservations[3].name);
        assertEquals(booked.get(3).name,reservations[2].name);
        assertEquals(booked.get(4).name,reservations[1].name);
        //printReservations();
    }

    /**
     * helper to print completed reservations
     */
    public void printReservations() {
        Map<String,String> map = new HashMap();
        for(Reservation booking : booked) {
            String tableName = booking.table.tableName;
            int tableSize = booking.size;
            if(!map.containsKey(tableName)) {  //new
                String value = "Table " + tableName + " " + booking.name + ", party of " + tableSize;
                map.put(tableName,value);
            } else {    //update
                String update = map.get(tableName);
                String current = update + " " + booking.name +  ", party of " + tableSize;
                map.put(tableName,current);
            }
        }
        for(String s : map.values()) {
            System.out.println(s);
        }
    }
}