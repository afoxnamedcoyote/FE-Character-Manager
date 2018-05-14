package com.afoxnamedcoyote.command_line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.afoxnamedcoyote.character_manager.CharacterClass;
import com.afoxnamedcoyote.character_manager.FECharacter;
import com.afoxnamedcoyote.character_manager.MookCharacter;
import com.afoxnamedcoyote.character_manager.PlayerCharacter;
import com.afoxnamedcoyote.character_manager.data.MetaData;

public class CharactersLoop {
	private Scanner reader;
	private MetaData metaData;
	private ArrayList<PlayerCharacter> characters;
	private String[] userInput;
	
	public CharactersLoop(Scanner reader, MetaData metaData, ArrayList<PlayerCharacter> characters) {
		this.reader = reader;
		this.metaData = metaData;
		this.characters = characters;
		this.userInput = new String[0];
	}
	
	public void run() {
		while (true) {
			System.out.println("\nThe characters are: (Type 'help' for a list of commands)");
			for (int i = 0; i < characters.size(); i++) {
				System.out.printf("%d) %s\n", i, characters.get(i).name);
			}
			System.out.printf("\n=> ");
			
			this.userInput = reader.nextLine().toLowerCase().split(" ");

			switch (userInput[0]) {
			case "back":
				return;
			case "help":
				displayHelp();
				break;
			case "new":
				newCharacter();
				break;
			case "details":
				displayCharacterDetails();
				break;
			default:
				awardExp();
				break;
			}
		}
	}
	
	private void newCharacter() {
		switch(userInput[1]) {
		case "character":
			createCharacter();
			break;
		case "enemy":
			generateEnemy();
			break;
		case "boss":
			generateBoss();
			break;
		default:
			System.out.println("Please insert a valid command.");
			System.out.println("The new command should look like: new (character | enemy | boss) CHARACTER_CLASS LEVEL");
			System.out.println("Type 'help new' for more information.");
			break;
		}
	}
	
	private void createCharacter() {
		String charName = String.join(" ", Arrays.copyOfRange(userInput, 1, userInput.length));
		this.characters.add(new PlayerCharacter(charName));
		System.out.printf("Created character: %s\n", charName);
	}
	
	private PlayerCharacter selectCharacter(String charIndexInput) throws CharacterNotFoundException {
		try {
			int charIndex = Integer.parseInt(charIndexInput);
			if (charIndex >= 0 && charIndex < characters.size()) {
				return characters.get(charIndex);
			}
		} catch (Exception i) {}

		throw new CharacterNotFoundException("Please select a character using the number displayed.");
	}
	
	private void displayCharacterDetails() {
		try {
			System.out.println(selectCharacter(userInput[1]).toString());
		} catch (CharacterNotFoundException i) {
			System.out.println(i.getMessage());
		}
	}
	
	private void awardExp() {
		try {
			PlayerCharacter selectedCharacter = selectCharacter(userInput[0]);
			int modifier = Integer.parseInt(userInput[2]);
			int expGained = 0;
			
			switch(userInput[1]) {
			case "kill":
				expGained = selectedCharacter.awardExpForKill(modifier);
				break;
			case "damage":
				expGained = selectedCharacter.awardExpForDamage(modifier);
				break;
			case "award":
				expGained = selectedCharacter.gainExp(modifier);
				break;
			default:
				throw new Exception();
			}
			
			System.out.printf("Gained %d EXP!\n", expGained);
			
			if (selectedCharacter.canLevelUp()) {
				int[] statGains = selectedCharacter.levelUp();
				System.out.printf("Leveled up! %s is now level %d!\n", selectedCharacter.name, selectedCharacter.level);
				System.out.println(selectedCharacter.statGainsToString(statGains));
			}
			
		} catch (CharacterNotFoundException i) {
			System.out.println(i.getMessage());
		} catch (Exception i) {
			System.out.println("Please insert a valid command.");
			System.out.println("The exp command should look like: CHARACTER_ID (kill | damage | award) NUMBER");
			System.out.println("Type 'help exp' for more information.");
		}
	}
	
	private void generateEnemy() {
		try {
			CharacterClass charClass = fetchCharacterClass(userInput[2]);
			int level = Integer.parseInt(userInput[3]);
			FECharacter newChar = new MookCharacter(level, charClass);
			System.out.printf("Created level %d %s:\n", level, charClass.name);
			System.out.println(newChar.toString());
			System.out.println(Arrays.toString(newChar.stats));
		} catch (CharacterNotFoundException i) {
			System.out.println(i.getMessage());
		} catch (Exception i) {
			System.out.println("Please insert a valid command.");
			System.out.println("The generate command should look like: generate CLASS LEVEL");
			System.out.println("Type 'help generate' for more information.");
		}
	}
	
	private void generateBoss() {
		try {
			CharacterClass charClass = fetchCharacterClass(userInput[2]);
			int level = Integer.parseInt(userInput[3]);
			FECharacter newChar = new FECharacter(level, charClass);
			System.out.printf("Created level %d %s:\n", level, charClass.name);
			System.out.println(newChar.toString());
			System.out.println(Arrays.toString(newChar.stats));
		} catch (CharacterNotFoundException i) {
			System.out.println(i.getMessage());
		} catch (Exception i) {
			System.out.println("Please insert a valid command.");
			System.out.println("The generate command should look like: generate CLASS LEVEL");
			System.out.println("Type 'help generate' for more information.");
		}
	}
	
    private CharacterClass fetchCharacterClass(String input) throws CharacterNotFoundException {
    	for(CharacterClass cc : metaData.characterClasses) {
    		if (cc.name.toLowerCase().split(" ")[0].equals(input)) {
    			return cc;
    		}
    	}

		throw new CharacterNotFoundException("Please select a valid character class.");
    }
		
	private void displayHelp() {
		System.out.println("Write this later");
	}
}
