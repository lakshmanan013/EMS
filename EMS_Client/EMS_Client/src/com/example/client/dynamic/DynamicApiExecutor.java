package com.example.client.dynamic;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;

import com.example.client.api.ApiClient;
import com.example.client.formatter.ResponseFormatter;
import com.example.client.logger.ApiResponseLogger;
import com.example.client.logger.LoggerUtil;
import com.example.client.util.JsonUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class DynamicApiExecutor {

    private static final Logger logger =
            LoggerUtil.getLogger(DynamicApiExecutor.class);

    public static boolean execute(MenuItem item, Scanner sc) {

        try {

            String method = item.getMethod().toUpperCase();
            String url = item.getUrl();


            Map<String, Object> inputValues = new HashMap<>();


            if (item.getFields() != null &&
                    !item.getFields().isEmpty()) {

                readInputs(item.getFields(), inputValues, sc);

            }


            url = buildUrl(url, inputValues);


            String requestBody = null;

            if ("POST".equals(method)
                    || "PUT".equals(method)) {

                requestBody = buildRequestBody(
                        item.getFields(),
                        inputValues);

            }
            
            String response;

            switch (method) {

                case "GET":

                    response =
                            ApiClient.get(url);

                    break;

                case "POST":

                    response =
                            ApiClient.post(
                                    url,
                                    requestBody);

                    break;

                case "PUT":

                    response =
                            ApiClient.put(
                                    url,
                                    requestBody);

                    break;

                case "DELETE":

                    response =
                            ApiClient.delete(url);

                    break;

                default:

                    System.out.println(
                            "Unsupported Method : "
                                    + method);

                    return false;

            }

            if (item.getSuccessMenu() == null || item.getSuccessMenu().isBlank()) {
                ResponseFormatter.print(response);
                ApiResponseLogger.log(item, response);
            }

            try {

            	JsonNode node = JsonUtil.getMapper().readTree(response);
            	
            	if (url.equals("/employees/login")
            	        && node.has("eid")) {

            	    com.example.client.session.EmployeeSession.employeeId =
            	            node.get("eid").asInt();

            	    com.example.client.session.EmployeeSession.employeeName =
            	            node.get("ename").asText();

            	    com.example.client.session.EmployeeSession.token =
            	            node.has("token") ? node.get("token").asText() : null;

            	    System.out.println();
            	    System.out.println("Login Successful");
            	    System.out.println("Employee Id : "
            	            + com.example.client.session.EmployeeSession.employeeId);
            	    System.out.println("Welcome "
            	            + com.example.client.session.EmployeeSession.employeeName);
            	    System.out.println();

            	    DynamicMenu.load("employee_menu.json", sc);

            	    return true;
            	}
            	
            	if (node.has("statusCode")
            	        && node.get("statusCode").asInt() == 200
            	        && item.getSuccessMenu() != null
            	        && !item.getSuccessMenu().isBlank()) {

            	    JsonNode data = node.path("data");

            	    if (url.equals("/admin/login") && data.has("adminId")) {

            	        com.example.client.session.AdminSession.adminId =
            	                data.get("adminId").asInt();

            	        com.example.client.session.AdminSession.adminUsername =
            	                data.has("username") ? data.get("username").asText() : null;

            	        com.example.client.session.AdminSession.token =
            	                data.has("token") ? data.get("token").asText() : null;
            	    }

            	    System.out.println();
            	    System.out.println(node.get("message").asText());
            	    System.out.println();

            	    DynamicMenu.load(item.getSuccessMenu(), sc);

            	    return true;
            	}

            } catch (Exception e) {

                logger.warn("Unable to determine API status.", e);

            }


            return true;

        }

        catch (Exception e) {

            logger.error(
                    "Execution Failed",
                    e);

            System.out.println(
                    e.getMessage());

            return false;

        }

    }


    private static String buildUrl(
            String url,
            Map<String, Object> values) {

        // Employee Dashboard APIs
        if (com.example.client.session.EmployeeSession.employeeId != null) {

            if (url.equals("/employees/{id}") && values.isEmpty()) {
                return "/employees/"
                        + com.example.client.session.EmployeeSession.employeeId;
            }

            if (url.equals("/departments/employee/{id}") && values.isEmpty()) {
                return "/departments/employee/"
                        + com.example.client.session.EmployeeSession.employeeId;
            }

            if (url.equals("/attendance/employee/{id}") && values.isEmpty()) {
                return "/attendance/employee/"
                        + com.example.client.session.EmployeeSession.employeeId;
            }
        }

        for (Map.Entry<String, Object> entry : values.entrySet()) {

            String placeholder = "{"
                    + entry.getKey()
                    + "}";

            if (url.contains(placeholder)) {

                url = url.replace(
                        placeholder,
                        String.valueOf(entry.getValue()));
            }
        }

        return url;
    }


    private static String buildRequestBody(

            List<InputField> fields,

            Map<String, Object> values)

            throws Exception {

        ObjectNode json =
                JsonUtil.getMapper()
                        .createObjectNode();

        for (InputField field : fields) {

            if ((field.getKey() == null) || field.getKey().equalsIgnoreCase("id")) {
				continue;
			}

            Object value =
                    values.get(field.getKey());

            if (value == null) {
				continue;
			}

            json.putPOJO(
                    field.getKey(),
                    value);

        }

        return JsonUtil
                .getMapper()
                .writeValueAsString(json);

    }

    private static void readInputs(
            List<InputField> fields,
            Map<String, Object> values,
            Scanner sc) {

        for (InputField field : fields) {

            while (true) {
            	
                System.out.print(field.getLabel() + " : ");

                String input = sc.nextLine().trim();

                if (field.isRequired() && input.isEmpty()) {

                    System.out.println(field.getLabel() + " is required.");
                    continue;

                }

                if (input.isEmpty()) {

                    values.put(field.getKey(), null);
                    break;

                }

                try {

                    Object value =
                            convertValue(
                                    input,
                                    field.getType());

                    values.put(
                            field.getKey(),
                            value);

                    break;

                }

                catch (Exception e) {

                    System.out.println(
                            "Invalid "
                            + field.getType()
                            + ". Try again.");

                }

            }

        }

    }


    private static Object convertValue(
            String value,
            String type) {

        if (type == null) {
			return value;
		}

        switch (type.toUpperCase()) {

            case "INT":
            case "INTEGER":
                return Integer.parseInt(value);

            case "LONG":
                return Long.parseLong(value);

            case "DOUBLE":
                return Double.parseDouble(value);

            case "FLOAT":
                return Float.parseFloat(value);

            case "BOOLEAN":
                return Boolean.parseBoolean(value);

            case "STRING":
            case "PASSWORD":
            	return value;

            case "EMAIL":
            	 if (!value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            	        throw new IllegalArgumentException("Invalid Email");
            	    }
            	    return value;
            case "DATE":
            	LocalDate.parse(value);   // validates yyyy-MM-dd
                return value;
            case "TIME":
            	 LocalTime.parse(value);   // validates HH:mm:ss
            	    return value;
            default:
                return value;

        }

    }


}