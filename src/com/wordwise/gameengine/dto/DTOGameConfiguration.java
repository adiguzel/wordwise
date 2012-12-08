package com.wordwise.gameengine.dto;

import java.util.List;

import com.wordwise.gameengine.Language;

public class DTOGameConfiguration {
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
