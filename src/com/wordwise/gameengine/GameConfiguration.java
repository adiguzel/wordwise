package com.wordwise.gameengine;

import java.util.Set;

import com.wordwise.server.dto.DTODifficulty;
import com.wordwise.server.dto.DTOLanguage;

/**
 * This interface defines the configuration information passed around to provide
 * the necessary information for the games to be initiated
 * 
 * @author Ugur Adiguzel, Dragan Mileski, Giovanni Maia
 * */
public class GameConfiguration {
	// difficulty of the game or game cycle
	private DTODifficulty difficulty;
	// set of proficient languages that the user speaks
	private Set<DTOLanguage> proficientLanguages;
	// the language the user wants to learn
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
