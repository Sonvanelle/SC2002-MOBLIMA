package entities;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cinema {
    public enum classType {
        PLATINUM, GOLDEN, NORMAL
    }
    
    private int cinemaID;
    private classType classtype;
    private ArrayList<Seat> seatingPlan;
    private ArrayList<Showing> showingList; //sorted by DateTime.
    private int column;  // --> print column/2 add space 1234 5678
    

    // Constructor
    public Cinema(int id, classType val, int rows, int cols){
        this.cinemaID = id;
        this.classtype = val;
        this.seatingPlan = new ArrayList<Seat>();

        for (int i = 0; i < rows; i++){
                for (int j = 0; i < cols; i++){
                    Seat tempSeat = new Seat(i , j, false);
                    this.seatingPlan.add(tempSeat);
                }
            }

        this.showingList = new ArrayList<Showing>();
    }

    //Accessor
    public int getCinemaID(){
        return this.cinemaID;
    }

    public classType getClassType() {
        return this.classtype;
    }
    
    public ArrayList<Seat> getSeatingPlan(){
        return this.seatingPlan;
    }
    
    public ArrayList<Showing> getShowingList() {
        return this.showingList;
    }
    
    public int getColumns(){
        return this.column;
    }
    
    public boolean verifyNoShowingOverlaps(Showing showing){
        if (showingList.size() ==  0) {
            return true;
        }

        for (int i=0; i < showingList.size(); i++){
            if (showingList.get(i).getShowtime().compareTo(showing.getShowtime())>0){ //find the showing that starts after showing to-be-added (TBA).
                Movie previousMovie = showingList.get(i-1).getMovie();
                LocalDateTime previousShowingEnd = (showingList.get(i-1).getShowtime().plusHours(previousMovie.getMovieHours())).plusMinutes(previousMovie.getMovieMin());
                
                if (previousShowingEnd.compareTo(showing.getShowtime())<0){ //check that (showing before + its movietime) doesn't overlap with (showing TBA).
                    Movie showingMovie = showing.getMovie();
                    LocalDateTime showingEnd = (showing.getShowtime().plusHours(showingMovie.getMovieHours())).plusMinutes(showingMovie.getMovieMin());

                    if (showingList.get(i).getShowtime().compareTo(showingEnd)>=0){return true;} //check that (showing after) doesn't overlap with (showing TBA + its movietime).
                    else{return false;}
                }   
            }
        }

        //showing TBA is the later than all current showings, so check the latest showing in showingList for overlap.
        Movie previousMovie = showingList.get(showingList.size()-1).getMovie();
        LocalDateTime previousShowingEnd = (showingList.get(showingList.size()-1).getShowtime().plusHours(previousMovie.getMovieHours())).plusMinutes(previousMovie.getMovieMin());
        if (previousShowingEnd.compareTo(showing.getShowtime())<0){return true;}
        return false;
    }

    public int addShowing(Showing showing){ 
        if (verifyNoShowingOverlaps(showing)) { // validate if no showing overlaps
            for (int i=0; i < showingList.size(); i++) { // Add at correct location in chronological order
                if (showingList.get(i).getShowtime().compareTo(showing.getShowtime())>0) {
                    showingList.add(i, showing); 
                    return 1;
                }
            }
            // If this branch reached, add new showing as last element in showingList
            showingList.add(showingList.size(), showing);
            return 1;
        }
        System.out.println("Error - Clash of show time");
        return -1;
    }

    public void editShowing(Showing oldShowing, Showing newShowing) { 
        /** Move this part to controller ig

        // Print out all showings with indexes; let user select index of showing they want to change
        for (int i = 0; i < showingList.size(); i++) {
            System.out.println("Index " + i + ": " + showingList.get(i).getMovie().getMovieName());
            // Scan the user's index choice

            // Call the editShowing method with the specific showing to-be-updated 

        }
        */



        // Validate that newly edited showing doesn't clash with other showings
        // 1. Save copy of old showing and delete it
        Showing tempOldShowing = oldShowing;
        int tempIndex = showingList.indexOf(oldShowing);
        showingList.remove(oldShowing);
        
        // 2. Try to add newly edited showing. If clash, add back the old showing
        if (addShowing(newShowing) == -1) { 
            showingList.add(tempIndex, tempOldShowing);
            System.out.println("Error - Clash of show time");
        }
        System.out.println("Showing successfully edited");
    }

    public void deleteShowing(Showing oldShowing) {
        showingList.remove(oldShowing);
    }
    
}