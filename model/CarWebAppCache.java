package awesomecars.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.json.JsonArray;
import javax.json.JsonObject;

import awesomecars.persistence.IDatabaseAdapter;
import awesomecars.persistence.MySQLDatabaseAdapter;

/**
 * This class stores references to all lists needed to support data
 * validation, the navigation pane, and the Advanced Search form. A
 * single stored procedure call that returns multiple ResultSets is
 * used to initialize these lists to reduce loading on the database
 * server. Implements the Singleton pattern.
 * 
 * @author Philip
 *
 */
public final class CarWebAppCache {

    /** Eager instantiation of Singleton class. */
    private static CarWebAppCache cache = new CarWebAppCache();
    
    /** Number of lists in the cache. */
    public static final int NUM_LISTS  = 4;
    
    /** Initialized flag. */
    private boolean initialized = false;

    /** List of make-model assocations. */
    private Map<String, ArrayList<String>> makeModelList;
    
    /** List of unique makes. */
    private List<String> makeList = new ArrayList<String>();
    
    /** List of unique models. */
    private List<String> modelList = new ArrayList<String>();
    
    /** List of unique body styles. */
    private List<String> bodyStyleList = new ArrayList<String>();
    
    /** List of unique exterior colors. */
    private List<String> extColorList = new ArrayList<String>();
    
    /** List of unique interior colors. */
    private List<String> intColorList = new ArrayList<String>();
    
    /** List of unqiue locations. */
    private List<String> locationList = new ArrayList<String>();

    /** @return the make-model association list */
    public Map<String, ArrayList<String>>  getMakeModelList() { 
        return makeModelList; }
    
    /** @return the make list */
    public List<String> getMakeList() { 
        return makeList; 
    }
    
    /** @return the model list */
    public List<String> getModelList() { 
        return modelList; 
    }
    
    /** @return the body styles list */
    public List<String> getBodyStyleList() {
        return bodyStyleList; 
    }
    
    /** @return the exterior color list */
    public List<String> getExteriorColorList() { 
        return extColorList; 
    }
    
    /** @return the interior color list */
    public List<String> getInteriorColorList() { 
        return intColorList; 
    }
    
    /** @return the location list */
    public List<String> getLocationList() { 
        return locationList; 
    }

    /**  
     * Private constructor prevents any other class from instantiating.
     */
    private CarWebAppCache() { 
        super();
    }

    /**
     *  Returns the instance of CarWebAppCache (if initialized).
     *  @return Instance of CarWebAppCache singleton 
     */
    public static CarWebAppCache getInstance() {
       return cache;
    }

    /**
     * This method must be called to create and initialize the CarWebAppCache
     * instance.
     * 
     * @param database Vehicle database to retrieve data from
     * @throws SQLException java.sql.SQLException
     */
    public void initCache(final IDatabaseAdapter database) throws SQLException {
        
        if (!initialized) {  
            List<ArrayList<String>> resultSets =   
                    ((MySQLDatabaseAdapter) database)
                    .callStoredProcedureMultipleResultSets("GetLookupLists");
                 
            // get body styles, int/ext colors and locations
            if (resultSets != null && resultSets.size() == NUM_LISTS) {
                int i = 0;
                bodyStyleList = resultSets.get(i++);
                extColorList = resultSets.get(i++);
                intColorList = resultSets.get(i++);
                locationList = resultSets.get(i);
    
                // creates the make/model map and the
                // make and model lists
                makeModelList = makeMakeModelList(database);
                if (makeModelList != null) {
                    initialized = true;                        
                }
            }   // end if
        }   // end if (!initialized)   
    }   // end initCache(...)
    
    /**
     * Extracts make and model information from the database and returns
     * it as an ordered TreeMap by make (key) and model (values). This
     * information is used by the app to generate the left-hand navigation
     * pane and parts of the Advanced Search form.
     * 
     * @param database Vehicle database
     * @return TreeMap of make (key) and model (values) information
     * @throws SQLException java.sql.SQLException
     */
    private Map<String, ArrayList<String>> makeMakeModelList(
            final IDatabaseAdapter database) throws SQLException {
        // collection of makes and models. Model is the key. Make is the value
        Map<String, ArrayList<String>> list = 
                new TreeMap<String, ArrayList<String>>();
        JsonArray results = null;
        
        results = ((MySQLDatabaseAdapter) database)
                .callStoredProcedure("GetMakeModelList");

        int len = results.size();
        String make = null;
        int i = 0;
            
        while (i < len) {
            List<String> models = new ArrayList<String>();
            JsonObject obj = results.getJsonObject(i);
            make = obj.getString("make_name");
                
            do {
                // add the model to the list, increment the index and get
                // the next object
                models.add(obj.getString("model_name"));
                modelList.add(obj.getString("model_name"));
                i++;
                if (i < len) {
                    obj = results.getJsonObject(i);
                } else {
                    break;
                }
            } while(obj.getString("make_name").equals(make));
            
            list.put(make, (ArrayList<String>) models);
            makeList.add(make);
        }   // end while
        
        return list;
    }   // end makeMakeModelList(...)
    
}   // end class CarWebAppCache 