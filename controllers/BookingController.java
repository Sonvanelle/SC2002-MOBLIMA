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

    private static final String filepath = "/dummy/";

    private ArrayList<Booking> bookingHistory;

    // holds an instance of the controller 
    private static BookingController controllerInstance = null;
    
    // methods
    /*
     * Instantiate a controller object when called.
     */
    public static BookingController getController() {
        if (controllerInstance == null) {
            controllerInstance = new BookingController();
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
        for (int i = 0; i < bookingHistory.size(); i++)
        {
            if (bookingHistory.get(i).getMovieGoer().getMovieGoerNumber() == movieGoerNumber)
            {   
                System.out.println(bookingHistory.get(i).toString());
            }
        }
    }

    public void listBookingViaEmail(String movieGoerEmail){
        for (int i = 0; i < bookingHistory.size(); i++)
        {
            if (bookingHistory.get(i).getMovieGoer().getEmailAddress() == movieGoerEmail)
            {   
                System.out.println(bookingHistory.get(i).toString());
            }
        }
    }

    public void saveData(Booking bookingObj){  
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        
            objectOut.writeObject(bookingObj);
            objectOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object loadData(){
        try{
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }        
    }
}