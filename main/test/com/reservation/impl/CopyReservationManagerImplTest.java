package com.reservation.impl;

import com.reservation.ReservationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CopyReservationManagerImplTest {

    private List<Reservation> booked; //desired list of reservations being acheived
    private Stack<Table> stack;
    private Reservation[] reservations;
    private ReservationManager manager;
    private Queue<Reservation> queue;
    private Queue<Reservation> unsafeQueue;
    private List<String> dislikes;


    @BeforeEach
    void setUp() {
        booked = new ArrayList<>();
        reservations = new Reservation[6]; //current set to six reservations.
        queue = new LinkedList<>();
        unsafeQueue = new LinkedList<>();

        Reservation thornton = new Reservation("Thornton", 3);
        Reservation garcia = new Reservation("Garcia", 2);
        Reservation taylor = new Reservation("Taylor", 5);
        Reservation smith = new Reservation("Smith", 1);
        Reservation owens = new Reservation("Owens", 6);
        Reservation reese = new Reservation("Reese", 7);

        reservations[0] = thornton; reservations[1] = garcia; reservations[2] = taylor;
        reservations[3] = smith; reservations[4] = owens; reservations[5] = reese;

        dislikes = new ArrayList<>();
        dislikes.add("Garcia"); dislikes.add("Thornton");

        Table a = new Table("a",8);Table b = new Table("b",8);
        Table c = new Table("c",7);Table d = new Table("d",7);

        List<Table> tables = new ArrayList<>();
        tables.add(a);tables.add(b);tables.add(c);tables.add(d);

        stack = new Stack<>();
        stack.add(a);stack.add(b);stack.add(c);stack.add(d);
        manager = new ReservationManagerImpl(stack);

        queue = new LinkedList<>();
        queue.add(reese);queue.add(owens);queue.add(smith);queue.add(taylor);

        unsafeQueue = new LinkedList<>();
        unsafeQueue.add(garcia); unsafeQueue.add(thornton);

    }

    @AfterEach
    void tearDown() {
        manager = null;
        booked = null;
        stack = null;
        reservations = null;
        dislikes = null;
        unsafeQueue = null;
    }

    @Test
    void queueReservations() {

        manager.queueReservations(queue,stack.pop(),booked,null);
        manager.updateTableCount(booked,dislikes);
        manager.queueReservations(unsafeQueue,stack.pop(),booked,null);
        assertEquals(booked.size(), 6);
        //assert (booked.contains())
        printReservations();
    }

    /**
     * unit test helper
     * @param reservations
     * @param filter
     * @return
     */
    public List<Reservation> helper(Reservation[] reservations, List<String> filter) {
        LinkedList<List<Reservation>> result = new LinkedList<List<Reservation>>();
        result.add(new ArrayList<Reservation>());
        int counter = 1;
        for (Reservation n : reservations) {
            int size = result.size();

            while (size > 0) {
                //System.out.println("looping times " + counter);
                List<Reservation> head = result.pollFirst();
                for (int i = 0; i <= head.size(); i++) {
                    List<Reservation> temp = new ArrayList<Reservation>(head);
                    if(!filter.contains(n.name))
                        temp.add(i, n);
                    result.add(temp);
                    size--;
                }

            }
            ++counter;
        }
        return result.pollLast();
    }

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