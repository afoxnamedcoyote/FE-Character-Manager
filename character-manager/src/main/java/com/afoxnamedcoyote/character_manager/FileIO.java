package com.afoxnamedcoyote.character_manager;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.afoxnamedcoyote.character_manager.SaveData;

public class FileIO {
	public static final String DEFAULT_SAVE_FILE_NAME = "fe_characters_data.json";
	
	public static void save(SaveData data, String filename) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(gson.toJson(data));
			writer.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public static SaveData load(String filename) throws FileNotFoundException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(filename));
		return gson.fromJson(reader, SaveData.class);
	}
}
