package com.afoxnamedcoyote.character_manager;

import java.util.Random;
import java.io.Serializable;

public class PlayerCharacter implements Serializable {
	public static final String[] STAT_NAMES = {"HP", "STR", "MAG", "DEF", "RES", "SKL", "SPD", "LUK"};
	public static final int NUM_STATS = 8;
	
	private static final long serialVersionUID = 1L;
	private static final int[] KILL_EXP = {59, 57, 53, 47, 41, 35, 30, 25, 19, 13, 7, 3, 1};
	private static final int[] DAMAGE_EXP = {20, 19, 18, 16, 14, 12, 10, 8, 6, 4, 2, 1, 0};
	
	public String name;
	public int level;
	public int exp;
	private int[] stats;
	private int[] statGrowths;
	
	public PlayerCharacter(String name) {
		this.name = name;
		this.level = 1;
		this.exp = 0;
		this.stats = new int[NUM_STATS];
		this.statGrowths = new int[NUM_STATS];
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
	
	public int[] levelUp(int numLevels) {
		int[] results = new int[NUM_STATS];
		
		if (numLevels < 1) {
			return results;
		}
		
		for (int levelCount = 0; levelCount < numLevels; levelCount++) {
			this.level++;
			for (int i = 0; i < NUM_STATS; i++) {
				results[i] = this.levelStat(this.statGrowths[i]);
				this.stats[i] += results[i];
			}
		}
		
		return results;
	}
	
	private int levelStat(int growth) {
		Random rand = new Random();

		int gain = growth / 100;
		if (rand.nextInt(100) < growth % 100) {
			gain++;
		}
			
		return gain;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer("");
		buf.append(String.format("Name: %s\n", name));
		buf.append(String.format("Level: %d (%d/100 EXP)\n", level, exp));
		for (int i = 0; i < NUM_STATS; i++) {
			buf.append(String.format("%s: %d (%d%% growth)\n", STAT_NAMES[i], stats[i], statGrowths[i]));
		}
		
		return buf.toString();
	}
}
