package com.example.client.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.example.client.config.ConfigLoader;
import com.example.client.logger.LoggerUtil;
import com.example.client.util.JsonUtil;

import com.fasterxml.jackson.databind.JsonNode;

public class ApiClient {

    private static final Logger logger = LoggerUtil.getLogger(ApiClient.class);

    private static final String BASE_URL =
            ConfigLoader.getConfig().getBaseUrl();

    private static void logRequest(String method,
            String endpoint,
            String json) {

        logger.info("----------------------------------------");
        logger.info("HTTP Outbound Dispatch Method -> {}", method);
        logger.info("Target Absolute Path Endpoint -> {}", BASE_URL + endpoint);

        if (json != null && !json.isBlank()) {

            logger.info("REQUEST PARAMETERS");

            try {

                JsonNode node =
                        JsonUtil.getMapper().readTree(json);

                for (Map.Entry<String, JsonNode> field : node.properties()) {

                    String key = field.getKey();
                    String value = field.getValue().asText();

                    if (key.equalsIgnoreCase("password")) {
                        value = "********";
                    }

                    logger.info("  {} : {}",
                            formatLabel(key),
                            value);

                }

            }

            catch (Exception e) {

                logger.warn("Unable to read request body.");

            }

        }

        logger.info("----------------------------------------");

    }

    private static String formatLabel(String key) {

        StringBuilder sb = new StringBuilder();

        for (char c : key.toCharArray()) {

            if (Character.isUpperCase(c)) {
                sb.append(' ');
            }

            sb.append(c);

        }

        String label = sb.toString();

        return Character.toUpperCase(label.charAt(0))
                + label.substring(1);

    }


    private static void logResponseStatus(int status) {

    	  logger.info("Response Status : {}", status);
    	    logger.info("----------------------------------------");

    }
    private static void logError(Exception e) {

        logger.error("========================================");

        logger.error("API ERROR");

        logger.error(e.getMessage(), e);

        logger.error("========================================");

    }

    public static String get(String endpoint) throws Exception {
        return execute("GET", endpoint, null);
    }

    public static String delete(String endpoint) throws Exception {
        return execute("DELETE", endpoint, null);
    }

    public static String post(String endpoint, String json) throws Exception {
        return execute("POST", endpoint, json);
    }

    public static String put(String endpoint, String json) throws Exception {
        return execute("PUT", endpoint, json);
    }


    private static String execute(String method,
            String endpoint,
            String requestBody) throws Exception {

			logRequest(method, endpoint, requestBody);

			HttpURLConnection con = null;

			try {

				URL url = new URI(BASE_URL + endpoint).toURL();

				con = (HttpURLConnection) url.openConnection();

				con.setRequestMethod(method);

				con.setRequestProperty("Content-Type", "application/json");

				con.setRequestProperty("Accept", "application/json");

				String authToken =
						com.example.client.session.AdminSession.token != null
								? com.example.client.session.AdminSession.token
								: com.example.client.session.EmployeeSession.token;

				if (authToken != null && !authToken.isBlank()) {
					con.setRequestProperty("Authorization", "Bearer " + authToken);
				}

			if (requestBody != null) {

				con.setDoOutput(true);

			try (OutputStream os = con.getOutputStream()) {

				os.write(requestBody.getBytes(StandardCharsets.UTF_8));

			}

		}

			int status = con.getResponseCode();

			InputStream is;

			if (status >= 200 && status < 300) {

				is = con.getInputStream();

			}

			else {

				is = con.getErrorStream();

			}

	BufferedReader br =
			new BufferedReader(
			      new InputStreamReader(is));

	StringBuilder response =
			new StringBuilder();

	String line;

	while ((line = br.readLine()) != null) {

			response.append(line);

		}



	logResponseStatus(
			status);

	return response.toString();

}

	catch (Exception e) {

		logError(e);

	throw e;

	}

	finally {

		if (con!= null) {

			con.disconnect();

		}

	}

    }
}