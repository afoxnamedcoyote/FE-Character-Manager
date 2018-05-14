package com.afoxnamedcoyote.command_line;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.afoxnamedcoyote.character_manager.data.*;

public class MainLoop {
	private Scanner reader;
	private MetaData metaData;
	private SaveData saveData;
	private String[] userInput;
	
	public MainLoop(Scanner reader) {
		this.reader = reader;
		this.userInput = new String[0];
		
		if (loadAndInitializeData()) {
			run();
		}
	}
	
	private boolean loadAndInitializeData() {
		try{
			System.out.println("Loading system data...");
			this.metaData = FileIO.loadMetaData();
		} catch (FileNotFoundException i) {
			System.out.println("System data not found!");
			return false;
		}
		
		try {
			System.out.println("Loading save data...");
			this.saveData = FileIO.loadDefault();
		} catch (FileNotFoundException i) {
			System.out.println("Save data not found! Generating new data...");
			this.saveData = new SaveData();
		}
		
		return true;
	}
	
	private void run() {
		while (true) {
			System.out.println("\nWhat would you like to do? (Type 'help' for a list of commands)");
			System.out.printf("\n=> ");

			this.userInput = reader.nextLine().toLowerCase().split(" ");
			
			switch (userInput[0]) {
			case "exit":
				return;
			case "help":
				displayHelp();
				break;
			case "save":
				saveToFile();
				break;
			case "load":
				loadFromFile();
				break;
			case "characters":
				new CharactersLoop(reader, metaData, saveData.characters).run();
				break;
			default:
				System.out.println("Sorry, but that's not a registered command.");
				break;
			}
		}
	}
	
	private void saveToFile() {
		String fileName;
		if (userInput.length > 1) {
			fileName = userInput[1];
		} else {
			fileName = FileIO.DEFAULT_SAVE_FILE_NAME;
		}

		FileIO.save(saveData, fileName);
		System.out.printf("Successfully saved file %s\n", fileName);
	}
	
	private void loadFromFile() {
		String fileName;
		if (userInput.length > 1) {
			fileName = userInput[1];
		} else {
			fileName = FileIO.DEFAULT_SAVE_FILE_NAME;
		}

		try {
			this.saveData = FileIO.load(fileName);
			System.out.printf("Successfully opened file %s\n", fileName);
		} catch (FileNotFoundException i) {
			System.out.printf("File not found: %s\n", fileName);
		}
	}
	
	private void displayHelp() {
		System.out.println("Write this later");
	}
}
