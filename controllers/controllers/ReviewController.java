package controllers;

import entities.Review;

import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class ReviewController implements Serializable{
    private ArrayList<Review> reviewList;
    private static ReviewController controllerInstance = null;
    private static final String filepath = "/data/reviews/";

    public ReviewController getController() {
        if (controllerInstance == null) {
            controllerInstance = new ReviewController();
        }

        return controllerInstance;
    }

    public void createReview(String movieName, int rating, String comments, String reviewer){
        Review review = new Review(movieName, rating, comments, reviewer);
        reviewList.add(review);
    }

    public void listReviews(String movieName)
    {
        for (int i = 0; i < reviewList.size(); i++){
            if (reviewList.get(i).getMovieName() == movieName)
            {
                System.out.println(reviewList.get(i).toString());
            }
        }
    }

    public void saveData(Review bookingObj){  
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