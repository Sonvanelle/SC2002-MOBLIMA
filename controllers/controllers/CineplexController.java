package controllers;

import entities.Cineplex;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;

public class CineplexController implements Serializable{

    private ArrayList<Cineplex> cineplexList; //this is where cineplexes are actually held.

    // holds an instance of the controller 
    private static CineplexController controllerInstance = null;
    
    // methods
    /*
     * Instantiate a controller object when called.
     */
    public static CineplexController getController() {
        if (controllerInstance == null) {
            controllerInstance = new CineplexController();
        }
        return controllerInstance;
    }

    public ArrayList<Cineplex> getCineplexList(){
        return cineplexList;
    }
}