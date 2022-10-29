package presentation;

import java.util.Scanner;

import entities.MovieGoer;
import controllers.BookingController;
import controllers.MovieController;

public class MovieGoerView {
    private MovieGoer movieGoer;
    
    public void printMenu() {
        int option;
		Scanner sc = new Scanner(System.in);

        do {
            System.out.println(
				"1. View Booking History \n" +
				"2. View Top 5 Movies \n" +
				"3. View all movies \n" +
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
                                BookingController.getController().listBookingViaEmail();
                                break;
                            
                            case 2:
                                BookingController.getController().listBookingViaNumber();
                                break;

                            case 0:
                                System.out.println("Navigating back to main application view.");
                                break;

                            default:
                                System.out.println("Please input an option from 1 to 3.");
                                break;
					    }

                } while(option2 != 0);

                case 2:
                    MovieController.getController().

                case 3:

                case 4:

                default:

			}
        
        } while(option != 0);
    }
}
