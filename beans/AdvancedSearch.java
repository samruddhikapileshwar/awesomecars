package awesomecars.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import awesomecars.model.CarWebAppCache;

/**
 * This class bundles the parameters from the advanced search request into an
 * object for processing and subsequent dispatching to the database.
 * 
 * @author Travis
 *
 */
public class AdvancedSearch {

    /** Maximum number of sort options. */
    public static final int MAX_SORT_OPTIONS = 3;
    
    /** Include used vehicles (if true). */
	private boolean 			includeUsed;
	
	/** Include new vehicles (if true).	*/
	private boolean 			includeNew;
	
	/** List of body styles to include. */
	private List<String>	includeBodyStyles;
	
	/** List of makes. */
	private List<String>	includeMakes;
	
	/** List of models. */
	private List<String>	includeModels;
	
	/** Minimum year. */
	private int					minYear;
	
	/** Maximum year. */
	private int					maxYear;
	
	/** Minimum price. */
	private int					minPrice;
	
	/** Maximum price. */
	private int					maxPrice;
	
	/** Maximum miles. */
	private int					maxMiles;
	
	/** Include automatic transmissions (if true). */
	private boolean				includeAutomaticTransmission;
	
	/** Include manual transmissions (if true). */
	private boolean				includeManualTransmission;
	
	/** Minimum city MPG. */
	private int					minMPGCity;
	
	/** Minimum highway MPG. */
	private int				 	minMPGHwy;
	
	/** List of exterior colors. */
	private List<String>	includeExteriorColors;
	
	/** List of interior colors. */
	private List<String>	includeInteriorColors;
	
	/** List of locations to search. */
	private List<String>	includeLocations;

	/** List of options to sort by. */
	private List<SortOption>   sortOptions;
	
   /** 
     * Default/Empty constructor not allowed because it
     * bypasses the validation logic needed to properly
     * initialize the AdvancedSearch object.
     */
    @SuppressWarnings("unused")
    private AdvancedSearch() { 
    }
	
	/**
	 *  
	 * Initializes the AdvancedSearch object from an HTTP request
	 * object. All internal logic and validation are performed here.
	 * @param request HTTP servlet request containing query parameters
	 */
    // TODO - Refactor: Too big and duplicated code.
    public AdvancedSearch(final HttpServletRequest request) {
	    Map<String, String[]> params = request.getParameterMap();
	    String key;
	    String value;
	    
	    // Process category
	    key = "category";
	    this.includeNew = false;
	    this.includeUsed = false;
	    if (params.containsKey(key)) {
	        int len = params.get(key).length;

	        for (int i = 0; i < len; i++) {
	            value = params.get(key)[i];
	            
	            if ("new".equals(value)) {
	                this.includeNew = true;
	            } else if ("used".equals(value)) {
                    this.includeUsed = true;
                } else {
                    System.out.println("Unrecognized " + key + ": "
                    + value);
                }
	        }  // end for
	    }  // end category processing
	    
	    
        // Process body style
	    key = "style";
	    this.includeBodyStyles = null;
        if (params.containsKey(key)) {
            this.includeBodyStyles = new ArrayList<String>();
            int len = params.get(key).length;

            for (int i = 0; i < len; i++) {
                value = params.get(key)[i];
                
                // validate body style against prequeried list
                // if invalid body style, print message to console
                if (CarWebAppCache.getInstance()
                        .getBodyStyleList().contains(value)) {
                    this.includeBodyStyles.add(value);
                } else {
                    System.out.println("Unrecognized " + key + ": "
                    + value);
                }
            }   //end for  
        }  // end body style processing

        // Process make
        key = "make";
        this.includeMakes = null;
        if (params.containsKey(key)) {
            this.includeMakes = new ArrayList<String>();
            int len = params.get(key).length;

            for (int i = 0; i < len; i++) {
                value = params.get(key)[i];
                
                // validate make against prequeried list
                // if invalid make, print message to console
                if (CarWebAppCache.getInstance()
                        .getMakeList().contains(value)) {
                    this.includeMakes.add(value);
                } else {
                    System.out.println("Unrecognized " + key + ": "
                    + value);
                }
            }   //end for  
        }  // end make processing
        
        // Process models
        key = "model";
        this.includeModels = null;
        if (params.containsKey(key)) {
            this.includeModels = new ArrayList<String>();
            int len = params.get(key).length;

            for (int i = 0; i < len; i++) {
                value = params.get(key)[i];
                
                // validate model against prequeried list
                // if invalid model, print message to console
                if (CarWebAppCache.getInstance()
                        .getModelList().contains(value)) {
                    this.includeModels.add(value);
                } else {
                    System.out.println("Unrecognized " + key + ": "
                    + value);
                }
            }   //end for  
        }  // end model processing

        // process min year
        this.minYear = -1;
        key = "yearMin";
        if (params.containsKey(key)) {
            // should only have ONE value. If multiple, take the first
            try {
                this.minYear = Integer.parseInt(params.get(key)[0]);
            } catch (NumberFormatException e) { 
                System.out.println("Unrecognized " + key + ": " + minYear);
                this.minYear = -1;
            }
        }   
       
        // process max year
        this.maxYear = -1;
        key = "yearMax";
        if (params.containsKey(key)) {
            // should only have ONE value. If multiple, take the first
            try {
                this.maxYear = Integer.parseInt(params.get(key)[0]);
            } catch (NumberFormatException e) {
                System.out.println("Unrecognized " + key + ": " + maxYear);
                this.maxYear = -1;
            }
        }   

        // process min price
        this.minPrice = -1;
        key = "priceMin";
        if (params.containsKey(key)) {
            // should only have ONE value. If multiple, take the first
            try {
                this.minPrice = Integer.parseInt(params.get(key)[0]);
                // should only have ONE value. If multiple, take the first
            } catch (NumberFormatException e) {
                System.out.println("Unrecognized " + key + ": " + minPrice);
                this.minPrice = -1;
            }
        }  
        
        // process max price
        this.maxPrice = -1;
        key = "priceMax";
        if (params.containsKey(key)) {
            // should only have ONE value. If multiple, take the first
            try {
                this.maxPrice = Integer.parseInt(params.get(key)[0]);
            } catch (NumberFormatException e) { 
                System.out.println("Unrecognized " + key + ": " + maxPrice);
                this.maxPrice = -1;
            }
        }   

        // process max miles
        this.maxMiles = -1;
        key = "milesMax";
        if (params.containsKey(key)) {
            // should only have ONE value. If multiple, take the first
            try {
                this.maxMiles = Integer.parseInt(params.get(key)[0]);
            } catch (NumberFormatException e) { 
                System.out.println("Unrecognized " + key + ": " + maxMiles);
                this.maxMiles = -1;
            }
        }   
        
        // Process transmission
        key = "transmission";
        this.includeManualTransmission = false;
        this.includeAutomaticTransmission = false;
        if (params.containsKey(key)) {
            int len = params.get(key).length;

            for (int i = 0; i < len; i++) {
                value = params.get(key)[i];
                
                if ("manual".equals(value)) {
                    this.includeManualTransmission = true;
                } else if ("automatic".equals(value)) {
                    this.includeAutomaticTransmission = true;
                } else {
                    System.out.println("Unrecognized " + key + ": "
                    + value);
                }
            }  // end for
        }
        
        // process min MPG city
        this.minMPGCity = -1;
        key = "MPGCityMin";
        if (params.containsKey(key)) {
            // should only have ONE value. If multiple, take the first
            try {
                this.minMPGCity = Integer.parseInt(params.get(key)[0]);
            } catch (NumberFormatException e) { 
                System.out.println("Unrecognized " + key + ": " + minMPGCity);
                this.minMPGCity = -1;
            }
        }   

        // process min MPG city
        this.minMPGHwy = -1;
        key = "MPGHwyMin";
        if (params.containsKey(key)) {
            // should only have ONE value. If multiple, take the first
            try {
                this.minMPGHwy = Integer.parseInt(params.get(key)[0]);
            } catch (NumberFormatException e) { 
                System.out.println("Unrecognized " + key + ": " + minMPGHwy);
                this.minMPGHwy = -1;
            }
        }   
        
        // Process interior colors
        key = "intColor";
        this.includeInteriorColors = null;
        if (params.containsKey(key)) {
            this.includeInteriorColors = new ArrayList<String>();
            int len = params.get(key).length;

            for (int i = 0; i < len; i++) {
                value = params.get(key)[i];
                
                // validate interior color against prequeried list
                // if invalid, print message to console
                if (CarWebAppCache.getInstance()
                        .getInteriorColorList().contains(value)) {
                    this.includeInteriorColors.add(value);
                } else {
                    System.out.println("Unrecognized " + key + ": "
                    + value);
                }
            }   //end for  
        }   // end interior color processing

        // Process exterior colors
        key = "extColor";
        this.includeExteriorColors = null;
        if (params.containsKey(key)) {
            this.includeExteriorColors = new ArrayList<String>();
            int len = params.get(key).length;

            for (int i = 0; i < len; i++) {
                value = params.get(key)[i];
                
                // validate exterior color against prequeried list
                // if invalid, print message to console
                if (CarWebAppCache.getInstance()
                        .getExteriorColorList().contains(value)) {
                    this.includeExteriorColors.add(value);
                } else {
                    System.out.println("Unrecognized " + key + ": "
                    + value);
                }
            }   //end for  
        }   // end exterior color processing
        
        // Process location
        key = "location";
        this.includeLocations = null;
        if (params.containsKey(key)) {
            this.includeLocations = new ArrayList<String>();
            int len = params.get(key).length;

            for (int i = 0; i < len; i++) {
                value = params.get(key)[i];
                
                // validate locations against prequeried list
                // if invalid, print message to console
                if (CarWebAppCache.getInstance()
                        .getLocationList().contains(value)) {
                    this.includeLocations.add(value);
                } else {
                    System.out.println("Unrecognized " + key + ": "
                    + value);
                }
            }   //end for  
        }   // end location processing

        // extract sort options
        sortOptions = new ArrayList<SortOption>();
        for (int i = 1; i < MAX_SORT_OPTIONS; i++) {
            if (params.containsKey("SortBy" + i)) {
                sortOptions.add(new SortOption(
                        params.get("SortBy" + i)[0],
                        params.get("SortOrder" + i)[0]));
            }
        }   // end sort processing
        
        // clean up includeMakes list
        reconcileMakeModels();

	}  // end constructor
	
	/**
	 * Converts the AdvancedSearch members into a properly formatted
	 * SQL query.
	 * 
	 * @return Properly formatted SQL string
	 */
	@Override
	public final String toString() {
	    String s = "";
	    
	    // include both new and used if both or neither are selected
	    boolean unionRequired = 
	                (this.includeUsed && this.includeNew) 
	            ||  (!this.includeUsed && !this.includeNew);
	    if (this.includeUsed || unionRequired) {
	        s += addSelectFrom();
	        s += " t3.category = \"used\" ";
	        s += addUsedFilters();
	        s += ") ";
	    } 
	    
	    // if both or neither are selected, union is required
	    if (unionRequired) {
	        s += " UNION ALL ";
	    }
	    
	    if (this.includeNew || unionRequired) {
	        s += addSelectFrom();
	        s += " t3.category = \"new\" ";
            s += addNewFilters();            
            s += " GROUP BY t2.model_name )";
	    }


	    Iterator<SortOption> sortIterator = sortOptions.iterator();
	    s += " ORDER BY ";
	    
	    while (sortIterator.hasNext()) {
	        SortOption so = sortIterator.next();
	        s += so.toString();
	        
	        if (sortIterator.hasNext()) {
	            s += " , ";
	        }
	    }
	    	    
	    return s;
	}  // end toString(...)
	
	/**
	 * Helper method which simply returns the common portion of the
	 * MySQL query.
	 * 
	 * @return SELECT, FROM and partial WHERE portions of SQL statement
	 */
	private String addSelectFrom() {
	    return "(SELECT t3.VIN, t1.make_name, t2.model_name, "
                + "t2.model_type, t3.year_model, t3.Price, t3.int_color, "
                + "t3.ext_color, t3.miles, t3.mpg_city, t3.mpg_hwy, "
                + "t3.category, t3.engine_type, t3.transmission, "
                + "t3.description,t3.picture,t4.store_name, t5.count_total "
                + "FROM vehicle_make t1, vehicle_model t2, vehicle_details "
                + "t3, store_information t4, vehicle_count t5 WHERE "
                + "t1.make_id = t2.make_id AND t1.make_id = t3.make_id "
                + "AND t2.model_id = t3.model_id AND t3.count_id = "
                + "t5.count_id AND t5.store_id = t4.store_id AND ";
	}
	
	/**
	 * Interprets the parameters of the AdvancedSearch object and
	 * generates the appropriate SQL strings, which are concatenated
	 * with the final SQL string.
	 * 
	 * @return Conditional portions of final SQL string
	 */
	private String addUsedFilters() {
	    String s = "";

	    if (this.includeModels != null || this.includeMakes != null) {
	        
	        s += " AND ( ";

	        if (this.includeMakes != null) {
	            Iterator<String> iterator = this.includeMakes.iterator();

	            while (iterator.hasNext()) {
	                
	                s += " (t1.Make_name = \"" + iterator.next() + "\") ";
	                if (iterator.hasNext()) {
	                    s += " OR ";
	                }
	            }
	        }
	        
	        if (this.includeModels != null) {
	            Iterator<String> iterator = this.includeModels.iterator();

	            if (this.includeMakes != null) {
	                s += " OR ";
	            }
	                
	            while (iterator.hasNext()) {

	                s += " (t2.Model_name = \"" + iterator.next() + "\") ";
	                if (iterator.hasNext()) {
	                    s += " OR ";
	                }
	            }
	        }
	        
	        s += " ) ";
	    }
	    

	    if (this.maxMiles >= 0) {
	        s += " AND (t3.miles <= " + this.maxMiles + ") "; 
	    }
	    
	    if (this.minMPGCity >= 0) {
	        s += " AND (t3.MPG_city >= " + this.minMPGCity + ") "; 
	    }
	    
	    if (this.minMPGHwy >= 0) {
	        s += " AND (t3.MPG_hwy >= " + this.minMPGHwy + ") "; 
	    }

	    if (this.minYear >= 0) {
	        s += " AND (t3.year_model >= " + this.minYear + ") "; 
	    }
	    
	    if (this.maxYear >= 0) {
	        s += " AND (t3.year_model <= " + this.maxYear + ") "; 
	    }
	    
	    if (this.minPrice >= 0) {
	        s += " AND (t3.price >= " + this.minPrice + ") "; 
	    }
	    
	    if (this.maxPrice >= 0) {
	        s += " AND (t3.price <= " + this.maxPrice + ") "; 
	    }
	    
	    if (this.includeAutomaticTransmission 
	            && !this.includeManualTransmission) {
	        s += " AND (t3.transmission = \"automatic\") ";
	    } else if (!this.includeAutomaticTransmission 
	            && this.includeManualTransmission) {
	        s += " AND (t3.transmission = \"manual\") ";
	    }
	    
	    if (this.includeExteriorColors != null) {
	        Iterator<String> iterator = this.includeExteriorColors.iterator();

	        s += " AND ( ";
	        while (iterator.hasNext()) {
	            
	            s += " (t3.ext_color = \"" + iterator.next() + "\") ";
	            if (iterator.hasNext()) {
	                s += " OR ";
	            }
	        }
	        s += " ) ";
	    }  

        if (this.includeInteriorColors != null) {
            Iterator<String> iterator = this.includeInteriorColors.iterator();

            s += " AND ( ";
            while (iterator.hasNext()) {
                
                s += " (t3.int_color = \"" + iterator.next() + "\") ";
                if (iterator.hasNext()) {
                    s += " OR ";
                }
            }
            s += " ) ";
        }  

        if (this.includeLocations != null) {
            Iterator<String> iterator = this.includeLocations.iterator();

            s += " AND ( ";
            while (iterator.hasNext()) {
                
                s += " (t4.Store_name = \"" + iterator.next() + "\") ";
                if (iterator.hasNext()) {
                    s += " OR ";
                }
            }
            s += " ) ";
        }  

        if (this.includeBodyStyles != null) {
            Iterator<String> iterator = this.includeBodyStyles.iterator();

            s += " AND ( ";
            while (iterator.hasNext()) {
                
                s += " (t2.Model_type = \"" + iterator.next() + "\") ";
                if (iterator.hasNext()) {
                    s += " OR ";
                }
            }
            s += " ) ";
        }  
	    
	    return s;
	}


	/**
     * Interprets the parameters of the AdvancedSearch object and
     * generates the appropriate SQL strings, which are concatenated
     * with the final SQL string.
     * @return Conditional portions of final SQL string 
	 */
	private String addNewFilters() {
	    String s = "";

	    if (this.includeModels != null || this.includeMakes != null) {
	            
	        s += " AND ( ";

	        if (this.includeMakes != null) {
	            Iterator<String> iterator = this.includeMakes.iterator();

	            while (iterator.hasNext()) {
	                    
	                s += " (t1.Make_name = \"" + iterator.next() + "\") ";
	                if (iterator.hasNext()) {
	                    s += " OR ";
	                }
	            }
	        }
	            
	        if (this.includeModels != null) {
	            Iterator<String> iterator = this.includeModels.iterator();

	            if (this.includeMakes != null) {
	                s += " OR ";
	            }
	                    
	            while (iterator.hasNext()) {

	                s += " (t2.Model_name = \"" + iterator.next() + "\") ";
	                if (iterator.hasNext()) {
	                    s += " OR ";
	                }
	            }
	        }
	            
	        s += " ) ";
	    }
	        
	    if (this.minMPGCity >= 0) {
	        s += " AND (t3.MPG_city >= " + this.minMPGCity + ") "; 
	    }
	        
	    if (this.minMPGHwy >= 0) {
	        s += " AND (t3.MPG_hwy >= " + this.minMPGHwy + ") "; 
	    }
	        
	    if (this.minPrice >= 0) {
	        s += " AND (t3.price >= " + this.minPrice + ") "; 
	    }
	        
	    if (this.maxPrice >= 0) {
	        s += " AND (t3.price <= " + this.maxPrice + ") "; 
	    }

	    if (this.includeLocations != null) {
	        Iterator<String> iterator = this.includeLocations.iterator();

	        s += " AND ( ";
	        while (iterator.hasNext()) {
	                
	            s += " (t4.Store_name = \"" + iterator.next() + "\") ";
	            if (iterator.hasNext()) {
	                s += " OR ";
	            }
	        }
	        s += " ) ";
	    }  

	    if (this.includeBodyStyles != null) {
	        Iterator<String> iterator = this.includeBodyStyles.iterator();

	        s += " AND ( ";
	        while (iterator.hasNext()) {
	                
	            s += " (t2.Model_type = \"" + iterator.next() + "\") ";
	            if (iterator.hasNext()) {
	                s += " OR ";
	            }
	        }
	        s += " ) ";
	    }  
	        
	    return s;
	}
	
	
	/**
	 * Scans list of models and removes the associated make from the 
	 * includeMake list so that the only makes which are included in
	 * the search are those for which no models were specified (which
	 * means the user wants all models of a particular make returned).
	 */
	private void reconcileMakeModels() {
 
	    if (includeModels == null) { 
	        return; 
	    }

	    List<String> includeMakesCopy 
	        = new ArrayList<String>(this.includeMakes);
	    
	    for (String model : includeModels) {
	        for (String make : includeMakesCopy) {
	            List<String> modelList 
	                = CarWebAppCache.getInstance().getMakeModelList().get(make);
	            if (modelList.contains(model) && includeMakes.contains(make)) {
	                includeMakes.remove(make);
	            }
	        }  // end for each make in includeMakesCopy
	    }  // end for each model in includeModels
	    
	    if (includeMakes.isEmpty()) {
	        includeMakes = null;
	    }
	}  // end reconcileMakeModels()
}
