package presentation;

import java.util.Scanner;

public class AppView {
    public static void main(String[] args) {
		int option;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println(
				"1. Movie-Goer \n" +
				"2. Staff \n" +
				"3. Quit"
			);
			System.out.println("Enter option: ");

			while (!sc.hasNextInt()) {
				System.out.println("Please input a number value.");
				sc.next();
			}
			
			option = sc.nextInt();
			switch(option) {
				case 1: 
					MovieGoerView customerview = new MovieGoerView();
					customerview.printMenu();
					break;
				case 2:
					while(true){
						System.out.println("Enter admin password: ");
						String password = sc.nextLine();
						String adminpass;

						//get admin password from txt file

						if (password==adminpass){
							System.out.println("Logging in... Entering admin mode.");
							AdminView adminview = new AdminView();
							adminview.printMenu();
						}

						else{
							System.out.println("Wrong password. Type 1 to try again, 2 to enter as Movie-Goer.");
							int pwoption = sc.nextInt();
							if (pwoption>2 | pwoption<1){
								System.out.println("Invalid; trying login again.");
								continue;
							}
							else if (pwoption==1){continue;}
							else{
								MovieGoerView custview = new MovieGoerView();
								customerview.printMenu();
								break;
								}
						}	
					break;
					}
					
					
			}
		} while(option!=3);

		System.out.println("Exiting...");
	}
}
