import entities.MovieGoer;
import entities.Movie;
import controllers.BookingController;
import controllers.MovieController;
import entities.showingStatus;
import java.util.Scanner;
import java.util.ArrayList;

public class AdminView{

    public void printMenu(){
        Scanner input = new Scanner(System.in);
        MovieController moviecontroller = new MovieController();
        int cont = 1;
        while(cont){
            System.out.println("1. Configure system settings \n" +
                                "2. Create a Movie Listing \n" +
                                "3. Update a Movie Listing \n" +
                                "4. Remove a Movie Listing \n" +
                                "5. View top 5 movies by Sales/Ratings \n +
                                6. Return to main menu"); 

            int option = input.nextInt();
            switch(option){
                case 1:
                    //write code for getting/setting system settings from a txt file
                    break;
                

                case 2:  //create movie listing
                    String movieName;
                    long movieMin;
                    showingStatus val;
                    String synopsis;
                    String director;
                    ArrayList<String> cast = new ArrayList<String>();

                    System.out.println("Enter movie name: "); movieName = input.nextLine();
                    System.out.println("Enter length of the movie in minutes: "); movieMin = input.nextLong();
                    System.out.println("Enter showing status: \n"+ 
                                        "1. COMING SOON \n" +
                                        "2. PREVIEW \n" +
                                        "3. NOW SHOWING \n" +
                                        "4. END OF SHOWING \n" );
                    int showstatus = input.nextInt();
                    switch (showstatus){
                        case 1: val = showingStatus.COMING_SOON; break;
                        case 2: val = showingStatus.PREVIEW; break;
                        case 3: val = showingStatus.NOW_SHOWING; break;
                        case 4: val = showingStatus.END_OF_SHOWING; break;
                        default: System.out.println("Invalid option; defaulting to COMING SOON."); val = showingStatus.COMING_SOON; break;
                    }
                    System.out.println("Enter movie synopsis: "); synopsis = input.nextLine();
                    System.out.println("Enter movie director: "); director = input.nextLine();
                    while(true){
                        System.out.println("Enter movie cast (format: Actor - Character); enter STOP to stop: ");
                        String castName = input.nextLine();
                        if (castName=="STOP"){break;}
                        cast.add(castName);
                    }
                    moviecontroller.createMovie(movieName, movieMin, val, synopsis, director, cast);
                    break;
                

                case 3: //update movie listing
                    System.out.println("Printing all current movie names...");
                    ArrayList<Movie> movieList = moviecontroller.getMovieList();
                    for (int i=0; i<movieList.size(); i++){
                        System.out.println("Movie Name: " + movieList.get(i).getMovieName());
                    }
                    System.out.println("Enter the movie name to edit: ");
                    String name = input.nextLine();
                    for (int i=0; i<movieList.size(); i++){
                        if (movieList.get(i).getMovieName() == name){

                        }
                    }


                    break;
                

                case 4: //remove a movie listing.
                    break;
                

                case 5: //view top 5 movies by sales/ratings
                    break;
                
                
                case 6: //return to main menu.
                    System.out.println("Exiting to main menu...");
                    moviecontroller.SaveData()
                    cont=0;
                    break;

                
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }

    }

