package com.wordwise.model;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Configuration{
	static final int EASY = 1;
	static final int MEDIUM = 2;
	static final int HARD = 3;
	
	private final String APP_SHARED_PREFS = "com.wordwise";

	private static Configuration instance = null;
	private int difficulty;
	private Set<String> proficientLanguages;
	private String learningLanguage;
	private SharedPreferences SP;

	private Configuration(Context context) {
		SP = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());//context.getSharedPreferences(APP_SHARED_PREFS , Activity.MODE_PRIVATE);//PreferenceManager.getDefaultSharedPreferences(context);
		difficulty = loadDifficulty();
		learningLanguage = loadLearningLanguage();
		proficientLanguages = loadProficientLanguages();
	}

	public static Configuration getInstance(Context context) {
		if (instance == null) {
			instance = new Configuration(context);
		}
		return instance;
	}
	
	public static Configuration getInstance() {
		return instance;
	}

	public int loadDifficulty() {
		return SP.getInt("difficulty", EASY);
	}

	public String loadLearningLanguage() {
		return SP.getString("learning_language", "");
	}

	public Set<String> loadProficientLanguages() {
		return SP.getStringSet("proficient_languages", new HashSet<String>());
	}

	public boolean saveDifficulty() {
		return SP.edit().putInt("difficulty", difficulty).commit();
	}

	public boolean saveLearningLanguage() {
		return SP.edit().putString("learning_language", learningLanguage).commit();

	}

	public boolean saveProficientLanguages() {
		return SP.edit().putStringSet("proficient_languages", proficientLanguages).commit();
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		// persist difficulty
		saveDifficulty();
	}

	public Set<String> getProficientLanguages() {
		return proficientLanguages;
	}

	public void addLanguage(String language) {
		proficientLanguages.add(language);
	}

	public void removeLanguage(String language) {
		proficientLanguages.remove(language);
	}

	public String getLearningLanguage() {
		return learningLanguage;
	}

	public void setLearningLanguage(String learningLanguage) {
		this.learningLanguage = learningLanguage;

	}

	public boolean isConfigured() {
		return !learningLanguage.isEmpty() && !proficientLanguages.isEmpty();
	}

	public boolean finishInitialConfiguration() {
		if (isConfigured()) {
			// persist learning language and proficient languages
			saveLearningLanguage();
			saveProficientLanguages();
			return true;
		}
		return false;
	}
}
