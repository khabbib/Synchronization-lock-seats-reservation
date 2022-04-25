package view;

import model.Seat;
import model.SeatManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View {
    private SeatManager seatManager;
    public View(SeatManager seatManager){
        this.seatManager = seatManager;
    }


    // Available seats
    public void printAvailableSeats(List<Seat> seat){
        System.out.println();
        System.out.println("Available seats:");
        System.out.println("---------------------------------");
        for (Seat item : seat){
            System.out.println("[" +item.getSeatId() + "]");
        }
        System.out.println("---------------------------------");

    }

    // Client info
    public void printClientInfo(List<String> result){
        /*
            for (Map.Entry<Integer, String> client : clientList.entrySet()){
                System.out.println(client.getValue() + " couldn't take seat: " + client.getKey());
            }
         */

        for (String rs : result){
            System.out.println(rs);
        }

    }
    // Which client have taken seat and could not take by.
    public void printTakenSeat(int seat, String tname){
        System.out.println("");
        System.out.println("=================================");
        System.out.println("Seat info");
        System.out.println("---------------------------------");
        System.out.println( tname + " booked seat: " + seat);
        System.out.println("---------------------------------");

        // taken seat
        // couldn't take seat
    }


    // no seat available
    public void notSeatAvailable(String seat){
        System.out.println("No seat available for chosen seat: " + seat);
    }


    public void prinSkapadeClienter(int i) {
        System.out.println("client" + i);
    }
}
