package controllers;

import entities.Movie;
import entities.showingStatus;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Collections;

public class MovieController implements Serializable{
    private static ArrayList<Movie> movieList;
    private static final String filepath = "movielist.ser";
    private static MovieController controllerInstance = null;
    
    
    /*
     * Instantiate a controller object when called.
     */
    @SuppressWarnings("unchecked")
    public static MovieController getController() {
        if (controllerInstance == null) {
            controllerInstance = new MovieController();
        }
        movieList = (ArrayList<Movie>)loadData();
        if (movieList==null){
            System.out.println("No movieList found; creating new file.");
            movieList = new ArrayList<Movie>();
            saveData();
        }
        return controllerInstance;
    }
    

    public void createMovie(String movieName, long movieMin, showingStatus val, 
    String synopsis, String director, ArrayList<String> cast)
    {
        Movie movie = new Movie(movieName, movieMin, val, synopsis, director, cast);
        movieList.add(movie);
    }

    public void deleteMovie(String movieName) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getMovieName() != null && movieList.get(i).getMovieName() == movieName) {
                movieList.remove(movieList.get(i));
            }
        }
    }

    public void editMovie(String movieName){
        Movie movieToEdit;
        for (int i=0; i<movieList.size(); i++){
            if (movieList.get(i).getMovieName() == movieName){
                movieToEdit = movieList.get(i);
            }
        }

        //TO DO: implement choices for editing the movie's metadata
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    // helper for printMovieListByStatus, takes in an array list and prints movie name and details
    public void printMovieList(ArrayList<Movie> moviesToPrint) {
        System.out.println("------------\n");
        for (int i = 0; i < moviesToPrint.size(); i++){
            System.out.println((i+1) + ") " + moviesToPrint.get(i).getMovieName());
            System.out.println("Status: " + moviesToPrint.get(i).getStatus());
            System.out.println("Rating: " + moviesToPrint.get(i).averageRating() + "\n");
        }
        System.out.println("------------\n");
    }

    // controller method that allows users to view all movies of a certain showing status
    // calls movieSelector on the chosen status for further action (booking, review, etc)
    public void printMovieListByStatus() {

        int statusOption;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println(
                "\nViewing Movies... \n" +
                "------------\n" +
                "Please select movie showing status: \n" +
                "1. Now Showing\n" +
                "2. Preview\n" +
                "3. Coming Soon\n" +
                "4. List All Movies\n" +
                "0. Back\n" +
                "------------\n"
            );
            
            System.out.println("Enter option: ");

            while (!sc.hasNextInt()) {
                System.out.println("Please input a number value.");
                sc.next();
            }

            statusOption = sc.nextInt();
            sc.nextLine();
            ArrayList<Movie> moviesToPrint = new ArrayList<Movie>();

            switch(statusOption) {
                case 1:
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStatus() == showingStatus.NOW_SHOWING) {
                            moviesToPrint.add(movieList.get(i));
                        }
                    }
                    
                    printMovieList(moviesToPrint);
                    movieSelector(moviesToPrint);
                    System.out.println("Returning...");
                    break;
                
                case 2:
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStatus() == showingStatus.PREVIEW) {
                            moviesToPrint.add(movieList.get(i));
                        }
                    }
                    
                    printMovieList(moviesToPrint);
                    movieSelector(moviesToPrint);
                    System.out.println("Returning...");
                    break;

                case 3:
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStatus() == showingStatus.COMING_SOON) {
                            moviesToPrint.add(movieList.get(i));
                        }
                    }
                    
                    printMovieList(moviesToPrint);
                    movieSelector(moviesToPrint);
                    System.out.println("Returning...");
                    break;

                case 4:
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStatus() != showingStatus.END_OF_SHOWING) {
                            moviesToPrint.add(movieList.get(i));
                        }
                    }
                    
                    printMovieList(moviesToPrint);
                    movieSelector(moviesToPrint);
                    System.out.println("Returning...");
                    break;

                case 0:
                    System.out.println("Navigating back to main menu.");
                    break;

                default:
                    System.out.println("Please input an option from 1 to 4.");
                    break;
            }
        } while(statusOption != 0);

        sc.close();
    }

    public void movieSelector(ArrayList<Movie> movieSelectionList) {
        int option = 0; 
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\nPlease select a movie (0 to exit menu): ");
                
            while (!sc.hasNextInt()) {
                System.out.printf("Ivalid input. Please enter option from 1 to %d.\n", movieSelectionList.size());
                sc.next();
            }
    
            // displayed list starts at index 1
            option = sc.nextInt() - 1;
            sc.nextLine();
            
            // user inputs 0 and exits selector
            if (option == -1) {
                System.out.println("Returning...");
                sc.close();
                return;
            }
            else if (option < 0 || option >= movieSelectionList.size()) {
                System.out.printf("Invalid input. Please enter option from 1 to %d.\n", movieSelectionList.size());
            }


        } while (option < 0 || option >= movieSelectionList.size());

        // print movie details and display action menu
        movieSelectionList.get(option).printDetails();
        // TODO: action menu for movie selection
    }

    public void viewTop5() {
        int option;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println(
                "View top 5 movies by:\n" +
                "1. By ticket sales\n" +
                "2. By overall rating\n" +
                "0. Back"
            );

            System.out.println("Enter option: ");

            while (!sc.hasNextInt()) {
                System.out.println("Please input a number value.");
                sc.next();
            }
            
            // after selection, fill the array list based on the selected parameters
            option = sc.nextInt();
            sc.nextLine();
            ArrayList<Movie> top5List = new ArrayList<Movie>();

            switch (option) {
                case 1:
                    // returns first 5 entries of preview and showing movies, sorted by sales numbers
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStatus().isEqual("NOW_SHOWING") ||
                            movieList.get(i).getStatus().isEqual("PREVIEW")) {
                                top5List.add(movieList.get(i));
                            }
                    }
                    // sort list and print top 5 (or any number less than 5 but above 0)
                    top5List.sort(Comparator.comparingDouble(Movie::getTicketSales).reversed());
                    
                    if (top5List.size() != 0) {
                        for (int i = 0; i < Math.min(top5List.size(), 5); i++) {
                            System.out.printf("%d. %s \n Sales: %d\n", (i+1), top5List.get(i).getMovieName(), top5List.get(i).getTicketSales());
                        }
                    } else {    
                        System.out.println("No movies match these terms.");
                        break;
                    }

                case 2: 
                    // returns first 5 entries of preview and showing movies, sorted by review ratings
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStatus().isEqual("NOW_SHOWING") ||
                            movieList.get(i).getStatus().isEqual("PREVIEW")) {
                                top5List.add(movieList.get(i));
                            }
                    }
                    // sort list and print top 5 (or any number less than 5 but above 0)
                    Collections.sort(top5List);
                    Collections.reverse(top5List);

                    if (top5List.size() != 0) {
                        for (int i = 0; i < Math.min(top5List.size(), 5); i++) {
                            System.out.printf("%d. %s \n Rating: %d\n", (i+1), top5List.get(i).getMovieName(), top5List.get(i).averageRating());
                        }
                    } else { 
                        System.out.println("No movies match these terms.");
                        break;
                    }
            }

        } while(option != 0);

        sc.close();
    }
    
    public static void saveData(){
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        
            objectOut.writeObject(movieList);
            objectOut.close();
        } catch (IOException e) {
            System.out.println("Got an error while saving movie data: " + e);
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
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Got an error while loading movie data: " + e);
            //e.printStackTrace();
            return null;
        }        
    }
}