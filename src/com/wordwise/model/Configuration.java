package com.wordwise.model;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wordwise.server.model.Difficulty;
import com.wordwise.server.model.Language;
import com.wordwise.util.LanguageUtils;

public class Configuration{
	private static Configuration instance = null;
	private Difficulty difficulty;
	private Set<Language> proficientLanguages = new HashSet<Language>();
	private Language learningLanguage = null;
	private SharedPreferences SP;

	private Configuration(Context context) {
		init(context);
	}
	
	private void init(Context context){
		SP = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
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

	public Difficulty loadDifficulty() {
		return Difficulty.getByDifficulty(SP.getInt("difficulty", Difficulty.EASY.getDifficulty()));
	}

	public Language loadLearningLanguage() {
		String langCode = SP.getString("learning_language", "");
		return LanguageUtils.getByCode(langCode);
	}

	public Set<Language> loadProficientLanguages() {
		Set<String> proficientLanguageCodes = SP.getStringSet("proficient_languages", new HashSet<String>());
		Set<Language> proficientLanguages = new HashSet<Language> ();
		for(String langCode : proficientLanguageCodes){
			proficientLanguages.add(LanguageUtils.getByCode(langCode));
		}
		return proficientLanguages;
	}

	public boolean saveDifficulty() {
		return SP.edit().putInt("difficulty", difficulty.getDifficulty()).commit();
	}

	public boolean saveLearningLanguage() {
		//do nothing if null
		if(learningLanguage == null)
			return false;
		return SP.edit().putString("learning_language", learningLanguage.getCode()).commit();
	}

	public boolean saveProficientLanguages() {
		Set<String> profLanguagesCodeSet = LanguageUtils.languageSetToCodeSet(proficientLanguages);
		return SP.edit().putStringSet("proficient_languages", profLanguagesCodeSet).commit();
	}

	public Difficulty getDifficulty() {
		loadDifficulty();
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
		// persist difficulty
		saveDifficulty();
	}

	public Set<Language> getProficientLanguages() {
		loadProficientLanguages();
		return proficientLanguages;
	}

	public void addLanguage(Language language) {
		proficientLanguages.add(language);
	}

	public void removeLanguage(Language language) {
		proficientLanguages.remove(language);
	}

	public Language getLearningLanguage() {
		loadLearningLanguage();
		return learningLanguage;
	}

	public void setLearningLanguage(Language language) {
		this.learningLanguage = language;
	}

	public boolean isConfigured() {
		return learningLanguage != null && !proficientLanguages.isEmpty();
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
