package com.example.client.dynamic;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;

import com.example.client.config.ConfigLoader;
import com.example.client.logger.LoggerUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DynamicMenu {

    private static final Logger logger =
            LoggerUtil.getLogger(DynamicMenu.class);

    private static final ObjectMapper mapper =
            new ObjectMapper();

    private static final String CONFIG_PATH =
            "D:/EMS_Config/";

    public static void load(String fileName, Scanner sc) {

        List<MenuItem> menuItems;

        try {
            menuItems = readMenuItems(fileName);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("Unable to load menu {}", fileName, e);
            System.out.println("Unable to load menu.");
            return;
        }

        if (menuItems.size() == 1 &&
                menuItems.get(0).isAutoExecute()) {

            DynamicApiExecutor.execute(menuItems.get(0), sc);
            return;
        }
        
        int invalidAttempts = 0;
        
        int maxAttempts =
        		ConfigLoader.getConfig().getMaxInvalidAttempts();

        while (true) {

            try {
                menuItems = readMenuItems(fileName);
            } catch (Exception e) {
                logger.warn("Unable to refresh menu {}", fileName);
            }

            System.out.println();
            System.out.println("====================================");
            System.out.println("              MENU");
            System.out.println("====================================");

            for (MenuItem item : menuItems) {
                System.out.println(item.getId() + ". " + item.getName());
            }

            logMenu(menuItems);

            System.out.print("\nEnter Choice : ");

            int choice;

            try {

                choice = Integer.parseInt(sc.nextLine());

            } catch (Exception e) {

                invalidAttempts++;

                System.out.println(
                        "Invalid Input (" +
                                invalidAttempts +
                                "/" +
                                maxAttempts +
                                ")");

                if (invalidAttempts >= maxAttempts) {

                    logger.warn("Maximum invalid attempts reached.");

                    System.out.println(
                            "\nMaximum invalid attempts reached.");

                    return;
                }

                continue;
            }

            MenuItem selected = null;

            for (MenuItem item : menuItems) {

                if (item.getId() == choice) {

                    selected = item;
                    break;

                }

            }

            if (selected == null) {

                invalidAttempts++;

                System.out.println(
                        "Invalid Choice (" +
                                invalidAttempts +
                                "/" +
                                maxAttempts +
                                ")");

                if (invalidAttempts >= maxAttempts) {

                	  logger.warn("Maximum invalid attempts reached. Application terminated.");

                	    System.out.println("\nMaximum invalid attempts reached.");
                	    System.out.println("Application Terminated.");

                	    System.exit(0);

                }

                continue;
            }

            invalidAttempts = 0;

            logger.info("User Selected Option : {}", choice);

            logger.info("Selected Menu : {}", selected.getName());


            if ("SYSTEM".equalsIgnoreCase(selected.getMethod())) {

            	 logger.info("System Menu Selected : {}", selected.getName());

            	    if ("Logout".equalsIgnoreCase(selected.getName())) {

            	        com.example.client.session.EmployeeSession.clear();
            	        com.example.client.session.AdminSession.clear();

            	        System.out.println("Logged out successfully.");

            	        return;
            	    }

            	    if ("Back".equalsIgnoreCase(selected.getName())) {
            	        return;
            	    }

            	    if ("Exit".equalsIgnoreCase(selected.getName())) {

            	        System.out.println("Thank You");
            	        System.out.println("Application Terminated");

            	        System.exit(0);
            	    }

            	    return;
            }


            if (selected.getMenu() != null &&
                    !selected.getMenu().isBlank()) {

                logger.info(
                        "Opening Menu {}",
                        selected.getMenu());

                load(selected.getMenu(), sc);

                continue;
            }


            boolean success =
                    DynamicApiExecutor.execute(selected, sc);

            if (!success) {

                System.out.println(
                        "Operation failed.");

            }

            while (true) {

                System.out.println();
                System.out.println("1. Continue");
                System.out.println("2. Exit");
                System.out.print("Enter Choice : ");

                try {

                    int option = Integer.parseInt(sc.nextLine());

                    if (option == 1) {
                        break;
                    }

                    if (option == 2) {
                    	 System.out.println("Thank You");
                    	 System.out.println("Application Terminated");
                    	 System.exit(0);
                    }

                    System.out.println("Invalid Choice. Please try again.");

                } catch (NumberFormatException e) {

                    System.out.println("Invalid Choice. Please try again.");
                }
            }

        }

    }

    	private static List<MenuItem> readMenuItems(String fileName) throws Exception {

    	    try (InputStream is =
    	            new FileInputStream(CONFIG_PATH + fileName)) {

    	        return mapper.readValue(
    	                is,
    	                new TypeReference<List<MenuItem>>() {});
    	    }
    	}


    private static void logMenu(List<MenuItem> menuItems) {

        logger.info("====================================");
        logger.info("MENU OPTIONS");
        logger.info("====================================");
        
        for (MenuItem item : menuItems) {

            logger.info("{}. {}", item.getId(), item.getName());

        }

        logger.info("====================================");

    }

}