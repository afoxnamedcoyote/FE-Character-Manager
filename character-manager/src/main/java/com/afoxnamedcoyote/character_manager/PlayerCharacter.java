package com.afoxnamedcoyote.character_manager;

public class PlayerCharacter extends FECharacter {
	
	private static final int[] KILL_EXP = {59, 57, 53, 47, 41, 35, 30, 25, 19, 13, 7, 3, 1};
	private static final int[] DAMAGE_EXP = {20, 19, 18, 16, 14, 12, 10, 8, 6, 4, 2, 1, 0};

	public String name;
	public int exp;
	
	public PlayerCharacter(String name) {
		super();
		this.name = name;
		this.exp = 0;
	}
	
	public int awardExpForKill(int enemyLevel) {
		return this.awardExp(enemyLevel, KILL_EXP);
	}
	
	public int awardExpForDamage(int enemyLevel) {
		return this.awardExp(enemyLevel, DAMAGE_EXP);
	}
	
	private int awardExp(int enemyLevel, int[] expArray) {
		int arrayIndx = level - enemyLevel + 6;
		
		if(arrayIndx < 0) {
			arrayIndx = 0;
		} else if(arrayIndx > 12) {
			arrayIndx = 12;
		}
		
		return this.gainExp(expArray[arrayIndx]);
	}
	
	public int gainExp(int gainedExp) {
		this.exp += gainedExp;
		return gainedExp;
	}
	
	public boolean canLevelUp() {
		return exp >= 100;
	}
	
	public int[] levelUp() {
		int numLevels = exp / 100;
		this.exp = exp % 100;
		
		return this.levelUp(numLevels);
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer("");
		buf.append(String.format("Name: %s\n", name));
		buf.append(String.format("Level: %d (%d/100 EXP)\n", level, exp));
		buf.append(super.toString());
		
		return buf.toString();
	}
}
