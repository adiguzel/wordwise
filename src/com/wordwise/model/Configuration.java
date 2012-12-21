package com.wordwise.model;

import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.wordwise.WordwiseApplication;

public class Configuration {
	static final int EASY = 1;
	static final int MEDIUM = 2;
	static final int HARD = 3;
	private static Configuration instance = null;
	private int difficulty;
	private Set<String> proficientLanguages;
	private String learningLanguage;
	private SharedPreferences SP;

	private Configuration() {
		SP = PreferenceManager.getDefaultSharedPreferences(WordwiseApplication
				.getAppContext());
		difficulty = loadDifficulty();
		learningLanguage = loadLearningLanguage();
		proficientLanguages = loadProficientLanguages();
		if(!learningLanguage.isEmpty())
		Log.v("CONF FIN LEARN", learningLanguage);
	}

	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}

	public int loadDifficulty() {
		Log.v("CONF DIFFICULTY", Integer.toString((SP.getInt("difficulty", EASY))));
		return SP.getInt("difficulty", EASY);
	}

	public String loadLearningLanguage() {
		Log.v("CONF LEARN", SP.getString("learning_language", ""));
		return SP.getString("learning_language", "");
	}

	public Set<String> loadProficientLanguages() {
		Log.v("CONF PROF",( SP.getStringSet("proficient_languages", new HashSet<String>())).toString());
		return SP.getStringSet("proficient_languages", new HashSet<String>());
	}

	public void saveDifficulty() {
		SP.edit().putInt("difficulty", difficulty);
	}

	public void saveLearningLanguage() {
		SP.edit().putString("learning_language", learningLanguage);
	}

	public void saveProficientLanguages() {
		SP.edit().putStringSet("proficient_languages", proficientLanguages);
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
