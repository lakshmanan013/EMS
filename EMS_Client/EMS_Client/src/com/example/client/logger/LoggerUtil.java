package com.example.client.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

    private LoggerUtil() {
        // Prevent instantiation
    }

    public static Logger getLogger(Class<?> cls) {
        return LogManager.getLogger(cls);
    }
}