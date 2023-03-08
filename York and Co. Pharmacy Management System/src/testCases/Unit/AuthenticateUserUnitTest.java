package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import databaseDAO.UserStub;
import middleLayer.AuthenticateUser;
import presentation.USER;

class AuthenticateUserUnitTest {

	@Test
	void testCheckUserValid() {
		//fail("Not yet implemented");

		AuthenticateUser auth = AuthenticateUser.getInstance();
		UserStub stub = new UserStub();
		auth.set_userDAO(stub);
		
		USER result;
		//input account info for PATIENT
		result = auth.checkUserValid(0, 0);
		assertEquals(result, USER.PATIENT);
		//input account info for OWNER
		result = auth.checkUserValid(0, 0);
		assertEquals(result, USER.OWNER);
		//input account infor for PHARMACIST
		result = auth.checkUserValid(0, 0);
		assertEquals(result, USER.PHARMACIST);
	}

}
