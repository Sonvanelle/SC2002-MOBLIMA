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
import java.util.Scanner;

public class ShowingController implements Serializable{

    private static ArrayList<Showing> showingList;

    // holds an instance of the controller 
    private static ShowingController controllerInstance = null;
    private static final String filepath = "showings.ser";


    // methods
    /*
     * Instantiate a controller object when called.
     * Deserializes showingList from file; if not found/null object, create new one and store in new file.
     */
    @SuppressWarnings("unchecked")
    public static ShowingController getController() {
        if (controllerInstance == null) {
            controllerInstance = new ShowingController();
        }
        showingList = (ArrayList<Showing>)loadData();
        if (showingList==null){
            System.out.println("No showingList; creating new file.");
        }
        return controllerInstance;
    }

    
    public void addShowing(Cinema cinema, LocalDateTime showTime, Movie movie){
        Showing showing = new Showing(cinema, showTime, movie);
        showingList.add(showing);
    }


    public void editShowing(String moviename){
        Scanner sc = new Scanner(System.in);
        ArrayList<Showing> showingsToEdit = new ArrayList<Showing>();
        int index = 0;
        for (int i=0; i<showingList.size(); i++){
            if (showingList.get(i).getMovie().getMovieName() == moviename){
                Showing temp = showingList.get(i);
                showingsToEdit.add(temp);
                System.out.println("Showing "+(index+1)+")\n"+
                                    "Showtime: " + temp.getShowtime() + "\n"+
                                    "Cineplex/Cinema" + temp.getCinema()
                );
                index++;
            }
        }
        if (showingsToEdit.size()==0) {System.out.println("No showings found. Returning... \n"); return;}

        //TO DO: pick a showing in showingsToEdit, by (index-1), and allow to change its datetime.
        // I've written an editShowing method in CinemaController alr, but I'm not too sure whether 
        // the parameters basically remain the same or what
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

    public static void saveData(ArrayList<Showing> showingObj){  
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        
            objectOut.writeObject(showingObj);
            objectOut.close();
        } catch (Exception e) {
            System.out.println("Got an error while saving showing data: "+e);
            //e.printStackTrace();
        }
    }

    public static Object loadData(){
        try{
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception e) {
            System.out.println("Got an error while loading showing data: " + e);
            //e.printStackTrace();
            return null;
        }        
    }

}