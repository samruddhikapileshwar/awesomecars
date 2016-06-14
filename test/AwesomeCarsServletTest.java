package awesomecars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

/**
 * Tests all use cases on a running server side implementation by
 * sending selected HttpPost messages and verifying the response.
 * Because the processing is done server-side, the code invoked
 * by these tests (outside of the tests themselves) is not counted
 * in the coverage rating.
 * 
 * @author Travis
 */
public class AwesomeCarsServletTest {

    /** Test client. */
    private final CloseableHttpClient client = HttpClients.createDefault();
    
    /**
     * Tests basic search use case by sending a search query with
     * a known result. Checks that the website returns the proper
     * number of results. Note: if the database changes (i.e. more
     * data is added or data is deleted) this test will FAIL and
     * should be updated accordingly!
     * @throws IOException IOException
     */
    @Test
    public final void testBasicSearch() throws IOException {
        String url = "http://localhost:8080/AwesomeCarsWebApp/awesomecars/";
        
        // create post request
        HttpPost post = new HttpPost(url);

        // Populate post request with basic search parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", "basicSearch"));
        params.add(new BasicNameValuePair("basicSearch", "txs"));
        
        // Encode parameters as URL encoded form entity to include in
        // body of POST
        UrlEncodedFormEntity requestBody 
            = new UrlEncodedFormEntity(params, Consts.UTF_8);
        
        // Attach URL encoded parameters to requestBody
        post.setEntity(requestBody);
               
        // execute request and receive response
        HttpResponse response = client.execute(post);
        
        // verify status code OK
        assertEquals(HttpStatus.SC_OK, 
                response.getStatusLine().getStatusCode());

        String responseBody = 
                IOUtils.toString(response.getEntity().getContent());
        
        assertTrue(responseBody.contains(
                "Your search for \"txs\" returned 1 results."));
    }   // end testBasicSearch()
    
    /**
     * Tests category search use case by sending a search query with
     * a known result. Checks that the website returns the proper
     * number of results. Note: if the database changes (i.e. more
     * data is added or data is deleted) this test will FAIL and
     * should be updated accordingly!
     * 
     * @throws IOException IOException
     */
    @Test
    public final void testCategorySearch() throws IOException {
        String url = "http://localhost:8080/AwesomeCarsWebApp/awesomecars/";
        
        // create post request
        HttpPost post = new HttpPost(url);

        // Populate post request with basic search parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", "categorySearch"));
        params.add(new BasicNameValuePair("model", "txs"));
        
        // Encode parameters as URL encoded form entity to include in
        // body of POST
        UrlEncodedFormEntity requestBody 
            = new UrlEncodedFormEntity(params, Consts.UTF_8);
        
        // Attach URL encoded parameters to requestBody
        post.setEntity(requestBody);
               
        // execute request and receive response
        HttpResponse response = client.execute(post);
        
        // verify status code OK
        assertEquals(HttpStatus.SC_OK, 
                response.getStatusLine().getStatusCode());

        String responseBody = 
                IOUtils.toString(response.getEntity().getContent());
        
        assertTrue(responseBody.contains(
                "Your search for \"txs\" returned 1 results."));
    }   // end testCategorySearch()

    /**
     * Tests advanced search use case by sending a search query with
     * a known result. Checks that the website returns the proper
     * number of results. Note: if the database changes (i.e. more
     * data is added or data is deleted) this test will FAIL and
     * should be updated accordingly!
     * 
     * @throws IOException IOException
     */
    @Test
    public final void testAdvancedSearch() throws IOException {
        String url = "http://localhost:8080/AwesomeCarsWebApp/awesomecars/";
        
        // create post request
        HttpPost post = new HttpPost(url);

        // Populate post request with basic search parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", "advancedResults"));
        params.add(new BasicNameValuePair("priceMin", ""));
        params.add(new BasicNameValuePair("priceMax", ""));
        params.add(new BasicNameValuePair("milesMax", ""));
        params.add(new BasicNameValuePair("MPGCityMin", ""));
        params.add(new BasicNameValuePair("MPGHwyMin", ""));
        params.add(new BasicNameValuePair("SortBy1", "price"));
        params.add(new BasicNameValuePair("SortOrder1", "ASC"));
        params.add(new BasicNameValuePair("SortBy2", "price"));
        params.add(new BasicNameValuePair("SortOrder2", "ASC"));
        params.add(new BasicNameValuePair("SortBy3", "price"));
        params.add(new BasicNameValuePair("SortOrder3", "ASC"));
        
        // Encode parameters as URL encoded form entity to include in
        // body of POST
        UrlEncodedFormEntity requestBody 
            = new UrlEncodedFormEntity(params, Consts.UTF_8);
        
        // Attach URL encoded parameters to requestBody
        post.setEntity(requestBody);
               
        // execute request and receive response
        HttpResponse response = client.execute(post);
        
        // verify status code OK
        assertEquals(HttpStatus.SC_OK, 
                response.getStatusLine().getStatusCode());

        String responseBody = 
                IOUtils.toString(response.getEntity().getContent());
        
        assertTrue(responseBody.contains(
                "Your Advanced Search returned 41 results."));
    }   // end testAdvancedSearch()

    
    /**
     * Tests retrieving details use case by sending a search query with
     * a known result. Checks that the website returns the proper
     * result. Note: if the database changes (i.e. more
     * data is added or data is deleted) this test will FAIL and
     * should be updated accordingly!
     * 
     * @throws IOException IOException
     */
    @Test
    public final void testGetDetailsUsed() throws IOException {
        String url = "http://localhost:8080/AwesomeCarsWebApp/awesomecars/";
        String vin = "A73N2L099PWOC72AB";
        
        // create post request
        HttpPost post = new HttpPost(url);

        // Populate post request with basic search parameters
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("action", "getDetailsUsed"));
        params.add(new BasicNameValuePair("vin", vin));
        
        // Encode parameters as URL encoded form entity to include in
        // body of POST
        UrlEncodedFormEntity requestBody 
            = new UrlEncodedFormEntity(params, Consts.UTF_8);
        
        // Attach URL encoded parameters to requestBody
        post.setEntity(requestBody);
               
        // execute request and receive response
        HttpResponse response = client.execute(post);
        
        // verify status code OK
        assertEquals(HttpStatus.SC_OK, 
                response.getStatusLine().getStatusCode());

        String responseBody = 
                IOUtils.toString(response.getEntity().getContent());
        
        // verify response contains vin and contains the phrase *Used*
        assertTrue(responseBody.contains(vin));
        assertTrue(responseBody.contains("*Used*"));
    }   // end testGetDetailsUsed()

   /**
    * Tests retrieving details use case by sending a search query with
    * a known result. Checks that the website returns the proper
    * result. Note: if the database changes (i.e. more
    * data is added or data is deleted) this test will FAIL and
    * should be updated accordingly!
    * 
    * @throws IOException IOException
    */
   @Test
   public final void testGetDetailsNew() throws IOException {
       String url = "http://localhost:8080/AwesomeCarsWebApp/awesomecars/";
       String model = "sentra";
       
       // create post request
       HttpPost post = new HttpPost(url);

       // Populate post request with basic search parameters
       List<NameValuePair> params = new ArrayList<>();
       params.add(new BasicNameValuePair("action", "getDetailsNew"));
       params.add(new BasicNameValuePair("model", model));
       
       // Encode parameters as URL encoded form entity to include in
       // body of POST
       UrlEncodedFormEntity requestBody 
           = new UrlEncodedFormEntity(params, Consts.UTF_8);
       
       // Attach URL encoded parameters to requestBody
       post.setEntity(requestBody);
              
       // execute request and receive response
       HttpResponse response = client.execute(post);
       
       // verify status code OK
       assertEquals(HttpStatus.SC_OK, 
               response.getStatusLine().getStatusCode());

       String responseBody = 
               IOUtils.toString(response.getEntity().getContent());
       
       // verify response contains correct information
       assertTrue(responseBody.contains("2015 Nissan Sentra"));
       assertTrue(responseBody.contains("*New*"));
   }   // end testGetDetailsNew()

   /**
    * Verifies that the locations page opens correctly by searching
    * for key information inside the page. If the database is modified,
    * this test may fail!
    * @throws IOException IOException
    */
   @Test
   public final void testLocations() throws IOException {
       String url = "http://localhost:8080/AwesomeCarsWebApp/awesomecars/";
      
       // create post request
       HttpPost post = new HttpPost(url);

       // Populate post request with basic search parameters
       List<NameValuePair> params = new ArrayList<>();
       params.add(new BasicNameValuePair("action", "locations"));
       
       // Encode parameters as URL encoded form entity to include in
       // body of POST
       UrlEncodedFormEntity requestBody 
           = new UrlEncodedFormEntity(params, Consts.UTF_8);
       
       // Attach URL encoded parameters to requestBody
       post.setEntity(requestBody);
              
       // execute request and receive response
       HttpResponse response = client.execute(post);
       
       // verify status code OK
       assertEquals(HttpStatus.SC_OK, 
               response.getStatusLine().getStatusCode());

       String responseBody = 
               IOUtils.toString(response.getEntity().getContent());
       
       // verify response contains correct information
       assertTrue(responseBody.contains("South Mesquite Road"));
   }    // end testLocations

   /**
    * Verifies that the advanced search form page opens correctly by 
    * searching for key information inside the page. If the database
    * is modified, this test may fail!
    * @throws IOException IOException
    */
   @Test
   public final void testAdvancedSearchForm() throws IOException {
       String url = "http://localhost:8080/AwesomeCarsWebApp/awesomecars/";
      
       // create post request
       HttpPost post = new HttpPost(url);

       // Populate post request with basic search parameters
       List<NameValuePair> params = new ArrayList<>();
       params.add(new BasicNameValuePair("action", "advancedSearch"));
       
       // Encode parameters as URL encoded form entity to include in
       // body of POST
       UrlEncodedFormEntity requestBody 
           = new UrlEncodedFormEntity(params, Consts.UTF_8);
       
       // Attach URL encoded parameters to requestBody
       post.setEntity(requestBody);
              
       // execute request and receive response
       HttpResponse response = client.execute(post);
       
       // verify status code OK
       assertEquals(HttpStatus.SC_OK, 
               response.getStatusLine().getStatusCode());

       String responseBody = 
               IOUtils.toString(response.getEntity().getContent());
       
       // verify response contains correct information
       assertTrue(responseBody.contains("Minimum Gas Mileage"));
   }    // end testAdvancedSearchForm   
}