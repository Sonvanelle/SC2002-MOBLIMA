package entities;
import java.util.ArrayList;

public class Movie {
    private String movieName;
    private long movieHours;
    private long movieMin;
    private int status; //0: COMING SOON - 1: PREVIEW - 2: NOW SHOWING - 3: END OF SHOWING
    private String synopsis;
    private String director;
    private ArrayList<String> cast;

    // Constructor
    public Movie(String movieName, long movieHours, long movieMin, int status, String synopsis, String director, ArrayList<String> cast) {
        this.movieName = movieName;
        this.movieHours = movieHours;
        this.movieMin = movieMin;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
    }

    // getters
    public String getMovieName() {
        return movieName;
    }

    public long getMovieHours() {
        return movieHours;
    }

    public long getMovieMin() {
        return movieMin;
    }

    public showingStatus getStatus() {
        return status;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDirector() {
        return director;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

   /*  public float getOverallRating() {
        return overallRating;
    }

    public ArrayList<Review> getPastReviews() {
        return pastReviews;
    } */

    // setters
    public void setStatus(showingStatus status) {
        this.status = status;
    }

    /* public void addReview(Review newReview) {
        this.pastReviews.add(newReview);
    } */
}