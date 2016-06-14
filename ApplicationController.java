package awesomecars;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import awesomecars.beans.Dealership;
import awesomecars.beans.Vehicle;
import awesomecars.model.DealershipRepository;
import awesomecars.model.VehicleRepository;

/**
 * Plays the GRASP role of Controller, forwarding request from View objects
 * to their corresponding Model objects. Because this is a utility class with
 * no data members of its own, all methods in this class are static.
 *
 * @author Travis
 *
 */
public final class ApplicationController {

    /**
     * Default constructor declared private so utility class cannot be
     * instantiated.
     */
	private ApplicationController() {
	    super();
	}

	/**
	 * Forwards request for basic search results from the view to the
	 * VehicleRepository model object.
	 *
	 * @param searchString	Basic search string
	 * @return List of vehicles
	 */
	public static List<Vehicle> getBasicSearchResults(
			final String searchString) {
		return VehicleRepository.searchVehiclesBasic(searchString);
	}	// end getSearchResults(...)
	
	/**
	 * Forwards request for used vehicle details to the VehicleRepository
	 * for execution. Should return only a single vehicle because all
	 * vehicles have a unique VIN.
	 *
	 * @param vin VIN (unique for used vehicles)
	 * @return Single used vehicle
	 */
	public static Vehicle getVehicleDetailsUsed(
	        final String vin) {
		return VehicleRepository.getUsedVehicle(vin);
	}	// end getVehicleDetails(...)

	/**
	 * Forwards request for new vehicle details to the VehicleRepository
	 * for execution. Unlike used vehicles, new vehicles are treated 
	 * generically by model. Thus, this method may return a collection
	 * of new vehicles that have the same model.
	 *  
	 * @param model Vehicle model to retrieve
	 * quantity data
	 * @return List of new vehicle entries
	 */
	public static Vehicle getVehicleDetailsNew(
	        final String model) {
		return VehicleRepository.getNewVehicle(model);
	}	// end getVehicleDetails(...)
	
	/**
	 * Forwards request for category results to the VehicleRepository
	 * for execution. Essentially this is a search by model. 
	 *  
	 * @param model Vehicle model
	 * @return List of vehicle details for a given model
	 */
	public static List<Vehicle> getCategorySearchResults(
	        final String model) {
		return VehicleRepository.searchVehiclesCategory(model);
	}
	
	/**
	 * Forwards request for store details to the DealershipRepository
	 * for execution. This method is used by the Locations page to retrieve
	 * all store details for display.
	 * 
	 * @return ArrayList of details on each store (Dealership)
	 */
	public static List<Dealership> getAllStoreDetails() {
		return DealershipRepository.getAllStoreDetails();
	}
		
	/**
	 * Forwards request for advanced search results from the view to the
     * VehicleRepository model object.
     *  
	 * @param request HTTP request object
	 * @return ArrayList of vehicle results
	 */
	public static List<Vehicle> getAdvancedSearchResults(
	        final HttpServletRequest request) {	    
	    return VehicleRepository.searchVehiclesAdvanced(request);
	}
}	// end class ApplicationController
