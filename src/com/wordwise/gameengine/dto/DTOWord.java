package com.wordwise.gameengine.dto;

import java.util.List;

public class DTOWord {
	//Represents the text of the word in English
	private String text;
	//Average difficulty of the word in a scale of 1-3
	private double difficulty;
	/*Average quality of the word, 
	//0 : bad quality, does not exist in reality
	//1 : good quality, existing word
	//-1: not specified*/
	private double quality;
	//list of translations for the English word
	private List<DTOTranslation> translations;
	
	public DTOWord(String text, int difficulty, int quality, List<DTOTranslation> translations ){
		this.text = text;
		this.difficulty = difficulty;
		this.quality = quality;
		this.translations = translations;
	}

	public double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}

	public List<DTOTranslation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<DTOTranslation> translations) {
		this.translations = translations;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}
}
