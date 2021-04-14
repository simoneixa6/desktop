package biz.ei6.interventions.desktop.framework.clients;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Eixa6
 */
public class SiteDTOTest {
    
    public SiteDTOTest() {
    }
    

    /**
     * Test of getAddress method, of class SiteDTO.
     */
    @Test
    public void testGetAddress() {
        SiteDTO instance = new SiteDTO();
        String expResult = "3 rue des feuilles";
        instance.setAddress(expResult);
        String result = instance.getAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAddress method, of class SiteDTO.
     */
    @Test
    public void testSetAddress() {
        String address = "3 rue des feuilles";
        SiteDTO instance = new SiteDTO();
        instance.setAddress(address);
        String result = instance.getAddress();
        assertEquals(address,result);
    }

    /**
     * Test of getZipCode method, of class SiteDTO.
     */
    @Test
    public void testGetZipCode() {
        SiteDTO instance = new SiteDTO();
        String expResult = "17852";
        instance.setZipCode(expResult);
        String result = instance.getZipCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of setZipCode method, of class SiteDTO.
     */
    @Test
    public void testSetZipCode() {
        String zipCode = "17582";
        SiteDTO instance = new SiteDTO();
        instance.setZipCode(zipCode);
        String result = instance.getZipCode();
        assertEquals(zipCode,result);
    }

    /**
     * Test of getCity method, of class SiteDTO.
     */
    @Test
    public void testGetCity() {
        SiteDTO instance = new SiteDTO();
        String expResult = "Marennes";
        instance.setAddress(expResult);
        String result = instance.getAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCity method, of class SiteDTO.
     */
    @Test
    public void testSetCity() {
        String city = "Marennes";
        SiteDTO instance = new SiteDTO();
        instance.setCity(city);
        String result = instance.getCity();
        assertEquals(result,city);
    }
}
