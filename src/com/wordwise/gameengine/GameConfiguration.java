package com.wordwise.gameengine;

import java.util.List;


public class GameConfiguration {
	private int difficulty;
	private List<Language> proficientLanguages;
	private Language learningLanguage;

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public List<Language> getProficientLanguage() {
		return proficientLanguages;
	}

	public void setProficientLanguage(List<Language> proficientLanguages) {
		this.proficientLanguages = proficientLanguages;
	}

	public Language getLearningLanguage() {
		return learningLanguage;
	}

	public void setLearningLanguage(Language learningLanguage) {
		this.learningLanguage = learningLanguage;
	}

}
