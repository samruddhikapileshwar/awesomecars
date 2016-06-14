package awesomecars.persistence;

/**
 * DatabaseCredentials is a simple class to store the username and password
 * for accessing a database. These members can only be initialized when the
 * class is created.
 *	
 * @author Travis
 */
public class DatabaseCredential {
	/** Username. */
    private final String username;
    
    /** Password. */
	private final String password;

	/**
	 * Initializes object with provided credentials.
	 * @param name the username
	 * @param pwd the password
	 */
	public DatabaseCredential(final String name, final String pwd) {
		username = name;
		password = pwd;
	}
	
	/** Hide default constructor. */
	@SuppressWarnings("unused")
    private DatabaseCredential() { 
	    username = "";
	    password = "";
	}
	
	/** @return the username */
	public final String getUserName() { return username; }

	/** @return the password */
	public final String getPassword() { return password; }
}
