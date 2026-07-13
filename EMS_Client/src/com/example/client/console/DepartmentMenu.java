package com.example.client.console;


import java.util.Scanner;

import com.example.client.dynamic.DynamicMenu;


public class DepartmentMenu {

    public static void departmentDashboard(Scanner sc) {

    	 DynamicMenu.load("department-menu.json", sc);
    }

}