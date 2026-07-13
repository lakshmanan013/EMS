package com.example.client.console;

import java.util.Scanner;

import com.example.client.dynamic.DynamicMenu;

public class EmployeeMenu {

	public static void employeeDashboard(Scanner sc) {

		 DynamicMenu.load("employee-menu.json", sc);
	}
}

