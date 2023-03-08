package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import databaseDAO.UserStub;
import middleLayer.ListOfPatients;
import middleLayer.Patient;

class ListOfPatientsUnitTest {

	@Test
	void testUpdatePatientListFromDatabase() {
		//fail("Not yet implemented");
		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub stub = new UserStub();
		patients.set_userDAO(stub);
		patients.updatePatientListFromDatabase();
		assertEquals(patients.getAllPatientsList(), stub.patientList);
	}

	@Test
	void testSearchPatientWithID() {
		//fail("Not yet implemented");
		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub stub = new UserStub();
		patients.set_userDAO(stub);
		Patient result;
		Patient expected;
		result = patients.searchPatientWithID(0);
		expected = stub.patientList.get(0);
		assertEquals(result, expected);
		result = patients.searchPatientWithID(99);
		assertEquals(result, null);
	}

	@Test
	void testModifyPatientDetails1() {
		//fail("Not yet implemented");
		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub stub = new UserStub();
		patients.set_userDAO(stub);
		try {
			assertFalse(patients.modifyPatientDetails(99, null, null, null, null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		fname.setText("");
		lname.setText("");
		phoneNum.setText("");
		address.setText("");
		assertThrows(Exception.class, () -> patients.modifyPatientDetails(1, fname, lname, phoneNum, address));
	}
	
	@Test
	void testModifyPatientDetails2() {
		ListOfPatients patients = ListOfPatients.getInstance();
		UserStub stub = new UserStub();
		patients.set_userDAO(stub);
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		JTextField phoneNum = new JTextField();
		JTextField address = new JTextField();
		String result = new String();
		fname.setText("fname");
		try {
			fname.setText("fname");
			patients.modifyPatientDetails(0, fname, lname, phoneNum, address);
			result = patients.getAllPatientsList().get(0).getFirstName();
			assertEquals(result, "fname");
			
			lname.setText("lname");
			patients.modifyPatientDetails(0, fname, lname, phoneNum, address);
			result = patients.getAllPatientsList().get(0).getLastName();
			assertEquals(result, "lname");
			
			phoneNum.setText("11122223333");
			patients.modifyPatientDetails(0, fname, lname, phoneNum, address);
			result = Integer.toString( patients.getAllPatientsList().get(0).getPhoneNum());
			assertEquals(result, "11122223333");
			
			address.setText("random address");
			patients.modifyPatientDetails(0, fname, lname, phoneNum, address);
			result = patients.getAllPatientsList().get(0).getAddress();
			assertEquals(result, "random address");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
