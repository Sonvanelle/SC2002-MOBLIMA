import entities.Movie;
import entities.showingStatus;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class MovieController implements Serializable{
    private ArrayList<Movie> movieList;

    public void createMovie(String movieName, long movieHours, long movieMin, showingStatus val, String synopsis, String director, ArrayList<String> cast)
    {
        Movie movie = new Movie(movieName, movieHours, movieMin, val, synopsis, director, cast);
        movieList.add(movie);
    }
    
    
    
    
    
    
    

    
    
    
    public void loadData(){

    }

    public void saveData(){

    }
}