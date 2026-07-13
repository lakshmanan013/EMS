package com.example.client.console;



import java.util.Scanner;

import com.example.client.dynamic.DynamicMenu;

public class AdminMenu {

    public static void adminDashboard(Scanner sc) {

    	DynamicMenu.load("admin-menu.json", sc);
    }

}