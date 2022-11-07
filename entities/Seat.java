package entities;

import java.io.Serializable;

public class Seat implements Serializable {

  public enum seatType {
    REGULAR, COUPLE, ELITE, ULTIMA, EMPTY
  }

  private int row;
  private int col;
  private String seatId;
  private seatType sType;
  private boolean occupied;

  // row and column are identifiers for a space, the seat ID is the identifier
  // a seat -- couple seats take up 2 spaces but share the same seat id
  Seat(int row, int col, seatType sType, String seatId, boolean occupied){
    this.row = row;
    this.col = col;
    this.seatId = seatId;
    this.sType = sType;
    this.occupied = false;
  }

  // getters

  public int getRow(){
    return this.row;
  }

  public int getCol(){
    return this.col;
  }

  public boolean getOccupancy(){
    return this.occupied;
  }

  public String getSeatId() {
    return this.seatId;
  }

  public seatType getSeatType() {
    return this.sType;
  }

  // setters

  public void setOccupancy(boolean occupancy){
    this.occupied = occupancy;
  }

  public void setSeatId(String id) {
    this.seatId = id;
  }

  public void setSeatType(seatType sType) {
    this.sType = sType;
  }
}