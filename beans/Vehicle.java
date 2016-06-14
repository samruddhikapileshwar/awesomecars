package awesomecars.beans;

import java.text.NumberFormat;
import java.util.Map;
import java.util.TreeMap;

import javax.json.JsonObject;

/**
 * Java bean which describes a vehicle.
 * 
 *  @author Travis
 */
public class Vehicle implements java.io.Serializable {

    /** Implements Serializable interface. */
	private static final long serialVersionUID = -7892633868357138131L;
	
	/** Make. */   
	private String	make;
	
	/** Model. */
	private String	model;
	
	/** Body style. */
	private String	bodyStyle;
	
	/** Model year. */
	private int		year;
	
	/** Price. */
	private int		price;
	
	/** Gas mileage in the city in MPG. */
	private	int		mpgCity;
	
	/** Gas mileage on the highway in MPG. */
	private int 	mpgHwy;
	
	/** Vehicle description (prose). */
	private String	description;
	
	/** Filename of vehicle image. */
	private String	imageURL;
	
	/** Category of vehicle (new/used). */
	private String	category;
	
	/** Vehicle identifcation number (VIN). */
	private String	vin;
	
	/** Interior color. */
	private String	intColor;
	
	/** Exterior color. */
	private String	extColor;
	
	/** Miles on the odometer. */
	private int		miles;
	
	/** Description of engine. */
	private	String	engineDesc;
	
	/** Type of transmission (automatic/manual). */
	private	String	transmission;
	
	/**
	 * Inventory where location is the key and quantity the value.
	 */
	private Map<String, Integer> inventory;
	
	/** Default constructor. */
	public Vehicle() { 
	    super();
	}
	
	/**
	 * Constructor to initialize a vehicle with a JSONObject (which in turn was
	 * extracted from a database).
	 * 
	 * @param obj JSONObject to initialize vehicle (must contain all fields)
	 */
	public Vehicle(final JsonObject obj) {
		this.category = obj.getString("category");
		this.make = obj.getString("make_name");
		this.model = obj.getString("model_name");
		this.bodyStyle = obj.getString("model_type");
		this.year = Integer.parseInt(obj.getString("year_model"));
		this.price = Integer.parseInt(obj.getString("price"));
		this.mpgCity = Integer.parseInt(obj.getString("mpg_city"));
		this.mpgHwy = Integer.parseInt(obj.getString("mpg_hwy"));
		this.description = obj.getString("description");
		this.imageURL = obj.getString("picture");
		this.inventory = new TreeMap<String, Integer>();
		setInventory(obj.getString("store_name"), 
		        Integer.parseInt(obj.getString("count_total")));
		
		if ("Used".equals(this.category)) {
			this.vin = obj.getString("vin");
			this.intColor = obj.getString("int_color");
			this.extColor = obj.getString("ext_color");
			this.miles = Integer.parseInt(obj.getString("miles"));
			this.engineDesc = obj.getString("engine_type");
			this.transmission = obj.getString("transmission");
		} else {
			this.vin = "N/A";
			this.intColor = "N/A";
			this.extColor = "N/A";
			this.miles = 0;
			this.engineDesc = "N/A";
			this.transmission = "N/A";			
		}
	}
	
	/** @param m the make to set */
	public final void setMake(final String m) { 
	    make = m; 
	}
	
	/** @return the make */
	public final String getMake() { 
	    return make; 
	}
	
	/** @param m the model to set */
	public final void setModel(final String m) { 
	    model = m; 
	}
	
	/** @return the model */
	public final String getModel() { 
	    return model; 
	}
	
	/** @param s the body style to set */
	public final void setBodyStyle(final String s) { 
	    bodyStyle = s; 
	}
	
	/** @return the body style */
	public final String getBodyStyle() { 
	    return bodyStyle; 
	}

	/** @param y the model year to set */
	public final void setYear(final int y) { 
	    year = y; 
	}
	
	/** @return the model year */
	public final int getYear() { 
	    return year; 
	}
	
	/** @param p the price to set */
	public final void setPrice(final int p) { 
	    price = p; 
	}
	
	/** @return the price as an int */
	public final int getPrice() { 
	    return price; 
	}
	
	/** @return the price as a properly formatted string (with commas) */
    public final String getPriceAsString() {
        return NumberFormat.getIntegerInstance().format(this.price);
    }

	/** @param m the city MPG to set */
	public final void setMpgCity(final int m) { 
	    mpgCity = m; 
	}
	
	/** @return the city MPG */
	public final int getMpgCity() { 
	    return mpgCity; 
	}

	/** @param m the highway MPG to set */
	public final void setMpgHwy(final int m) { 
	    mpgHwy = m; 
	}
	
	/** @return the highway MPG */
	public final int getMpgHwy() { 
	    return mpgHwy; 
	}
	
	/** @param d the description to set */
	public final void setDescription(final String d) { 
	    description = d; 
	}
	
	/** @return the description */
	public final String getDescription() { 
	    return description; 
	}   
	
	/** @param u the image filename to set */
	public final void setImageURL(final String u) { 
	    imageURL = u; 
	}
	
	/** @return the image filename */
	public final String getImageURL() { 
	    return imageURL; 
	}

	/** @param c the category (new/used) to set */
	public final void setCategory(final String c) { 
	    category = c; 
	}
	
	/** @return the category (new/used) */
	public final String getCategory() { 
	    return category; 
	}
	
	/** @param v the VIN to set */
	public final void setVin(final String v) { 
	    vin = v; 
	}
	
	/** @return the VIN */
	public final String getVin() { 
	    return vin; 
	}
	
	/** @param c the interior color to set */
	public final void setIntColor(final String c) { 
	    intColor = c; 
	}
	
	/** @return the interior color */
	public final String getIntColor() { 
	    return intColor; 
	}
	
	/** @param c the exterior color to set */
	public final void setExtColor(final String c) { 
	    extColor = c; 
	}
	
	/** @return the exterior color */
	public final String getExtColor() { 
	    return extColor; 
	}
	
	/** @param m the miles to set */
	public final void setMiles(final int m) { 
	    miles = m; 
	}
	
	/** @return the miles as an int */
	public final int getMiles() { 
	    return miles; 
	}
	
	/** @return the miles as a properly formatted string (with commas) */
	public final String getMilesAsString() {
	    return NumberFormat.getIntegerInstance().format(this.miles);
	}
	
	/** @param e the engine description to set */
	public final void setEngineDesc(final String e) { 
	    engineDesc = e; 
	}
	
	/** @return the engine description */
	public final String getEngineDesc() { 
	    return engineDesc; 
	}
	
	/** @param t the transmission to set */
	public final void setTransmission(final String t) { 
	    transmission = t; 
	}
	
	/** @return the transmission */
	public final String getTransmission() { 
	    return transmission; 
	}
	
	/** 
	 * Adds (location, quantity) key-value pair to the inventory.
	 * @param key the key (location) to set
	 * @param value the value (quantity available) to set
	 */
	public final void setInventory(final String key, final int value) { 
        this.inventory.put(key, value);	        
	}  // end setInventory
	
	/** @return the inventory */
	public final Map<String, Integer> getInventory() {
	    return this.inventory;
	}  // end getInventory
	
}
