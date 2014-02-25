package com.karthik.BillingSoftware.JDBC;

public class DataBaseProcess {
	
	private static String _DATABASEPASSWORD = "";
	private static String _DATABASEUSERNAME = "";
	private static String _DATABASECONNECTIONURL = "";
	
	public DataBaseProcess(){
		_DATABASECONNECTIONURL = "jdbc:oracle:thin:@localhost:1521:orcl";
		_DATABASEUSERNAME = "vishnusai";
		_DATABASEPASSWORD = "vishnusai";
	}
	
	public String getDatabasePassword() {
		return _DATABASEPASSWORD;
	}
	
	public String getDatabaseUsername() {
		return _DATABASEUSERNAME;
	}
	
	public String getDatabaseConnectionUrl() {
		return _DATABASECONNECTIONURL;
	}
	
	public void setDatabasePassword(String password) {
		_DATABASEPASSWORD = password;
	}
	
	public void setDatabaseUsername(String username) {
		_DATABASEUSERNAME = username;
	}
	
	public void setDatabaseConnectionUrl(String url) {
		_DATABASECONNECTIONURL = url;
	}

}
