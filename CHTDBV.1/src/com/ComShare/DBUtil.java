package com.ComShare; 
import java.io.File;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.swing.*;

public class DBUtil {
	static String UserName="",Password="",Port="",Database="",Server="";
	static public Connection con = null;
	static public Statement sta = null;

	//Connect to DB
	public static void dbConnect() throws ClassNotFoundException, SQLException 
	{
		Class.forName("org.sqlite.JDBC");
		con=DriverManager.getConnection("jdbc:sqlite:CHTDB.db");
	}
	
	//DB Execute Query Operation
	public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
		Statement stmt = null;
		ResultSet resultSet = null;
		CachedRowSet crs=null;
		try {
			dbConnect();
			System.out.println("Select statement: " + queryStmt + "\n");
			stmt = con.createStatement();
			resultSet = stmt.executeQuery(queryStmt);
			crs = RowSetProvider.newFactory().createCachedRowSet();
			crs.populate(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error!!"+e.getMessage());
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			dbDisconnect();
		}
		return crs;
	}

	//DB Execute Update (For Update/Insert/Delete) Operation
	public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {

		Statement stmt = null;
		try {
			dbConnect();
			stmt = con.createStatement();
			stmt.executeUpdate(sqlStmt);
			System.out.println("Execute statement: " + sqlStmt + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error!!"+e.getMessage());
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			dbDisconnect();
		}
	}


	//Close Connection
	public static void dbDisconnect() throws SQLException {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error!!"+e.getMessage());
		}
	}

}