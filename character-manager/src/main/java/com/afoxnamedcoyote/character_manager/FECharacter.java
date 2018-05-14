package com.afoxnamedcoyote.character_manager;

import java.util.Arrays;
import java.util.Random;

public class FECharacter {
	public static final String[] STAT_NAMES = {"HP", "STR", "MAG", "DEF", "RES", "SKL", "SPD", "LUK"};
	public static final int NUM_STATS = 8;
	public static final int[] DEFAULT_STAT_GROWTHS = {80, 70, 65, 60, 50, 35, 25, 20};

	public int level;
	public int[] stats;
	public int[] statGrowths;
	
	public FECharacter() {
		this.level = 1;
		this.stats = new int[NUM_STATS];
		this.statGrowths = new int[NUM_STATS];
	}
	
	public FECharacter(int level, CharacterClass charClass) {
		this.level = 1;
		this.stats = Arrays.copyOf(charClass.baseStats, NUM_STATS);
		this.statGrowths = Arrays.copyOf(charClass.defaultGrowths, NUM_STATS);
		
		generateRandomStatGrowths();
		levelUp(level - 1);
	}
	
	public int[] levelUp(int numLevels) {
		int[] results = new int[NUM_STATS];
		
		if (numLevels < 1) {
			return results;
		}
		
		this.level += numLevels;
		
		for (int i = 0; i < NUM_STATS; i++) {
			for (int levelCount = 0; levelCount < numLevels; levelCount++) {
				results[i] += this.levelStat(this.statGrowths[i]);
			}
			
			this.stats[i] += results[i];
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
		for (int i = 0; i < NUM_STATS; i++) {
			buf.append(String.format("%s: %d (%d%% growth)\n", STAT_NAMES[i], stats[i], statGrowths[i]));
		}
		
		return buf.toString();
	}
	
	public String statGainsToString(int[] statGains) {
		StringBuffer buf = new StringBuffer("");
		
		for (int i = 0; i < PlayerCharacter.NUM_STATS; i++) {
			if (statGains[i] > 0) {
				buf.append(String.format("+%d %s\n", statGains[i], STAT_NAMES[i]));
			}
		}
		return buf.toString();
	}
	
	private void generateRandomStatGrowths() {
		Random rand = new Random();
		this.statGrowths = Arrays.copyOf(DEFAULT_STAT_GROWTHS, NUM_STATS);
		for (int i = 0; i < NUM_STATS; i++) {
			int index = rand.nextInt(NUM_STATS);
			int temp = this.statGrowths[index];
			this.statGrowths[index] = this.statGrowths[i];
			this.statGrowths[i] = temp;
		}
	}
	
	public void applyStatModifier(double variance) {
		Random rand = new Random();
		for (int i = 0; i < NUM_STATS; i++) {
			int bounds = (int) (stats[i] * variance);
			if (bounds > 0) {
				this.stats[i] = this.stats[i] - rand.nextInt(bounds);
			}
		}
	}
}
