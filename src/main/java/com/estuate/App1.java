//package com.estuate;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.File;
//import java.io.IOException;
//import java.util.Scanner;
//import java.sql.*;
//
//public class App1 {
//
//	public static void main(String[] args) throws IOException {
//		// Path to the JSON file
//		String filePath = "property.json";
//
//		try {
//			// Create an ObjectMapper instance
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			// Read the JSON file into a JsonNode object
//			JsonNode rootNode = objectMapper.readTree(new File(filePath));
//			
//			// Access each config object and retrieve the connectionURL, username, and password
//			Scanner scan = new Scanner(System.in);
//			
//			System.out.println("Enter Config name");
//			String confName = scan.next();
//			JsonNode config1 = rootNode.get(confName);
//			String connectionURL = config1.get("connectionURL").asText();
//			String username = config1.get("username").asText();
//			String password = config1.get("password").asText();
//
//			// Print the values
//			System.out.println("Config1 - ConnectionURL: " + connectionURL + ", Username: " + username + ", Password: " + password);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		    final String DB_URL = "jdbc:hive2://192.168.33.82:10000/blob";
//		    final String USER = "";
//		    final String PASS = "";
//		    final String QUERY = "SELECT * FROM testblob1 where length = 4842585 ";
//		
//	      try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//	    	         Statement stmt = conn.createStatement();
//	    	         ResultSet rs = stmt.executeQuery(QUERY);) {
//	    	         // Extract data from result set
//	    	         while (rs.next()) {
//	    	            // Retrieve by column name
//	    	            System.out.println("Path :" + rs.getString("path"));
////	    	            System.out.print("Modification Time : " + rs.getDate("modificationTime"));
//	    	            System.out.println("Length: " + rs.getInt("length"));
//	    	            Object data = rs.getObject("content");
//	    	            byte[] content =  (byte[]) data;
//	    	            System.out.println("Content " + content);
////	    	            System.out.println("Content: " + rs.getBinaryStream("content"));
//	    	         }
//	    	      } catch (SQLException e) {
//	    	         e.printStackTrace();
//	    	      } 
//	}
//}
//
