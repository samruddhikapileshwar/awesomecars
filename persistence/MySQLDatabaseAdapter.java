package awesomecars.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

/**
 * This is the MySQL specific implementation of the DatabaseAccessor abstract
 * class. The user is required to provide read and write credentials to the
 * constructor, as the default constructor is private.
 * 
 * @author Travis
 *
 */
public class MySQLDatabaseAdapter implements IDatabaseAdapter {

    /** Username and password needed for read-only (query) access. */
	private DatabaseCredential readCredentials = null;
	
	/**
	 *  Username and password needed for write (update) access. 
	 *  Not used in this project because we do not provide a back-end. 
	 */
	@SuppressWarnings("unused")
    private DatabaseCredential writeCredentials = null;
	
	/** URL of the database to connect to. */
	private String dbURL = null;

	/**
	 * Constructor for MySQLDatabaseAdapter.  Must supply the relevant
	 * credentials for a user that has read-only permissions and another
	 * user that has write permissions. This should enhance security
	 * through the principle of least privilege. Query operations only
	 * use the credentials for reading the database.
	 * 
	 * @param databaseURL URL of the database to connect to
	 * @param readUserName Username that has read-only permissions
	 * @param readPassword Password for user with read-only permissions
	 * @param writeUserName Username that has write permissions
	 * @param writePassword Password for use with write permissions
	 */
	public MySQLDatabaseAdapter(
	        final String databaseURL, 
	        final String readUserName, final String readPassword, 
	        final String writeUserName, final String writePassword) {
		dbURL = databaseURL;
		readCredentials = new DatabaseCredential(readUserName, readPassword);
		writeCredentials = new DatabaseCredential(writeUserName, writePassword);
	}
	
	/**
	 *  Make the default constructor private so that only the correct 
	 *  constructor can be used. 
	 */
	@SuppressWarnings("unused")
    private MySQLDatabaseAdapter() { 
	    super();
	};

	/**
	 * Helper method that prepares the CallableStatement and binds parameters
	 * to interface with MySQL's stored procedures. This method is not part of
	 * the IDatabaseAdapter interface, and unfortunately it contains
	 * app-specific code which would not be useful from the perspective of
	 * software reuse.
	 *  
	 * @param procedure Name of stored procedure to be called
	 * @param args Variable number of arguments to be sent to the 
	 * procedure
	 * @return JSONArray representation of ResultSet from stored procedure
	 * @throws SQLException SQLException
	 */
	public final JsonArray callStoredProcedure(
	        final String procedure, final Object... args) throws SQLException {

		Connection conn = getConnection();
		CallableStatement cs = null;
		ResultSet rs = null;
		JsonArray jsonResults = null;

		if (conn != null) {
			switch (procedure) {
			case "BasicSearchQuery":
				cs = conn.prepareCall("{call BasicSearchQuery(?)}");
				cs.setString(1, (String) args[0]);
				break;
			case "CategorySearchQuery":
				cs = conn.prepareCall("{call CategorySearchQuery(?)}");
				cs.setString(1, (String) args[0]);
				break;
			case "GetNewVehicle":
				cs = conn.prepareCall("{call GetNewVehicle(?)}");
				cs.setString(1, (String) args[0]);
				break;
			case "GetUsedVehicle":
				cs = conn.prepareCall("{call GetUsedVehicle(?)}");
				cs.setString(1, (String) args[0]);
				break;
			case "GetAllStoreDetails":
				cs = conn.prepareCall("{call GetAllStoreDetails()}");
				break;
			case "GetMakeModelList":
				cs = conn.prepareCall("{call GetMakeModelList()}");
				break;
			default:
				System.out.println(procedure + " stored procedure not found!");
				return jsonResults;	// will be null
			}
			
			// executes query and converts ResultSet to JSONArray
			try {
				rs = cs.executeQuery();
				
				try {
					jsonResults = convertToJson(rs); 
				} catch (Exception e) {
					System.out.println(
					        "Could not covert ResultSet to JSON:"
					        + e.getMessage());
				}
			} catch (SQLException e) {
				System.out.println(
				        "Could not query database: " + e.getMessage());
			} finally {
				// close all connections
				if (rs != null) {
					rs.close();
				}
				
				if (cs != null) {
					cs.close();
				}
				
				putConnection(conn);
			}	// end try/finally
		}	// end if
		
		return jsonResults;		
	}	// end calledStoredProcedure(...)
	
	/**
	 * Helper method that is similar to the callStoredProcedure method, but
     * allows the stored procedure to return mutiple ResultSets.
     * 
     * @param procedure Name of stored procedure to be called
     * @param args Variable number of arguments to be sent to the 
     * procedure 
     * @return List of String Lists
     * @throws SQLException SQLException
	 */
	// TODO - Refactor: return type should be in JSON not a list of lists
	public final List<ArrayList<String>> 
	    callStoredProcedureMultipleResultSets(final String procedure, 
	        final Object... args) throws SQLException {

        List<ArrayList<String>> allLists = null; 
	    Connection conn = getConnection();
        CallableStatement stmt = null;
       
        if (conn == null) { return null; }
        
        if (procedure.equals("GetLookupLists")) {
            stmt = conn.prepareCall("{call GetLookupLists()}");
        } else {
            System.out.println(procedure + " stored procedure not found!");
            return null; // will be null
        }   // end switch
            
        allLists = new ArrayList<ArrayList<String>>();
        boolean results = stmt.execute(); 

        // loop through ResultSets if they exist
        while (results) {
            List<String> innerList = new   ArrayList<String>();
            ResultSet rs = stmt.getResultSet();
	               
            //Retrieve data from the result set.
            while (rs.next()) {
                // get the string in the first column.
                innerList.add(rs.getString(1));  
            } // end while
	               
            rs.close();
            ((ArrayList<ArrayList<String>>) allLists)
                .add((ArrayList<String>) innerList);
	               
            // Check for next result set
            results = stmt.getMoreResults();
        }   // end while(results)
            
        stmt.close();
        return allLists;
	}  // end callStoredProcecureMultipleResultSets(...)

	/**
	 * Obtains a read-only connection to the database and attempts to query the
	 * database. If successful, converts the ResultSet to a JSONArray. 
	 * Otherwise, it returns null.
	 * 
	 * @param query	Correctly formatted MySQL query string
	 * @return JSONArray generic form of ResultSet from query if successful;
	 * otherwise null
	 * @throws SQLException SQLException
	 */
	@Override
	public final JsonArray queryDatabase(final String query) 
	        throws SQLException {
		
		Connection conn = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		JsonArray jsonResults = null;

		if (conn != null) {
			try {
				ps = conn.prepareStatement(query);
				rs = ps.executeQuery(query);
				try {
					jsonResults = convertToJson(rs); 
				} catch (Exception e) {
					System.out.println(
					        "Could not covert ResultSet to JSON:" 
					        + e.getMessage());
				}
			} catch (SQLException e) {
				System.out.println(
				        "Could not query database: " + e.getMessage());
			} finally {
				if (rs != null) {
					rs.close();
				}
				
				if (ps != null) {
					ps.close();
				}
				
				putConnection(conn);
			}	// end try/finally
		}	// end if
		
		return jsonResults;
	}	// end queryDatabase(...)

	@Override
	public final void updateDatabase(final String statement) {
	    throw new UnsupportedOperationException(
	            "Not implemented for this project."); 
	}	// end updateDatabase(...)

	/**
	 * Attempts to return a connection to the database using read-only
	 * credentials.
	 * 
	 * @return		Connection to database
	 */
	private Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(
			        dbURL, 
			        readCredentials.getUserName(),
			        readCredentials.getPassword());
		} catch (ClassNotFoundException e) {
			System.out.println(String.format(
			  "getConnection could not instantiate the driver class: %s", e));
			e.printStackTrace();
		} catch (SQLException e)	{
			System.out.println(String.format("getConnection error: %s", e));
			e.printStackTrace();
		}
		
		return null;
	}	// end getConnection()

	/**
	 * Attempts to close the database connection.
	 * @param connection Existing database connection
	 */
	private void putConnection(final Connection connection) {
		if (connection != null) {
			try { 
				connection.close(); 
			} catch (SQLException e) {
		         System.out.println(String.format(
		                 "putConnection error: %s", e));
		         e.printStackTrace();
			}
		}	// end if
	}	// end putConnection(...)

	/**
	 * Uses a technique described at 
	 * biercoff.blogspot.com/2013/11/nice-and-simple-converter-of-java.html
	 * to convert a JDBC ResultSet into a generic JSONArray. This is necessary
	 * because the ResultSet must be closed before the Connection, so the
	 * ResultSet cannot be returned to the client. It must be converted
	 * into a generic format (here JSON) that can be returned to the client.
	 * @param rs The JDBC ResultSet that must be converted to JSON
	 * @return	JSONArray containing the data from the ResultSet
	 * @throws SQLException java.sql.SQLException
	 */
	private JsonArray convertToJson(final ResultSet rs) throws SQLException {
		JsonArrayBuilder jsonResults = Json.createArrayBuilder();
		
		while (rs.next()) {
			int totalColumns = rs.getMetaData().getColumnCount();
			JsonObjectBuilder obj = Json.createObjectBuilder();
			for (int i = 0; i < totalColumns; i++) {

			    obj.add(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(),
			            rs.getObject(i + 1).toString());
			}
			jsonResults.add(obj.build());
		}
		
		return jsonResults.build();
	}	// end convertToJson(ResultSet)
	
}	// end class MySQLDatabaseAccessor
