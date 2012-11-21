package com.wordwise.gameengine.dto;

public class DTOTranslation {
	private String word;
	private String lang;
	private String translation;
	
	public DTOTranslation(String word, String lang, String translation){
		this.word = word;
		this.lang = lang;
		this.translation = translation;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}
}
