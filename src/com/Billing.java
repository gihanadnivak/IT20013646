package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class Billing {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					
					"jdbc:mysql://localhost:3306/billing?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertProject(String Month, String Customer_name, String Meter_reading, String Charge_for_this_month, String Total_Bill_amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into bill(`billingId`, `Month`, `Customer_name`, `Meter_reading`, `Charge_for_this_month`,`Total_Bill_amount`)"
					+ " values ( ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Month);
			preparedStmt.setString(3, Customer_name);
			preparedStmt.setString(4, Meter_reading);
			preparedStmt.setString(5, Charge_for_this_month);
			preparedStmt.setString(6, Total_Bill_amount);

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readProject() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Bill ID</th><th>Month</th><th>Customer Name</th><th>Meter Reading</th><th>Charge for This Month</th><th>Total Bill Amount</th></tr>";;
			String query = "select * from bill";

			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String billingId = Integer.toString(rs.getInt("billingId"));
				String Month = rs.getString("Month");
				String Customer_name = rs.getString("Customer_name");
				String Meter_reading = rs.getString("Meter_reading");
				String Charge_for_this_month = rs.getString("Charge_for_this_month");
				String Total_Bill_amount = rs.getString("Total_Bill_amount");

				// Add into the html table
				output += "<tr><td>" + billingId + "</td>";
				output += "<td>" + Month + "</td>";
				output += "<td>" + Customer_name + "</td>";
				output += "<td>" + Meter_reading + "</td>";
				output += "<td>" + Charge_for_this_month + "</td>";
				output += "<td>" + Total_Bill_amount + "</td>";

				
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-productid='"
						+ billingId + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the projects.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateProject(String billingId, String Month, String Customer_name, String Meter_reading, String Charge_for_this_month, String Total_Bill_amount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE bill SET Month=?,Customer_name=?,Meter_reading=?,Charge_for_this_month=?,Total_Bill_amount=?" + "WHERE billingId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, Month);
			preparedStmt.setString(2, Customer_name);
			preparedStmt.setString(3, Meter_reading);
			preparedStmt.setString(4, Charge_for_this_month);
			preparedStmt.setString(5, Total_Bill_amount);
			preparedStmt.setInt(6, Integer.parseInt(billingId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteProject(String billingId) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from bill where billingId =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(billingId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "Error while deleting the project.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
