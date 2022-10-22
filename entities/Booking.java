package entities;
import java.time.LocalDateTime;

public class Booking {
  private String TID;
  private Person movieGoer;
  private String movieGoerID;
  private String movieGoerNumber;
  private Showing showing;
  private LocalDateTime bookingTime;
  private Seat seat;


  public Booking(Showing showing, String TID, Person movieGoer, Seat seat)
  {
      this.TID = TID;
      this.movieGoer = movieGoer;
      this.seat = seat;
      this.showing = showing;
      seat.setOccupancy(true); 
  }

  public String getTID()
  {
    return this.TID;
  }

  public Person getMovieGoer() 
  {
    return this.movieGoer;
  }

  public Seat getSeat() 
  {
    return this.seat;
  }
  
  public void setTID(String TID)
  {
    this.TID=TID;
  }
  public void setmovieGoerID(String movieGoerID)
  {
    this.movieGoerID=movieGoerID;
  }
  
  public void setmovieGoerNumber(String movieGoerNumber)
  {
    this.movieGoerNumber=movieGoerNumber;
  }
}