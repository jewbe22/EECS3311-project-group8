package testCases.IntegrationTests;
import databaseDAO.superDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import middleLayer.Users.*;



class PharmacistTest {
    private Patient patient1;
    static String pass = "hello123";  // TA please change this according to your mySQL password in order for the tests to work



    @Test
    void searchPatientByName() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist p = new Pharmacist(1234567890, 12345678);
        assertEquals("[First name: SMITH, Last name: JOHN, Address: 5324 YONGE ST, Phone Number: 1112223333, Health Card: 1111122222, Date of birth: 11111222\n]", p.searchPatientByName("Smith", "FirstName").toString());
    }

    @Test

    void addPatient() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListOfPatients val = ListOfPatients.getInstance();
        ArrayList<Patient> listOfPat = val.getAllPatientsList();
        
        long newHealthCardNum = 1111144444;
        for (int i = 0; i < listOfPat.size(); i++) {
        	if (listOfPat.get(i).getHealthCardNum() == newHealthCardNum) {
        		newHealthCardNum = -1; // patient already exists
        		break;
        	}
        }
        
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        
        if (newHealthCardNum != -1) {
        	patient1 = new Patient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
             
            
             comparator1.add(patient1);
             try {
     			subject1.addPatient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012);
     		} catch (Exception e) {
     			e.printStackTrace();
     		}

             
             ArrayList<Patient> result = subject1.searchPatientByName("MAN", "LastName");

             assertEquals(comparator1.toString(), result.toString());
        }
        else {
        	try {
        		assertThrows(Exception.class, () -> subject1.addPatient("Test", "Man", "5334 yonge St", 1112224444, 1111144444, 11222012));
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        }
       
    }
   
    @Test
    void addPatientInvalid() { // negative health card num
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        long newHealthCardNum = -555;
      
        Pharmacist p = new Pharmacist(0,0);
        assertThrows(Exception.class, () -> p.addPatient("another", "test", "123 fake street", 647, newHealthCardNum, 20221010));
       
    }
    
    @Test
    void addPatientInvalid2() { // negative phone number
    	try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListOfPatients val = ListOfPatients.getInstance();
        ArrayList<Patient> listOfPat = val.getAllPatientsList();
        
        Pharmacist subject1 = new Pharmacist(0, 0);
        assertThrows(Exception.class, () -> subject1.addPatient("New", "Test", "123 Fake Street", -416, 123789, 11222012));
    }
    
    @Test
    void searchPatientByNameTestValidInput1()  {	
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("SMITH", "FirstName");
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput2(){
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("JOHN", "LastName");
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput3() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("SMITH JOHN", "FullName");
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertEquals(comparator1.toString(), result.toString());
    }
    
    @Test
    void searchPatientByNameTestValidInput4() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("", "FirstName");
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));	
    	assertNotEquals(comparator1.toString(), result.toString());
    }

    @Test
    void searchPatientByNameTestValidInput5()  {	
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = subject1.searchPatientByName("SMITH Man", "FirstName");
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }


    @Test
    void searchPatientByNameTestValidInput8()  {	
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = subject1.searchPatientByName("SMITH ", "FirstName");
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    @Test
    void searchPatientByNameTestValidInput9()  {	
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = subject1.searchPatientByName(" SMITH", "FirstName");
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    @Test
    void searchPatientByNameTestValidInput10()  {	
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = subject1.searchPatientByName(" JOHN", "LastName");
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    @Test
    void searchPatientByNameTestValidInput11()  {	
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = subject1.searchPatientByName("JOHN ", "LastName");
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }

    @Test
    void searchPatientByNameTestValidInput12()  {	
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist subject1 = new Pharmacist(0, 0);
        ArrayList<Patient> comparator1 = new ArrayList<Patient>();
        ArrayList<Patient> result = subject1.searchPatientByName("SMITH, JOHN", "LastName");
        comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
        assertEquals(comparator1.toString(), result.toString());
    }
    @Test
    void searchPatientByNameTestInvalidInput1() {
        try {
            superDAO.setPassword(pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Pharmacist subject1 = new Pharmacist(0, 0);
    	ArrayList<Patient> comparator1 = new ArrayList<Patient>();
    	ArrayList<Patient> result = subject1.searchPatientByName("", "FirstName");
    	comparator1.add(new Patient("SMITH", "JOHN", "5324 YONGE ST", 1112223333, 1111122222, 11111222));
    	assertNotEquals(comparator1.toString(), result.toString());
    }

   
}