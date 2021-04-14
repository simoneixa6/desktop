package biz.ei6.interventions.desktop.framework.clients;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Eixa6
 */
public class ClientDTOTest {
    
    public ClientDTOTest() {
    }

    /**
     * Test of getId method, of class ClientDTO.
     */
    @Test
    public void testGetId() {
        ClientDTO instance = new ClientDTO();
        String expResult = "60532ce3cc24f90ab7233db3";
        instance.setId(expResult);
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class ClientDTO.
     */
    @Test
    public void testSetId() {
        String _id = "60532ce3cc24f90ab7233db3";
        ClientDTO instance = new ClientDTO();
        instance.setId(_id);
        String result = instance.getId();
        assertEquals(_id, result);
    }

    /**
     * Test of getCivility method, of class ClientDTO.
     */
    @Test
    public void testGetCivility() {
        ClientDTO instance = new ClientDTO();
        String expResult = "M.";
        instance.setCivility(expResult);
        String result = instance.getCivility();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCivility method, of class ClientDTO.
     */
    @Test
    public void testSetCivility() {
        String civility = "Mme.";
        ClientDTO instance = new ClientDTO();
        instance.setCivility(civility);
        String result = instance.getCivility();
        assertEquals(result,civility);
    }

    /**
     * Test of getName method, of class ClientDTO.
     */
    @Test
    public void testGetName() {
        ClientDTO instance = new ClientDTO();
        String expResult = "Paulo";
        instance.setName(expResult);
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class ClientDTO.
     */
    @Test
    public void testSetName() {
        String name = "Paulo";
        ClientDTO instance = new ClientDTO();
        instance.setName(name);
        String result = instance.getName();
        assertEquals(name,result);
    }

    /**
     * Test of getLastname method, of class ClientDTO.
     */
    @Test
    public void testGetLastname() {
        ClientDTO instance = new ClientDTO();
        String expResult = "jean";
        instance.setLastname(expResult);
        String result = instance.getLastname();
        assertEquals(expResult, result);
    }

    /**
     * Test of setLastname method, of class ClientDTO.
     */
    @Test
    public void testSetLastname() {
        String lastname = "jean";
        ClientDTO instance = new ClientDTO();
        instance.setLastname(lastname);
        String result = instance.getLastname();
        assertEquals(lastname, result);
    }

    /**
     * Test of getCompany method, of class ClientDTO.
     */
    @Test
    public void testGetCompany() {
        ClientDTO instance = new ClientDTO();
        String expResult = "eixa6";
        instance.setCompany(expResult);
        String result = instance.getCompany();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCompany method, of class ClientDTO.
     */
    @Test
    public void testSetCompany() {
        String company = "eixa6";
        ClientDTO instance = new ClientDTO();
        instance.setCompany(company);
        String result = instance.getCompany();
        assertEquals(company, result);
    }

    /**
     * Test of getCompanyStatus method, of class ClientDTO.
     */
    @Test
    public void testGetCompanyStatus() {
        ClientDTO instance = new ClientDTO();
        String expResult = "EIRL";
        instance.setCompanyStatus(expResult);
        String result = instance.getCompanyStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCompanyStatus method, of class ClientDTO.
     */
    @Test
    public void testSetCompanyStatus() {
        String companyStatus = "EIRL";
        ClientDTO instance = new ClientDTO();
        instance.setCompanyStatus(companyStatus);
        String result = instance.getCompanyStatus();
        assertEquals(companyStatus, result);
    }

    /**
     * Test of getPhone method, of class ClientDTO.
     */
    @Test
    public void testGetPhone() {
        ClientDTO instance = new ClientDTO();
        String expResult = "0889889980";
        instance.setPhone(expResult);
        String result = instance.getPhone();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPhone method, of class ClientDTO.
     */
    @Test
    public void testSetPhone() {
        String phone = "0889889980";
        ClientDTO instance = new ClientDTO();
        instance.setPhone(phone);
        String result = instance.getPhone();
        assertEquals(phone, result);
    }

    /**
     * Test of getMail method, of class ClientDTO.
     */
    @Test
    public void testGetMail() {
        ClientDTO instance = new ClientDTO();
        String expResult = "eixa6@eixa6.com";
        instance.setMail(expResult);
        String result = instance.getMail();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMail method, of class ClientDTO.
     */
    @Test
    public void testSetMail() {
        String mail = "eixa6@eixa6.com";
        ClientDTO instance = new ClientDTO();
        instance.setMail(mail);
        String result = instance.getMail();
        assertEquals(mail,result);
    }

    /**
     * Test of getAddresses method, of class ClientDTO.
     */
    @Test
    public void testGetAddresses() {
        System.out.println("getAddresses");
        ClientDTO instance = new ClientDTO();
        List<SiteDTO> expResult = null;
        List<SiteDTO> result = instance.getAddresses();
        assertEquals(expResult, result);
    }


    /**
     * Test of getFirstVisitDate method, of class ClientDTO.
     */
    @Test
    public void testGetFirstVisitDate() {
        System.out.println("getFirstVisitDate");
        ClientDTO instance = new ClientDTO();
        String expResult = "2011-12-03T10:15:30";
        instance.setFirstVisitDate(expResult);
        String result = instance.getFirstVisitDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setFirstVisitDate method, of class ClientDTO.
     */
    @Test
    public void testSetFirstVisitDate() {
        System.out.println("setFirstVisitDate");
        String firstVisitDate = "2011-12-03T10:15:30";
        ClientDTO instance = new ClientDTO();
        instance.setFirstVisitDate(firstVisitDate);
        String result = instance.getFirstVisitDate();
        assertEquals(result, firstVisitDate);
    }

    /**
     * Test of getHow method, of class ClientDTO.
     */
    @Test
    public void testGetHow() {
        ClientDTO instance = new ClientDTO();
        String expResult = "loremipsum";
        instance.setHow(expResult);        
        String result = instance.getHow();
        assertEquals(expResult, result);;
    }

    /**
     * Test of setHow method, of class ClientDTO.
     */
    @Test
    public void testSetHow() {
        System.out.println("setHow");
        String how = "loremipsum";
        ClientDTO instance = new ClientDTO();
        instance.setHow(how);
        String result = instance.getHow();
        assertEquals(how, result);
    }

    /**
     * Test of getWhy method, of class ClientDTO.
     */
    @Test
    public void testGetWhy() {
        ClientDTO instance = new ClientDTO();
        String expResult = "loremipsum";
        instance.setWhy(expResult);
        String result = instance.getWhy();
        assertEquals(expResult, result);
    }

    /**
     * Test of setWhy method, of class ClientDTO.
     */
    @Test
    public void testSetWhy() {
        String why = "loremipsum";
        ClientDTO instance = new ClientDTO();
        instance.setWhy(why);
        String result = instance.getWhy();
        assertEquals(why, result);
    }

    /**
     * Test of getProblematic method, of class ClientDTO.
     */
    @Test
    public void testGetProblematic() {
        ClientDTO instance = new ClientDTO();
        String expResult = "true";
        instance.setProblematic(expResult);
        String result = instance.getProblematic();
        assertEquals(expResult, result);
    }

    /**
     * Test of setProblematic method, of class ClientDTO.
     */
    @Test
    public void testSetProblematic() {
        System.out.println("setProblematic");
        String problematic = "true";
        ClientDTO instance = new ClientDTO();
        instance.setProblematic(problematic);
        String result = instance.getProblematic();
        assertEquals(problematic, result);
    }

    /**
     * Test of getDeleted method, of class ClientDTO.
     */
    @Test
    public void testGetDeleted() {
        ClientDTO instance = new ClientDTO();
        String expResult = "false";
        instance.setDeleted(expResult);
        String result = instance.getDeleted();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDeleted method, of class ClientDTO.
     */
    @Test
    public void testSetDeleted() {
        System.out.println("setDeleted");
        String deleted = "false";
        ClientDTO instance = new ClientDTO();
        instance.setDeleted(deleted);
        String result = instance.getDeleted();
        assertEquals(deleted, result);
    }
}
