package com.estuate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estuate.repositories.HiveRepository;

import java.util.List;
import java.util.Map;

@Service
public class HiveService {

    @Autowired
    private HiveRepository hiveRepository;

    public List<Map<String, Object>> getTables(String schema) {
        return hiveRepository.getTables(schema);
    }

    public List<Map<String, Object>> getSchemas() {
        return hiveRepository.getSchemas();
    }

    public List<Map<String, Object>> getTablePreview(String schema, String table) {
        return hiveRepository.getTablePreview(schema, table);
    }
}
