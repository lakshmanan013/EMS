package com.example.client.config;

import java.io.FileInputStream;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String CONFIG_PATH =
            "D:/EMS_Config/config.json";

    private static Config config;

    static {

        try (InputStream is = new FileInputStream(CONFIG_PATH)) {

            config = mapper.readValue(is, Config.class);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to load config file: " + CONFIG_PATH, e);

        }

    }

    public static Config getConfig() {
        return config;
    }
}