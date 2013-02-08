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

public class Configuration {
	private static Configuration instance = null;
	private SharedPreferences SP;
	private Set<DTOLanguage> proficientLanguages = null;
	
	final String POINTS_PREFIX = "points_";
	final String nameKey = "name";
	final String proficientLangsKey = "proficient_languages";
	final String learningLangKey = "learning_language";
	final String difficultyKey = "difficulty";

	private Configuration(Context context) {
		init(context);
	}

	private void init(Context context) {
		SP = PreferenceManager.getDefaultSharedPreferences(context);
		proficientLanguages = getProficientLanguages();
	}

	public static Configuration getInstance(Context context) {
		if (instance == null) {
			instance = new Configuration(context);
		}
		return instance;
	}

	public GameConfiguration getCurrentGameConfiguration() {
		GameConfiguration gameConf = new GameConfiguration();
		gameConf.setDifficulty(getDifficulty());
		gameConf.setLearningLanguage(getLearningLanguage());
		gameConf.setProficientLanguage(getProficientLanguages());
		return gameConf;
	}

	public String getName() {
		return SP.getString(nameKey, "");
	}

	public int getPoints() {
		String pointsQueryStr = POINTS_PREFIX + getLearningLanguage().getCode();
		return SP.getInt(pointsQueryStr, 0);
	}

	public DTODifficulty getDifficulty() {
		return DTODifficulty.getByDifficulty(SP.getInt(difficultyKey,
				DTODifficulty.EASY.getDifficulty()));
	}

	public DTOLanguage getLearningLanguage() {
		String langCode = SP.getString(learningLangKey, "");
		return LanguageUtils.getByCode(langCode);
	}

	public Set<DTOLanguage> getProficientLanguages() {
		Set<String> proficientLanguageCodes = SP.getStringSet(
				proficientLangsKey, new HashSet<String>());
		Set<DTOLanguage> proficientLanguages = new HashSet<DTOLanguage>();
		for (String langCode : proficientLanguageCodes) {
			proficientLanguages.add(LanguageUtils.getByCode(langCode));
		}
		return proficientLanguages;
	}

	public boolean setName(String name) {
		return SP.edit().putString(nameKey, name).commit();
	}

	public boolean setDifficulty(DTODifficulty difficulty) {
		return SP.edit().putInt(difficultyKey, difficulty.getDifficulty())
				.commit();
	}

	public boolean setLearningLanguage(DTOLanguage learningLanguage) {
		// do nothing if null
		if (learningLanguage == null)
			return false;
		return SP.edit().putString(learningLangKey, learningLanguage.getCode())
				.commit();
	}

	public boolean setProficientLanguages(Set<DTOLanguage> proficientLanguages) {
		Set<String> profLanguagesCodeSet = LanguageUtils
				.languageSetToCodeSet(proficientLanguages);
		return SP.edit().putStringSet(proficientLangsKey, profLanguagesCodeSet)
				.commit();
	}

	public boolean setPoints(int points) {
		String pointsQueryStr = POINTS_PREFIX + getLearningLanguage().getCode();
		return SP.edit().putInt(pointsQueryStr, points).commit();
	}

	public void addLanguage(DTOLanguage language) {
		proficientLanguages.add(language);
		setProficientLanguages(proficientLanguages);
	}

	public void removeLanguage(DTOLanguage language) {
		proficientLanguages.remove(language);
		setProficientLanguages(proficientLanguages);
	}

	public boolean isConfigured() {
		return getLearningLanguage() != null && !getProficientLanguages().isEmpty()
				&& !getName().isEmpty();
	}

	public boolean finishInitialConfiguration() {
		return isConfigured();
	}
}
