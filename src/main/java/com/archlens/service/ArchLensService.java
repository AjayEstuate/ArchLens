package com.archlens.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.archlens.configuration.ExternalTableConfig;
import com.archlens.configuration.ExternalTableConfigInterface;
import com.archlens.controller.ArchLensController;
import com.archlens.entity.ExternalTableDataSource;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class ArchLensService {

	//	@Autowired
	//	@Qualifier("config")
	private ExternalTableConfigInterface config = new ExternalTableConfig();

	public static void viewBlobData(String config, String schema,String table,
			String blobColName,String fileName, String idName, String idVal,HttpServletResponse response ) throws  SQLException, IOException {

		List result = ExternalTableConfig.createConnection(config, schema, table,blobColName, fileName, idName, idVal );


		//			while (result.next()) {
		//
		//				Object data = result.getObject(blobColName);
		//				String fullPath = result.getString("path");
		
		System.out.println("Result " +result);
		Object data = result.get(0);
		String fullPath = (String) result.get(1);


		byte[] content =  (byte[]) data;
		fullPath =	ArchLensController.getSubstringAfterLastSlash(fullPath);

		File file = new File(fullPath);
		file.createNewFile();

		try (OutputStream outputStream = new FileOutputStream(fullPath)) {
			outputStream.write(content);
		}

		InputStream inputStream = new FileInputStream(fullPath);
		response.setContentType(MediaType.ALL_VALUE);
		StreamUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		file.delete();
		//			}
	}
	
	
	public static String addConfig( ExternalTableDataSource configData) {
	    String configName = configData.getConfigName();
	    String host = configData.getHost();
	    String port = configData.getPort();
	    String userName = configData.getUserName();
	    String password = configData.getPassword();

	    ObjectMapper objectMapper = new ObjectMapper();
	    ObjectNode configNode = objectMapper.createObjectNode();
	    ObjectNode connectionNode = objectMapper.createObjectNode();

	    connectionNode.put("connectionURL", "jdbc:hive2://" + host + ":" + port);
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
	    configNode.set(configName, connectionNode);

	    // Write all configurations back to the file
	    try {
	        FileWriter fileWriter = new FileWriter("property.json");
	        objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, configNode);
	        fileWriter.close();
	        System.out.println("Configuration has been written to property.json successfully!");
	    } catch (IOException e) {
	        e.printStackTrace();
	        System.err.println("Error while writing the configuration to property.json");
	        return "Failed to add the config.";
	    }

	    return "Config Added";
	}


}
