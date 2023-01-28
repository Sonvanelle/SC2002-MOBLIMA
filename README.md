SC2002-MOBLIMA
========

Movie Booking and Listing Management Application (MOBLIMA) provides a simple interface approximating a movie theatre chain's front and back-facing software.
This project was part of NTU's Computer Science SC2002 Object-Oriented Design & Programming module, allowing for demonstration of proficiency in different OOP concepts.

#

It supports admin operations such as:
- Creating and modifying theatre layouts and seating
- Defining ticket prices for different seat categories and holidays
- Creating and updating movie listings, showings
- Viewing movie sales figures and Top 5 metrics

Movie-goers also may create accounts on the application to:
- View movies by showing status
- Make bookings for movie showings
- Leave reviews

An example of the command-line user interface:

    Choose a Cineplex: 
    ORCHARD
    -----
    List of Showings at ORCHARD
    1. Top Gun: Maverick              | 14-11-2022 02:00:00 - 03:40:00
    2. Top Gun: Maverick              | 14-11-2022 04:00:00 - 05:40:00
    3. Top Gun: Maverick              | 14-11-2022 08:00:00 - 09:40:00
    4. Top Gun: Maverick              | 15-11-2022 00:00:00 - 01:40:00
    -------
    Choose a showing: 
    1
    
                  ----------SCREEN----------
    A [x][ ][ ][ ][ ][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] A
    B [ ][ ][ ][ ][ ][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] B
    C [ ][ ][ ][ ][ ][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] C
    D [ ][ ][ ][ ][x][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] D
    E [ ][ ][ ][ ][ ][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] E
    F [ ][ ][ ][ ][x][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] F
    G [x][ ][ ][ ][ ][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] G
    H [x][ ][ ][ ][ ][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] H
    I [ ][ ][ ][ ][ ][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] I
    J [ ][ ][ ][ ][ ][ ][ ][ ]  [ ][ ][ ][ ][ ][ ][ ][ ] J

    Enter seat position

Application Setup
--------
The application may be started by navigating to `presentation/AppView.java`, and running that driver class.

    MOBLIMA 
    ------------
    1. Movie-Goer 
    2. Staff 
    3. Quit 
    ------------
    Enter option: 


Admin Functions
------------

To access the admin/staff menu, enter `2` when prompted. The class `presentation/AdminView.java` will contain all the admin functions. Accessing the menu will require an account, with the password pre-set as `test`.
Once logged in and in the main menu, enter `9` to exit and log out.

    Enter admin password:
    test
    Logging in... Entering admin mode.

    Admin View 
    ------------
    1. Configure system settings 
    2. Create a Movie Listing 
    3. Update a Movie Listing 
    4. View top 5 movies by Sales/Ratings 
    5. View showings 
    6. Add showing 
    7. Edit showing 
    8. Delete showing 
    9. Return to main menu 
    ------------

Movie-Goer Functions
------------

To access the movie-goer menu, enter `1` when prompted. The class `presentation/MovieGoerView.java` will contain all the movie-goer functions.
A movie-goer account must be used to log in and access the main menu. 

    Movie-Goer Menu
    ------------
    1. Log In
    2. Create New Account
    0. Back 
    ------------
    
After entering `2` to create a new account, the user will be prompted for their name, email, phone number and age. The values are checked against existing accounts to prevent key conflicts.

    Creating new account. 
    We will require a valid name, email, mobile number and age:
    Enter your name: Testperson
    Enter your email address: test@testing.mail
    Enter your mobile number: 98889888
    Enter your age: 24
    New Account Details:

    Name:Testperson
    Number:98889888
    Email:test@testing.mail
    
Logging in to a movie-goer account requires entering the associated email address. The movie-goer will be presented with the main menu, entering `0` will log them out and return to the previous interface.

    Input your email address: jon@gmail.com
    Searching for account..

    Welcome, Jonathan!
    ------------
    1. View Booking History 
    2. View Top 5 Movies 
    3. View Movies
    0. Back 
    ------------
    Enter option: 

Resetting pre-defined data
------------
The project includes several admin and movie-goer accounts already entered into the system for testing purposes, along with dummy movies and showings.
These pre-defined data entries may be safely removed by deleting their corresponding serialisable files with the `.ser` extension, found in the main directory.

When there is no serialisable file present for an entity, the application will handle creation and population of a new file whenever the entity is created in the user interface.


Contribute
----------
- Source Code: https://github.com/Sonvanelle/SC2002-MOBLIMA.git
