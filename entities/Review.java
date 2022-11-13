package entities;
import java.io.Serializable;

public class Review implements Serializable{
    private String movieName;
    private int rating;
    private String review;
    private String reviewer;


	public Review(String movieName, int rating, String review, String reviewer) {
        this.movieName = movieName;
        this.rating = rating;
        this.review = review;
        this.reviewer = reviewer;
	}

	//Accessors
    public String getMovieName(){
        return this.movieName;
    }
    public int getRating()
    {
        return this.rating;
    }
    public String getReview()
    {
        return this.review;
    }
    public String getReviewer()
    {
        return this.reviewer;
    }

    //Mutators
    public void setMovieName(String movieName){
        this.movieName = movieName;
    }
    public void setRating(int rating)
    {
        this.rating=rating;
    }
    public void setReview(String review)
    {
        this.review=review;
    }
    public void setReviewer(String reviewer)
    {
        this.reviewer=reviewer;
    }
}