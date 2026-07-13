package com.example.client.console;


import java.util.Scanner;

import com.example.client.dynamic.DynamicMenu;

public class AttendanceMenu {

    public static void attendanceDashboard(Scanner sc) {

    	 DynamicMenu.load("attendance-menu.json", sc);
    }


}