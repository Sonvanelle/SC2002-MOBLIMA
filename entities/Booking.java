package entities;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking implements Serializable {
  private String TID;
  private MovieGoer movieGoer;
  private Showing showing;
  private int cinemaID;
  private int cineplexID;
  private String bookingTime;
  private Seat seat;


  public Booking(String TID, MovieGoer movieGoer, Seat seat, Showing showing, int cinemaID, int cineplexID)
  {
	this.TID = TID;
	this.movieGoer = movieGoer;
	this.seat = seat;
	this.showing = showing;
	this.cinemaID = cinemaID;
	this.cineplexID = cineplexID;
	this.seat.setOccupancy(true); 
	this.bookingTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
  }

  // methods
  public void printBooking() 
  {
    System.out.printf("Booking Transaction ID: %s\n", TID);
	System.out.printf("Booking timestamp: %s\n", bookingTime);
	System.out.printf("Cineplex: %d\n", cineplexID);
	System.out.printf("Cinema: %d\n", cinemaID);
	System.out.printf("Moviegoer ID: %d\n", movieGoer.getMovieGoerNumber());
	System.out.printf("Showing: %s at %d\n", showing.getMovie(), showing.getShowtime());
	System.out.printf("Seat: %d-5d\n", seat.getRow(), seat.getCol());
  }

  // getters

  public String getTID()
  {
    return this.TID;
  }

  public MovieGoer getMovieGoer() 
  {
    return this.movieGoer;
  }


  public Seat getSeat() 
  {
    return this.seat;
  }
  
  // setters

  public void setTID(String TID)
  {
    this.TID = TID;
  }
  
  public void setMovieGoer(MovieGoer movieGoer) 
  {
    this.movieGoer = movieGoer;
  }
}