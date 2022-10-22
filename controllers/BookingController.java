import entities.Booking;
import entities.Person;
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
    private ArrayList<Booking> bookingHistory;

    private static final String filepath = "/dummy/";
    
    public void createBooking(Showing showing, Person movieGoer, Seat seat) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatStr = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String tid = now.format(formatStr);
        tid = showing.getCinema().getCinemaID() + tid;
        
        Booking booking = new Booking(
            showing,
            tid,
            movieGoer,
            seat
        );
        bookingHistory.add(booking);
    }

    public void listBooking(String movieGoerNumber){
        for (int i = 0; i < bookingHistory.size(); i++)
        {
            if (bookingHistory.get(i).getMovieGoer().getMovieGoerNumber() == movieGoerNumber)
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