package awesomecars.test;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import awesomecars.ApplicationController;
import awesomecars.beans.Dealership;
import awesomecars.beans.Vehicle;
import awesomecars.model.CarWebAppCache;
import awesomecars.model.DealershipRepository;
import awesomecars.model.VehicleRepository;
import awesomecars.persistence.IDatabaseAdapter;
import awesomecars.persistence.MySQLDatabaseAdapter;

/**
 * Test the application controller methods client-side. While
 * this bypasses the Servlet, leaving it essentially untouched
 * by the Code Coverage tools, it does allow Code Coverage tracking
 * of all other methods. While the basic logic duplicates that of
 * the AwesomeCarsServletTest, the key difference is that class tests
 * the server-side behavior, which this does not.
 * 
 * @author Travis
 */
public class ApplicationControllerTest {
    
    /** Number of stores in database. */
    public static final int NUM_STORES = 5;
    
    /** Number of cars in database. */
    public static final int NUM_CARS = 41;

    /** Current year for new cars. */
    public static final int NEW_CAR_YEAR = 2015;
    
    /** Database adapter. */
    private static IDatabaseAdapter database = null;
    
    /**
     * Create Database Adapter and register it with repository
     * classes.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        String dbUrl = "jdbc:mysql://localhost:3306/cardealership";

        // initialize database
        database = new MySQLDatabaseAdapter(
                dbUrl, "root", "", "root", "");
        
        // initialize the CarWebAppCache singleton and repositories
        try {
            CarWebAppCache.getInstance().initCache(database);
        } catch (SQLException e) {
            System.err.println("Unable to init web app cache.");
        }
        
        DealershipRepository.initRepository(database);
        VehicleRepository.initRepository(database);
    }


    /**
     * Tests controller's ability to retrieve basic search
     * results.
     */
    @Test
    public final void testGetBasicSearchResults() {
        String search = "txs";
        
        List<Vehicle> result 
            = ApplicationController.getBasicSearchResults(search);
        
        // verify only 1 result and model is TXS
        assertTrue(result.size() == 1);
        assertTrue("TXS".equals(result.get(0).getModel()));
    }

    /**
     * Tests controller's ability to retrieve a used vehicle.
     */
    @Test
    public final void testGetVehicleDetailsUsed() {
        String vin = "A73N2L099PWOC72AB";
        
        Vehicle result = ApplicationController.getVehicleDetailsUsed(vin);
        
        // verify vin 
        assertTrue(result.getVin().equals(vin));
    }

    
    /**
     * Tests controller's ability to retrieve new vehicle.
     */
    @Test
    public final void testGetVehicleDetailsNew() {
        String model = "sentra";
        
        Vehicle result = ApplicationController.getVehicleDetailsNew(model);
        
        // verify year and model
        assertTrue("Sentra".equals(result.getModel()));
        assertTrue(result.getYear() == NEW_CAR_YEAR);
    }

    /**
     * Tests controller's ability to retrieve category results.
     */
    @Test
    public final void testGetCategorySearchResults() {
        String category = "txs";
        
        List<Vehicle> result 
            = ApplicationController.getCategorySearchResults(category);
        
        // verify only 1 result and model is TXS
        assertTrue(result.size() == 1);
        assertTrue("TXS".equals(result.get(0).getModel()));
    }

    /**
     * Tests controller's ability to retrieve all store details.
     */
    @Test
    public final void testGetAllStoreDetails() {
        List<Dealership> stores 
            = ApplicationController.getAllStoreDetails();
        
        assertTrue(stores.size() == NUM_STORES);
    }

    /**
     * Tests controller's ability to retrieve advanced search results.
     */
    @Test
    public final void testAdvancedSearchResults1() {

        // create and stuff mock servlet request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("action", "advancedResults");

        String key = "category";
        request.addParameter(key, "used");
        request.addParameter(key, "new");
        
        key = "style";
        request.addParameter(key, "Coupe");
        request.addParameter(key, "Sedan");
        
        key = "make";
        request.addParameter(key, "Ford");
        request.addParameter(key, "Nissan");
        request.addParameter(key, "Ford");
        request.addParameter(key, "Accura");
        
        key = "model";
        request.addParameter(key, "Taurus");
        request.addParameter(key, "Sentra");
        
        request.addParameter("yearMin", "1990");
        request.addParameter("yearMax", "2015");
        request.addParameter("priceMin", "10000");
        request.addParameter("priceMax", "20000");
        request.addParameter("milesMax", "100000");
        request.addParameter("transmission", "automatic");
        request.addParameter("MPGCityMin", "20");
        request.addParameter("MPGHwyMin", "25");
        
        key = "extColor";
        request.addParameter(key, "Black");
        request.addParameter(key, "Blue");
        request.addParameter(key, "Grey");
        request.addParameter(key, "Red");
        request.addParameter(key, "Silver");
        request.addParameter(key, "White");
        request.addParameter(key, "Yellow");
        
        key = "intColor";
        request.addParameter(key, "Ash");
        request.addParameter(key, "Black");
        request.addParameter(key, "Brown");
        request.addParameter(key, "Green");
        request.addParameter(key, "Grey");
        request.addParameter(key, "Sky Blue");
        
        key = "location";
        request.addParameter(key, "Arlington");
        request.addParameter(key, "Austin");
        request.addParameter(key, "Dallas");
        request.addParameter(key, "Fort Worth");
        request.addParameter(key, "Plano");
        
        request.addParameter("SortBy1", "price");
        request.addParameter("SortOrder1", "ASC");
        request.addParameter("SortBy2", "miles");
        request.addParameter("SortOrder2", "ASC");
        request.addParameter("SortBy3", "year");
        request.addParameter("SortOrder3", "DESC");
        
        List<Vehicle> results 
            = ApplicationController.getAdvancedSearchResults(request);

        // verify expectations were satisfied
        assertTrue(results.size() == 1);
    }

    /**
     * Tests advanced search error conditions.
     */
    @Test
    public final void testAdvancedSearchResults2() {

        // create and stuff mock servlet request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("action", "advancedResults");
        
        request.addParameter("category", "fake");
        request.addParameter("style", "fake");
        request.addParameter("make", "fake");
        request.addParameter("model", "fake");
        request.addParameter("yearMin", "fake");
        request.addParameter("yearMax", "fake");
        request.addParameter("priceMin", "fake");
        request.addParameter("priceMax", "fake");
        request.addParameter("milesMax", "fake");
        request.addParameter("transmission", "fake");
        request.addParameter("MPGCityMin", "fake");
        request.addParameter("MPGHwyMin", "fake");
        request.addParameter("extColor", "fake");
        request.addParameter("intColor", "fake");
        request.addParameter("location", "fake");
        request.addParameter("SortBy1", "fake");
        request.addParameter("SortOrder1", "fake");
        request.addParameter("SortBy2", "fake");
        request.addParameter("SortOrder2", "fake");
        request.addParameter("SortBy3", "fake");
        request.addParameter("SortOrder3", "fake");
        
        List<Vehicle> results 
            = ApplicationController.getAdvancedSearchResults(request);

        // verify expectations were satisfied
        assertTrue(results.isEmpty());
    }

    /**
     * Tests advanced search with no conditions.
     */
    @Test
    public final void testAdvancedSearchResults3() {

        // create and stuff mock servlet request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("action", "advancedResults");
        request.addParameter("SortBy1", "price");
        request.addParameter("SortOrder1", "ASC");
        request.addParameter("SortBy2", "miles");
        request.addParameter("SortOrder2", "ASC");
        request.addParameter("SortBy3", "year");
        request.addParameter("SortOrder3", "DESC");
        
        List<Vehicle> results 
            = ApplicationController.getAdvancedSearchResults(request);

        System.out.println(results.size());
        // verify expectations were satisfied
        assertTrue(results.size() == NUM_CARS);
    }
    
}   // end class ApplicationControllerTest
