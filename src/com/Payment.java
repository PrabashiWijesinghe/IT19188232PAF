package com;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.Part;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paymentdb","root", "rootpassword");
			System.out.print("Successfully connected!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
 //inserting data
	public String insertPayment(String CUSID, String CUSName, String Amount, String Bank, String CardNo, String CVV , String paymentDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into userpay_table(`CUSID`, `CUSName`, `Amount`,`Bank`, `CardNo`, `CVV`,`paymentDate`)"
					+ " values ( ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
		
			preparedStmt.setString(1, CUSID);
			preparedStmt.setString(2, CUSName);
			preparedStmt.setDouble(3, Double.parseDouble(Amount));
			preparedStmt.setString(4, Bank);
			preparedStmt.setString(5, CardNo);
			preparedStmt.setString(6, CVV);
			preparedStmt.setString(7, paymentDate);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Payment.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1' class='table table-bordered'>"
			
			+"<tr>"

			+"<th>CUSID</th>"
			+"<th>CUSName</th>"+"<th>Amount</th>"+"<th>Bank</th>"+"<th>CardNo</th>"+"<th>CVV</th>"+"<th>paymentDate</th>"+"<th>Update</th>"+"<th>Delete</th>"+"</tr>";
			String query = "select * from userpay_table";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String PID = Integer.toString(rs.getInt("PID"));
				String CUSID = rs.getString("CUSID");
				String CUSName = rs.getString("CUSName");
				String Amount = Double.toString(rs.getDouble("Amount"));
				String Bank = rs.getString("Bank");
				String CardNo = rs.getString("CardNo");
				String CVV = rs.getString("CVV");
				String paymentDate = rs.getString("paymentDate");

				// Add into the html table
//				output += "<tr><td><input id=\'hidPaymentIdUpdate\' name=\'hidPaymentIdUpdate\' type=\'hidden\' value=\'"
//						+ PID + "'>" + CUSID + "</td>";
				output += "<tr>";
				output += "<td>" + CUSID + "</td>";
				output += "<td>" + CUSName + "</td>";
				output += "<td>" + Amount + "</td>";
				output += "<td>" + Bank + "</td>";
				output += "<td>" + CardNo + "</td>";
				output += "<td>" + CVV + "</td>";
				output += "<td>" + paymentDate + "</td>";

				
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-paymentid='" + PID +"'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentid='"
						+ PID + "'></td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payment.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updatePayment(int PID, String CUSID, String CUSName, String Amount,String Bank, String CardNo, String CVV , String paymentDate) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE userpay_table SET CUSID=?,CUSName=?,Amount=? Bank=?,CardNo=?,CVV=? paymentDate=? WHERE PID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, CUSID);
			preparedStmt.setString(2, CUSName);
			preparedStmt.setString(3, Amount);
			preparedStmt.setString(4, Bank);
			preparedStmt.setString(5, CardNo);
			preparedStmt.setString(6, CVV);
			preparedStmt.setString(7, paymentDate);
			
			preparedStmt.setInt(8, PID);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deletePayment(int PID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from userpay_table where PID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1,PID);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayment = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";
		} catch (Exception e) {
			output = "Error while deleting the project.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
