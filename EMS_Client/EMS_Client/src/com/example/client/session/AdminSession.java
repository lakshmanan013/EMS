package com.example.client.session;

public class AdminSession {
    public static Integer adminId;
    public static String adminUsername;
    public static String token;

    public static void clear() {
        adminId = null;
        adminUsername = null;
        token = null;
    }
}