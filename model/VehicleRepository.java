package awesomecars.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;

import awesomecars.beans.AdvancedSearch;
import awesomecars.beans.Vehicle;
import awesomecars.persistence.IDatabaseAdapter;
import awesomecars.persistence.MySQLDatabaseAdapter;

/**
 * This class receives requests from the View via the ApplicationController and
 * does the majority of the work. This class performs all work associated with
 * retrieving and manipulating vehicles from the database.
 * 
 * @author Travis
 *
 */
public final class VehicleRepository {

    /** Reference to the database adapter. */ 
     private static IDatabaseAdapter databaseInstance;
    
    /**
     * Utility class should not have a public or default constructor.
     */
    private VehicleRepository() { 
        super();
    }
    
    /** 
     * Queries the database for the search phrase entered into the basic
     * search window and returns the results as an ArrayList of Vehicles.
     * 
     * @param searchString String of keywords to query the database with
     * @return ArrayList of vehicle results
     */
	public static List<Vehicle> searchVehiclesBasic(
	        final String searchString) {

		// create container for vehicle results
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		JsonArray results = null;

		try {
			results = ((MySQLDatabaseAdapter) databaseInstance)
			        .callStoredProcedure("BasicSearchQuery", searchString);
			vehicles = makeVehicleList(results);
		} catch (SQLException e) {
			System.out.println("Error obtaining BasicSearchResults: " 
			        + e.getMessage());
		}

		return vehicles;
	}	// end searchVehiclesBasic(...)
	
    /**
     * Queries the database based a number of filter criteria and returns
     * results in an ArrayList.
     * 
     * @param request HTTP servlet request containing search parameters
     * @return ArrayList of vehicle results
     */
    public static List<Vehicle> searchVehiclesAdvanced(
            final HttpServletRequest request) {

        // create container for vehicle results
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        AdvancedSearch search = new AdvancedSearch(request);
        JsonArray results = null;

        try {
            results = ((MySQLDatabaseAdapter) databaseInstance)
                    .queryDatabase(search.toString());
            vehicles = makeVehicleList(results);
        } catch (SQLException e) {
            System.out.println("Error obtaining AdvancedSearchResults: "
                    + e.getMessage());
        }
        
        return vehicles;
    }   // end searchVehiclesAdvanced(...)

	/** 
	 * Queries the database for all cars of a specific model. Returns results
	 * as an ArrayList of vehicles.
	 * 
	 * @param model Vehicle model to search for
	 * @return ArrayList of vehicle results
	 */
	public static List<Vehicle> searchVehiclesCategory(
	        final String model) {

		// create container for vehicle results
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
 		JsonArray results = null;
		try {		
			results = ((MySQLDatabaseAdapter) databaseInstance)
			        .callStoredProcedure("CategorySearchQuery", model);
			vehicles = makeVehicleList(results);
		} catch (SQLException e) {
			System.out.println("Error obtaining Result Set: " + e.getMessage());
		}
		
		return vehicles;
	}	// end searchVehiclesBasic(...)
	
	/**
	 * Helper method which converts a set of database results (in JSON format)
	 * to vehicle objects.
	 * 
	 * @param results Database results in JSON format
	 * @return ArrayList of vehicles
	 */
	private static List<Vehicle> makeVehicleList(final JsonArray results) {
		List<Vehicle> list = new ArrayList<Vehicle>();
	    if (results != null) {
    
    		int arrLength = results.size();
    		
    		for (int i = 0; i < arrLength; i++) {
    			JsonObject obj = results.getJsonObject(i);
    			Vehicle v = new Vehicle(obj);
    			list.add(v);
    		}	// end for
	    }  // end if
		
		return list;
	}	// end makeVehicleList
	
	/**
	 * Queries the database for a unique used car using the VIN as key since
	 * all vehicles has a unique VIN. Shows an error if one or zero results
	 * are returned.
	 *  
	 * @param vin Vehicle identification number
	 * @return Details for specific used vehicle
	 */
	public static Vehicle getUsedVehicle(final String vin) {
		
		// instantiate vehicle object and connect to database
		Vehicle vehicle =  null;
		JsonArray results = null;

		try {
			results = ((MySQLDatabaseAdapter) databaseInstance)
			        .callStoredProcedure("GetUsedVehicle", vin);
			if (results.size() == 1) {
				vehicle = new Vehicle(results.getJsonObject(0));	
			} else if (results.isEmpty()) {
				System.out.println(
				        "Error: Database returned no results for VIN " + vin);
			} else {
				System.out.println(
				        "Error: Database returned multiple results for VIN "
				        + vin);
			}			
		} catch (SQLException e) {
			System.out.println("Error obtaining UsedVehicle: " 
			        + e.getMessage());
		}

		return vehicle;
	}	// end getUsedVehicle(...)

	/**
	 * Queries the database for details on a new car model. New cars are treated
	 * in aggregate by the site, describing only generic information for a new
	 * car model. The quantity of "customizable units" is shown for each new
	 * car model, so this method will likely return multiple rows for each
	 * model. The multiple rows indicate the quantity on-hand at each store.
	 * 
	 * @param model Model of new car to be displayed
	 * @return ArrayList of vehicle objects
	 */
	public static Vehicle getNewVehicle(final String model) {
		// instantiate vehicle and connect to database
	    Vehicle vehicle = null;
		JsonArray results = null;
	
		try {
			results = ((MySQLDatabaseAdapter) databaseInstance)
			        .callStoredProcedure("GetNewVehicle", model);
			int numResults = results.size();
			
			if (numResults > 0) {
			    // create vehicle from first result
			    vehicle = new Vehicle(results.getJsonObject(0));

			    // add the remaining inventory data from the other rows
			    for (int i = 1; i < numResults; i++) {
			        JsonObject obj = results.getJsonObject(i);
			        vehicle.setInventory(
			                obj.getString("store_name"), 
			                Integer.parseInt(obj.getString("count_total")));
			    }
			} else {
				System.out.println(
				        "Error: Database returned no results for model "
				         + model);
			}			
		} catch (SQLException e) {
			System.out.println("Error obtaining NewVehicle: " + e.getMessage());
		}
		
		return vehicle;    
	}	// end getNewVehicle(...)

	/**
	 * Initializes the VehicleRepository with the provided
     * database adapter.
	 * @param db the database adapter to attach to the repository
	 */
	public static void initRepository(final IDatabaseAdapter db) { 
        databaseInstance = db; 
    }

}	// end class VehicleRepository


