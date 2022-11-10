package controllers;

import entities.Cinema;
import entities.Showing;
import entities.Seat.seatType;
import entities.Movie;
import entities.Seat;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Still needs Serializable method
public class CinemaController implements Serializable { 
    
    ArrayList<Cinema> cinemaList;

    // holds an instance of the controller 
    private static CinemaController controllerInstance = null;
    
    // methods
    /*
     * Instantiate a controller object when called.
     */
    public static CinemaController getController() {
        if (controllerInstance == null) {
            controllerInstance = new CinemaController();
        }
        return controllerInstance;
    }

    public void createCinema(int id, Cinema.classType val, int rows, int cols){
        Cinema cinema = new Cinema(id, val, rows, cols);
        cinemaList.add(cinema);
    }

    public void listCinema(){
        for (int i=0; i<cinemaList.size();i++){
            
        }
    }

    // TODO Defines layout of the cinema (empty spaces, seat types, etc)
    public void defineLayout(Cinema cinema) {
        Scanner sc = new Scanner(System.in);
        printCinema2(cinema);

    }


    public void printCinema(Cinema cinema){ //prints cinema. each seat should take 3 characters to print.
        int columns = -1;
        for (int i=0; i<cinema.getSeatingPlan().size();i++){ //if end of row, goes to next column of cinema + prints column number.
            if (i%(cinema.getRows()-1)==0){
                System.out.println("\n");
                if (columns>0){System.out.println(++columns + " ");}
                else{System.out.println("--");}
                } 

            if (columns==-1){System.out.println("---");} //print the screen at column -1.
            else if (columns == 0){System.out.println(" " + i%cinema.getRows() +" ");} //prints the row numbers.
            else{
                Seat currentSeat = cinema.getSeatingPlan().get(i);
                if (currentSeat.getOccupancy()==true){
                    if (currentSeat.getSeatType()==seatType.COUPLE | currentSeat.getSeatType() == seatType.ELITE | currentSeat.getSeatType() == seatType.ULTIMA){
                        System.out.println("[x][x]");
                    }
                    else{System.out.println("[x]");}
                }
                if (currentSeat.getSeatType() == seatType.REGULAR){System.out.println("[ ]");}
                if (currentSeat.getSeatType() == seatType.EMPTY){System.out.println("   ");}
                if (currentSeat.getSeatType() == seatType.COUPLE){System.out.println("[c  c]");}
                if (currentSeat.getSeatType() == seatType.ELITE){System.out.println("[e  e]");}
                if (currentSeat.getSeatType() == seatType.ULTIMA){System.out.println("[u  u]");} 
            }
        }
    }

    public void printCinema2(Cinema cinema) {
        System.out.println("----------SCREEN----------\n");
        char rowLetter = 'A';
        int col = 0; // for a cinema with 16 cols, print row letter when col is 0 or (numOfCols + 1) 
        // the aisle spaces won't be a separate column in the code logic, but will be printed at the midpoint
        
        for (int seatNum = 0; seatNum < cinema.getSeatingPlan().size(); seatNum++) {
            if (col == 0) { // if start of row
                System.out.print(rowLetter + " ");
            }
            else if (col == cinema.getColumns() + 1) { // if end of row
                System.out.print(" " + rowLetter);
            }
            else { // if col is a seat
                if (col == cinema.getColumns() / 2 + 1) { // if col is first seat in right half of the row, add spaces before it to represent aisle
                    System.out.print("  ");
                }
                Seat currentSeat = cinema.getSeatingPlan().get(seatNum);
                if (currentSeat.getOccupancy()==true){
                    if (currentSeat.getSeatType()==seatType.COUPLE || currentSeat.getSeatType() == seatType.ELITE || currentSeat.getSeatType() == seatType.ULTIMA){
                        System.out.print("[x][x]");
                    }
                    else{System.out.print("[x]");}
                }
                else {
                    if (currentSeat.getSeatType() == seatType.REGULAR){System.out.print("[ ]");}
                    if (currentSeat.getSeatType() == seatType.EMPTY){System.out.print("   ");}
                    if (currentSeat.getSeatType() == seatType.COUPLE){System.out.print("[c  c]");}
                    if (currentSeat.getSeatType() == seatType.ELITE){System.out.print("[e  e]");}
                    if (currentSeat.getSeatType() == seatType.ULTIMA){System.out.print("[u  u]");} 
                }
            }
            
            // Increment col number based on whether seat takes 1 or 2 spaces
            if (col ==  0) { // If start of row
                col++;
            }
            else if (col == cinema.getColumns() + 1) { // If end of row reached
                col = 0;
                if (rowLetter == 'H') {rowLetter = 'J'; } // Skip letter 'I'
                else {rowLetter += 1;}
                System.out.println();
            }
            else if (cinema.getSeatingPlan().get(seatNum).getSeatType() == seatType.REGULAR) {
                col++;
            }
            else { // If seat type takes 2 spaces
                col += 2;
            }
            
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

    // Returns true if show successfully added without clashes
    public boolean addShowingHelper(Cinema cinema, Showing newShowing) { // helper method so can be used in editShowing()
        ArrayList<Showing> showingList = cinema.getShowingList();
        if (verifyNoShowingOverlaps(cinema, newShowing)) { // validate if no showing overlaps
            for (int i=0; i < showingList.size(); i++) { // Add at correct location in chronological order
                if (showingList.get(i).getShowtime().compareTo(newShowing.getShowtime())>0) {
                    showingList.add(i, newShowing); 
                    return true;
                }
            }
            // If this branch reached, add new showing as last element in showingList
            showingList.add(showingList.size(), newShowing);
            cinema.setShowingList(showingList);
            return true;
        }
        System.out.println("Error - Clash of show time");
        return false;
    }

    public boolean addShowing(Cinema cinema){ 

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

        // Create the newShowing with the user-inputted showtime and movie
        Showing newShowing = new Showing(cinema, newShowTime, movieList.get(movieChoice));

        return addShowingHelper(cinema, newShowing);
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

            } else if (userChoice == 2) { // Edit showing's show time
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
                if (verifyNoShowingOverlaps(cinema, tempOldShowing)) {
                    addShowingHelper(cinema, newShowing);
                }

                if (addShowingHelper(cinema, newShowing) == false) { 
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
        

    }

    public void deleteShowing(Cinema cinema) {
        ArrayList<Showing> showingList = cinema.getShowingList();
        Scanner sc = new Scanner(System.in);

        // Print out all showings with indexes
        for (int i = 0; i < cinema.getShowingList().size(); i++) {
            Movie currentMovie = showingList.get(i).getMovie();
            System.out.printf("Movie %d: %-30s | %tT - %tT\n", i+1, showingList.get(i).getMovie().getMovieName(), showingList.get(i).getShowtime(), showingList.get(i).getShowtime().plusMinutes(currentMovie.getMovieMin()));
        }

        // Let user select index of showing they want to delete
        System.out.println("Enter index of showing you would like to delete: ");
        int index = sc.nextInt();
        while (index < 1 || index > showingList.size()) {
            System.out.printf("Error. Please enter a number from %d to %d: \n");
            index = sc.nextInt();
        }

        showingList.remove(index);
    }

}