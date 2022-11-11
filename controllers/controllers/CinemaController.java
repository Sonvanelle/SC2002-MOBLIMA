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

    public void defineLayout(Cinema cinema) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("------------");
            System.out.println("1. Use default layout (only for cinema with 10 rows and 16 columns");
            System.out.println("2. Define custom layout");
            System.out.println("0. Exit");
            System.out.println("------------");

            int userChoice = sc.nextInt();

            if (userChoice == 1) { // Use default layout
                if (cinema.getRows() != 10 || cinema.getColumns() != 16) { // Validate that cinema has exactly 10 rows and 16 cols
                    System.out.println("Unsuccessful. Default layout cannot be used with cinema's current dimensions");
                } else {
                    defineLayoutHelperDefineSeatType(cinema, seatType.EMPTY, true);
                    defineLayoutHelperDefineSeatType(cinema, seatType.COUPLE, true);
                    defineLayoutHelperDefineSeatType(cinema, seatType.ELITE, true);
                    defineLayoutHelperDefineSeatType(cinema, seatType.ULTIMA, true); 
                    printCinema(cinema);
                }                               
            }
            else if (userChoice == 2) { // Define custom layout
                // Assume a complete, rectangular grid of regular seats.
                // First line of user input defines all the non-seats.
                // Second line defines couple seats, third line defines elite seats, fourth line defines ultima seats.

                // Regardless of whether cinema already had a defined layout, reset it and start from scratch        
                ArrayList<Seat> newSeatingPlan = new ArrayList<Seat>();
                for (int i = 0; i < cinema.getRows(); i++) {
                    for (int j = 0; j < cinema.getColumns(); j++){
                        Seat newSeat = new Seat(i , j, seatType.REGULAR, "nullId", false); 
                        newSeatingPlan.add(newSeat);
                    }
                }
                cinema.setSeatingPlan(newSeatingPlan);
                
                System.out.println("Layout is currently: ");
                printCinema(cinema);

                // Set empty (invalid), couple, elite, ultima seats
                defineLayoutHelperDefineSeatType(cinema, seatType.EMPTY, false);
                defineLayoutHelperDefineSeatType(cinema, seatType.COUPLE, false);
                defineLayoutHelperDefineSeatType(cinema, seatType.ELITE, false);
                defineLayoutHelperDefineSeatType(cinema, seatType.ULTIMA, false);
            }
            else if (userChoice == 0) {
                break;
            }
            else {
                System.out.println("Invalid. Please enter a number from 0 to 2: ");
                userChoice = sc.nextInt();
            }
        }
    }

    public void defineLayoutHelperDefineSeatType(Cinema cinema, seatType sType, boolean defaultLayoutFlag) {
        Scanner sc = new Scanner(System.in);
        String[] seatPositionsArray = null;
        if (defaultLayoutFlag) { // If admin wants to use default layout
            if (sType == seatType.EMPTY) {
                seatPositionsArray = new String[] {"A1", "A2", "B1", "B2", "C1", "C2", "D1", "D2", "J9", "J10"};
            }
            else if (sType == seatType.COUPLE) {
                seatPositionsArray = new String[] {"H1", "H3", "H5", "H7", "H9", "H11", "H13", "H15"};
            }
            else if (sType == seatType.ELITE) {
                seatPositionsArray = new String[] {"I1", "I3", "I5", "I7", "I9", "I11", "I13", "I15"};
            }
            else if (sType == seatType.ULTIMA) {
                seatPositionsArray = new String[] {"J1", "J3", "J5", "J7", "J13", "J15"};
            }
        }
        else { // if admin wants a custom layout
            // Get 1 line of user input, which defines all instances of the particular seatType in the cinema 
            if (sType == seatType.EMPTY) {
                System.out.println("Enter all invalid seats separated by spaces (e.g. a1 A2 B1 B2 C1 C2 D1 D2 J9 J10)");
            }
            else { // if 2-seater (the argument sType won't be a REGULAR seat)
                System.out.printf("Enter all %s seats separated by spaces (e.g. h1 H3 H7 H9 H11 H13 H15)\nNote: A1 represents a %s seat from A1-A2\n", sType.name(), sType.name());
            }
            
            String userin = sc.nextLine();
            if (userin == "") { // if user input is an empty line, don't define that seat type
                System.out.println("Layout is currently: ");
                printCinema(cinema);
                return;
            }

            // Validate user input
            while (!validateDefineLayoutUserInput(cinema, userin)) { 
                if (sType == seatType.EMPTY) {
                    System.out.println("Enter all invalid seats separated by spaces (e.g. a1 A16 B1 B16)");
                }
                else { // if 2-seater (the argument sType won't be a REGULAR seat)
                    System.out.printf("Enter all %s seats separated by spaces (e.g. e1 E15 F2 F14)\nNote: A1 represents a %s seat from A1-A2\n", sType.name(), sType.name());
                }
                userin = sc.nextLine();
            }

            seatPositionsArray = userin.split(" ");
        }
        
        
        // Iterate through each seatPosition from the user input and update cinema's seatingPlan accordingly
        for (String seatPos: seatPositionsArray) {
            int seatPosRow = (int) Character.toUpperCase(seatPos.charAt(0))  - 65;
            int seatPosCol = Integer.parseInt(seatPos.substring(1)) - 1;
            for (int i = 0; i < cinema.getSeatingPlan().size(); i++) { // Find seat that matches in the seats ArrayList
                if (cinema.getSeatingPlan().get(i).getRow() == seatPosRow && cinema.getSeatingPlan().get(i).getCol() == seatPosCol) {
                    if (sType == seatType.EMPTY) {
                        cinema.getSeatingPlan().get(i).setSeatType(seatType.EMPTY);
                    }
                    else {
                        // TODO Check whether 2-seaters clash with sides or aisle, or other previously defined seatTypes
                        // Since 2-seater spans 2 seats, remove the right seat and set the left seat to the appropriate seatType
                        cinema.getSeatingPlan().remove(i+1);
                        cinema.getSeatingPlan().get(i).setSeatType(sType);
                    }
                    break;
                }
            }
        }

        if (defaultLayoutFlag == false) { // Print layout at each step only if admin is doing custom layout
            System.out.println("Layout is currently: ");
            printCinema(cinema);
        }
    }

    public boolean validateDefineLayoutUserInput(Cinema cinema, String userin) {
        // Validate that user input has expected formatting (seats sep. by spaces, e.g. "A1 A16 b1 b16") 
        // and that row letters and col numbers don't exceed cinema's dimensions

        // Logic for validating no clashes is in a separate function
        String[] seatPositionsArray = userin.split(" "); 
        for (String seatPos : seatPositionsArray) {
            // Separate each supposed seat position string (like "A1") into the row and col parts
            char seatPosRowChar = seatPos.charAt(0);

            
            if (!Character.isAlphabetic(seatPosRowChar)) { // Validate row char is a letter
                System.out.println("Invalid. First character of each seat must be a letter.");
                return false;
            }
            int seatPosCol;
            try { // Validate col String is numeric
                seatPosCol = Integer.parseInt(seatPos.substring(1)) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid. Column for each seat must be a number.");
                return false;
            }
            
            // Validate row and col don't exceed cinema's dimensions
            int seatPosRow = (int) Character.toUpperCase(seatPos.charAt(0))  - 65;
            if (seatPosRow >= cinema.getRows() || seatPosRow < 0 || seatPosCol >= cinema.getColumns() || seatPosCol < 0) {
                System.out.println("Invalid. Seat exceeds cinema's dimensions.");
                return false;
            }
        }
        return true;
    }

    public void printCinema(Cinema cinema) {
        String screen = "----------SCREEN----------";
        // Print screen in the center of the 0th row
        for (int i = 0; i < (cinema.getColumns()*3+6)/2 - screen.length()/2; i++) {
            System.out.print(" ");
        }
        System.out.println(screen);


        char rowLetter = 'A';
        int col = 0; // for a cinema with 16 cols, print row letter when col is 0 or (numOfCols + 1) 
        // the aisle spaces won't be a separate column in the code logic, but will be printed at the midpoint
        
        int seatNum = 0;
        while (seatNum < cinema.getSeatingPlan().size()) {
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
            
            // Increment col number, rowLetter and seatNum
            if (col ==  0) { // If start of row
                col++;
            }
            else if (col == cinema.getColumns() + 1) { // If end of row reached
                col = 0;
                rowLetter += 1;
                System.out.println();
            }
            else if (cinema.getSeatingPlan().get(seatNum).getSeatType() == seatType.REGULAR || cinema.getSeatingPlan().get(seatNum).getSeatType() == seatType.EMPTY) {
                col++;
                seatNum++;
            }
            else { // If seat type takes 2 spaces
                col += 2;
                seatNum++;
            }
            
        }
        System.out.print(" " + rowLetter + "\n\n");
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

            //showing TBA is later than all current showings, so check the latest showing in showingList for overlap.
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