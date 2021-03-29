package biz.ei6.interventions.desktop.framework.medias;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Eixa6
 */
public class MediaDTOTest {
    
    public MediaDTOTest() {
    }

    /**
     * Test of getId method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetId() {
        System.out.println("getId");
        
        String id = "60532ce3cc24f90ab7233db3";
        
        MediaDTO instance = new MediaDTO();
        instance.setId(id);
        String result = instance.getId();
        
        assertEquals(id,result,"L'id n'est pas le bon");
        assertTrue(result.length() == 24);
    }

    /**
     * Test of setId method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testSetId() {
        String id = "60532ce3cc24f90ab7233db3";
        MediaDTO instance = new MediaDTO();
        instance.setId(id);
        
        String expResult = instance.getId();
        
        assertEquals(id, expResult);
    }

    /**
     * Test of getIntervention_id method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetIntervention_id() {

        MediaDTO instance = new MediaDTO();
        String intervention_id = "60532ce3cc24f90ab7233db3";
        
        instance.setIntervention_id(intervention_id);
        String result = instance.getIntervention_id();
        
        assertEquals(intervention_id,result,"L'id n'est pas le bon");
        assertTrue(result.length() == 24);
        
    }

    /**
     * Test of setIntervention_id method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testSetIntervention_id() {
        String intervention_id = "60532ce3cc24f90ab7233db3";
        MediaDTO instance = new MediaDTO();
        instance.setIntervention_id(intervention_id);
        String expResult = instance.getIntervention_id();
        
        assertEquals(intervention_id, expResult);
    }

    /**
     * Test of getDate method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetDate() {

        MediaDTO instance = new MediaDTO();
        
        String expResult = "2011-12-03T10:15:30";
        
        instance.setDate(expResult);
        
        String result = instance.getDate();
        
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setDate method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testSetDate() {

        String date = "2011-12-03T10:15:30";
        MediaDTO instance = new MediaDTO();
        instance.setDate(date);

        String expResult = instance.getDate();
        
        assertEquals(date, expResult);
    }

    /**
     * Test of getFileName method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetFileName() {

        MediaDTO instance = new MediaDTO();
        
        String expResult = "test.txt";
        
        instance.setFileName(expResult);
        
        String result = instance.getFileName();
        
        assertEquals(expResult, result);
        assertTrue(!result.substring(result.lastIndexOf(".") + 1).isEmpty()); 
    }

    /**
     * Test of setFileName method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testSetFileName() {

        String fileName = "test.txt";
        MediaDTO instance = new MediaDTO();
        
        instance.setFileName(fileName);
        
        String expResult = instance.getFileName();
        
        assertEquals(fileName, expResult);
    }

    /**
     * Test of getDeleted method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetDeleted() {

        MediaDTO instance = new MediaDTO();
        
        String expResult = "true";
        
        instance.setDeleted(expResult);
        
        String result = instance.getDeleted();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of setDeleted method, of class MediaDTO.
     */
    @org.junit.jupiter.api.Test
    public void testSetDeleted() {
        
        String deleted = "false";
        MediaDTO instance = new MediaDTO();
        
        instance.setDeleted(deleted);
        
        String expResult = instance.getDeleted();
        
        assertEquals(deleted,expResult);
    }
}
