package com.dataexport.TransactionServer.service.fileExporter;

import com.dataexport.TransactionServer.service.interfaces.Exporter;
import com.dataexport.TransactionServer.service.utality.FileExportUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class JsonExporter implements Exporter {

    Logger log = LoggerFactory.getLogger(JsonExporter.class);

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    @Override
    public void export(Integer exportId, List<?> data) {
        if (data == null || data.isEmpty()) return;

        File file = FileExportUtils.getOrCreateFile("json-files", "export_" + exportId + ".json");

        try (FileWriter writer = new FileWriter(file)) {
            objectMapper.writeValue(writer, data);
            log.info("JSON file exported to: {}", file.getAbsolutePath());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

