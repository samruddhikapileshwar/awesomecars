package awesomecars.beans;

import javax.json.JsonObject;

/**
 * Describes a dealership as per the domain model.
 * @author Travis
 */
public class Dealership implements java.io.Serializable {

    /** Implement Serializable interface. */
	private static final long serialVersionUID = -1185686332756639042L;
	
	/** Dealership display name. */
	private String	name;
	
	/** Dealership address. */
	private String 	address;
	
	/** Dealership city. */
	private String city;
	
	/** Dealership state. */
	private String state;
	
	/** Dealership zip code. */
	private int zip;
	
	/** Dealership phone number. */
	private String	phoneNumber;
	
	/** Dealership hours. */
	private String	hours;

	/** Default constructor. */
	public Dealership() { 
	    super();
	}
	
	/**
	 * Constructor. Initializes instance from a JSONObject.
	 * @param obj JSONObject containing initialization data
	 */
	public Dealership(final JsonObject obj) {
		this.name = obj.getString("store_name");
		this.address = obj.getString("store_address");
		this.city = obj.getString("store_city");
		this.state = obj.getString("store_state");
		this.zip = Integer.parseInt(obj.getString("store_zip"));
		this.phoneNumber = obj.getString("store_phone_no");
		this.hours = obj.getString("store_hours");
	}

    /** @return the dealership's display name */
    public final String getName() { 
        return name; 
    }

    /** @param n the display name to set */
    public final void setName(final String n) { 
        name = n; 
    }

    /** @return the address */
    public final String getAddress() { 
        return address; 
    }

    /** @param a the address to set */
    public final void setAddress(final String a) { 
        address = a; 
    }

    /** @return the city */
    public final String getCity() { 
        return city; 
    }

    /** @param c the city to set */
    public final void setCity(final String c) { 
        city = c; 
    }
    
    /** @return the state */
    public final String getState() { 
        return state; 
    }

    /** @param s the state to set */
    public final void setState(final String s) { 
        state = s; 
    }
    
    /** @return the zip */
    public final int getZip() { 
        return zip; 
    }

    /** @param z the zip to set */
    public final void setZip(final int z) { 
        zip = z; 
    }

    /** @return the phone number */
    public final String getPhoneNumber() { 
        return phoneNumber; 
    }

    /** @param pn the phone number to set */
    public final void setPhoneNumber(final String pn) { 
        phoneNumber = pn; 
    }

    /** @return the hours */
    public final String getHours() { 
        return hours; 
    }

    /** @param h the hours to set */
    public final void setHours(final String h) { 
        hours = h; 
    }
}
