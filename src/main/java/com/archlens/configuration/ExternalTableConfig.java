package com.archlens.configuration;

import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
public class ExternalTableConfig  implements ExternalTableConfigInterface{

	static String filePath = "property.json";



	public static List getSchemas( Connection connection) throws SQLException  {

		String query = "SHOW SCHEMAS";
//		Connection connection = null;

		try {
//			connection = DriverManager.getConnection(connectionURL, username, password);
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
//			connection.close();
		}
		return null;



	}

	public static List getTables(Connection connection ) throws SQLException{
		String query ="SHOW Tables";
//		Connection connection = null;
		try {
//			connection = DriverManager.getConnection(connectionURL+"/"+table, username, password);
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
//			connection.close();
		}
		return null;
	}


	
	//			List schemas = getSchemas(connectionURL, username, password);
	//			List tables = getTables(connectionURL, username, password, table);
	//			
	//			if ( !(schemas.contains(schema)) ) {
	//				throw new SQLException("Invalid Schema");
	//			}else if (!(tables.contains(table))) {
	//				throw new SQLException("Invalid Table");
	//			}else {
	//				
	//			}
	
	public static  List createConnection(String config, String schema, String table,
			String blobColName, String fileName, String idName, String idVal  ) throws IOException, SQLException  {
		System.out.println("fileName->"+fileName+"blobColName->"+blobColName+"idName->"+idName+"idVal->"+idVal);
		
		
		String connectionURL = null,username = null,password = null;


		// Create an ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		// Read the JSON file into a JsonNode object
		JsonNode rootNode = null;

		try {
			rootNode = objectMapper.readTree(new File(filePath));
			//			} catch (JsonProcessingException e) {
			//				throw new JsonProcessingException("Failed to read property.json");
			//				e.printStackTrace();
		} catch (IOException e) {
			throw new IOException("Failed to read property.json");

		}


		// Access each config object and retrieve the connectionURL, username, and password
		JsonNode config1 = rootNode.get(config);
		if (config1 == null) {
			throw new IOException("Invalid Configuration");
		}

		connectionURL = config1.get("connectionURL").asText() ;
		username = config1.get("username").asText();
		password = config1.get("password").asText();

		String query = "Select * From "+ table + " where " + idName + " = " + idVal;



		List blobData = new ArrayList();
		Connection conn = null;
		ResultSet result = null;
		try {
			String url = connectionURL ; // + "/"+ schema;
			System.out.println("url---->"+url);
			
			conn = DriverManager.getConnection(url, username, password);
			List schemas = getSchemas(conn);
			System.out.println("Schema---->"+schema);
			System.out.println("schemas-------->"+schemas);
			if ( !(schemas.contains(schema)) ) {
				throw new SQLException("Invalid Schema");
			}
			
			conn = DriverManager.getConnection(url+ "/"+ schema, username, password);
			
			List tables = getTables( conn);
			System.out.println("tables-------->"+tables);
			
			
			if (!(tables.contains(table))) {
				throw new SQLException("Invalid Table");
			}else {
				
			}
			
			
			System.out.println("conn"+conn);
			Statement stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			System.out.println("query------>"+query);



			System.out.println("result---->"+result);
//			System.out.println("result.next()---->"+result.next());
			while (result.next()) {
				
				System.out.println("blobColName->"+blobColName);
				System.out.println("fileName->"+fileName);
				
				Object data = result.getObject(blobColName);
				String fullPath = result.getString(fileName);
				System.out.println("Adding");
				blobData.add(data);
				blobData.add(fullPath);
			}

		} catch (Exception e) {
			if (conn == null) {
				throw new SQLException("Connection Refused : \n"+ e.getMessage());
			}else if (result == null) {
				throw new SQLException("Failed to query data : \n"+ e.getMessage());
			}else {
				throw new SQLException("Something went wrong : \n"+ e.getMessage());
			}

		}finally {
			conn.close();
		}
		System.out.println("Returning op "+blobData);
		return blobData ;	
	}



	//	public static  ResultSet joinTable(String config, String schema, String table1,String table2 ,String idName, String idVal, String primaryKey  ) {
	//		String connectionURL = null,username = null,password = null;
	//		ResultSet result = null;
	//		try {
	//			// Create an ObjectMapper instance
	//			ObjectMapper objectMapper = new ObjectMapper();
	//			String filePath = "property.json";
	//			// Read the JSON file into a JsonNode object
	//			JsonNode rootNode = objectMapper.readTree(new File(filePath));
	//
	//			// Access each config object and retrieve the connectionURL, username, and password
	//			JsonNode config1 = rootNode.get(config);
	//
	//			connectionURL = config1.get("connectionURL").asText() + "/"+ schema;
	//			username = config1.get("username").asText();
	//			password = config1.get("password").asText();
	//			//			SELECT * 
	//			//			FROM optim_customers oc 
	//			//			Inner join optim_orders oo  
	//			//			on oc.CUST_ID = oo.CUST_ID 
	//			//			WHERE oo.CUST_ID = '11033';
	//
	//			String query = "Select * From "+ table1  + " t1 Inner join " + table2 + " t2"+ 
	//					" on t1."+ primaryKey + " = " + "t2." +primaryKey +
	//					" Where " ;
	//
	//			System.out.println("query------>"+query);
	//
	//			Connection conn = DriverManager.getConnection(connectionURL, username, password);
	//			Statement stmt = conn.createStatement();
	//			result = stmt.executeQuery(query);
	//
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//			System.out.println("Failed to read Property.json");
	//		}
	//		catch (SQLException e) {
	//			e.printStackTrace();
	//			System.out.println("Failed to Query data");
	//		} catch (Exception e) {
	//			System.out.println("Something went wrong");
	//			e.printStackTrace();
	//		}
	//
	//		return result;	
	//	}



}
