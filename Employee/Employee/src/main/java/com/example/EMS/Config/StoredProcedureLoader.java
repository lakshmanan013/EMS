package com.example.EMS.Config;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StoredProcedureLoader {

    @Value("${stored.procedure.config.path}")
    private String configPath;

    private final Map<String, StoredProcedure> procedureMap =
            new HashMap<>();

    @PostConstruct
    public void loadProcedures() {

        try {

            ObjectMapper mapper = new ObjectMapper();

            StoredProcedureConfig config =
                    mapper.readValue(
                            new File(configPath),
                            StoredProcedureConfig.class);

            for (StoredProcedure procedure : config.getProcedures()) {

                procedureMap.put(
                        procedure.getKey(),
                        procedure);

            }

        }

        catch (Exception e) {

            throw new RuntimeException(
                    "Unable to load Stored Procedure Configuration",
                    e);

        }

    }

    public StoredProcedure getProcedure(String key) {

        StoredProcedure procedure =
                procedureMap.get(key);

        if (procedure == null) {

            throw new RuntimeException(
                    "Stored Procedure Not Found : " + key);

        }

        return procedure;

    }

}