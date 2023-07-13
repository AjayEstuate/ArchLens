package com.archlens.configuration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import org.springframework.context.annotation.Configuration;

import java.sql.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
public class ExternalTableConfig  implements ExternalTableConfigInterface{

	public static  ResultSet createConnection(String config, String schema, String table,String idName, String idVal  ) {
		String connectionURL = null,username = null,password = null;
		ResultSet result = null;
		try {
			// Create an ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();
			String filePath = "property.json";
			// Read the JSON file into a JsonNode object
			JsonNode rootNode = objectMapper.readTree(new File(filePath));

			// Access each config object and retrieve the connectionURL, username, and password
			JsonNode config1 = rootNode.get(config);

			connectionURL = config1.get("connectionURL").asText() + "/"+ schema;
			username = config1.get("username").asText();
			password = config1.get("password").asText();
			String query = "Select * From "+ table + " where " + idName + " = " + idVal;
			
			System.out.println("query------>"+query
					);

			Connection conn = DriverManager.getConnection(connectionURL, username, password);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(query);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to read Property.json");
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to Query data");
		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}

		return result;	
	}
	
	
	
	public static  ResultSet joinTable(String config, String schema, String table1,String table2 ,String idName, String idVal, String primaryKey  ) {
		String connectionURL = null,username = null,password = null;
		ResultSet result = null;
		try {
			// Create an ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();
			String filePath = "property.json";
			// Read the JSON file into a JsonNode object
			JsonNode rootNode = objectMapper.readTree(new File(filePath));

			// Access each config object and retrieve the connectionURL, username, and password
			JsonNode config1 = rootNode.get(config);

			connectionURL = config1.get("connectionURL").asText() + "/"+ schema;
			username = config1.get("username").asText();
			password = config1.get("password").asText();
//			SELECT * 
//			FROM optim_customers oc 
//			Inner join optim_orders oo  
//			on oc.CUST_ID = oo.CUST_ID 
//			WHERE oo.CUST_ID = '11033';
			
			String query = "Select * From "+ table1  + " t1 Inner join " + table2 + " t2"+ 
							" on t1."+ primaryKey + " = " + "t2." +primaryKey +
							" Where " ;
			
			System.out.println("query------>"+query);

			Connection conn = DriverManager.getConnection(connectionURL, username, password);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(query);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to read Property.json");
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to Query data");
		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}

		return result;	
	}

//	@Override
//	public void test() {
//		// TODO Auto-generated method stub
//		System.out.println("Test");
//	}


}
