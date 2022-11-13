package controllers;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Arrays;

import entities.Seat;
import entities.Cinema;
import entities.Showing;
import entities.MovieGoer;
import utils.SerializeObjects;

/**
 * It's a class that holds all the settings for the cinema, such as ticket
 * prices, cinema prices,
 * holidays, discounts, etc
 */
public class SettingsController implements Serializable {
    private static HashMap<Seat.seatType, Double> ticketPrices; // save ticket prices for each seat type to file.
    private static String price_filepath = "ticketprices.ser";

    private static HashMap<Cinema.classType, Double> cinemaPrices; // save an addition price for each cinema to file.
    private static String cinema_filepath = "cinemaprices.ser";

    private static ArrayList<LocalDate> holidays; // save holiday dates to file.
    private static String holidays_filepath = "holidays.ser";

    private static HashMap<String, Double> discounts;
    private static String discounts_filepath = "discounts.ser";

    private static SettingsController controllerInstance = null;

    private static ArrayList<String> discountTypes = new ArrayList<String>(
            Arrays.asList("Wednesday", "Thursday", "Students", "Holiday", "Weekend", "Senior")); // any discounts are added here.

    // The code is creating a new instance of the SettingsController class.
    // loads a controller instance + ticketPrices, cinemaPrices, holidays from file.
    // if none found/null object, create new one and save it to file.
    @SuppressWarnings("unchecked")
    public static SettingsController getController() {
        if (controllerInstance == null) {
            controllerInstance = new SettingsController();
        }

        ticketPrices = (HashMap<Seat.seatType, Double>) SerializeObjects.loadData(price_filepath);
        holidays = (ArrayList<LocalDate>) SerializeObjects.loadData(holidays_filepath);
        cinemaPrices = (HashMap<Cinema.classType, Double>) SerializeObjects.loadData(cinema_filepath);
        discounts = (HashMap<String, Double>) SerializeObjects.loadData(discounts_filepath);

        if (ticketPrices == null) { // if there are no ticket prices, we must set a ticket price for each seat type.
            Scanner sc = new Scanner(System.in);
            System.out.println("No ticketPrices found; creating new file.");
            ticketPrices = new HashMap<Seat.seatType, Double>();

            for (Seat.seatType seat : Seat.seatType.values()) {
                System.out.println("Input a price for the seatType: " + seat);
                while (!sc.hasNextDouble()) {
                    System.out.println("Input a valid number.");
                    sc.next();
                }
                ticketPrices.put(seat, sc.nextDouble());
                sc.nextLine();
            }
            SerializeObjects.saveData(price_filepath, ticketPrices);
        }

        if (holidays == null) {
            System.out.println("No holidays found; creating new file.");
            holidays = new ArrayList<LocalDate>();
            SerializeObjects.saveData(holidays_filepath, holidays);
        }

        if (cinemaPrices == null) {
            Scanner sc = new Scanner(System.in);
            System.out.println("No ticketPrices found; creating new file.");
            cinemaPrices = new HashMap<Cinema.classType, Double>();

            for (Cinema.classType cinema : Cinema.classType.values()) {
                System.out.println("Input an addition price for the cinema type: " + cinema);
                while (!sc.hasNextDouble()) {
                    System.out.println("Input a valid number.");
                    sc.next();
                }
                cinemaPrices.put(cinema, sc.nextDouble());
                sc.nextLine();
            }
            SerializeObjects.saveData(cinema_filepath, cinemaPrices);
        }

        if (discounts == null) {
            Scanner sc = new Scanner(System.in);
            System.out.println("No discounts found; creating new file.");
            discounts = new HashMap<String, Double>();
            for (String i : discountTypes) {
                System.out.println("Enter a discount percentage (float number) for the discount: " + i);
                while (!sc.hasNextDouble()) {
                    System.out.println("Please enter valid float.");
                    sc.next();
                }
                discounts.put(i, sc.nextDouble());
                sc.nextLine();
            }
            SerializeObjects.saveData(discounts_filepath, discounts);
        }

        return controllerInstance;
    }

    public ArrayList<LocalDate> getHolidays() {
        return holidays;
    }

    /**
     * It prints all the settings/configs of the cinema
     */
    public void printSettings() {
        System.out.println("Printing all settings/configs \n------------------\n");
        System.out.println("Holidays: \n");
        for (LocalDate date : holidays)
            System.out.println(date);
        System.out.println("\n");

        System.out.println("Seat type prices: \n");
        for (Seat.seatType seat : Seat.seatType.values()) {
            System.out.println(seat + ": " + ticketPrices.get(seat));
        }
        System.out.println("\n");

        System.out.println("Cinema price additions: \n");
        for (Cinema.classType cinema : Cinema.classType.values()) {
            System.out.println(cinema + ": " + cinemaPrices.get(cinema));
        }
        System.out.println("\n");

        System.out.println("Discounts available: \n");
        for (String d : discountTypes) {
            System.out.println(d + ": " + discounts.get(d));
        }
        System.out.println("\n------------------\n");
    }

    /**
     * This function takes in a seat type, a showing, and a moviegoer, and returns
     * the price of the
     * ticket
     * depending on: seat type, cinema
     * type, and various discounts held in discountTypes.
     * 
     * @param seat      the type of seat (e.g. Standard, Premium, Platinum)
     * @param showing   the showing object
     * @param movieGoer MovieGoer object
     * @return The price of the ticket.
     */
    public double getPrice(Seat.seatType seat, Showing showing, MovieGoer movieGoer) {
        double price = ticketPrices.get(seat);
        price += cinemaPrices.get(showing.getShowingCinema().getClassType());
        LocalDateTime showingDateTime = showing.getShowtime();
        LocalDate showingDate = LocalDate.of(showingDateTime.getYear(), showingDateTime.getMonthValue(),
                showingDateTime.getDayOfMonth());

        int isHoliday = 0;
        for (LocalDate date : holidays) {
            if (date.isEqual(showingDate)) {
                System.out.println("("+(discounts.get("Holiday")*100)+"% Holiday Discount)");
                price *= discounts.get("Holiday");
                isHoliday = 1;
                break;
            }
        }

        if (showingDate.getDayOfWeek() == DayOfWeek.WEDNESDAY || showingDate.getDayOfWeek() == DayOfWeek.TUESDAY || showingDate.getDayOfWeek() == DayOfWeek.MONDAY){
            System.out.println("("+(discounts.get("Wednesday")*100)+"% Weekday Discount)");
            if (isHoliday == 0) price *=discounts.get("Wednesday");
        }
        if (showingDate.getDayOfWeek() == DayOfWeek.THURSDAY && isHoliday == 0) price *=discounts.get("Thursday");

        if (movieGoer.getMovieGoerAge() >= 65) {price *= discounts.get("Senior"); System.out.println("("+(discounts.get("Senior")*100)+"% Senior Discount)");}
        if (movieGoer.getMovieGoerAge() <= 18) {price *= discounts.get("Students"); System.out.println("("+(discounts.get("Students")*100)+"% Student Discount)");}

        return price;
    }

    /**
     * It takes in a year, month, and day, and adds it to the holidays list
     */
    public void addHoliday() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a year: ");
        while (!sc.hasNextInt()) {
            System.out.println("Enter a valid number.");
            sc.next();
        }
        int year = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter a month between 1 and 12: ");
        while (!sc.hasNextInt()) {
            System.out.println("Enter a valid number.");
            sc.next();
        }
        int month = sc.nextInt();
        if (month > 12) {
            System.out.println("Month was greater than 12; setting to 12.");
            month = 12;
        }
        if (month < 1) {
            System.out.println("Month is less than 1; setting to 1.");
            month = 1;
        }
        sc.nextLine();

        System.out.println("Enter a valid day for the month inputted: ");
        while (!sc.hasNextInt()) {
            System.out.println("Enter a valid number.");
            sc.next();
        }
        int day = sc.nextInt(); // i didn't check for valid day because it differs by month; just ensure a
                                // correct one is inputted.
        sc.nextLine();

        LocalDate newHoliday = LocalDate.of(year, month, day);
        holidays.add(newHoliday);
        System.out.println("Added new holiday: " + newHoliday);
        saveAllData();
        return;
    }

    /**
     * It deletes a holiday from the list of holidays by allowing the user to choose
     * from the holiday indexes
     */
    public void deleteHoliday() {
        Scanner sc = new Scanner(System.in);
        if (holidays.size() == 0) {
            System.out.println("No holidays added yet.");
            return;
        }
        System.out.println("Listing all current holidays... \n -------------------- \n");
        int i = 0;
        for (LocalDate date : holidays) {
            System.out.println((i + 1) + ") " + date);
            i++;
        }

        System.out.println("Enter index of the holiday you wish to delete: ");
        while (!sc.hasNextInt()) {
            System.out.println("Invalid option.");
            sc.next();
        }
        int index = sc.nextInt() - 1;
        sc.nextLine();
        if (index >= holidays.size()) {
            System.out.println("Invalid choice. Returning...");
            return;
        }
        System.out.println("Deleted: " + holidays.get(index));
        holidays.remove(index);
        saveAllData();
        return;
    }

    /**
     * It takes in a seat type and a new price, and updates the ticketPrices hashmap
     * with the new
     * price.
     */
    public void setNewPrice() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Current seat types: \n");
        for (Seat.seatType seattype : Seat.seatType.values())
            System.out.println(seattype);

        System.out.println("Enter a seat type to set new price for: ");
        String seatTypeName = sc.nextLine();
        int found = 0;

        for (Seat.seatType seattype : Seat.seatType.values()) {
            if (seattype.name().equals(seatTypeName)) {
                found = 1;
                System.out.println("Enter a new price for this seat type: ");
                while (!sc.hasNextDouble()) {
                    System.out.println("Please input a valid number.");
                    sc.next();
                }
                double newPrice = sc.nextDouble();
                sc.nextLine();
                ticketPrices.put(seattype, newPrice);
                System.out.println("Seat type" + seattype.name() + "has a new price " + newPrice + ".");
                break;
            }
        }
        if (found == 0)
            System.out.println("No such seat type was found.");
        saveAllData();
        return;
    }

    /**
     * It saves all the data in the static objects of this class to their respective
     * files.
     * Since this class contains 2 static objects, we use this method to save them
     * all at any time required.
     */
    public static void saveAllData() {
        SerializeObjects.saveData(price_filepath, ticketPrices);
        SerializeObjects.saveData(holidays_filepath, holidays);
        SerializeObjects.saveData(cinema_filepath, cinemaPrices);
        return;
    }
}