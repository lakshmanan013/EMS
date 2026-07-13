package com.example.client.logger;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.example.client.dynamic.MenuItem;
import com.example.client.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;

public class ApiResponseLogger {

    private static final Logger logger =
            LoggerUtil.getLogger(ApiResponseLogger.class);

    public static void log(MenuItem item, String response) {

        try {

            JsonNode root =
                    JsonUtil.getMapper().readTree(response);

            int status =
                    root.path("statusCode").asInt();

            String message =
                    root.path("message").asText();

            JsonNode data =
                    root.path("data");

            logger.info("");
            logger.info("-----------------------------------------");
            logger.info("RESPONSE LOGS FOR : {}",
                    item.getName().toUpperCase());
            logger.info("-----------------------------------------");

            logger.info("Status Code    : {}", status);
            logger.info("Status Message : {}", message);

            if (data.isObject()) {

                logger.info("");
                logger.info("DETAILS");

                for (Map.Entry<String, JsonNode> field
                        : data.properties()) {

                    logger.info("  {} : {}",
                            format(field.getKey()),
                            field.getValue().asText());

                }

            }

            logger.info("-----------------------------------------");

        }

        catch (Exception e) {

            logger.warn("Unable to log response.");

        }

    }

    private static String format(String key) {

        StringBuilder sb = new StringBuilder();

        for (char c : key.toCharArray()) {

            if (Character.isUpperCase(c)) {

                sb.append(' ');

            }

            sb.append(c);

        }

        return Character.toUpperCase(sb.charAt(0))
                + sb.substring(1);

    }

}