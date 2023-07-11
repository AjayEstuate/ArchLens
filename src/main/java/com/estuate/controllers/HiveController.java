package com.estuate.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estuate.services.HiveService;

@RestController
@RequestMapping("/blob-viewer")
public class HiveController {
	



	private byte[] parseBinaryData(byte[] binaryData) {
		try {
			return Base64.getDecoder().decode(binaryData);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Autowired
	private HiveService hiveService;

	@GetMapping(value = "/{schema}/tables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Map<String, Object>>> getTablesForSchema(@PathVariable String schema) {
		List<Map<String, Object>> rows = hiveService.getTables(schema);
		return new ResponseEntity<>(rows, HttpStatus.OK);
	}

	@GetMapping(value = "/schemas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Map<String, Object>>> getSchemas() {
		List<Map<String, Object>> rows = hiveService.getSchemas();
		return new ResponseEntity<>(rows, HttpStatus.OK);
	}

	@GetMapping(value = "/{schema}/preview/{table}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Map<String, Object>>> previewTable(
			@PathVariable String schema, @PathVariable String table
			) {
		List<Map<String, Object>> rows = hiveService.getTablePreview(schema, table);
		return new ResponseEntity<>(rows, HttpStatus.OK);
	}

//	@GetMapping(value = "/{schema}/view/{table}/blobData={blobVaLColName}/fileName={fileNameCN}/extension={extensionCN}/{idName}={idVal}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public void getBlobData(@PathVariable String schema,@PathVariable String table,
//			@PathVariable String idName, @PathVariable String idVal,
//			@PathVariable String blobVaLColName, @PathVariable String fileNameCN, @PathVariable String extensionCN,
//			HttpServletResponse response) {
//		List<Map<String, Object>> rows = hiveService.getBlobData(schema, table, idName, idVal);
//		System.out.println(rows);
//		Map<String, Object> map = rows.get(0);
//		byte[] blobData =  (byte[]) map.get(blobVaLColName);
//		String fileName = (String) map.get(fileNameCN);
//		String fileExtension = (String) map.get(extensionCN);
//
//		byte[] data = parseBinaryData(blobData);
//		if (data != null) {
//			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ fileName+"."+ fileExtension);
//
//			try {
//				response.getOutputStream().write(data);
//				response.getOutputStream().flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//		}
//	}
	
	@GetMapping(value = "/{schema}/{table}/{idName}/{idVal}", produces = MediaType.ALL_VALUE)
	public void viewBlobContent(@PathVariable String schema,@PathVariable String table,
								@PathVariable String idName, @PathVariable String idVal, 
								HttpServletResponse response) throws IOException {
		List<Map<String, Object>> rows = hiveService.getBlobData(schema, table, idName, idVal);
		System.out.println("APi Hitted");
		Map<String, Object> map = rows.get(0);
		System.out.println(map);
		Object data = map.get("content");
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



