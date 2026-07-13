package com.example.EMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EMSApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        
    	 System.setProperty("spring.config.location",
    	            "file:D:/EMS_BACKEND_CONFIG/application.properties");

    	 System.setProperty("logging.config",
    	            "file:D:/EMS_BACKEND_CONFIG/logback.xml");

    	    
    	return builder.sources(EMSApplication.class);
    }
    
	public static void main(String[] args) {
		
		System.setProperty("spring.config.location",
 	            "file:D:/EMS_BACKEND_CONFIG/application.properties");

		System.setProperty("logging.config",
 	            "file:D:/EMS_BACKEND_CONFIG/logback.xml");

		SpringApplication.run(EMSApplication.class, args);
	}
}

