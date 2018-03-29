package com.afoxnamedcoyote.command_line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.afoxnamedcoyote.character_manager.PlayerCharacter;

public class CharactersLoop {
	private Scanner reader;
	private ArrayList<PlayerCharacter> characters;
	private String[] userInput;
	
	public CharactersLoop(Scanner reader, ArrayList<PlayerCharacter> characters) {
		this.reader = reader;
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
			
			this.userInput = reader.nextLine().split(" ");

			switch (userInput[0]) {
			case "back":
				return;
			case "help":
				displayHelp();
				break;
			case "create":
				createCharacter();
				break;
			case "details":
				displayCharacterDetails();
				break;
			case "exp":
				awardExp();
				break;
			default:
				System.out.println("Sorry, but that's not a registered command.");
				break;
			}
		}
	}
	
	private void createCharacter() {
		String charName = String.join(" ", Arrays.copyOfRange(userInput, 1, userInput.length));
		this.characters.add(new PlayerCharacter(charName));
		System.out.printf("Created character: %s\n", charName);
	}
	
	private PlayerCharacter selectCharacter() throws CharacterNotFoundException {
		try {
			int charIndex = Integer.parseInt(userInput[1]);
			if (charIndex >= 0 && charIndex < characters.size()) {
				return characters.get(charIndex);
			}
		} catch (Exception i) {}

		System.out.println("Please select a character using the number displayed.");
		throw new CharacterNotFoundException();
	}
	
	private void displayCharacterDetails() {
		try {
			System.out.println(selectCharacter().toString());
		} catch (CharacterNotFoundException i) {}
	}
	
	private void awardExp() {
		try {
			PlayerCharacter selectedCharacter = selectCharacter();
			String command = userInput[2];
			int modifier = Integer.parseInt(userInput[3]);
			int expGained = 0;
			
			switch(command) {
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
				System.out.printf("Gained a level! %s is now level %d!\n", selectedCharacter.name, selectedCharacter.level);

				for (int i = 0; i < PlayerCharacter.NUM_STATS; i++) {
					if (statGains[i] > 0) {
						System.out.printf("+%d %s\n", statGains[i], PlayerCharacter.STAT_NAMES[i]);
					}
				}
			}
			
		} catch (CharacterNotFoundException i) {
			return;
		} catch (Exception i) {
			System.out.println("Please insert a valid command.");
			System.out.println("The exp command should look like: exp CHARACTER_NUMBER (kill | damage | award) NUMBER");
			System.out.println("Type 'help exp' for more information.");
		}
	}
		
	private void displayHelp() {
		System.out.println("Write this later");
	}
}
