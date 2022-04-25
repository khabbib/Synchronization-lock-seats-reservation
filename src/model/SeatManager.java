package model;

import control.Control;
import view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SeatManager {
    private Control control;
    private View view;
    private List<Seat> seats = new ArrayList<>();
    private List<String> logBook = new ArrayList<>();
    private final Object lock = new Object();
    private  Random random;
    private final int maxSeats = 10;
    private final int availableSeats = 5;
    private final int maxClients = 15;
    private List<Seat> takenSeat = new ArrayList<>();
    private List<Client> clientList = new ArrayList<>();
    private HashMap<Integer,String> notReachedClientList = new HashMap<Integer, String>();
    private Client client;

    // Constructor
    public SeatManager(Control control){
        this.control = control;
        this.view = new View(this); // run view
        boolean readyToPick = pickRandomSeats(); //generate randomly seats (1-10);
        if (readyToPick){// if generated seats tho clients creates.
            for (int i = 0; i < maxClients; i++){
                int rd = random.nextInt(10);
                client = new Client(this, 1, "client" + i);
                logBook.add("Client" +i + " looking for seat: " + rd);
                System.out.println("client skapade " + i);
                clientList.add(client);
                //view.prinSkapadeClienter(i);
            }
        }
    }

    // Select 5 random number without duplicate between 0 and 10
    public boolean pickRandomSeats(){
        /* // manuel set seats available
        takenSeat.add(new Seat(1,Status.Available));
        takenSeat.add(new Seat(2,Status.Available));
        takenSeat.add(new Seat(3,Status.Available));
        takenSeat.add(new Seat(4,Status.Available));
        takenSeat.add(new Seat(5,Status.Available));
         */

        takenSeat = getRandomNonRepeatingIntegers(availableSeats, 0, maxSeats);
        view.printAvailableSeats(takenSeat);

        return true;
    }

    // Get selected size number without duplicate
    public List<Seat> getRandomNonRepeatingIntegers(int size, int min, int max) {
        List<Seat> numbers = new ArrayList<>();
        random = new Random();
        while (numbers.size() < size) {
            //Get Random numbers between range
            int randomNumber = random.nextInt((max - min) + 1) + min;
            //Check for duplicate values
            if (!numbers.contains(randomNumber)) {
                numbers.add(new Seat(randomNumber, Status.Available));
            }
        }
        return numbers;
    }

    /////// get status ////////
    public void printStatus(){
        String result = "";
        for (Seat seat: takenSeat) {
            result = seat.toString();
            logBook.add(result);
        }
    }
    public int getSeatId(int id, String name){
        int output = 0;
        // synchronization implemented here
        synchronized (lock){ // the lock takes by one client at a time
            //System.out.println( name + " takes the lock");
            for (int i =0; i < availableSeats; i++){
                if (takenSeat.get(i).getSeatId() == id){
                    if (takenSeat.get(i).getSeatStatus().equals(Status.Available)){
                        takenSeat.get(i).setSeatStatus(Status.Occupied);
                        System.out.println(name + "take seat nr: " + id);
                        output= id;
                        logBook.add(name +", take seat: " + id);
                        //view.printTakenSeat(id, name);
                    }else {
                        if (!logBook.contains(name)){
                            //notReachedClientList.put(id, name);
                            //logBook.add(name + ", coudn't take seat: " + id);
                        }
                        output = -1;
                    }
                }
            }
            /*
            if (notReachedClientList.size() > 0){
                view.printClientInfo(notReachedClientList);
            }

             */
            if (clientList.size() <= 1){
                view.printClientInfo(logBook);
            }
            //System.out.println( name + " leaved the lock");

            return output;
        }
    }

    public void removeClient(Client client) {
        clientList.remove(client);
    }
}
