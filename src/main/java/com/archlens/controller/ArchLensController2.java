package com.archlens.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.archlens.configuration.ExternalTableConfig;
import com.archlens.entity.ExternalTableDataSource;
import com.archlens.service.ArchLensService;

@RestController
public class ArchLensController2 {

	@PostMapping("/data-source")
	public ResponseEntity<?> addConfig(@RequestBody ExternalTableDataSource configData) {
		try {
			String respone = ArchLensService.addConfig(configData);
			return new ResponseEntity<String>(respone, HttpStatus.CREATED);
		} catch (SQLException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/data-sources")
	public List getDataSources() {
		List<String> ds = null;
		try { 
			ds = ArchLensService.getJsonKeysFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String, String>> mapList = convertListToMap(ds, "dataSource");
		List list = new ArrayList<>();
		for (Map<String, String> map : mapList) {
            list.add(map);
        }
		return list;
	}	

	public static List<Map<String, String>> convertListToMap(List<String> list, String key) {
        List<Map<String, String>> mapList = new ArrayList<>();

        for (String item : list) {
            Map<String, String> map = new HashMap<>();
            map.put(key, item);
            mapList.add(map);
        }

        return mapList;
    }

	@GetMapping("/schemas")
	public List getSchemas(String datasource) {
		List<String> schemas = null;
		try {
			schemas = ExternalTableConfig.getSchemas(datasource);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String, String>> mapList = convertListToMap(schemas, "schema");
		List list = new ArrayList<>();
		for (Map<String, String> map : mapList) {
            list.add(map);
        }
		return list;
	}

	@GetMapping("/tables")
	public List getTables(String datasource, String schema) {
		List<String> tables = null;
		try {
			tables = ExternalTableConfig.getTables(datasource, schema);
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String, String>> mapList = convertListToMap(tables, "table");
		List list = new ArrayList<>();
		for (Map<String, String> map : mapList) {
            list.add(map);
        }
		return list;
	}

	@GetMapping(value = "/view", produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> viewBlob(String datasource, String schema, String table, String blobColName,
			String fileName, String idName, String idVal, HttpServletResponse response) {

		try {
			ArchLensService.viewBlobData(datasource, schema, table, blobColName, fileName, idName, idVal, response);

			return new ResponseEntity<String>("Details Fetched Sucesfully", HttpStatus.CREATED);
		} catch (SQLException c) {
			return new ResponseEntity<String>(c.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (IOException p) {
			return new ResponseEntity<String>(p.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping(value = "/download", produces = MediaType.ALL_VALUE)
	public void downloadFile(String datasource, String schema, String table, String blobColName, String file_name,
			String idName, String idVal, HttpServletResponse response) throws IOException, SQLException {

		List result = ExternalTableConfig.createConnection(datasource, schema, table, blobColName, file_name, idName,
				idVal);

		Object fileData = result.get(0);
		String fileName = (String) result.get(1);

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

	}

}
