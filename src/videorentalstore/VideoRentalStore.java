package videorentalstore;

import videorentalstore.database.Database;
import videorentalstore.User.User;

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
        //rentalStore.initDatabaseTables();
        //System.out.println("Tables created");
        
        rentalStore.findMoviesByTitle("Star Wars");
        System.out.print("\n");
        rentalStore.findMoviesByTitle("of the");
        System.out.print("\n");
        rentalStore.findMoviesByDirector("ge Lu");
        System.out.println("\nDone running");
        
        User u = new User(1,"Will","Acheson","willyach07","password");
        
        //rentalStore.addUsertoDB(u);
        
        //rentalStore.searchUserinDBbyID(50);
        rentalStore.searchUserinDBbyID(1);
        
        rentalStore.searchUserinDBbyLastName("Acheson");
        
        rentalStore.displayTable();
        //rentalStore.deleteUser("Will", "Acheson");
        
        
        
        
    }
}
