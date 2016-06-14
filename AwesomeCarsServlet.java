/**
 *  AwesomeCarsServlet plays the role of the Controller in the MVC architecture.
 */
package awesomecars;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import awesomecars.model.CarWebAppCache;
import awesomecars.model.DealershipRepository;
import awesomecars.model.VehicleRepository;
import awesomecars.persistence.IDatabaseAdapter;
import awesomecars.persistence.MySQLDatabaseAdapter;

/**
 * Entry point for the AwesomeCarsWebApp. Performs initialization of the
 * app, stores important objects in the ServletContext for later use
 * and directs HTTP requests to the appropriate JSP pages.
 * 
 * @author Travis
 *
 */
public class AwesomeCarsServlet extends javax.servlet.http.HttpServlet
	implements javax.servlet.Servlet {
	
    /**
     * Required to implement the Serializable interface.
     */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor simply calls the superclass's constructor.
	 */
	public AwesomeCarsServlet() { 
	    super(); 
	}
	
	/**
	 * Retrieves initialization parameters and creates important objects
	 * such as the DatabaseAdapter and the lists needed to generate the
	 * advanced search form and navigation pane.
	 * @param config ServletConfig object from Tomcat
	 * @throws ServletException ServletException
	 */
	public final void init(final ServletConfig config) throws ServletException {
		System.out.println("*** initializing servlet.");
		super.init(config);
		
		// instantiates the database adapter and initializes it with
		// database information stored in the web.xml file.
		IDatabaseAdapter vehicleDB = new MySQLDatabaseAdapter(
				config.getInitParameter("dbURL"),
				config.getInitParameter("dbReadUserName"),
				config.getInitParameter("dbReadPassword"),
				config.getInitParameter("dbWriteUserName"),
				config.getInitParameter("dbWritePassword")
				);
		
		// initialize the CarWebAppCache singleton and repositories
		try {
		    CarWebAppCache.getInstance().initCache(vehicleDB);
		} catch (SQLException e) {
		    System.err.println("Unable to init web app cache.");
		}
		DealershipRepository.initRepository(vehicleDB);
		VehicleRepository.initRepository(vehicleDB);
		
		// saves the base and image URLs as servlet attributes
		// as well as the reference to the vehicle Database adapter
		ServletContext context = config.getServletContext();
		context.setAttribute("base", config.getInitParameter("base"));
		context.setAttribute("imageURL", config.getInitParameter("imageURL"));
		
		// load the database JDBC driver (connector J)
		try {
			Class.forName(config.getInitParameter("jdbcDriver"));
			System.out.println("*** jdbcDriverLoaded.");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}	// end init(...)
	
	/**
	 * Forwards HTTP Get request to doPost method.
	 * 
	 * @param request HTTP request to servlet
	 * @param response HTTP response object from servlet
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	protected final void doGet(
	        final HttpServletRequest request, 
	        final HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request, response);
	}	// end doGet(...)
	
	/**
	 * Dispatches request to appropriate JSP page based on
	 * the "action" parameter contained in the URL.
	 * 
	 * @param request HTTP request to servlet
	 * @param response HTTP response object from servlet
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	protected final void doPost(
	        final HttpServletRequest request, 
	        final HttpServletResponse response)
		throws ServletException, IOException {

		String base = "/jsp/";
		String url = base + "index.jsp";
		String action = request.getParameter("action");
		
		if (action != null) {
			switch(action) {
			
			case "home":
				break;
			case "basicSearch":
			case "categorySearch":
			case "advancedResults":
				url = base + "ShowResults.jsp";
				break;
			case "advancedSearch":
				url = base + "AdvancedSearchForm.jsp";
				break;
			case "getDetailsUsed":
			case "getDetailsNew":
				url = base + "ShowDetails.jsp";
				break;
			case "locations":
				url = base + "Locations.jsp";
				break;
			default:
			    url = base + "index.jsp";
			    break;
			}	// end switch(action)
		}	// end if
		
		// forward request to appropriate URL 
		RequestDispatcher requestDispatcher = 
		        getServletContext().getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}	// end doPost(...)
	
}	// end class AwesomeCarsServlet