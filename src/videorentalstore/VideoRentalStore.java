package videorentalstore;



import videorentalstore.User.User;
import videorentalstore.database.Database;
import videorentalstore.gui.*;


/**
 *
 * @author bslater
 */
public class VideoRentalStore {

    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        Database rentalStore = new Database("movies.db");
        System.out.println("Database loaded\n");
        rentalStore.initDatabaseTables();
        System.out.println("Tables created");
        //rentalStore.findMoviesByTitle("Star Wars");
        //System.out.print("\n");
        rentalStore.findMoviesByTitle("of the");
        System.out.print("\n");
        rentalStore.findMoviesByDirector("Lucas");
        System.out.print("\n");
        rentalStore.browseMoviesByTitles();
        System.out.print("\n");
        rentalStore.browseMoviesByGenre("Adventure");
        System.out.println();
        rentalStore.browseDirectors();
        //TODO include actors, user ratings searches/browse options
        System.out.println("\nDone running");        
        
        User u = new User("Brian","Slater","acstache@gmail.com","password", "00/00/00" , "1234567891234567" , "09/15" , "55 Sideways St", "Town" , "CT" , "012345");
        
        rentalStore.addUsertoDB(u);
        
        rentalStore.searchUserinDBbyID(0);
        rentalStore.searchUserinDBbyLastName("Acheson");
        rentalStore.searchUserinDBbyID(2);
        
        
        //rentalStore.deleteUserbyID(1);
        
        
        //rentalStore.deleteUser("Brian", "Slater");
        
        
        new Account_SignIn(rentalStore).setVisible(true);
        
       
    }
}
