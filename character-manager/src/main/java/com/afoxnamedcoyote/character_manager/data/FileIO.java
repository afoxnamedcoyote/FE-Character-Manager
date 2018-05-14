package com.afoxnamedcoyote.character_manager.data;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class FileIO {
	public static final String DEFAULT_SAVE_FILE_NAME = "fe_characters_data.json";
	
	public static MetaData loadMetaData() throws FileNotFoundException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("metadata.json"));
		return gson.fromJson(reader, MetaData.class);
	}
	
	public static SaveData loadDefault() throws FileNotFoundException {
		return load(DEFAULT_SAVE_FILE_NAME);
	}
	
	public static SaveData load(String filename) throws FileNotFoundException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(filename));
		return gson.fromJson(reader, SaveData.class);
	}
	
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
}
