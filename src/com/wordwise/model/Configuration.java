package com.wordwise.model;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class Configuration extends Activity{
	private static Configuration instance = null;
	private static int difficulty;
	private static List<String> proficientLanguages = new ArrayList<String>();
    private static String learningLanguage;
  //  private SharedPreferences SP;
	public static Configuration getInstance() {
		if (instance == null)
			return new Configuration();
		return instance;
	}

	// TODO IMPLEMENTATION NEEDED
	public void saveProficientLanguages() {
		// SAVE TO SETTINGS FILE
	//	SP = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
		//System.out.println("AL BAKALIM>>> " + SP.getString("proficient_languages", "test"));
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		Configuration.difficulty = difficulty;
	}

	public List<String> getProficientLanguages() {
		return proficientLanguages;
	}

	public void addLanguage(String language) {
		if (!proficientLanguages.contains(language))
			proficientLanguages.add(language);
	}

	public void removeLanguage(String language) {
		if (proficientLanguages.contains(language))
			proficientLanguages.remove(language);
	}

	public String getLearningLanguage() {
		return learningLanguage;
	}

	public void setLearningLanguage(String learningLanguage) {
		Configuration.learningLanguage = learningLanguage;
	}
}
