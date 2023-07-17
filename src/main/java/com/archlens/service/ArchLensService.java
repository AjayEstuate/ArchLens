package com.archlens.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.archlens.configuration.ExternalTableConfig;
import com.archlens.entity.ExternalTableDataSource;
import com.archlens.security.ArchLensSecurity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class ArchLensService {

	public static void viewBlobData(String config, String schema, String table, String blobColName, String fileName,
			String idName, String idVal, HttpServletResponse response) throws SQLException, IOException {

		List result = ExternalTableConfig.createConnection(config, schema, table, blobColName, fileName, idName, idVal);

		Object data = result.get(0);
		String file_name = (String) result.get(1);

		byte[] content = (byte[]) data;

		File file = new File(file_name);
		file.createNewFile();

		try (OutputStream outputStream = new FileOutputStream(file_name)) {
			outputStream.write(content);
		}

		InputStream inputStream = new FileInputStream(file_name);
		response.setContentType(MediaType.ALL_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		file.delete();

	}


	public static String addConfig(ExternalTableDataSource configData)throws SQLException, Exception , ConnectException{
		String dataSource = configData.getDataSource();
		String host = configData.getHost();
		String port = configData.getPort();
		String userName = configData.getUserName();
		String password = configData.getPassword();

		String connectionURL = "jdbc:hive2://" + host + ":" + port;
		try {
			Connection connection = DriverManager.getConnection(connectionURL, userName, password);
			if (connection == null) {
				throw new  SQLException("Connetion Refused");
			}
			try {
				userName = ArchLensSecurity.encrypt(userName);
				password = ArchLensSecurity.encrypt(password);
			} catch (Exception e) {
				e.printStackTrace();
			}

			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode configNode = objectMapper.createObjectNode();
			ObjectNode connectionNode = objectMapper.createObjectNode();

			connectionNode.put("connectionURL", connectionURL);
			connectionNode.put("username", userName);
			connectionNode.put("password", password);

			// Read existing configurations from the file
			try {
				File configFile = new File("property.json");
				if (configFile.exists()) {
					JsonNode existingConfig = objectMapper.readTree(configFile);
					configNode.setAll((ObjectNode) existingConfig);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error while reading existing configurations from property.json");
				return "Failed to add the config.";
			}

			// Add the new configuration to the existing ones
			configNode.set(dataSource, connectionNode);

			// Write all configurations to a temporary file
			try {
				File tempFile = new File("property_temp.json");
				FileWriter fileWriter = new FileWriter(tempFile);
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, configNode);
				fileWriter.close();
				System.out.println("DataSource configurations have been updated in property_temp.json.");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Error while writing the configuration to property_temp.json");
				return "Failed to add the config.";
			}

			// Replace the original file with the temporary file
			File tempFile = new File("property_temp.json");
			File configFile = new File("property.json");
			try {
				Files.move(tempFile.toPath(), configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("DataSource configurations have been added to property.json successfully!");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Failed to replace property.json with property_temp.json");
				return "Failed to add the config.";
			}
			return dataSource + " Data source added successfully";

		} catch (SQLException e) {
			e.printStackTrace();
			throw new  SQLException("Connetion Refused : " + e.getMessage());
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new  Exception("Connetion Refused : " + e.getMessage());
		}

	}

	public static String getSubstringAfterLastSlash(String filePath) {
		int lastSlashIndex = filePath.lastIndexOf('/');

		if (lastSlashIndex != -1 && lastSlashIndex < filePath.length() - 1) {
			return filePath.substring(lastSlashIndex + 1);
		}

		return "";
	}

}
