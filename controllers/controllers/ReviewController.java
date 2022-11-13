package controllers;

import entities.Review;
import entities.Movie;
import utils.SerializeObjects;

import java.util.ArrayList;

import javax.swing.plaf.multi.MultiViewportUI;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * Controller class that handles the creation, modification of Reviews for Movies.
 */
public class ReviewController implements Serializable {
    private static ArrayList<Review> reviewList;
    private static ReviewController controllerInstance = null;
    private static final String filepath = "reviews.ser";

    @SuppressWarnings("unchecked")
    public static ReviewController getController() {
        if (controllerInstance == null) {
            controllerInstance = new ReviewController();
        }

        reviewList = (ArrayList<Review>) SerializeObjects.loadData(filepath);
        if (reviewList == null) {
            System.out.println("No reviewList found; creating new file.");
            reviewList = new ArrayList<Review>();
            SerializeObjects.saveData(filepath, reviewList);
        }
        return controllerInstance;
    }

    /**
     * This function creates a new review object and adds it to the review list
     * 
     * @param movieName String
     * @param rating    1-5
     * @param comments  String
     * @param reviewer  the name of the person who is reviewing the movie
     */
    public void createReview(String movieName, int rating, String comments, String reviewer) {
        Review review = new Review(movieName, rating, comments, reviewer);

        // add review to list
        reviewList.add(review);

        // add rating to movie object
        MovieController.getController().addRating(movieName, rating);
        SerializeObjects.saveData(filepath, reviewList);
    }

    /**
     * This function will print out all the reviews for a movie
     * 
     * @param movieName The name of the movie to list reviews for
     */
    public void listReviews(String movieName) {
        if (reviewList.size() == 0) {
            System.out.println("There is currently no reviews for this movie yet");
            return;
        }

        System.out.print("Reviews for " + movieName + ":\n");
        for (int i = 0; i < reviewList.size(); i++) {
            if (reviewList.get(i).getMovieName().equals(movieName)) {
                System.out.println(reviewList.get(i).getRating() + "/5");
                System.out.printf("By: %s\n", reviewList.get(i).getReviewer());
                System.out.println(reviewList.get(i).getReview());
                System.out.println();
            }
        }
    }
}