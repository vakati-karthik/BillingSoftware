package com.karthik.BillingSoftware.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.karthik.BillingSoftware.Constants.BillConstants;
import com.karthik.BillingSoftware.Load.Application;

public class ViewInfoByPlaceJDBC {
	private Statement statement;
	private String query;
	private ArrayList<HashMap<String, String>> truckDetailsArray;

	public ArrayList<HashMap<String, String>> getPlaceDetails(boolean isSourceEnabled, boolean isDestEnabled, String source, String destination) {
		
		try {
			statement = Application.connection.createStatement();
			if (isSourceEnabled && !isDestEnabled)
				query = "SELECT * FROM bill_info_table WHERE station = '" + source + "';";
			else if (!isSourceEnabled && isDestEnabled)
				query = "SELECT * FROM bill_info_table WHERE delivery_at = '" + destination + "';";
			else if (isSourceEnabled && isDestEnabled)
				query = "SELECT * FROM bill_info_table WHERE station = '" + source + "' AND delivery_at = '" + destination + "';";
	    	ResultSet rs = statement.executeQuery(query);
	    	truckDetailsArray = new ArrayList<HashMap<String, String>>();
	    	HashMap<String, String> truckDetails;
	        while (rs.next()) {
	        	truckDetails = new HashMap<String, String>();
				truckDetails.put(BillConstants.billNumber, rs.getString("bill_number"));
				truckDetails.put(BillConstants.truckNumber, rs.getString("truck_number"));
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
				/*truckDetails.put(BillConstants., "" + (rs.getFloat("driver_charges")
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
