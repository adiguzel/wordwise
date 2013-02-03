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
	
	final String POINTS_PREFIX = "points_";
	final String nameKey = "name";
	final String proficientLangsKey = "proficient_languages";
	final String learningLangKey = "learning_language";
	final String difficultyKey = "difficulty";
	
	private Configuration(Context context) {
		init(context);
	}
	
	private void init(Context context){
		SP = PreferenceManager.getDefaultSharedPreferences(context) ;
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
	
	public String loadName() {
		return SP.getString(nameKey, "");
	}
	
	public int loadPoints() {
		String pointsQueryStr = POINTS_PREFIX + learningLanguage.getCode();
		return SP.getInt(pointsQueryStr, 0);
	}

	public DTODifficulty loadDifficulty() {
		return DTODifficulty.getByDifficulty(SP.getInt(difficultyKey, DTODifficulty.EASY.getDifficulty()));
	}

	public DTOLanguage loadLearningLanguage() {
		String langCode = SP.getString(learningLangKey, "");
		return LanguageUtils.getByCode(langCode);
	}

	public Set<DTOLanguage> loadProficientLanguages() {
		Set<String> proficientLanguageCodes = SP.getStringSet(proficientLangsKey, new HashSet<String>());
		Set<DTOLanguage> proficientLanguages = new HashSet<DTOLanguage> ();
		for(String langCode : proficientLanguageCodes){
			proficientLanguages.add(LanguageUtils.getByCode(langCode));
		}
		return proficientLanguages;
	}
	
	public boolean saveName(String name) {
		return SP.edit().putString(nameKey, name).commit();
	}

	public boolean saveDifficulty() {
		return SP.edit().putInt(difficultyKey, difficulty.getDifficulty()).commit();
	}

	public boolean saveLearningLanguage() {
		//do nothing if null
		if(learningLanguage == null)
			return false;
		return SP.edit().putString(learningLangKey, learningLanguage.getCode()).commit();
	}

	public boolean saveProficientLanguages() {
		Set<String> profLanguagesCodeSet = LanguageUtils.languageSetToCodeSet(proficientLanguages);
		return SP.edit().putStringSet(proficientLangsKey, profLanguagesCodeSet).commit();
	}
	
	public boolean savePoints(int points) {	
		String pointsQueryStr = POINTS_PREFIX + learningLanguage.getCode();
		return SP.edit().putInt(pointsQueryStr, points).commit();
	}
	
	public void setName(String name) {
		saveName(name);
	}
	
	public void setPoints(int points) {
		savePoints(points);
	}
	
	public void setDifficulty(DTODifficulty difficulty) {
		this.difficulty = difficulty;
		// persist difficulty
		saveDifficulty();
	}
	
	public String getName() {
		return loadName();
	}
		
	public int getPoints() {
		return loadPoints();
	}
	
	public DTODifficulty getDifficulty() {
		return loadDifficulty();
	}

	public Set<DTOLanguage> getProficientLanguages() {
		return loadProficientLanguages();
	}

	public void addLanguage(DTOLanguage language) {
		proficientLanguages.add(language);
	}

	public void removeLanguage(DTOLanguage language) {
		proficientLanguages.remove(language);
	}

	public DTOLanguage getLearningLanguage() {
		return loadLearningLanguage();
	}

	public void setLearningLanguage(DTOLanguage language) {
		this.learningLanguage = language;
	}

	public boolean isConfigured() {
		return learningLanguage != null && !proficientLanguages.isEmpty() && !getName().isEmpty();
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
