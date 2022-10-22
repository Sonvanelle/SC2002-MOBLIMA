package entities;

import java.io.Serializable;

public class Seat implements Serializable {
  private int row;
  private int col;
  private boolean occupied;

  Seat(int row, int col, boolean occupied){
    this.row = row;
    this.col = col;
    this.occupied = false;
  }

  public int getRow(){
    return this.row;
  }

  public int getCol(){
    return this.col;
  }

  public boolean getOccupancy(){
    return this.occupied;
  }

  public void setOccupancy(boolean occupancy){
    this.occupied = occupancy;
  }

}