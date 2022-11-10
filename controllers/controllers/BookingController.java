package controllers;

import entities.Booking;
import entities.MovieGoer;
import entities.Seat;
import entities.Showing;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookingController implements Serializable{

    private static final String filepath = "bookinghistory.ser"; //testing purposes

    private static ArrayList<Booking> bookingHistory;

    // holds an instance of the controller 
    private static BookingController controllerInstance = null;
    
    // methods
    /*
     * Instantiate a controller object when called.
     * Also deserializes bookingHistory from file; if none found/null object, create a new one.
     */
    @SuppressWarnings("unchecked")
    public static BookingController getController() {
        if (controllerInstance == null) {
            controllerInstance = new BookingController();
        }
        bookingHistory = (ArrayList<Booking>)loadData();
        if (bookingHistory == null){
            System.out.println("No bookingHistory found; creating new file.");
            bookingHistory = new ArrayList<Booking>();
            saveData(bookingHistory);
        }
        return controllerInstance;
    }



    /*
     * Create new Booking object and adds it to the booking history list
     */
    public void createBooking(Showing showing, MovieGoer movieGoer, Seat seat, int cinemaID, int cineplexID) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatStr = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String tid = now.format(formatStr);
        tid = showing.getCinema().getCinemaID() + tid;
        
        Booking booking = new Booking(tid, movieGoer, seat, showing, cineplexID, cineplexID);
        bookingHistory.add(booking);
    }

    public void listBookingViaNumber(String movieGoerNumber){
        if (bookingHistory.size()==0) {System.out.println("No bookings found."); return;}
        for (int i = 0; i < bookingHistory.size(); i++)
        {
            if (bookingHistory.get(i).getMovieGoer().getMovieGoerNumber() == movieGoerNumber)
            {   
                System.out.println(bookingHistory.get(i).toString());
            }
        }
    }

    public void listBookingViaEmail(String movieGoerEmail){
        if (bookingHistory.size()==0) {System.out.println("No bookings found."); return;}
        for (int i = 0; i < bookingHistory.size(); i++)
        {
            if (bookingHistory.get(i).getMovieGoer().getEmailAddress() == movieGoerEmail)
            {   
                System.out.println(bookingHistory.get(i).toString());
            }
        }
    }

    public static void saveData(ArrayList<Booking> bookingObj){  
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        
            objectOut.writeObject(bookingObj);
            objectOut.close();
        } catch (IOException e) {
            System.out.println("Got an error while saving booking data: "+e);
            //e.printStackTrace();
        }
    }

    public static Object loadData(){
        try{
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Got an error while loading booking data: " + e);
            //e.printStackTrace();
            return null;
        }        
    }
}