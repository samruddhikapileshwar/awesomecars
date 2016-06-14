package awesomecars.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import awesomecars.beans.Dealership;

/** Tests for Dealership class. */
public class DealershipTest {

    /** Zip code test. */
    public static final int TEST_ZIP = 12345;
    
    /** Default constructor. */
    public DealershipTest() {
        super();
    }
    
    /** 
     * Tests getter and setter methods for Dealership class.
     */
    @Test
    public final void testDealershipAccessMethods() {
        Dealership d = new Dealership();

        d.setName("store");
        assertEquals("store", d.getName());

        d.setAddress("address");
        assertEquals("address", d.getAddress());
        
        d.setCity("city");
        assertEquals("city", d.getCity());
        
        d.setState("state");
        assertEquals("state", d.getState());

        d.setZip(TEST_ZIP);
        assertEquals(TEST_ZIP, d.getZip());

        d.setPhoneNumber("123-456");
        assertEquals("123-456", d.getPhoneNumber());

        d.setHours("hours");
        assertEquals("hours", d.getHours());
    }
}
