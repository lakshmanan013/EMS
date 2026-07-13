package com.example.client.formatter;


import java.util.Map;

import com.example.client.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;

public class ResponseFormatter {

    public static void print(String response) {

        try {

            JsonNode root = JsonUtil.getMapper().readTree(response);

            String message = root.path("message").asText();
            int statusCode = root.path("statusCode").asInt();

            JsonNode data = root.path("data");

            System.out.println();

            System.out.println("====================================");

            if (!message.isBlank()) {
                System.out.println(" " + message.toUpperCase());
            } else {
                System.out.println(" RESPONSE");
            }

            System.out.println("====================================");

            if (statusCode != 200 && statusCode != 201) {

                System.out.println(message);

                System.out.println("====================================");
                return;
            }
            if (data.isMissingNode()
                    || data.isNull()
                    || (data.isArray() && data.isEmpty())
                    || (data.isObject() && data.isEmpty())) {

                System.out.println("No records found.");
                System.out.println("====================================");
                return;
            }
            if (data.isObject()) {

                for (Map.Entry<String, JsonNode> field : data.properties()) {

                    System.out.printf("%-20s : %s%n",
                            formatLabel(field.getKey()),
                            field.getValue().asText());
                }

            } else if (data.isArray()) {

                for (JsonNode obj : data) {

                    for (Map.Entry<String, JsonNode> field : obj.properties()) {

                        System.out.printf("%-20s : %s%n",
                                formatLabel(field.getKey()),
                                field.getValue().asText());
                    }

                    System.out.println("------------------------------------");
                }
            }


            else {

                System.out.println(data.asText());

            }

            System.out.println("====================================");

        }

        catch (Exception e) {

            System.out.println(response);

        }

    }

    private static String formatLabel(String key) {

        StringBuilder sb = new StringBuilder();

        for (char ch : key.toCharArray()) {

            if (Character.isUpperCase(ch)) {

                sb.append(' ');

            }

            sb.append(ch);

        }

        String label = sb.toString();

        return Character.toUpperCase(label.charAt(0))
                + label.substring(1);

    }

}