package com.karthik.BillingSoftware.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.karthik.BillingSoftware.Load.Application;

public class JDBCUtils {
	private Statement statement;
	private Statement statement1;
	private ResultSet rs;
	private String query;
	private String seqNo = "";
	
	public String getNextBillNo() {
		
		try {			
				statement = Application.connection.createStatement();
		    	query = "SELECT DISTINCT max(bill_number) FROM bill_info_table;";
		    	rs = statement.executeQuery(query);
		    	if (rs.next())
		    		seqNo = rs.getString(1);
		    	statement.close();
		    	return "" + (Integer.parseInt(seqNo) + 1);
		}
		catch (SQLException e) 
		{			
			System.out.println("Getting Last Row From the database failed");
			return "";
		}		
	}
	
	public void createTable() {
		
		try {			
				statement = Application.connection.createStatement();
		    	query = "CREATE TABLE IF NOT EXISTS bill_info_table (bill_number INTEGER PRIMARY KEY ASC," +
		    			"station VARCHAR(40)," +
		    			"delivery_at VARCHAR(40)," +
		    			"consignor VARCHAR(400)," +
		    			"no_of_packages INTEGER," +
		    			"description_said_to_contain VARCHAR(500)," +
		    			"private_marks VARCHAR(200)," +
		    			"remarks VARCHAR(400)," +
		    			"bill_date VARCHAR(10)," +
		    			"truck_number VARCHAR(15)," +
		    			"consignee VARCHAR(400)," +
		    			"actual_weight REAL," +
		    			"freight_per_tonne REAL," +
		    			"check_post REAL," +
		    			"side_poles REAL," +
		    			"craine_charge REAL," +
		    			"road_expansion REAL," +
		    			"door_open REAL," +
		    			"weighment_charge REAL," +
		    			"value_of_goods_in_rs REAL," +
		    			"driver_charges REAL," +
		    			"loading_charges REAL," +
		    			"checkpost1 REAL," +
		    			"sidepoles1 REAL," +
		    			"other_expenses REAL);";
		    	statement.executeUpdate(query);
		    	insertBlankRow();
		    	statement.close();
		}
		catch (SQLException e) 
		{			
			System.out.println("Cannot create database");
		}		
	}

	private void insertBlankRow() {
		
		try {			
				statement = Application.connection.createStatement();
				query = "SELECT * FROM bill_info_table;";
				rs = statement.executeQuery(query);
				if (!rs.next()) {
					statement1 = Application.connection.createStatement();
			    	query = "INSERT INTO bill_info_table (bill_number) VALUES (9999999);";
			    	statement1.executeUpdate(query);
			    	/*query = "INSERT INTO bill_info_table VALUES(NULL, 'Chennai', 'Hyderabad', 'afadfd', '6', 'asdf', 'dsaf', 'fd', '10 NOVEMBER 2013', 'AP 11 A 1111', 'fds', '345 Tonnes', '5435', '345', '547', '67', '56', '980', '23', '34', '233', '43', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Hyderabad', 'Chennai', 'afdfd', '6', 'asdf', 'dsaf', 'fd', '10 NOVEMBER 2013', 'AP 26 J 138', 'fds', '345 Tonnes', '544335', '345', '547', '67', '56', '980', '23', '34', '23', '463', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Madurai', 'Hyderabad', 'afasd', '6', 'asdf', 'dsaf', 'fd', '10 NOVEMBER 2013', 'AP 11 AZ 1234', 'fds', '345 Tonnes', '5435', '345', '547', '67', '56', '980', '23', '34', '23', '43', '544567', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Khammam', 'Hyderabad', 'afghd', '6', 'asdf', 'dsaf', 'fd', '10 NOVEMBER 2013', 'AP 11 K 5171', 'fds', '345 Tonnes', '54385', '37645', '547', '67', '56', '980', '23', '34', '23', '43', '544567', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Hyderabad', 'Vizayawada', 'afhgfd', '6', 'asdf', 'dsaf', 'fd', '10 NOVEMBER 2013', 'AP 12 B 4352', 'fds', '345 Tonnes', '5435', '3457', '547', '67', '56', '980', '23', '34', '23', '4453', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Gudur', 'Hyderabad', 'adsffd', '6', 'asdf', 'dsaf', 'fd', '11 NOVEMBER 2013', 'AP 11 B 1323', 'fds', '345 Tonnes', '5435', '345', '567847', '67', '56', '980', '23', '34', '23', '43', '547', '345645', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Hyderabad', 'Gudur', 'afdfgf', '6', 'asdf', 'dsaf', 'fd', '11 NOVEMBER 2013', 'AP 11 A 1111', 'fds', '345 Tonnes', '5435', '345', '54787', '67', '56', '980', '23', '34', '23', '43', '547', '345', '24563');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Gudur', 'Chennai', 'afqqwerd', '6', 'asdf', 'dsaf', 'fd', '11 NOVEMBER 2013', 'AP 13 AX 8711', 'fds', '345 Tonnes', '5435', '345', '547', '7867', '56', '980', '23', '34', '23453', '43', '5447', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Chennai', 'Gudur', 'aferqrd', '6', 'asdf', 'dsaf', 'fd', '11 NOVEMBER 2013', 'AP 11 A 1111', 'fds', '345 Tonnes', '5435', '345', '547', '67', '56', '96780', '23', '34', '23', '43', '54347', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Chennai', 'Madurai', 'afyttryd', '6', 'asdf', 'dsaf', 'fd', '11 NOVEMBER 2013', 'AP 11 B 1341', 'fds', '345 Tonnes', '5435', '345', '547', '67', '5786', '980', '23', '34', '23345', '43', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Madurai', 'Chennai', 'aferytryd', '6', 'asdf', 'dsaf', 'fd', '12 NOVEMBER 2013', 'AP 11 A 1111', 'fds', '345 Tonnes', '5435', '345', '547', '67', '56', '980', '7823', '34', '23453', '43', '543547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Chennai', 'Khammam', 'afeyttryd', '6', 'asdf', 'dsaf', 'fd', '12 NOVEMBER 2013', 'AP 26 J 138', 'fds', '345 Tonnes', '5437785', '345', '547', '67', '56', '980', '23', '34', '23', '43453', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Khammam', 'Chennai', 'aferytd', '6', 'asdf', 'dsaf', 'fd', '12 NOVEMBER 2013', 'AP 25 HJ 1671', 'fds', '345 Tonnes', '5435', '345', '576847', '67', '56', '980', '23', '34', '23', '4453', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Chennai', 'Vizayawada', 'avcbvcfd', '6', 'asdf', 'dsaf', 'fd', '12 NOVEMBER 2013', 'AP 13 A 1100', 'fds', '345 Tonnes', '5435', '346785', '547', '67', '56', '980', '23', '34', '23', '43453', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Chennai', 'Visakhapatnam', 'afnbvnmd', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 11 A 1111', 'fds', '345 Tonnes', '5435', '34765', '547', '67', '56', '980', '23', '34', '23', '43', '544357', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Vizayawada', 'Chennai', 'afyuoid', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 14 UI 1234', 'fds', '345 Tonnes', '5435', '347685', '547', '67', '56', '980', '23', '34', '23', '43', '547', '344355', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Visakhapatnam', 'Chennai', 'ayoifd', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 09 QW 4567', 'fds', '345 Tonnes', '5435', '345', '597847', '67', '56', '980', '23', '34', '23', '44353', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Visakhapatnam', 'Hyderabad', 'ayoifd', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 12 IO 8902', 'fds', '345 Tonnes', '5435', '345', '547', '67', '59876', '980', '23', '34', '245343', '43', '547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Visakhapatnam', 'Madurai', 'aqwerfd', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 01 DF 8034', 'fds', '345 Tonnes', '5435', '345', '547', '6977', '56', '980', '23', '34', '23', '43', '534547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Vizayawada', 'Visakhapatnam', 'aflkjld', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 05 JK 3732', 'fds', '345 Tonnes', '5435', '37845', '547', '67', '56', '980', '23', '34', '23', '43', '547', '334545', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Vizayawada', 'Hyderabad', 'aqreqwrfd', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 24 S 111', 'fds', '345 Tonnes', '5435', '345', '547', '67', '56', '980', '23', '34', '23', '43', '547', '344355', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Madurai', 'Vizayawada', 'ayuoioipfd', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 19 TY 9264', 'fds', '345 Tonnes', '5435', '345', '547', '67', '87956', '980', '23', '34', '23', '43', '534547', '345', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Vizayawada', 'Madurai', 'afvnmvcvbd', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 15 KT 8436', 'fds', '345 Tonnes', '5435', '345', '547', '68797', '56', '980', '23', '34', '23', '43', '547', '3455', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Gudur', 'Hyderabad', 'afqreqrd', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 02 JE 9447', 'fds', '345 Tonnes', '5435', '345', '547', '6457', '56', '980', '23', '34', '23', '43', '547', '345435', '23');";
			    	statement1.executeUpdate(query);
			    	query = "INSERT INTO bill_info_table VALUES(NULL, 'Chennai', 'Hyderabad', 'af12433214d', '6', 'asdf', 'dsaf', 'fd', '13 NOVEMBER 2013', 'AP 11 A 1111', 'fds', '345 Tonnes', '5435', '34345', '547', '67', '56', '980', '23', '34', '23', '43', '54347', '345', '23');";
			    	statement1.executeUpdate(query);*/
			    	statement1.close();
			    	Application.connection.commit();			    	
				}
		    	statement.close();
		}
		catch (SQLException e) 
		{			
			System.out.println("Cannot insert blank row");
		}		
	}

}
