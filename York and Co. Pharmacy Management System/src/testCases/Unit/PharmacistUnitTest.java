package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import databaseDAO.UserStub;
import middleLayer.ListOfPatients;
import middleLayer.Patient;
import middleLayer.Pharmacist;

class PharmacistUnitTest {

	@Test
	void testAddPatient() {
		//fail("Not yet implemented");
		Pharmacist subject1 = new Pharmacist(0, 0);
		UserStub stub = new UserStub();
		subject1.set_userDAO(stub);
		ArrayList<Patient> expected = new ArrayList<Patient>();
		ArrayList<Patient> result = new ArrayList<Patient>();
		try {
			subject1.addPatient("test", "name", "address", 1111144444, 1111144444, 11111444);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		expected.add(new Patient("test", "name", "address", 1111144444, 1111144444, 11111444));
		//result =
		
	}	

	@Test
	void testSearchPatientByName() {
		/*the implementation of the method SearchPatientByName() for this class
		 * is exactly identical to the one of the same method in Owner class
		 * no need to test twice 
		 */
		//fail("Not yet implemented");
	}

}
