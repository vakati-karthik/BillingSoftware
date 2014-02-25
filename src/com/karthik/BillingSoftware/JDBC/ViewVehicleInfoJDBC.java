package com.karthik.BillingSoftware.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.karthik.BillingSoftware.Constants.BillConstants;
import com.karthik.BillingSoftware.Load.Application;

public class ViewVehicleInfoJDBC {
	private Statement statement;
	private String query;
	private ArrayList<HashMap<String, String>> truckDetailsArray;

	public ArrayList<HashMap<String, String>> getTruckDetails(String truckNo) {
		
		try {
			statement = Application.connection.createStatement();
	    	query = "SELECT * FROM bill_info_table WHERE truck_number = '" + truckNo + "';";
	    	ResultSet rs = statement.executeQuery(query);
	    	HashMap<String, String> truckDetails;
        	truckDetailsArray = new ArrayList<HashMap<String, String>>();
	        while (rs.next()) {
	        	truckDetails = new HashMap<String, String>();
				truckDetails.put(BillConstants.billNumber, rs.getString("bill_number"));
				truckDetails.put(BillConstants.date, rs.getString("bill_date"));
				truckDetails.put(BillConstants.station, rs.getString("station"));
				truckDetails.put(BillConstants.deliveryAt, rs.getString("delivery_at"));
				truckDetails.put(BillConstants.actualWeight, rs.getString("actual_weight"));
				truckDetails.put(BillConstants.total, "" + (rs.getFloat("freight_per_tonne")
														+ rs.getFloat("check_post")
														+ rs.getFloat("side_poles")
														+ rs.getFloat("craine_charge")
														+ rs.getFloat("road_expansion")
														+ rs.getFloat("door_open")
														+ rs.getFloat("weighment_charge")
														+ rs.getFloat("value_of_goods_in_rs")));
				/*truckDetails.put(BillConstants.otherCharges, "" + (rs.getFloat("driver_charges")
														+ rs.getFloat("loading_charges")
														+ rs.getFloat("checkpost1")
														+ rs.getFloat("sidepoles1")
														+ rs.getFloat("other_expenses")));*/
				truckDetailsArray.add(0, truckDetails);
	        }
	        rs.close();
            statement.close();
            return truckDetailsArray;
		}
		catch (SQLException e) 
		{			
			System.out.println("Getting Truck Details has a problem");
			return null;
		}
	}
}
