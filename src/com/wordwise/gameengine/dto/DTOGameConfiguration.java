package com.wordwise.gameengine.dto;

public class DTOGameConfiguration {
	private int difficulty;
	private String proficientLanguage;
	private String learningLanguage;

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getProficientLanguage() {
		return proficientLanguage;
	}

	public void setProficientLanguage(String proficientLanguage) {
		this.proficientLanguage = proficientLanguage;
	}

	public String getLearningLanguage() {
		return learningLanguage;
	}

	public void setLearningLanguage(String learningLanguage) {
		this.learningLanguage = learningLanguage;
	}

}
