package com.wordwise.gameengine;

import java.util.Set;

import com.wordwise.server.model.Language;


public class GameConfiguration {
	private int difficulty;
	private Set<Language> proficientLanguages;
	private Language learningLanguage;

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public Set<Language> getProficientLanguage() {
		return proficientLanguages;
	}

	public void setProficientLanguage(Set<Language> proficientLanguages) {
		this.proficientLanguages = proficientLanguages;
	}

	public Language getLearningLanguage() {
		return learningLanguage;
	}

	public void setLearningLanguage(Language learningLanguage) {
		this.learningLanguage = learningLanguage;
	}

}
