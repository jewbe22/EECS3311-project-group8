package testCases.Unit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import middleLayer.Order;
import middleLayer.Report;
import databaseDAO.OrderStub;

class ReportUnitTest {
	//done
	@Test
	void testCalculateRevenue() {
		//fail("Not yet implemented");
		Report report = new Report();
		report.setOrderDAO(new OrderStub());
		try {
			assertEquals(70.0, report.calculateRevenue(), 0.001);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testCalculateProfit() {
		//fail("Not yet implemented");
		Report report = new Report();
		report.setOrderDAO(new OrderStub());
		try {
			assertEquals(21.0, report.calculateProfit(), 0.01);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testSeeSummaryOfSales() {
		//fail("Not yet implemented");
		Report report = new Report();
		OrderStub stub = new OrderStub();
		report.setOrderDAO(stub);
		String expected = new String();
		for(Order o : stub.orderList) {
			expected += o.toString();
		}
		assertEquals(expected, report.seeSummaryOfSales() );
	}

}
