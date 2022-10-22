package entities;

import java.io.Serializable;

public class Person implements Serializable {
    private String movieGoerName;
    private String emailAddress;
    private String movieGoerNumber;

    public Person(String name, String email, String number){
        this.movieGoerName = name;
        this.emailAddress = email;
        this.movieGoerNumber = number;
    }

    public String getMovieGoerName(){
        return this.movieGoerName;
    }

    public String getEmailAddress(){
        return this.emailAddress;
    }

    public String getMovieGoerNumber(){
        return this.movieGoerNumber;
    }

    
}

