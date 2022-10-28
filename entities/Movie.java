package entities;
import java.util.ArrayList;

public class Movie {
    private String movieName;
    private double movieDuration;
    private showingStatus status; //0: COMING SOON - 1: PREVIEW - 2: NOW SHOWING - 3: END OF SHOWING
    private String synopsis;
    private String director;
    private ArrayList<String> cast;
    private ArrayList<Review> reviews;
    private double ticketSales = 0;

    /*
     * should we have the parameters declared in the constructor arguments, or use the setters in
     * another line?
     */

    // Constructor
    public Movie(String movieName, double movieDuration, showingStatus status, String synopsis, String director, ArrayList<String> cast) {
        this.movieName = movieName;
        this.movieDuration = movieDuration;
        this.status = status;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.reviews = new ArrayList<>();
        this.ticketSales = 0;
    }

    // methods

    public float averageRating() {
        int total = 0;
        for (int i = 0; i < getReviews().size(); i++) {
            total += getReviews().get(i).getRating();
        }
        return total / getReviews().size();
    }

    public void printDetails() {
        System.out.printf("Title: %s\n", getMovieName());
        System.out.printf(status.toString());
        System.out.printf("Runtime: %d minutes\n", getMovieDuration());
        if (getReviews().size() != 0) {
            System.out.printf("Average score: %.2f\n", averageRating());
        }
        System.out.printf("Director: %s\n", getDirector());
        System.out.println("Cast:");
        for (int i = 0; i < getCast().size(); i++) {
            System.out.printf(getCast().get(i)+ "\n");
        }
        System.out.printf("Synopsis:\n%s", getSynopsis());

    }

    // getters
    public String getMovieName() {
        return movieName;
    }

    public double getMovieDuration() {
        return movieDuration;
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

    public ArrayList<Review> getReviews() {
        return reviews;
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