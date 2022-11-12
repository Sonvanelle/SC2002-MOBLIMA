package controllers;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import entities.Seat;
import entities.Cinema;
import entities.Showing;

public class SettingsController implements Serializable{
    private static HashMap<Seat.seatType, Double> ticketPrices; //save ticket prices for each seat type to file.
    private static String price_filepath = "ticketprices.ser";

    private static HashMap<Cinema.classType, Double> cinemaPrices; //save an addition price for each cinema to file. (normal cinema should be 0)
    private static String cinema_filepath = "cinemaprices.ser";

    private static ArrayList<LocalDate> holidays; //save holiday dates to file.
    private static String holidays_filepath = "holidays.ser";

    private static SettingsController controllerInstance = null;
    private static double holidayDiscount = 0.8; //discount to apply when the date is a holiday.


    //loads a controller instance + ticketPrices, cinemaPrices, holidays from file. if none found/null object, create new one and save it to file.
    @SuppressWarnings("unchecked")
    public static SettingsController getController() {
        if (controllerInstance == null) {
            controllerInstance = new SettingsController();
        }

        ticketPrices = (HashMap<Seat.seatType, Double>)loadData(price_filepath);
        holidays = (ArrayList<LocalDate>)loadData(holidays_filepath);
        cinemaPrices = (HashMap<Cinema.classType, Double>)loadData(cinema_filepath);

        if (ticketPrices==null){ //if there are no ticket prices, we must set a ticket price for each seat type.
            Scanner sc = new Scanner(System.in);
            System.out.println("No ticketPrices found; creating new file.");
            ticketPrices = new HashMap<Seat.seatType, Double>();

            for (Seat.seatType seat : Seat.seatType.values()){
                System.out.println("Input a price for the seatType: " + seat);
                while (!sc.hasNextDouble()){System.out.println("Input a valid number."); sc.next();}
                ticketPrices.put(seat, sc.nextDouble());
                sc.nextLine();
            }
            saveData(ticketPrices, price_filepath);
        }

        if (holidays==null){
            System.out.println("No holidays found; creating new file.");
            holidays = new ArrayList<LocalDate>();
            saveData(holidays, holidays_filepath);
        }
        
        if (cinemaPrices==null){
            Scanner sc = new Scanner(System.in);
            System.out.println("No ticketPrices found; creating new file.");
            cinemaPrices = new HashMap<Cinema.classType, Double>();

            for (Cinema.classType cinema : Cinema.classType.values()){
                System.out.println("Input an addition price for the cinema type: ");
                while (!sc.hasNextDouble()){System.out.println("Input a valid number."); sc.next();}
                cinemaPrices.put(cinema, sc.nextDouble());
                sc.nextLine();
            }
            saveData(cinemaPrices, cinema_filepath);
        }
        return controllerInstance;
    }


    //getters
    public ArrayList<LocalDate> getHolidays(){
        return holidays;
    }

    public double getPrice(Seat.seatType seat, Showing showing){
        double price = ticketPrices.get(seat);
        price += cinemaPrices.get(showing.getShowingCinema().getClassType());
        LocalDateTime showingDateTime = showing.getShowtime();
        LocalDate showingDate = LocalDate.of(showingDateTime.getYear(), showingDateTime.getMonthValue(), showingDateTime.getDayOfMonth());
        for (LocalDate date : holidays){
            if (date.isEqual(showingDate)) {price *= holidayDiscount; break;}
        }
        return price;
    }
    
    public void addHoliday(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a year: ");
        while (!sc.hasNextInt()) {System.out.println("Enter a valid number."); sc.next();}
        int year = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter a month between 1 and 12: ");
        while (!sc.hasNextInt()) {System.out.println("Enter a valid number."); sc.next();}
        int month = sc.nextInt();
        if (month > 12) {System.out.println("Month was greater than 12; setting to 12."); month = 12;}
        if (month < 1) {System.out.println("Month is less than 1; setting to 1."); month = 1;}
        sc.nextLine();

        System.out.println("Enter a VALID day for the month inputted (likely between 1 and 31): ");
        while (!sc.hasNextInt()) {System.out.println("Enter a valid number."); sc.next();}
        int day = sc.nextInt(); // i didn't check for valid day because it differs by month; just ensure a correct one is inputted.
        sc.nextLine();

        LocalDate newHoliday = LocalDate.of(year, month, day);
        holidays.add(newHoliday);
        System.out.println("Added new holiday: " + newHoliday);
        return;
    }


    public void deleteHoliday(){
        Scanner sc = new Scanner(System.in);
        if (holidays.size()==0) {System.out.println("No holidays added yet."); return;}
        System.out.println("Listing all current holidays... \n -------------------- \n");
        int i=0;
        for (LocalDate date : holidays){
            System.out.println((i+1) + ") " + date); i++;
        }

        System.out.println("Enter index of the holiday you wish to delete: ");
        while (!sc.hasNextInt()){System.out.println("Invalid option."); sc.next();}
        int index = sc.nextInt() -1;
        sc.nextLine();
        if (index >= holidays.size()){
            System.out.println("Invalid choice. Returning...");
            return;
        }
        System.out.println("Deleted: " + holidays.get(index));
        holidays.remove(index);
        return;
    }


    public void setNewPrice(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Current seat types: \n");
        for (Seat.seatType seattype : Seat.seatType.values()) System.out.println(seattype);

        System.out.println("Enter a seat type to set new price for: ");
        String seatTypeName = sc.nextLine(); 
        int found = 0;

        for (Seat.seatType seattype : Seat.seatType.values()){
            if (seattype.name() == seatTypeName){
                found = 1;
                System.out.println("Enter a new price for this seat type: ");
                while (!sc.hasNextDouble()){System.out.println("Please input a valid number."); sc.next();}
                double newPrice = sc.nextDouble();
                sc.nextLine();
                ticketPrices.put(seattype, newPrice);
                System.out.println("Seat type" + seattype.name() + "has a new price " + newPrice + ".");
                break;
            }
        }
        if (found==0) System.out.println("No such seat type was found.");
        return;
    }







    public static void saveAllData(){ //since this class contains 2 static objects, we use this method to save them all at any time required.
        saveData(ticketPrices, price_filepath);
        saveData(holidays, holidays_filepath);
        saveData(cinemaPrices, cinema_filepath);
        return;
    }


    //serializing to/from files
    public static void saveData(Object obj, String fpath){  
        try {
            FileOutputStream fileOut = new FileOutputStream(fpath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        
            objectOut.writeObject(obj);
            objectOut.close();
        } catch (Exception e) {
            System.out.println("Error saving file in SettingsController: " + e);
        }
    }

    public static Object loadData(String fpath){
        try{
            FileInputStream fileIn = new FileInputStream(fpath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();
            objectIn.close();
            return obj;
        } catch (Exception e) {
            System.out.println("Error loading file in SettingsController: " + e);
            return null;
        }        
    }



}