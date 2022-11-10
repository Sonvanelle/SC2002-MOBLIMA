package controllers;

import entities.Showing;
import entities.showingStatus;
import entities.Movie;
import entities.Cinema;
import entities.Cineplex;
import controllers.MovieController;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class ShowingController implements Serializable{

    ArrayList<Showing> showingList;

    // holds an instance of the controller 
    private static ShowingController controllerInstance = null;

    // methods
    /*
     * Instantiate a controller object when called.
     */
    public static ShowingController getController() {
        if (controllerInstance == null) {
            controllerInstance = new ShowingController();
        }
        return controllerInstance;
    }

    public void addShowing(Cinema cinema, LocalDateTime showTime, Movie movie){
        Showing showing = new Showing(cinema, showTime, movie);
        showingList.add(showing);
    }

    // returns list of showings by cineplex 
    // TODO: implement Cineplex methods
    public void listShowings(Cineplex cineplex) {
        for (int i = 0; i < showingList.size(); i++)
        {
            
            for (int j = 0; j < cineplex.getCinemaList().size(); j++) {
                if (showingList.get(i).getCinema() == cineplex.getCinemaList().get(j))
                {
                    System.out.println(showingList.get(i).toString());
                }
            }
        }
    }

    // returns list of showings by movie
    public ArrayList<Showing> listShowings(Movie movie) {
        ArrayList<Showing> showingSelectionList = new ArrayList<Showing>();

        for (int i = 0; i < showingList.size(); i++)
        {
            if (showingList.get(i).getMovie() == movie) {
                showingSelectionList.add(showingList.get(i));
            }
        }
        return showingSelectionList;
    }

}