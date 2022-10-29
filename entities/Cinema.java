package entities;
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


    //Mutators
    public void setCinemaID(int cinemaID){
        this.cinemaID = cinemaID;
    }

    public void setClassType(classType classType) {
        this.classtype = classType;
    }
    
    public void setSeatingPlan(ArrayList<Seat> seatingPlan){
        this.seatingPlan = seatingPlan;
    }
    
    public void setShowingList(ArrayList<Showing> showingList) {
        this.showingList = showingList;
    }
    
    public void setColumns(int column){
        this.column = column;
    }
    
}