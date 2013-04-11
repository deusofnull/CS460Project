package videorentalstore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Collection;


import videorentalstore.User.User;

import videorentalstore.movies.Movie;

public class Database {
    private Connection conn;
    private Statement stmt;
    private String DbName, DbUrl;
    private String Jdbc = "jdbc:sqlite";
    private int timeout = 30;
    private Collection<Movie> movies;
    private Collection<User> users;
    
    public Database(String DbName) throws Exception {
        // register the driver 
        String sDriverName = "org.sqlite.JDBC";
        Class.forName(sDriverName);
        
        this.DbName = DbName;
        DbUrl = Jdbc + ":" + this.DbName;
        
        this.conn = DriverManager.getConnection(DbUrl);
        
        //createMovieObjects(); no actors column in movies table
    }
    
    public void initDatabaseTables() throws Exception {
        executeUpdate("CREATE TABLE \"customer\" (\"customerId\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , \"firstName\" VARCHAR NOT NULL ,"
                + " \"lastName\" VARCHAR NOT NULL , \"email\" VARCHAR NOT NULL , \"password\" VARCHAR NOT NULL , \"birthday\" VARCHAR, \"creditCardNum\" INTEGER, "
                + "\"creditCardExpDate\" VARCHAR, \"address\" VARCHAR, \"city\" VARCHAR, \"state\" VARCHAR, \"zipcode\" INTEGER)");
        executeUpdate("CREATE TABLE \"movies\" (\"movieId\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE , \"title\" VARCHAR NOT NULL , "
                + "\"genre\" VARCHAR NOT NULL , \"director\" VARCHAR NOT NULL , \"MPAA\" VARCHAR NOT NULL , \"userRating\" INTEGER NOT NULL )");
        executeUpdate("CREATE TABLE \"rentals\" (\"rentalID\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE ,\"customerID\" INTEGER NOT NULL , "
                + "\"movieID\" INTEGER NOT NULL ,\"timeRented\" DATETIME NOT NULL DEFAULT (CURRENT_DATE) ,\"timeDue\" DATETIME NOT NULL DEFAULT (CURRENT_DATE) )");
    }
    
    private void createMovieObjects() throws Exception {
        stmt = conn.createStatement();
        stmt.setQueryTimeout(timeout);
        ResultSet rs = stmt.executeQuery("SELECT * FROM movies");
        while(rs.next()) {
            movies.add(new Movie(rs.getInt("movieId"), rs.getString("title"), rs.getString("director"), rs.getString("actors"), rs.getInt("userRating"), rs.getString("MPAA")));
        }
    }
    
    private void executeUpdate(String update) throws Exception {
        stmt = conn.createStatement();
        stmt.setQueryTimeout(timeout);
        stmt.executeUpdate(update);
    }
    
    private ResultSet executeQuery(String query) throws Exception {
        stmt = conn.createStatement();
        stmt.setQueryTimeout(timeout);
        return stmt.executeQuery(query);
    }
    
    
    /***************************************
     *                                     *
     *         Movies Table Stuff          *
     *                                     *
     ***************************************/
    
    
    public void addMovieToDB(Movie movie) {
        //add movie into database
    }
    
    public void remMovieFromDB(Movie movie) {
        //find movie in database, remove it
    }
    
    public void editMovieInDB(Movie movie) {
        //find movie in database, compare "changes", if different, make changes
    }
    
    
    public void findMoviesByTitle(String title) {
        String search = "SELECT title FROM movies WHERE title LIKE '%" + title + "%'";
        try {
            ResultSet movies = executeQuery(search);
            while (movies.next()) {
                String movie = movies.getString("title");
                System.out.println(movie);
                //TODO make this do something else, possibly add to a collection of Movie Objects
            }
            try {
                movies.close();
            }
            catch (Exception ignore) {}
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void findMoviesByDirector(String director) {
        String search = "SELECT DISTINCT director FROM movies WHERE director LIKE '%" + director + "%'";
        try {
            ResultSet directors = executeQuery(search);
            while (directors.next()) {
                String dirName = directors.getString("director");
                System.out.println(dirName);
                System.out.println(search);
                //TODO make this do something else, possibly add to a collection of Movie Objects
            }
            try {
                directors.close();
            }
            catch (Exception ignore) {}
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    /***************************************
     *                                     *
     *        Customer Table Stuff         *
     *                                     *
     ***************************************/
    public void addUsertoDB(User user){
        //Statement stmt;
        boolean userAddRES;
        try {
            String insert = "INSERT INTO customer (customerID , lastName , firstName , email, password) VALUES ( " +  user.getCustomerID() + " , " + " ' " + user.getLastName() + " ' " +
                " , " + " ' " + user.getFirstName() + " ' " +  " , " + " ' " +  user.getEmail() + " ' " + " , " + " ' " + user.getPassword() + " ' " + " ) ";
            System.out.println(insert);
            stmt = conn.createStatement();
            userAddRES = stmt.execute(insert);
        }
        catch (Exception e){
            System.out.println("Error adding user to Database: " + e.toString());
            try {
                conn.close();
            }
            catch (Exception e1){
            }
        }
    }
    
    public void searchUserinDBbyID(int ID){
        ResultSet searchUserRES;
        try {
            String search = "SELECT * FROM customer WHERE customerID = " + ID ;
            stmt = conn.createStatement();
            searchUserRES = stmt.executeQuery(search);
            while (searchUserRES.next()) {
                
                String user = "First Name: " + searchUserRES.getString("firstName") + "Last Name: " +searchUserRES.getString("lastName") + 
                        "Email Address" + searchUserRES.getString("email") + "Password:  " + searchUserRES.getString("password");
                System.out.println("User Found at searched ID " + ID + ": " + user);
            }    
        }
        catch (Exception e){
            
            try {
                
                conn.close();
            }
            catch (Exception e1){
            }
            
        }
        
    }
   
    public void searchUserinDBbyLastName(String lastName){
        ResultSet searchUserLastNameRES;
        try {
            String search = "SELECT * FROM customer WHERE lastName = " + " ' " + lastName + " ' " ;
            stmt = conn.createStatement();
            searchUserLastNameRES = stmt.executeQuery(search);
            while (searchUserLastNameRES.next()) {
                
                String user = "First Name: " + searchUserLastNameRES.getString("firstName") + "Last Name: " +searchUserLastNameRES.getString("lastName") + 
                        "Email Address" + searchUserLastNameRES.getString("email") + "Password:  " + searchUserLastNameRES.getString("password");
                System.out.println("User Found at Last Name: " + lastName + ": " + user);
            }    
        }
        catch (Exception e){
            
            try {
                
                conn.close();
            }
            catch (Exception e1){
            }
            
        }
        
    }
   
    //doesnt work yet
    public void displayTable(){
        ResultSet customerTable ; 
        try {
            String wholeTable = "SELECT * FROM customer";
            stmt = conn.createStatement();
            customerTable = stmt.executeQuery(wholeTable);
            
        }
        
        catch (Exception e){
            
            try {
                
                conn.close();
            }
            catch (Exception e1){
            }
            
        }
    }
     
    
     
    public void deleteUser(String firstName , String lastName){
        boolean userDeleteRES;
        try {
            String delete = "DELETE FROM customer WHERE lastName = " + " ' " + lastName + " ' " + "AND firstName = " + " ' " + firstName + " ' ";
            System.out.println(delete);
            stmt = conn.createStatement();
            userDeleteRES = stmt.execute(delete);
            System.out.println("User: " + firstName + lastName + " was deleted from the database.");
        }
        catch (Exception e){
            System.out.println("Error deleting user from Database: " + e.toString());
            try {
                conn.close();
            }
            catch (Exception e1){
            }
        }
    }
    
    /***************************************
     *                                     *
     *         Rental Table Stuff          *
     *                                     *
     ***************************************/
}