package awesomecars.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import awesomecars.beans.Dealership;
import awesomecars.persistence.IDatabaseAdapter;
import awesomecars.persistence.MySQLDatabaseAdapter;

/**
 * This class does all the work associated with managing Dealership objects.
 * 
 * @author Travis
 *
 */
public final class DealershipRepository {
	
    /** Reference to the database adapter. */
    private static IDatabaseAdapter databaseInstance;
    
    /** Default constructor must be private. */
    private DealershipRepository() { };
    
    /**
     * Queries the database for all dealership information. This is
     * used to generate the Locations page.
     * @return ArrayList of dealership information
     */
	public static List<Dealership> getAllStoreDetails() {

		// create container for store results
		List<Dealership> stores = new ArrayList<Dealership>();
		JsonArray results = null;

		try {
			results = ((MySQLDatabaseAdapter) databaseInstance)
			        .callStoredProcedure("GetAllStoreDetails");
			
			int len = results.size();
		        
		    for (int i = 0; i < len; i++) {
		        JsonObject obj = results.getJsonObject(i);
		        Dealership d = new Dealership(obj);
		        stores.add(d);
		    }   // end for
		} catch (SQLException e) {
			System.out.println("Error obtaining Store Details: " 
			        + e.getMessage());
		}

		return stores;
	}	// end getAllStoreDetails(...)
	
	/**
	 * Initializes the DealershipRepository with the provided
	 * database adapter.
	 * @param db the database adapter to attach to repository
	 */
	public static void initRepository(final IDatabaseAdapter db) { 
	    databaseInstance = db; 
	}
	
}	// end class DealershipRepository