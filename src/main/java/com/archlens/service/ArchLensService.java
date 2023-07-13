package com.archlens.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.archlens.configuration.ExternalTableConfig;
import com.archlens.configuration.ExternalTableConfigInterface;

@Component
public class ArchLensService {
	
//	@Autowired
//	@Qualifier("config")
	private ExternalTableConfigInterface config = new ExternalTableConfig();
	
	public static void viewBlobData(String config, String schema,String table,
			 				 String blobColName, String idName, String idVal,HttpServletResponse response ) throws FileNotFoundException, SQLException, IOException {

		ResultSet result = ExternalTableConfig.createConnection(config, schema, table, idName, idVal);
		while (result.next()) {
	
			Object data = result.getObject(blobColName);
			byte[] content =  (byte[]) data;
			
			String fullPath = "Output";
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
		}
		
		
		
		
	}

}
