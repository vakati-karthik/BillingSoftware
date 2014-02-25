package com.karthik.BillingSoftware.JDBC;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.karthik.BillingSoftware.Constants.BillConstants;
import com.karthik.BillingSoftware.Load.Application;

public class CreateNewBillJDBC {
	private Statement statement;
	private String query;

	public boolean addBill(HashMap<String, String> billDetails) {
				
		try {
			statement = Application.connection.createStatement();
		    	query = "INSERT INTO bill_info_table VALUES(NULL, '"
						+ billDetails.get(BillConstants.station) + "', '" 
						+ billDetails.get(BillConstants.deliveryAt) + "', '"
						+ billDetails.get(BillConstants.consignor) + "', '"
						+ billDetails.get(BillConstants.packages) + "', '"
						+ billDetails.get(BillConstants.packagesDescription) + "', '"
						+ billDetails.get(BillConstants.privateMarks) + "', '"
						+ billDetails.get(BillConstants.remarks) + "', '"
						+ billDetails.get(BillConstants.date) + "', '" 
						+ billDetails.get(BillConstants.truckNumber) + "', '"
						+ billDetails.get(BillConstants.consignee) + "', '"
						+ billDetails.get(BillConstants.actualWeight) + "', '"
						+ billDetails.get(BillConstants.freightPerTonne) + "', '"
						+ billDetails.get(BillConstants.checkPost) + "', '"
						+ billDetails.get(BillConstants.sidePoles) + "', '"
						+ billDetails.get(BillConstants.craineCharge) + "', '"
						+ billDetails.get(BillConstants.roadExpansion) + "', '"
						+ billDetails.get(BillConstants.doorOpen) + "', '"   
						+ billDetails.get(BillConstants.weighmentCharge) + "', '"
						+ billDetails.get(BillConstants.valueOfGoods) + "', '"
						+ billDetails.get(BillConstants.driverCharges) + "', '"
						+ billDetails.get(BillConstants.loadingCharges) + "', '"  
						+ billDetails.get(BillConstants.checkPost1) + "', '"
						+ billDetails.get(BillConstants.sidePoles1) + "', '"
						+ billDetails.get(BillConstants.otherExpenses) + "');";
		    	statement.executeUpdate(query);
		    	statement.close();
		    	Application.connection.commit();
				return true;
		}
		catch (SQLException e) 
		{			
			System.out.println("Insert Operation Failed");
			return false;
		}
	}
}
