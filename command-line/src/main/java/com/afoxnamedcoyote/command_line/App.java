package com.afoxnamedcoyote.command_line;

import java.util.Scanner;

public class App {
	public static void main(String [] args) {
		System.out.println("Welcome to the Fire Emblem Tabletop RPG Character Manager!");
		Scanner reader = new Scanner(System.in);
		
		new MainLoop(reader);
		
		reader.close();
		System.out.println("Goodbye!");
	}

}
