//package com.estuate.controllers;
//
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//import java.util.Map;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.estuate.entity.ExternalDBConfig;
//import com.estuate.entity.ExternalDBConfigDTO;
//import com.estuate.services.HiveService;
//
//@RestController
//@RequestMapping("/blob-viewer")
//public class HiveController {
//
//
//
//	@Autowired
//	private HiveService hiveService;
//
//	@GetMapping(value ="/connection" , produces = MediaType.ALL_VALUE)
//	public String connectToExternalTable(HttpServletResponse response) throws IOException {
//		final String DB_URL = "jdbc:hive2://192.168.33.82:10000/blob";
//		final String USER = "";
//		final String PASS = "";
//		final String QUERY = "SELECT * FROM testblob1 where length = 15872 ";
//
//		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(QUERY);) {
//			// Extract data from result set
//			while (rs.next()) {
//				// Retrieve by column name
//				System.out.println("Path :" + rs.getString("path"));
//				//    	            System.out.print("Modification Time : " + rs.getDate("modificationTime"));
//				System.out.println("Length: " + rs.getInt("length"));
//				Object data = rs.getObject("content");
//				byte[] content =  (byte[]) data;
//				System.out.println("Content " + content);
//				
//				String fullPath = "Output";
//				File file = new File(fullPath);
//				file.createNewFile();
//
//				try (OutputStream outputStream = new FileOutputStream(fullPath)) {
//					outputStream.write(content);
//				}
//
//				InputStream inputStream = new FileInputStream(fullPath);
//				response.setContentType(MediaType.ALL_VALUE);
//				StreamUtils.copy(inputStream, response.getOutputStream());
//				inputStream.close();
//				file.delete();
//			}
////			String fullPath = "Output";
////			File file = new File(fullPath);
////			file.createNewFile();
////
////			(OutputStream outputStream = new FileOutputStream(fullPath)) {
////				outputStream.write(content);
////			}
////
////			InputStream inputStream = new FileInputStream(fullPath);
////			response.setContentType(MediaType.ALL_VALUE);
////			StreamUtils.copy(inputStream, response.getOutputStream());
////			inputStream.close();
////			file.delete();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} 
//
//
//		return "Configuration set Sucessfully";
//	}
//
//	@GetMapping("/viewConnection")
//	public ExternalDBConfigDTO viewConnection() {
//		ExternalDBConfigDTO dbConf = new ExternalDBConfigDTO();
//
//		dbConf.setHost(ExternalDBConfig.host);
//		dbConf.setPort(ExternalDBConfig.port);
//		dbConf.setUserName(ExternalDBConfig.userName);
//		dbConf.setPassword(ExternalDBConfig.password);
//
//		return dbConf;
//	}
//
//	@GetMapping(value = "/{schema}/tables", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<Map<String, Object>>> getTablesForSchema(@PathVariable String schema) {
//		List<Map<String, Object>> rows = hiveService.getTables(schema);
//		return new ResponseEntity<>(rows, HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/schemas", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<Map<String, Object>>> getSchemas() {
//		List<Map<String, Object>> rows = hiveService.getSchemas();
//		return new ResponseEntity<>(rows, HttpStatus.OK);
//	}
//
//	@GetMapping(value = "/{schema}/preview/{table}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<List<Map<String, Object>>> previewTable(
//			@PathVariable String schema, @PathVariable String table
//			) {
//		List<Map<String, Object>> rows = hiveService.getTablePreview(schema, table);
//		return new ResponseEntity<>(rows, HttpStatus.OK);
//	}
//
//
//
//	@GetMapping(value = "/{schema}/{table}/{idName}/{idVal}", produces = MediaType.ALL_VALUE)
//	public void viewBlobContent(@PathVariable String schema,@PathVariable String table,
//			@PathVariable String idName, @PathVariable String idVal, 
//			HttpServletResponse response) throws IOException {
//		List<Map<String, Object>> rows = hiveService.getBlobData(schema, table, idName, idVal);
//		Map<String, Object> map = rows.get(0);
//		Object data = map.get("content");
//		byte[] content =  (byte[]) data;
//
//		String fullPath = "Output";
//		File file = new File(fullPath);
//		file.createNewFile();
//
//		try (OutputStream outputStream = new FileOutputStream(fullPath)) {
//			outputStream.write(content);
//		}
//
//		InputStream inputStream = new FileInputStream(fullPath);
//		response.setContentType(MediaType.ALL_VALUE);
//		StreamUtils.copy(inputStream, response.getOutputStream());
//		inputStream.close();
//		file.delete();
//	}
//
//
//}
//
//
//
