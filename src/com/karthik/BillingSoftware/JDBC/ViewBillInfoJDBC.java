package com.karthik.BillingSoftware.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.karthik.BillingSoftware.Constants.BillConstants;
import com.karthik.BillingSoftware.Constants.UIConstants;
import com.karthik.BillingSoftware.Load.Application;

public class ViewBillInfoJDBC {
	private Statement statement;
	private String query;
	private HashMap<String, String> billDetails;
	
	public HashMap<String, String> getBillInfo(String billNo) {
		
		try {
			statement = Application.connection.createStatement();
	    	query = "SELECT * FROM bill_info_table WHERE bill_number = " + billNo + ";";
	    	ResultSet rs = statement.executeQuery(query);
	        if (rs.next()) {
	        	billDetails = new HashMap<String, String>();
	        	billDetails.put(BillConstants.station, rs.getString("station"));
	        	billDetails.put(BillConstants.deliveryAt, rs.getString("delivery_at"));
	        	billDetails.put(BillConstants.consignor, rs.getString("consignor"));
	        	billDetails.put(BillConstants.packages, "" + rs.getInt("no_of_packages"));
	        	billDetails.put(BillConstants.packagesDescription, rs.getString("description_said_to_contain"));
	        	billDetails.put(BillConstants.privateMarks, rs.getString("private_marks"));
	        	billDetails.put(BillConstants.remarks, rs.getString("remarks"));
	        	String[] date = rs.getString("bill_date").split(" ");
	        	billDetails.put(UIConstants.day, date[0]);
	        	billDetails.put(UIConstants.month, date[1]);
	        	billDetails.put(UIConstants.year, date[2]);
	        	billDetails.put(BillConstants.truckNumber, rs.getString("truck_number"));
	        	billDetails.put(BillConstants.consignee, rs.getString("consignee"));
	        	String[] weight = rs.getString("actual_weight").split(" ");
	        	billDetails.put(UIConstants.weight, weight[0]);
	        	billDetails.put(UIConstants.weightUnits, weight[1]);
	        	billDetails.put(BillConstants.freightPerTonne, "" + rs.getFloat("freight_per_tonne"));
	        	billDetails.put(BillConstants.checkPost, "" + rs.getFloat("check_post"));
	        	billDetails.put(BillConstants.sidePoles, "" + rs.getFloat("side_poles"));
	        	billDetails.put(BillConstants.craineCharge, "" + rs.getFloat("craine_charge"));
	        	billDetails.put(BillConstants.roadExpansion, "" + rs.getFloat("road_expansion"));
	        	billDetails.put(BillConstants.doorOpen, "" + rs.getFloat("door_open"));
	        	billDetails.put(BillConstants.weighmentCharge, "" + rs.getFloat("weighment_charge"));
	        	billDetails.put(BillConstants.valueOfGoods, "" + rs.getFloat("value_of_goods_in_rs"));
	        	billDetails.put(BillConstants.driverCharges, "" + rs.getFloat("driver_charges"));
	        	billDetails.put(BillConstants.loadingCharges, "" + rs.getFloat("loading_charges"));
	        	billDetails.put(BillConstants.sidePoles1, "" + rs.getFloat("sidepoles1"));
	        	billDetails.put(BillConstants.checkPost1, "" + rs.getFloat("checkpost1"));
	        	billDetails.put(BillConstants.otherExpenses, "" + rs.getFloat("other_expenses"));
	           rs.close();
	           statement.close();
	           return billDetails;
	        }
	        else {
	        	rs.close();
		        statement.close();
	        	return null;
	        }
		}
		catch (SQLException e) 
		{			
			System.out.println("Insert Operation Failed");
			return null;
		}		
	}
	
	public boolean updateBill(HashMap<String, String> billDetails) {
				
		try {
			statement = Application.connection.createStatement();
	    	query = "UPDATE bill_info_table SET station = '" + billDetails.get(BillConstants.station) + "', " 
					+ "delivery_at = '" + billDetails.get(BillConstants.deliveryAt) + "', "
					+ "consignor = '" + billDetails.get(BillConstants.consignor) + "', "
					+ "no_of_packages = '" + billDetails.get(BillConstants.packages) + "', "
					+ "description_said_to_contain = '" + billDetails.get(BillConstants.packagesDescription) + "', "
					+ "private_marks = '" + billDetails.get(BillConstants.privateMarks) + "', "
					+ "remarks = '" + billDetails.get(BillConstants.remarks) + "', "
					+ "bill_date = '" + billDetails.get(BillConstants.date) + "', " 
					+ "truck_number = '" + billDetails.get(BillConstants.truckNumber) + "', "
					+ "consignee = '" + billDetails.get(BillConstants.consignee) + "', "
					+ "actual_weight = '" + billDetails.get(BillConstants.actualWeight) + "', "
					+ "freight_per_tonne = '" + billDetails.get(BillConstants.freightPerTonne) + "', "
					+ "check_post = '" + billDetails.get(BillConstants.checkPost) + "', "
					+ "side_poles = '" + billDetails.get(BillConstants.sidePoles) + "', "
					+ "craine_charge = '" + billDetails.get(BillConstants.craineCharge) + "', "
					+ "road_expansion = '" + billDetails.get(BillConstants.roadExpansion) + "', "
					+ "door_open = '" + billDetails.get(BillConstants.doorOpen) + "', "   
					+ "weighment_charge = '" + billDetails.get(BillConstants.weighmentCharge) + "', "
					+ "value_of_goods_in_rs = '" + billDetails.get(BillConstants.valueOfGoods) + "', "
					+ "driver_charges = '" + billDetails.get(BillConstants.driverCharges) + "', "
					+ "loading_charges = '" + billDetails.get(BillConstants.loadingCharges) + "', "  
					+ "checkpost1 = '" + billDetails.get(BillConstants.checkPost1) + "', "
					+ "sidepoles1 = '" + billDetails.get(BillConstants.sidePoles1) + "', "
					+ "other_expenses = '" + billDetails.get(BillConstants.otherExpenses) + "' WHERE bill_number = " + billDetails.get(BillConstants.billNumber) + ";";
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
