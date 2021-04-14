package biz.ei6.interventions.desktop.framework.interventions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Eixa6
 */
public class PeriodDTOTest {

    public PeriodDTOTest() {
    }

    /**
     * Test of getDate method, of class PeriodDTO.
     */
    @Test
    public void testGetDate() {
        PeriodDTO instance = new PeriodDTO();
        String expResult = "2011-12-03T10:15:30";
        instance.setDate(expResult);
        String result = instance.getDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDate method, of class PeriodDTO.
     */
    @Test
    public void testSetDate() {
        String date = "2011-12-03T10:15:30";
        PeriodDTO instance = new PeriodDTO();
        instance.setDate(date);
        String result = instance.getDate();
        assertEquals(date, result);
    }

    /**
     * Test of getStart method, of class PeriodDTO.
     */
    @Test
    public void testGetStart() {
        PeriodDTO instance = new PeriodDTO();
        String expResult = "2021-03-30T14:25:00Z";
        instance.setStart(expResult);
        String result = instance.getStart();
        assertEquals(expResult, result);
    }

    /**
     * Test of setStart method, of class PeriodDTO.
     */
    @Test
    public void testSetStart() {
        System.out.println("setStart");
        String start = "2021-03-30T14:25:00Z";
        PeriodDTO instance = new PeriodDTO();
        instance.setStart(start);
        String result = instance.getStart();
        assertEquals(result, start);
    }

    /**
     * Test of getEnd method, of class PeriodDTO.
     */
    @Test
    public void testGetEnd() {
        PeriodDTO instance = new PeriodDTO();
        String expResult = "2021-03-30T14:25:00Z";
        instance.setEnd(expResult);
        String result = instance.getEnd();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEnd method, of class PeriodDTO.
     */
    @Test
    public void testSetEnd() {
        System.out.println("setEnd");
        String end = "2021-03-30T14:25:00Z";
        PeriodDTO instance = new PeriodDTO();
        instance.setEnd(end);
        String result = instance.getEnd();
        assertEquals(result, end);
    }

}
