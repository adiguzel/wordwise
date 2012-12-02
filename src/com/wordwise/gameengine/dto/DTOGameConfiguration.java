package com.wordwise.gameengine.dto;

import java.util.List;

public class DTOGameConfiguration {
	private int difficulty;
	private List<String> proficientLanguages;
	private String learningLanguage;

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public List<String> getProficientLanguage() {
		return proficientLanguages;
	}

	public void setProficientLanguage(List<String> proficientLanguages) {
		this.proficientLanguages = proficientLanguages;
	}

	public String getLearningLanguage() {
		return learningLanguage;
	}

	public void setLearningLanguage(String learningLanguage) {
		this.learningLanguage = learningLanguage;
	}

}
