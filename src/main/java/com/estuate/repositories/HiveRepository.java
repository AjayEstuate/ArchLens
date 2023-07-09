package com.estuate.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HiveRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SHOW_TABLES_QUERY = "show tables";
    private static final String SHOW_DATABASES_QUERY = "show databases";
    private static final String TABLE_PLACEHOLDER = "{table}", ID_NAME = "{idName}", ID_VALUE = "{idVal}";
    private static final String PREVIEW_TABLE_QUERY = "select * from " + TABLE_PLACEHOLDER + " limit 10";
    private static final String PREVIEW_ROW_QUERY = "select * from " + TABLE_PLACEHOLDER + " where " + ID_NAME + " = "+ID_VALUE;

    public List<Map<String, Object>> getTables(String schema) {
        jdbcTemplate.execute("use " + schema);
        return jdbcTemplate.queryForList(SHOW_TABLES_QUERY);
    }

    public List<Map<String, Object>> getSchemas() {
        return jdbcTemplate.queryForList(SHOW_DATABASES_QUERY);
    }

    public List<Map<String, Object>> getTablePreview(String schema, String table) {
        jdbcTemplate.execute("use " + schema);
        String query = PREVIEW_TABLE_QUERY.replace(TABLE_PLACEHOLDER, table);
        return jdbcTemplate.queryForList(query);
    }
    
    public List<Map<String, Object>> getBlobData(String schema, String table, String idName, String idVal ) {
        jdbcTemplate.execute("use " + schema);
        String query = PREVIEW_ROW_QUERY.replace(TABLE_PLACEHOLDER, table).replace(ID_NAME, idName).replace(ID_VALUE, idVal);
        System.out.println("Query"+query);
        return jdbcTemplate.queryForList(query);
    }
}
