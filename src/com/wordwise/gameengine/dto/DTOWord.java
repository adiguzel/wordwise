package com.wordwise.gameengine.dto;

import java.util.List;

public class DTOWord {
	private String wordEn;
	private int difficulty;
	private List<DTOTranslation> translations;

	public String getWordEn() {
		return wordEn;
	}

	public void setWordEn(String wordEn) {
		this.wordEn = wordEn;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public List<DTOTranslation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<DTOTranslation> translations) {
		this.translations = translations;
	}
}
