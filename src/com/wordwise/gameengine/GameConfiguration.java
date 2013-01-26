package com.wordwise.gameengine;

import java.util.Set;

import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;


public class GameConfiguration {
	private DTODifficulty difficulty;
	private Set<DTOLanguage> proficientLanguages;
	private DTOLanguage learningLanguage;

	public DTODifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(DTODifficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Set<DTOLanguage> getProficientLanguage() {
		return proficientLanguages;
	}

	public void setProficientLanguage(Set<DTOLanguage> proficientLanguages) {
		this.proficientLanguages = proficientLanguages;
	}

	public DTOLanguage getLearningLanguage() {
		return learningLanguage;
	}

	public void setLearningLanguage(DTOLanguage learningLanguage) {
		this.learningLanguage = learningLanguage;
	}

}
