package awesomecars.persistence;

import java.sql.SQLException;

import javax.json.JsonArray;

/**
 * Defines common interface for all database adapters.
 * @author Travis
 */
public interface IDatabaseAdapter {	
    /**
     * @param query the SQL query to execute
     * @return the results as a JSONArray
     * @throws SQLException SQLException
     */
    JsonArray queryDatabase(String query) throws SQLException;
    
    /** @param statement the update statement to send to the database */
	void updateDatabase(String statement);
}
