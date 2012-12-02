package com.wordwise.model;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
	private static int difficulty;
	private static List<String> proficientLanguages = new ArrayList<String>();
	private static String learningLanguage;

	public static int getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(int difficulty) {
		Configuration.difficulty = difficulty;
	}

	public static List<String> getProficientLanguages() {
		return proficientLanguages;
	}

	public static void addLanguage(String language) {
		if (!proficientLanguages.contains(language))
			proficientLanguages.add(language);
	}

	public static void removeLanguage(String language) {
		if (proficientLanguages.contains(language))
			proficientLanguages.remove(language);
	}

	public static String getLearningLanguage() {
		return learningLanguage;
	}

	public static void setLearningLanguage(String learningLanguage) {
		Configuration.learningLanguage = learningLanguage;
	}

}
