package presentation;

import java.util.Scanner;

import entities.MovieGoer;
import controllers.BookingController;
import controllers.MovieController;
import controllers.MovieGoerController;

public class MovieGoerView {
    
    public void printMenu() {
        int option;
		Scanner sc = new Scanner(System.in);

        /*
         * MAIN MENU of the movie-goer application
         */

        // prompt user for login
        do {
            System.out.println(
                "\nMovie-Goer Menu\n" + 
                "------------\n" +
				"1. Log In\n" +
				"2. Create New Account\n" +
                "0. Back \n" +
                "------------"
			);

            System.out.print("Enter option: ");
            
            // reject non-integer input
            while (!sc.hasNextInt()) {
				System.out.println("Please input a number value.");
				sc.next();
			}
			
			option = sc.nextInt();
            sc.nextLine();
            switch(option) {
                case 1:
                    // prompt for customer number or email, then log in and get the associated movieGoer object
					int option2;

					do {
						System.out.println(
                            "\nLog In\n" +
                            "------------\n" +
                            "Please select login method: \n" +
                            "1. Email Address\n" +
                            "2. Mobile Number\n" +
                            "0. Back \n" +
                            "------------\n"
                        );
                        
                        System.out.println("Enter option: ");

                        while (!sc.hasNextInt()) {
                            System.out.println("Please input a number value.");
                            sc.next();
                        }

                        option2 = sc.nextInt();
                        sc.nextLine();

                        // temp account used to check for validity with the movieGoerList
                        MovieGoer movieGoerCheck = null;

                        switch(option2) {
                            case 1:
                                movieGoerCheck = null;

                                while (movieGoerCheck == null) {
                                    System.out.println("Input your email address: ");
                                    String emailInput = sc.nextLine();

                                    // check for valid login - if the MovieGoer exists in the movieGoerList
                                    if (MovieGoerController.getController().searchMovieGoerEmail(emailInput) != null) {
                                        movieGoerCheck = MovieGoerController.getController().searchMovieGoerEmail(emailInput);
                                    } else {
                                        
                                    }
                                }
                                
                                // set current account to the one used to log in - account is held by the controller
                                MovieGoerController.getController().setCurrentMovieGoer(movieGoerCheck);
                                break;
                            
                            case 2:
                                movieGoerCheck = null;

                                while (movieGoerCheck == null) {
                                    System.out.println("Input your phone number: ");
                                String numberInput = sc.nextLine();

                                    // check for valid login
                                    if (MovieGoerController.getController().searchMovieGoerNumber(numberInput) != null) {
                                        movieGoerCheck = MovieGoerController.getController().searchMovieGoerNumber(numberInput);
                                    }
                                }
                                
                                // set current account to the one used to log in
                                MovieGoerController.getController().setCurrentMovieGoer(movieGoerCheck);
                                break;

                            case 0:
                                System.out.println("Navigating back to movie-goer view.");
                                break;

                            default:
                                System.out.println("Please input an option from 1 to 2.");
                                break;
					    }

                    } while(option2 != 0);

                case 2:
                    // create movieGoer object 
                    MovieGoer newAccount = MovieGoerController.getController().createMovieGoerHelper();

                    // add the movieGoer object to the list and use this account for the current session
                    MovieGoerController.getController().createMovieGoer(newAccount);
                    MovieGoerController.getController().setCurrentMovieGoer(newAccount);

                case 0:
                    System.out.println("Navigating back to main application view.");
                    // reset the currently-loaded movieGoer account
                    MovieGoerController.getController().resetCurrentMovieGoer();

                    // save all the serializables in the controllers
                    MovieGoerController.saveData();
                    MovieController.saveData();
                    BookingController.saveData();
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;
            }
            
        } while(option != 0);

        /*
         * this menu is printed AFTER user has successfully logged in or created an account
         */ 
        do {
            System.out.println(
                "\nWelcome, " + MovieGoerController.getController().getCurrentMovieGoer().getMovieGoerName() + "!\n" + 
                "------------\n" +
				"1. View Booking History \n" +
				"2. View Top 5 Movies \n" +
				"3. View Movies (Login) \n" +
                "0. Back \n" +
                "------------"
			);

            System.out.print("Enter option: ");
            
            // reject non-integer input
            while (!sc.hasNextInt()) {
				System.out.println("Please input a number value.");
				sc.next();
			}
			
			option = sc.nextInt();
            sc.nextLine();
			switch(option) {
				case 1: 
                    System.out.println(
                        "\nViewing bookings... \n" +
                        "------------\n");

                    // pass the current movieGoer object to the BookingController
                    BookingController.getController().listBookingViaAccount(
                        MovieGoerController.getController().getCurrentMovieGoer());             

                case 2:
                    MovieController.getController().viewTop5();
                    break;
                
                // use the view movies method as an entry to book movies
                case 3:
                    MovieController.getController().printMovieListByStatus();
                    break;
                    
                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;

			}
        
        } while(option != 0 || MovieGoerController.getController().getCurrentMovieGoer() != null);
    }
}
