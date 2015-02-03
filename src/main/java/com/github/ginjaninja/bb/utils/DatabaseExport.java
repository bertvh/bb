package com.github.ginjaninja.bb.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;



public class DatabaseExport {
	
	public static void exportToDTD() throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException, IOException{
		Class driverClass = Class.forName("com.mysql.jdbc.Driver");
		Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/rest", 
				"rest", "rest");
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
		
		FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("C:\test.dtd"));
	}
	
	public static void main(String[] args) throws Exception{
		exportToDTD();
	}
}
