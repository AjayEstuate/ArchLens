package com.archlens.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archlens.configuration.ExternalTableConfig;
import com.archlens.service.ArchLensService;

@RestController
@RequestMapping("/ArchLens")
public class ArchLensController {
	
    public static String getSubstringAfterLastSlash(String filePath) {
        int lastSlashIndex = filePath.lastIndexOf('/');
        
        if (lastSlashIndex != -1 && lastSlashIndex < filePath.length() - 1) {
            return filePath.substring(lastSlashIndex + 1);
        }
        
        return "";
    }

	@GetMapping(value ="/view/cfg={config}/schema={schema}/tablename={table}/blobColName={blobColName}/{idName}={idVal}" , produces = MediaType.ALL_VALUE)
	public void viewBlob(
			@PathVariable String config,
			@PathVariable String schema,
			@PathVariable String table,
			@PathVariable String blobColName,
			@PathVariable String idName,
			@PathVariable String idVal,
			HttpServletResponse response
			) {

		try {
			ArchLensService.viewBlobData(config, schema, table, blobColName, idName, idVal, response);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


//	@GetMapping(value ="/download/cfg={config}/schema={schema}/tablename={table}/blobColName={blobColName}/{idName}={idVal}" , produces = MediaType.ALL_VALUE)
//	public ResponseEntity<InputStreamResource> downloadFile(
//			@PathVariable String config,
//			@PathVariable String schema,
//			@PathVariable String table,
//			@PathVariable String blobColName,
//			@PathVariable String idName,
//			@PathVariable String idVal,
//			HttpServletResponse response
//			) throws IOException, SQLException  {
//
//		ResultSet result = ExternalTableConfig.createConnection(config, schema, table, idName, idVal);
//		if (result.next()) {
//			String file_name = result.getString("path");
//			Object fileData = result.getObject(blobColName);
//			byte[] content = (byte[]) fileData;
//			if (fileData != null) {
//				// Convert the binary data to a file
//				InputStream inputStream = new ByteArrayInputStream(content);
//				
//				String fileName = getSubstringAfterLastSlash(file_name);
//				File file = new File(fileName);
//				FileOutputStream outputStream = new FileOutputStream(file);
//				byte[] buffer = new byte[4096];
//				int bytesRead;
//				while ((bytesRead = inputStream.read(buffer)) != -1) {
//					outputStream.write(buffer, 0, bytesRead);
//				}
//
//				// Set response headers
//				HttpHeaders headers = new HttpHeaders();
//				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//				headers.setContentDispositionFormData("attachment", file.getName());
//
//				// Create the InputStreamResource from the file
//				InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//				// Clean up resources
//				inputStream.close();
//				outputStream.close();
//				file.delete();
//
//				// Return the file as a response entity
//				return ResponseEntity.ok()
//						.headers(headers)
//						.contentLength(file.length())
//						.body(resource);
//			} else {
//				// File not found in the database
//				response.sendError(HttpServletResponse.SC_NOT_FOUND);
//			}
//
//		}
//		return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
//
//	}
	
	@GetMapping(value = "/download/cfg={config}/schema={schema}/tablename={table}/blobColName={blobColName}/{idName}={idVal}", produces = MediaType.ALL_VALUE)
	public void downloadFile(
	        @PathVariable String config,
	        @PathVariable String schema,
	        @PathVariable String table,
	        @PathVariable String blobColName,
	        @PathVariable String idName,
	        @PathVariable String idVal,
	        HttpServletResponse response) throws IOException, SQLException {

	    ResultSet result = ExternalTableConfig.createConnection(config, schema, table, idName, idVal);
	    if (result.next()) {
	        String file_name = result.getString("path");
	        String fileName = getSubstringAfterLastSlash(file_name);
	        Object fileData = result.getObject(blobColName);
	        byte[] content = (byte[]) fileData;
	        if (fileData != null) {
	            // Set response headers
	            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

	            // Write the content directly to the response output stream
	            OutputStream outputStream = response.getOutputStream();
	            outputStream.write(content);
	            outputStream.flush();
	            outputStream.close();
	        } else {
	            // File not found in the database
	            response.sendError(HttpServletResponse.SC_NOT_FOUND);
	        }
	    } else {
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	}

}




