
package controllers;

/**
 * Enum for the showing status of the Movie, which determines if Bookings
 * can be made for it. Movies that are END_OF_SHOWING do not appear to moviegoers.
 */
public enum showingStatus {
    COMING_SOON, 
    PREVIEW, 
    NOW_SHOWING, 
    END_OF_SHOWING;
    
}
