package com.afoxnamedcoyote.character_manager;

public class MookCharacter extends FECharacter {
	public MookCharacter(int level, CharacterClass charClass) {
		super(level, charClass);
		applyStatModifier();
	}
	
	public void applyStatModifier() {
		applyStatModifier(0.2);
	}
}
