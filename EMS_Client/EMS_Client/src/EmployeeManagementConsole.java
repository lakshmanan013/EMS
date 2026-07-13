import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.example.client.config.ConfigLoader;
import com.example.client.dynamic.DynamicMenu;
import com.example.client.logger.LoggerUtil;

public class EmployeeManagementConsole {

	static {
        Configurator.initialize(
                null,
                "file///D:/EMS_XML/log4j2.xml");
    }
    private static final Logger logger =
            LoggerUtil.getLogger(EmployeeManagementConsole.class);

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler((thread, ex) ->
                logger.error("Uncaught exception in thread {}", thread.getName(), ex));

        logger.info("================================");
        logger.info("EMS Console Application Started");
        logger.info("================================");

        int invalidAttempts = 0;
        int maxAttempts = ConfigLoader.getConfig().getMaxInvalidAttempts();

        while (true) {

            try {

                System.out.println("\n====================================");
                System.out.println(" EMPLOYEE MANAGEMENT SYSTEM ");
                System.out.println("====================================");

                System.out.println("1. Admin Login");
                System.out.println("2. Employee Login");
                System.out.println("3. Exit");
                System.out.println("\n");
                System.out.print("Enter Choice : ");

                int choice;

                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {

                    invalidAttempts++;

                    logger.warn("Non-numeric input entered at main menu");

                    System.out.println("Invalid Choice (" + invalidAttempts + "/" + maxAttempts + ")");

                    if (invalidAttempts >= maxAttempts) {
                    	System.out.println("Maximum invalid attempts reached.");
                    	logger.warn("Maximum invalid attempts reached. Application terminated.");
                    	System.exit(0);
                    }

                    continue;
                }

                logger.info("User Selected Option : {}", choice);

                switch (choice) {

                    case 1:

                        invalidAttempts = 0;

                        logger.info("Navigating To Admin Login");

                        DynamicMenu.load("login-menu.json", sc);


                        break;
                    
                    case 2:
                    	
                    	invalidAttempts = 0;
                    	
                    	logger.info("Navigating To Employee Login");
                    	
                        DynamicMenu.load("login-menu.json", sc);
                       
                        break;
                        
                    case 3:

                        logger.info("User selected: Exit");
                        logger.info("Application Closed");

                        System.out.println("Thank You");
                        return;

                    default:

                        invalidAttempts++;

                        logger.warn("Invalid menu choice entered: {}", choice);

                        System.out.println("Invalid Choice (" + invalidAttempts + "/" + maxAttempts + ")");

                        if (invalidAttempts >= maxAttempts) {
                        	System.out.println("Maximum invalid attempts reached.");
                        	logger.warn("Maximum invalid attempts reached. Application terminated.");
                        	System.exit(0);
                        }

                }

            } catch (Exception e) {

                logger.error("Unexpected error in main menu loop", e);

                System.out.println("Something went wrong. Please try again.");
            }
        }
    }

}