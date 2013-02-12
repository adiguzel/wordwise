package com.wordwise.controller;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.wordwise.gameengine.GameConfiguration;
import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;
import com.wordwise.util.LanguageUtils;

/**
 * This singleton class handles all persisting and retaining preferences data to
 * the device.
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class PreferencesIOManager {
	private static PreferencesIOManager instance = null;
	// shared preferences for the app on the device
	private SharedPreferences SP;
	private Set<DTOLanguage> proficientLanguages = null;

	final String POINTS_PREFIX = "points_";
	final String nameKey = "name";
	final String proficientLangsKey = "proficient_languages";
	final String learningLangKey = "learning_language";
	final String difficultyKey = "difficulty";

	private PreferencesIOManager(Context context) {
		init(context);
	}

	private void init(Context context) {
		SP = PreferenceManager.getDefaultSharedPreferences(context);
		proficientLanguages = getProficientLanguages();
	}

	public static PreferencesIOManager getInstance(Context context) {
		if (instance == null) {
			instance = new PreferencesIOManager(context);
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

	/*
	 * loads the name of the user that was previously persisted on the device
	 */
	public String getName() {
		return SP.getString(nameKey, "");
	}

	/*
	 * loads the total points of the user that was previously persisted on the
	 * device
	 */
	public int getPoints() {
		String pointsQueryStr = POINTS_PREFIX + getLearningLanguage().getCode();
		return SP.getInt(pointsQueryStr, 0);
	}

	/*
	 * loads the difficulty of the current game cycle
	 */
	public DTODifficulty getDifficulty() {
		return DTODifficulty.getByDifficulty(SP.getInt(difficultyKey,
				DTODifficulty.EASY.getDifficulty()));
	}

	/*
	 * loads the language the user learns that was previously persisted on the
	 * device
	 */
	public DTOLanguage getLearningLanguage() {
		String langCode = SP.getString(learningLangKey, "");
		return LanguageUtils.getByCode(langCode);
	}

	/*
	 * loads the set of proficient languages that the user speaks that was previously persisted on the
	 * device
	 */
	public Set<DTOLanguage> getProficientLanguages() {
		Set<String> proficientLanguageCodes = SP.getStringSet(
				proficientLangsKey, new HashSet<String>());
		Log.v("", " profs code: " + proficientLanguageCodes);
		Set<DTOLanguage> proficientLanguages = new HashSet<DTOLanguage>();
		for (String langCode : proficientLanguageCodes) {
			proficientLanguages.add(LanguageUtils.getByCode(langCode));
		}
		return proficientLanguages;
	}

	////////PERSISTING OPERATIONS OF PREFERENCE DATA////////////
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
	////////END OF PERSISTING OPERATIONS OF PREFERENCE DATA////////////
	
	/*
	 * Checks whether initial configuration is completed
	 * */
	public boolean isConfigured() {
		return getLearningLanguage() != null
				&& !getProficientLanguages().isEmpty() && !getName().isEmpty();
	}

	public boolean finishInitialConfiguration() {
		return isConfigured();
	}
}
