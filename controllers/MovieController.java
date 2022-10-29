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
    private ArrayList<Movie> movieList;

    private static final String filepath = "/dummy/";
    
    @SuppressWarnings("unchecked")
    public MovieController(){
        movieList = (ArrayList<Movie>)loadData();
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

    public ArrayList<Movie> getMovieList() {
        return movieList;
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
    
    public void saveData(){
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        
            objectOut.writeObject(movieList);
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