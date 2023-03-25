package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import middleLayer.MerchandiseInventory.Merchandise;
import middleLayer.Orders.*;
import presentation.USER;
import databaseDAO.superDAO;
import databaseDAO.MerchandiseData.MerchandiseStub;
import databaseDAO.OrderData.OrderStub;
import databaseDAO.UserData.UserStub;

class ListOfOrdersUnitTest {
	public static ListOfOrders orders;
	public static OrderStub orderStub;
	public static MerchandiseStub merStub;
	public static UserStub userStub;
	//beforeAll is just used to established a connection with the database to prevent exceptions. The database is NOT being accessed for unit tests
	@BeforeEach
	public void before() {
		try {
			//superDAO.setPassword("Motp1104#");// TA please change this according to your mySQL password in order for the tests to work
			orderStub = new OrderStub();
			merStub = new MerchandiseStub();
			userStub = new UserStub();
			orders = ListOfOrders.getInstance(orderStub, merStub, userStub);
			orders.setOrderDAO(orderStub, merStub, userStub);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Test
	void testUpdateOrderListFromDatabase() {
		//orders = ListOfOrders.getInstance();
		//orders.setOrderDAO(new OrderStub());
		//orders.updateOrderListFromDatabase();
		ArrayList<Order> expected = new ArrayList<Order>();
		ArrayList<Order> result = new ArrayList<Order>();

		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);  
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		expected.add(order1);
		expected.add(order2);
		expected.add(order3);
		expected.add(order4);


		result = orders.getListofAllOrders();
		assertEquals(expected, result);

		
	}
	
	@Test
	void testAddOrderToDatabase1() {
		//orders = ListOfOrders.getInstance();
		//orders.setOrderDAO(new OrderStub());
		//orders.updateOrderListFromDatabase();
		ArrayList<Order> expected = new ArrayList<Order>();
		ArrayList<Order> result = new ArrayList<Order>();

		Order newOrder = new Order(5, 1, 1111122222, 5, 10.00, false);  
		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);  
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		expected.add(order1);
		expected.add(order2);
		expected.add(order3);
		expected.add(order4);
		expected.add(newOrder);
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			fail(e.getMessage());
		}
		result = orders.getListofAllOrders();
		assertEquals(expected, result);
	}
	
	@Test
	void testAddOrderToDatabase2() {

		Order newOrder = new Order(5, 1, 1111122222, -1, 10.00, true);  
		String errorString = null;

		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		
		assertEquals("Quantity Bought Must Be Positive!", errorString);
	}
	
	@Test
	void testAddOrderToDatabase3() {
		Order newOrder = new Order(5, 10, 1111122222, 5, 10.00, false);  
		String errorString = null;
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Medication doesn't exist!", errorString);
	}
	
	@Test
	void testAddOrderToDatabase4() {
		Order newOrder = new Order(5, 1, 1111122229, 5, 10.00, false);  
		String errorString = null;
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Patient doesn't exist!", errorString);
	}
	
	@Test
	void testAddOrderToDatabase5() {
		Order newOrder = new Order(5, 4, 1111122222, 5, 10.00, false);  
		String errorString = null;
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Not an OTC!", errorString);
	}
	
	@Test
	void testAddOrderToDatabase6() {
		Order newOrder = new Order(5, 1, 1111122222, 5, 10.00, false);  
		String errorString = null;
		merStub.allInventoryStub.get(0).setQuantity(0);
		try {
			orders.addOrderToDatabase(newOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			errorString = e.getMessage();
		}

		assertEquals("Check inventory!", errorString);
		
		merStub.allInventoryStub.get(0).setQuantity(3);
		errorString = null;
		try {
			orders.addOrderToDatabase(newOrder);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			errorString = e.getMessage();
		}
		assertEquals("Check inventory!", errorString);
	}
	
	@Test
	void testAddPresOrderToDb1() {
		Prescription pres = new Prescription(5, 3, 1111122222, 0);
		String errorString = null;
		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Refills must be positive", errorString);
		
	}
	
	@Test
	void testAddPresOrderToDb2() {
		Prescription pres = new Prescription(5, 10, 1111122222, 5);
		String errorString = null;
		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
			
		}
		assertEquals("Medication doesn't exist!", errorString);
	}
	
	@Test
	void testAddPresOrderToDb3() {
		Prescription pres = new Prescription(5, 1, 1111122222, 5);
		String errorString = null;
		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
			
		}
		assertEquals("Not an Rx!", errorString);
	}
	
	@Test
	void testAddPresOrderToDb4() {
		Prescription pres = new Prescription(5, 3, 1111155555, 5);
		String errorString = null;
		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
			
		}
		assertEquals("Patient doesn't exist!", errorString);
	}
	
	@Test
	void testAddPresOrderToDb5() {
		Prescription pres = new Prescription(3, 3, 1111122222, 5);

		try {
			orders.addPresFormToDb(pres);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(pres, orderStub.prescriptionList.get(2));
	}
	
	@Test
	void testAddRefillToDatabase1() {
		Order order = new Order(5, 3, 1111122222, 1, 20.00, true);
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(order, orderStub.orderList.get(4));
	}
	
	@Test
	void testAddRefillToDatabase2() {
		Order order = new Order(5, 10, 1111122222, 1, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("No record found of prescription!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase3() {
		Order order = new Order(5, 3, 1111122225, 1, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("No record found of prescription!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase4() {
		Order order = new Order(5, 3, 1111122222, -1, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Quantity Bought Must Be Positive!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase5() {
		Order order = new Order(5, 3, 1111122222, 5, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("No refills left!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase6() {
		Order order = new Order(5, 3, 1111122222, 11, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Only have " + 5 +  " refills left!", errorString);
	}
	
	@Test
	void testAddRefillToDatabase7() {
		Order order = new Order(5, 3, 1111122222, 11, 20.00, true);
		String errorString = null;
		try {
			orders.addRefillToDatabase(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Only have " + 5 +  " refills left!", errorString);
	}
	
	@Test
	void testSpecificPatientOrderHistory1() {
		ArrayList<Order> expected = orderStub.orderList;
		try {
			assertEquals(expected, orders.specificPatientOrderHistory(1111122222));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
	
	@Test
	void testSpecificPatientOrderHistory2() {
		String errorString = null;
		try {
			orders.specificPatientOrderHistory(111112222);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Please enter a valid, positive, 10-digit health card number", errorString);
	}
	@Test
	void testSpecificPatientOrderHistory3() {
		String errorString = null;
		try {
			orders.specificPatientOrderHistory(11111222222L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Please enter a valid, positive, 10-digit health card number", errorString);
	}
	@Test
	void testSpecificPatientOrderHistory4() {
		String errorString = null;
		try {
			orders.specificPatientOrderHistory(1111122223);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errorString = e.getMessage();
		}
		assertEquals("Patient doesn't exist!", errorString);
	}
	
	@Test
	void testOutputOrderHistoryDetails1() {
		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);   
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		ArrayList<Order> expectedOrders = new ArrayList<Order>();
		
		expectedOrders.add(order1);
		expectedOrders.add(order2);
		expectedOrders.add(order3);
		expectedOrders.add(order4);

		ArrayList<String> expectedString = new ArrayList<String>();
		expectedString.add("ORDER HISTORY OF PATIENT WITH HEALTH CARD NUMBER " + 1111122222 + "\n\n");
		
		int orderNum = 1;
		Merchandise associatedMedication = null;
		for (Order o : expectedOrders) {
			String oneFullOrder = "";
			oneFullOrder += "ORDER #" + orderNum + "\n";
			oneFullOrder += "Medication ID: " + o.getMedicationID() + "\nQuantity bought: " + o.getQuantityBought() + "\nTotal price: " + o.getTotalPriceOfOrder(); 
			associatedMedication = orderStub.medications.get(o.getMedicationID()-1);
			String OTCorRx = "Rx";
			if (associatedMedication.getisOTC()) {
				OTCorRx = "OTC";
			}
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\nOTCorRx: " + OTCorRx + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			expectedString.add(oneFullOrder);
			orderNum++;
		}
		try {
			assertEquals(expectedString, orders.outputOrderHistoryDetails(1111122222, USER.OWNER));
			assertEquals(expectedString, orders.outputOrderHistoryDetails(1111122222, USER.PHARMACIST));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		
	}
	
	@Test
	void testOutputOrderHistoryDetails2() {
		Order order1 = new Order(1, 1, 1111122222, 5, 10.00, false);   
		Order order2 = new Order(2, 2, 1111122222, 5, 15.00, false);
		Order order3 = new Order(3, 3, 1111122222, 5, 20.00, true);
		Order order4 = new Order(4, 4, 1111122222, 5, 25.00, true);
		ArrayList<Order> expectedOrders = new ArrayList<Order>();
		
		expectedOrders.add(order1);
		expectedOrders.add(order2);
		expectedOrders.add(order3);
		expectedOrders.add(order4);

		ArrayList<String> expectedString = new ArrayList<String>();
		expectedString.add("YOUR ORDERS:\n\n");
		
		int orderNum = 1;
		Merchandise associatedMedication = null;
		for (Order o : expectedOrders) {
			String oneFullOrder = "";
			oneFullOrder += "ORDER #" + orderNum + "\n";
			oneFullOrder += "Medication ID: " + o.getMedicationID() + "\nQuantity bought: " + o.getQuantityBought() + "\nTotal price: " + o.getTotalPriceOfOrder(); 
			associatedMedication = orderStub.medications.get(o.getMedicationID()-1);
			String OTCorRx = "Rx";
			if (associatedMedication.getisOTC()) {
				OTCorRx = "OTC";
			}
			
			oneFullOrder += "\nMEDICATION DETAILS: \nName: " + associatedMedication.getName() + "\nType: " + associatedMedication.getType() + "\nForm: " + associatedMedication.getForm() + "\nOTCorRx: " + OTCorRx + "\n";
			
			oneFullOrder += "\n";
			oneFullOrder += "----------------------------\n";
			expectedString.add(oneFullOrder);
			orderNum++;
		}
		try {
			assertEquals(expectedString, orders.outputOrderHistoryDetails(1111122222, USER.PATIENT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		
	}
	@Test
	void testOutputOrderHistoryDetails3() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Patient with health card number " + 2222233333L + " has not made any orders.");
		try {
			assertEquals(expected, orders.outputOrderHistoryDetails(2222233333L, USER.OWNER));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
	@Test
	void testOutputOrderHistoryDetails4() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("Patient with health card number " + 2222233333L + " has not made any orders.");
		try {
			assertEquals(expected, orders.outputOrderHistoryDetails(2222233333L, USER.PHARMACIST));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
	
	@Test
	void testOutputOrderHistoryDetails5() {
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("You have not made any orders.");
		try {
			assertEquals(expected, orders.outputOrderHistoryDetails(2222233333L, USER.PATIENT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
	@Test
	void testSpecificPatientPres1() {
		ArrayList<Prescription> expected = new ArrayList<Prescription>();
		Prescription pres1 = new Prescription(1, 3, 1111122222, 10);
		Prescription pres2 = new Prescription(1, 4, 1111122222, 20);
		expected.add(pres1);
		expected.add(pres2);
		try {
			assertEquals(expected, orders.specificPatientPres(1111122222));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
	
	@Test 
	void testSpecificPatientPres2(){
		String result = null;
		try {
			orders.specificPatientPres(111112222);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals( "Please enter a valid, positive, 10-digit health card number", result);
	}
	
	@Test 
	void testSpecificPatientPres3(){
		String result = null;
		try {
			orders.specificPatientPres(11111222222L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals( "Please enter a valid, positive, 10-digit health card number", result);
	}
	
	@Test 
	void testSpecificPatientPres4(){
		String result = null;
		try {
			orders.specificPatientPres(1111122223);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage();
		}
		assertEquals( "Patient doesn't exist!", result);
	}
	
	@Test
	void testSpecificPatientPres5() {
		ArrayList<Prescription> expected = new ArrayList<Prescription>();
		ArrayList<Prescription> result = null;
		try {
			result = orders.specificPatientPres(2222233333L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(expected, result);
		
		
	}
	


}
