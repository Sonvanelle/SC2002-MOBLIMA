package presentation;

import java.util.ArrayList;
import java.util.Scanner;

import entities.Movie;
import entities.MovieGoer;
import entities.showingStatus;
import controllers.BookingController;
import controllers.MovieController;

public class MovieGoerView {
    private MovieGoer movieGoer;
    
    public void printMenu() {
        int option;
		Scanner sc = new Scanner(System.in);

        /*
         * MAIN MENU of the movie-goer application
         */

        do {
            System.out.println(
				"1. View Booking History \n" +
				"2. View Top 5 Movies \n" +
				"3. View movies \n" +
                "4. Make new booking \n" + 
                "0. Back"
			);

            System.out.print("Enter option: ");
            
            // reject non-integer input
            while (!sc.hasNextInt()) {
				System.out.println("Please input a number value.");
				sc.next();
			}
			
			option = sc.nextInt();
			switch(option) {
				case 1: 
					// prompt for customer number or email, then seatch for booking list
					
					int option2;

					do {
						System.out.println(
                            "Please select login method: \n" +
                            "1. Email Address\n" +
                            "2. Mobile Number\n" +
                            "0. Back"
                        );
                        
                        System.out.println("Enter option: ");

                        while (!sc.hasNextInt()) {
                            System.out.println("Please input a number value.");
                            sc.next();
                        }

                        option2 = sc.nextInt();

                        switch(option2) {
                            case 1:
                                BookingController.getController().listBookingViaEmail(movieGoer.getEmailAddress());
                                break;
                            
                            case 2:
                                BookingController.getController().listBookingViaNumber(movieGoer.getMovieGoerNumber());
                                break;

                            case 0:
                                System.out.println("Navigating back to main application view.");
                                break;

                            default:
                                System.out.println("Please input an option from 1 to 3.");
                                break;
					    }
                        

                    } while(option2 != 0);
                    break;

                case 2:
                    MovieController.getController().viewTop5();
                    break;
                
                // use the view movies method as an entry to book movies
                case 3:
                    MovieController.getController().printMovieListByStatus();

                case 4:
                    /* 
                    MovieController.getController().printMovieList();
                    System.out.println("Enter which movie you would like to watch: ");
                    while (!sc.hasNextInt()){
                        System.out.println("Please input number value."); sc.next();
                    }
                    int option3 = sc.nextInt();
                    MovieController.getController().getMovieList().get(option3-1);
                    
                    //TO DO
                    break; 
                    */

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;

			}
        
        } while(option != 0);
    }
}
