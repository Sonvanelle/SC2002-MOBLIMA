package entities;
public class Review{
    private String movieName;
    private int rating;
    private String review;
    private String reviewer;

  public Review(int rating, String review, String reviewer)
  {
      this.rating = rating;
      this.review = review;
      this.reviewer= reviewer;
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