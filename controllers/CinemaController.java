import entities.Cinema;
import entities.Showing;
import entities.Movie;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class CinemaController implements Serializable {
    ArrayList<Cinema> cinemaList;

    

    public void createCinema(int id, Cinema.classType val, int rows, int cols){
        Cinema cinema = new Cinema(id, val, rows, cols);
        cinemaList.add(cinema);
    }

    public void listCinema(){
        for (int i=0; i<cinemaList.size();i++){
            
        }
    }

    public boolean verifyNoShowingOverlaps(Cinema cinema, Showing showing){ 
            if (cinema.getShowingList().size() ==  0) {
                return true;
            }

            for (int i=0; i < cinema.getShowingList().size(); i++){
                if (cinema.getShowingList().get(i).getShowtime().compareTo(showing.getShowtime())>0){ //find the showing that starts after showing to-be-added (TBA).
                    if (i == 0) { // new showing is the earliest in the showingList
                        Movie previousMovie = showing.getMovie();
                        LocalDateTime previousShowingEnd = showing.getShowtime().plusMinutes(previousMovie.getMovieMin());
                        if (previousShowingEnd.compareTo(cinema.getShowingList().get(0).getShowtime())<0){ //check that (current showing + its movietime) doesn't overlap with next showing start time.
                            return true;
                        } else {
                            return false;
                        }
                        
                    }

                    Movie previousMovie = cinema.getShowingList().get(i-1).getMovie();
                    LocalDateTime previousShowingEnd = cinema.getShowingList().get(i-1).getShowtime().plusMinutes(previousMovie.getMovieMin());
                    
                    if (previousShowingEnd.compareTo(showing.getShowtime())<0){ //check that (showing before + its movietime) doesn't overlap with (showing TBA).
                        Movie showingMovie = showing.getMovie();
                        LocalDateTime showingEnd = showing.getShowtime().plusMinutes(showingMovie.getMovieMin());

                        if (cinema.getShowingList().get(i).getShowtime().compareTo(showingEnd)>=0) { //check that (showing after) doesn't overlap with (showing TBA + its movietime).
                            return true;
                        } 
                        else {
                            return false;
                        }
                    }   
                }
            }

            //showing TBA is the later than all current showings, so check the latest showing in showingList for overlap.
            Movie previousMovie = cinema.getShowingList().get(cinema.getShowingList().size()-1).getMovie();
            LocalDateTime previousShowingEnd = cinema.getShowingList().get(cinema.getShowingList().size()-1).getShowtime().plusMinutes(previousMovie.getMovieMin());
            if (previousShowingEnd.compareTo(showing.getShowtime())<0){
                return true;
            }
            return false;
        }

    public int addShowing(Cinema cinema){ 

        // Get movie and showtime from admin
        Scanner sc = new Scanner(System.in);
        
        // Print list of currently-showing movies
        ArrayList<Movie> movieList = new MovieController().getMovieList(); // Is there a better way of getting the movieList? Seems weird to instantiate MovieController just to get movie list
        for (int i = 0; i < movieList.size(); i++) {
            System.out.printf("Movie %d: %s\n", i+1, movieList.get(i).getMovieName());
        }
        // Get movie choice from admin
        System.out.println("Enter movie index you would you like to change to: ");
        int movieChoice = sc.nextInt();
        while (movieChoice < 1 || movieChoice > movieList.size()) {
            System.out.printf("Error. Enter an index from %d to %d: \n", 1, movieList.size());
            movieChoice = sc.nextInt();
        }
        // Get showtime from admin
        System.out.println("Enter new show time in the format DD-MM-YYYY hh:mm");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        boolean validDateTime = false;
        LocalDateTime newShowTime = null;        
        while (!validDateTime) { // Validate date and time input
            try {
                String newShowTimeString = sc.nextLine();
                newShowTime = LocalDateTime.parse(newShowTimeString, dateTimeFormatter);
                validDateTime = true;
            } catch (DateTimeParseException e) {
                System.out.println("Error. Please enter a valid date in the format DD-MM-YYYY hh:mm");
            }
        }


        Showing newShowing = new Showing(cinema, newShowTime, movieList.get(movieChoice));



        ArrayList<Showing> showingList = cinema.getShowingList();
        if (verifyNoShowingOverlaps(cinema, newShowing)) { // validate if no showing overlaps
            for (int i=0; i < showingList.size(); i++) { // Add at correct location in chronological order
                if (showingList.get(i).getShowtime().compareTo(newShowing.getShowtime())>0) {
                    showingList.add(i, newShowing); 
                    return 1;
                }
            }
            // If this branch reached, add new showing as last element in showingList
            showingList.add(showingList.size(), newShowing);
            cinema.setShowingList(showingList);
            return 1;
        }
        System.out.println("Error - Clash of show time");
        return -1;
    }


    public void editShowing(Cinema cinema) { 
        ArrayList<Showing> showingList = cinema.getShowingList();
        Scanner sc = new Scanner(System.in);

        // Print out all showings with indexes
        for (int i = 0; i < cinema.getShowingList().size(); i++) {
            Movie currentMovie = showingList.get(i).getMovie();
            System.out.printf("Movie %d: %-30s | %tT - %tT\n", i+1, showingList.get(i).getMovie().getMovieName(), showingList.get(i).getShowtime(), showingList.get(i).getShowtime().plusMinutes(currentMovie.getMovieMin()));
        }

        // Let user select index of showing they want to change
        System.out.println("Enter index of showing you would like to edit: ");
        int index = sc.nextInt();
        while (index < 1 || index > showingList.size()) {
            System.out.printf("Error. Please enter a number from %d to %d: \n");
            index = sc.nextInt();
        }

        // Let user choose which aspect of the showing they would like to change (either the movie, or show time)
        while (true) {
            System.out.println("1. Change movie");
            System.out.println("2. Change show time");
            System.out.println("0. Stop editing");

            int userChoice = sc.nextInt();

            if (userChoice == 1) { // Change movie

                // Print list of currently-showing movies
                ArrayList<Movie> movieList = new MovieController().getMovieList(); // Is there a better way of getting the movieList? Seems weird to instantiate MovieController just to get movie list
                for (int i = 0; i < movieList.size(); i++) {
                    System.out.printf("Movie %d: %s\n", i+1, movieList.get(i).getMovieName());
                }
                // Get user's choice of new movie
                System.out.println("Enter movie index you would you like to change to: ");
                int movieChoice = sc.nextInt();
                while (movieChoice < 1 || movieChoice > movieList.size()) {
                    System.out.printf("Error. Enter an index from %d to %d: \n", 1, movieList.size());
                    movieChoice = sc.nextInt();
                }
                // Change movie
                showingList.get(index).setMovie(movieList.get(movieChoice));               

            } else if (userChoice == 2) { 
                System.out.println("Enter new show time in the format DD-MM-YYYY hh:mm");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                boolean validDateTime = false;
                LocalDateTime newShowTime = null; 

                // Validate date and time input
                while (!validDateTime) {
                    try {
                        String newShowTimeString = sc.nextLine();
                        newShowTime = LocalDateTime.parse(newShowTimeString, dateTimeFormatter);
                        validDateTime = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Error. Please enter a valid date in the format DD-MM-YYYY hh:mm");
                    }
                }

                Showing newShowing = new Showing(cinema, newShowTime, showingList.get(index).getMovie());
                Showing tempOldShowing = showingList.get(index);
                
                showingList.remove(showingList.get(index));
                if (addShowing(cinema, newShowing) == -1) { 
                    showingList.add(index, tempOldShowing);
                    System.out.println("Error - Clash of show time");
                }
                System.out.println("Showing successfully edited");


            } else if (userChoice == 0) {
                break;

            } else {
                System.out.println("Invalid. Please enter a number from 0 to 2: ");
                userChoice = sc.nextInt();
            }
        }
        
        sc.close();

    }

    public void deleteShowing(Cinema cinema, Showing oldShowing) {
        ArrayList<Showing> temp = cinema.getShowingList();
        temp.remove(oldShowing);
        cinema.setShowingList(temp);
    }

}