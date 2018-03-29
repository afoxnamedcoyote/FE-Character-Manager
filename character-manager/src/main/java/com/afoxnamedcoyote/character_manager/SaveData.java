package com.afoxnamedcoyote.character_manager;

import java.util.ArrayList;

import com.afoxnamedcoyote.character_manager.PlayerCharacter;

public class SaveData {
	public ArrayList<PlayerCharacter> characters;

    public SaveData() {
        this.characters = new ArrayList<PlayerCharacter>();
    }
}
