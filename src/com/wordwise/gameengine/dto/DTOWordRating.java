package com.wordwise.gameengine.dto;

import java.util.List;

public class DTOWordRating {
	// Difficulty of the word in a scale of 1-3
	private int difficultyRating;
	/*
	 * Quality of the word, 0 : bad quality, does not exist in reality 1 : good
	 * quality, existing word -1: not specified
	 */
	private int qualityRating;
	// word that is being rated
	private List<DTOWord> word;

	public int getDifficultyRating() {
		return difficultyRating;
	}

	public void setDifficultyRating(int difficultyRating) {
		this.difficultyRating = difficultyRating;
	}

	public int getQualityRating() {
		return qualityRating;
	}

	public void setQualityRating(int qualityRating) {
		this.qualityRating = qualityRating;
	}

	public List<DTOWord> getWord() {
		return word;
	}

	public void setWord(List<DTOWord> word) {
		this.word = word;
	}
}
