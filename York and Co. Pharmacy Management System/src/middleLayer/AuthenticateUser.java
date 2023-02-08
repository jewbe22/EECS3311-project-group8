package middleLayer;

import java.util.ArrayList;
import java.util.Arrays;

import presentation.USER;

public class AuthenticateUser { // singleton???
	
//	int num;
//	
//	public AuthenticateUser() {
//		this.num = 10;
//	}
//	
	//made static is that fine?
//	private static ArrayList<int[]> allUsernamesAndPasswordsList = new ArrayList<int[]>();
	private static ArrayList<User> allUsernamesAndPasswordsList = new ArrayList<User>();
	
	public static USER checkUserValid(int username, int password) {
		
//		int usernameInt = Integer.parseInt(username);
		//System.out.println("Username: " + username);
		//System.out.println("Pass: " + password);
//		String pass = Arrays.toString(password);
//		int p = Integer.parseInt(new String(pass));
//		System.out.println("pass " + pass);
		// Creating a string using append() method
//		StringBuilder sb = new StringBuilder();
//      for (int i = 0; i < password.length; i++) {
//          sb.append(password[i]);
//      }String pass = sb.toString();
//      System.out.println("pass " + pass);
//      int passInteger = Integer.parseInt(pass);
		// IF IT IS A VALID USERNAME+PASS, RETURN TRUE
		// ELSE RETURN FALSE
		
		//--hardcoded without objects--//
//		int[] ownerUandP = {1111111111, 11111111};
//		int[] pharmacistUandP = {1234567890, 12345678};
//		
//		allUsernamesAndPasswordsList.add(ownerUandP);
//		allUsernamesAndPasswordsList.add(pharmacistUandP);
//		
//		Boolean foundUsername = false;
//		
//		for (int i = 0; i < allUsernamesAndPasswordsList.size(); i++) {
//			if ((((allUsernamesAndPasswordsList.get(i))[0]) == username) && (((allUsernamesAndPasswordsList.get(i))[1]) == password)) {
//				foundUsername = true;
//			}
//		}
		//--end--//
		
		//--BELOW IS WITH PROPER OBJECTS--//
		User ownerUser = new Owner(1111111111, 11111111);
		User pharmacistUser = new Pharmacist(1234567890, 12345678);
		User patientUser = new Patient("Smith", "John", "5324 yonge St", 1112223333, 1111122222, 11112233);
		USER userType;
		allUsernamesAndPasswordsList.add(ownerUser);
		allUsernamesAndPasswordsList.add(pharmacistUser);
		allUsernamesAndPasswordsList.add(patientUser);
		
		//Boolean foundUsername = false;
		
		for (int i = 0; i < allUsernamesAndPasswordsList.size(); i++) {
			if ((allUsernamesAndPasswordsList.get(i).getUsername() == username) && (allUsernamesAndPasswordsList.get(i).getPassword() == password)) {
				//foundUsername = true;
				System.out.println("class type: " + allUsernamesAndPasswordsList.get(i).getClass().getSimpleName());
				userType = USER.getValue(allUsernamesAndPasswordsList.get(i).getClass().getSimpleName());
				return userType;
			}
		}
		
		return USER.PATIENT;
  
        
	}

}
