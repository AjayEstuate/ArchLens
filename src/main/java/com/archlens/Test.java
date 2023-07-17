package com.archlens;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static List getSchemas() throws SQLException  {

		String query = "SHOW SCHEMAS";
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:hive2://192.168.33.82:10000", "", "");
			Statement stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			ResultSetMetaData metadata = resultSet.getMetaData();
			int columnCount = metadata.getColumnCount();

			List schemas = new ArrayList();
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					Object value = resultSet.getObject(i);
					schemas.add(value);
				}
			}

			return schemas;
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
		}
		return null;



	}

	public static List getTables() throws SQLException{
		String query ="SHOW Tables";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:hive2://192.168.33.82:10000/blob", "", "");
			Statement stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(query);
			ResultSetMetaData metadata = resultSet.getMetaData();
			int columnCount = metadata.getColumnCount();

			List tables = new ArrayList();

			while (resultSet.next()) {
				for (int i = 2; i <= columnCount ; i++) {
					Object value = resultSet.getObject(i);
					tables.add(value);
					break;
				}
			}
			return tables;

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connection.close();
		}
		return null;
	}





	public static void main(String[] args) {

		try {
			List schema = getSchemas();
			//			List table = getTables();


			System.out.println("Schemas----->"+schema);
			//			System.out.println("Tables------>"+table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		try {
		//			// Establish the connection
		//			Connection connection = DriverManager.getConnection("jdbc:hive2://192.168.33.82:10000/blob", "", "");
		//
		//			// Create a statement
		//			Statement statement = connection.createStatement();
		//
		//			// Execute the query
		//			ResultSet resultSet = statement.executeQuery("show tables");
		//
		//			// Get the ResultSet metadata
		//			ResultSetMetaData metadata = resultSet.getMetaData();
		//			int columnCount = metadata.getColumnCount();
		//
		//			// Print column names
		//			for (int i = 1; i <= columnCount; i++) {
		//				System.out.print(metadata.getColumnName(i) + "\t");
		//			}
		//			System.out.println();
		//
		//			List list = new ArrayList();
		//
		//			// Print column values
		//			while (resultSet.next()) {
		//				for (int i = 2; i <= columnCount ; i++) {
		//					Object value = resultSet.getObject(i);
		//					list.add(value);
		//					System.out.print(value + "\t");
		//					break;
		//				}
		//				System.out.println();
		//			}
		//			System.out.println("Tables-------->"+list);
		//			// Close the resources
		//			resultSet.close();
		//			statement.close();
		//			connection.close();
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		}
	}
}




