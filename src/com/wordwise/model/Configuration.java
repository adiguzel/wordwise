package com.wordwise.model;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wordwise.gameengine.GameConfiguration;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.util.LanguageUtils;

public class Configuration{
	private static Configuration instance = null;
	private DTODifficulty difficulty;
	private Set<DTOLanguage> proficientLanguages = new HashSet<DTOLanguage>();
	private DTOLanguage learningLanguage = null;
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
	
	public GameConfiguration getCurrentGameConfiguration(){
		GameConfiguration gameConf = new GameConfiguration();
		gameConf.setDifficulty(getDifficulty());
		gameConf.setLearningLanguage(getLearningLanguage());
		gameConf.setProficientLanguage(getProficientLanguages());
		return gameConf;
	}
	
	public DTODifficulty loadDifficulty() {
		return DTODifficulty.getByDifficulty(SP.getInt("difficulty", DTODifficulty.EASY.getDifficulty()));
	}

	public DTOLanguage loadLearningLanguage() {
		String langCode = SP.getString("learning_language", "");
		return LanguageUtils.getByCode(langCode);
	}

	public Set<DTOLanguage> loadProficientLanguages() {
		Set<String> proficientLanguageCodes = SP.getStringSet("proficient_languages", new HashSet<String>());
		Set<DTOLanguage> proficientLanguages = new HashSet<DTOLanguage> ();
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

	public DTODifficulty getDifficulty() {
		loadDifficulty();
		return difficulty;
	}

	public void setDifficulty(DTODifficulty difficulty) {
		this.difficulty = difficulty;
		// persist difficulty
		saveDifficulty();
	}

	public Set<DTOLanguage> getProficientLanguages() {
		loadProficientLanguages();
		return proficientLanguages;
	}

	public void addLanguage(DTOLanguage language) {
		proficientLanguages.add(language);
	}

	public void removeLanguage(DTOLanguage language) {
		proficientLanguages.remove(language);
	}

	public DTOLanguage getLearningLanguage() {
		loadLearningLanguage();
		return learningLanguage;
	}

	public void setLearningLanguage(DTOLanguage language) {
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
