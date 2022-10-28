package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieGoer implements Serializable {
    private String movieGoerName;
    private String emailAddress;
    private String movieGoerNumber;
    private ArrayList<Booking> movieBookings;

    public MovieGoer(String name, String email, String number){
        this.movieGoerName = name;
        this.emailAddress = email;
        this.movieGoerNumber = number;
        this.movieBookings = new ArrayList<Booking>();
    }

    // methods 
    public void addBooking(Showing showing, String TID, Seat seat) {
        Booking booking = new Booking(showing, TID, this, seat);
        movieBookings.add(booking);
    }

    // getters
    public String getMovieGoerName(){
        return this.movieGoerName;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    public String getMovieGoerNumber(){
        return this.movieGoerNumber;
    }

    // setters
    public void setMovierGoerName(String name) {
        this.movieGoerName = name;
    }

    public void setEmailAddress(String email) {
        this.emailAddress = email;
    }

    public void setmovieGoerNumber(String number) {
        this.movieGoerNumber = number;
    }
}

