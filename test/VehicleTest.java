package awesomecars.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import awesomecars.beans.Vehicle;

/**
 * Tests the Vehicle class's getters and setters.
 * @author Travis
 */
public class VehicleTest {

    /** Test year. */
    public static final int TEST_YEAR = 1000;
    
    /** Test price. */
    public static final int TEST_PRICE = 5000;
    
    /** Test MPG City. */
    public static final int TEST_MPG_CITY = 30;
    
    /** Test MPG Highway. */
    public static final int TEST_MPG_HWY = 40;
    
    /** Test miles. */
    public static final int TEST_MILES = 9000;
    
    /** Tests Vehicle class getter and setter methods. */
    @Test
    public final void testVehicleAccessMethods() {
        Vehicle v = new Vehicle();
        String teststr = "";
        
        teststr = "make";
        v.setMake(teststr);
        assertEquals(teststr, v.getMake());
        
        teststr = "model";
        v.setModel(teststr);
        assertEquals(teststr, v.getModel());

        v.setYear(TEST_YEAR);
        assertEquals(TEST_YEAR, v.getYear());

        v.setPrice(TEST_PRICE);
        assertEquals(TEST_PRICE, v.getPrice());
        
        teststr = "body";
        v.setBodyStyle(teststr);
        assertEquals(teststr, v.getBodyStyle());

        v.setMpgCity(TEST_MPG_CITY);
        assertEquals(TEST_MPG_CITY, v.getMpgCity());
        
        v.setMpgHwy(TEST_MPG_HWY);
        assertEquals(TEST_MPG_HWY, v.getMpgHwy());

        teststr = "description";
        v.setDescription(teststr);
        assertEquals(teststr, v.getDescription());
        
        teststr = "image.jpg";
        v.setImageURL(teststr);
        assertEquals(teststr, v.getImageURL());

        teststr = "category";
        v.setCategory(teststr);
        assertEquals(teststr, v.getCategory());

        teststr = "ABCD";
        v.setVin(teststr);
        assertEquals(teststr, v.getVin());

        teststr = "intcolor";
        v.setIntColor(teststr);
        assertEquals(teststr, v.getIntColor());

        teststr = "extcolor";
        v.setExtColor(teststr);
        assertEquals(teststr, v.getExtColor());

        v.setMiles(TEST_MILES);
        assertEquals(TEST_MILES, v.getMiles());

        teststr = "engine";
        v.setEngineDesc(teststr);
        assertEquals(teststr, v.getEngineDesc());

        teststr = "transmission";
        v.setTransmission(teststr);
        assertEquals(teststr, v.getTransmission());
    }

}
